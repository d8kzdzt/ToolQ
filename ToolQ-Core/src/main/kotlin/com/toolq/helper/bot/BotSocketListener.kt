package com.toolq.helper.bot

import com.toolq.BotConfig
import com.toolq.ToolQ
import com.toolq.helper.bot.packet.Packet
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.jce.JceHelper
import com.toolq.helper.logger.TLog
import com.toolq.helper.packet.ByteReader
import com.toolq.helper.socket.SocketListener
import com.toolq.helper.vector.SafeVector
import com.toolq.qq.protocol.jce.pushPack.SvcReqPushMsg
import com.toolq.qq.protocol.jce.statsvc.RequestPushForceOffline
import com.toolq.qq.protocol.msg.GetMsgEvent.Companion.getMsg
import com.toolq.qq.protocol.protobuf.onlinepush.OnlinePushTrans
import com.toolq.qq.protocol.protobuf.onlinepush.msg_onlinepush.PbPushMsg
import com.toolq.utils.*

/**
 * @author luoluo
 * @date 2020/Tlv0/2 18:27
 */
@ExperimentalUnsignedTypes
class BotSocketListener(private val toolQ: ToolQ, private val recorder: BotRecorder) : SocketListener {
    // 接包等待队列
    private val waiterList = SafeVector<Waiter>()
    // 已的接包器队列
    private val ignorePushList = SafeVector<Long>()
    // 已接收的广播队列
    private val ignoreTransList = HashMap<Long, SafeVector<Long>>()


    // 已接收广播队列
    private val ignoreTroopPushMsg = HashMap<Long, SafeVector<Long>>()
    // 已接收广播队列
    private val ignoreFriendPushMsg = HashMap<Long, SafeVector<Long>>()

    override fun onHighWayReceive(head: ByteArray?, data: ByteArray?) {}

    override fun onReceive(body: ByteArray?) {
        fastCatch<Exception> {
            toolQ.threadManager.addTask {
                body?.let {
                    receiveEvent(it)
                }
            }
        }?.let {
            throw RuntimeException(("Msg：${it.message}" +
                    "\nAt:${HexUtil.bytesToHexString(body)} " +
                    "\nKey：") + HexUtil.bytesToHexString(toolQ.recorder.d2Key)
            )
        }
    }

    private fun receiveEvent(body: ByteArray) {
        var reader = ByteReader(body)
        if (reader.readInt() == reader.length()) {
            val keyType = reader.readInt()
            val packetType = reader.readByte()
            reader.readByte()
            if (packetType.toInt() == 0) return
            val key = recorder.d2Key
            val user = reader.readStringByInt(4)
            reader = ByteReader(TeaUtil.decrypt(reader.readRestBytes(), key))
            val headReader = reader.readReader(reader.readInt() - 4)
            var bodyReader = reader.readReader(reader.readInt() - 4)
            val msfSsoSeq = headReader.readInt()
            if (headReader.readInt() != 0) {
                val token = headReader.readBytesByInt(4)
            } else {
                headReader.dis(4)
                // Int
            }
            val cmd = headReader.readStringByInt(4)
            val randomSession = headReader.readBytesByInt(4)
            if(!bodyReader.isEmpty) {
                when (headReader.readInt()) {
                    0, 4 -> {

                    }
                    1 -> bodyReader = ByteReader(ZipUtil.uncompress(bodyReader.readRestBytes()))
                    else -> TLog.warn("Can't parse this packet, because encodeType is unknown.")
                }
            }
            val packet = bodyReader.readRestBytes()
            TLog.info(String.format("Receive a packet by %s, packetCmd: %s, RealLength：%s", user, cmd, packet.size))
            if (packet.size > 1024 * 100) return
            // 大于20kb的包不处理，这些包多半不正常

            val handleEvent = HandleEvent(toolQ.eventVector)
            // 消息处理器列表
            if ("MessageSvc.PushNotify" == cmd && BotConfig.isAllowFriendMessage()) {
                getMsg(toolQ)
            }
            else if ("MessageSvc.PbSendMsg" == cmd) {
                handleEvent.handleMsgSentEvent(packet)
            }
            else if ("OnlinePush.ReqPush" == cmd) {
                val pushMsg = JceHelper.decodePacket(packet.withSize(4), "req", SvcReqPushMsg())
                val msgTime = pushMsg.uMsgTime
                if(!pushMsg.vMsgInfos.isNullOrEmpty())
                    handleEvent.handlePushMsg(ignoreTroopPushMsg, ignoreFriendPushMsg, msgTime, pushMsg.vMsgInfos)
            }
            else if ("OnlinePush.PbPushGroupMsg" == cmd) {
                val pushMsg = PbPushMsg().parse(packet)
                if (pushMsg.msg.has()) {
                    handleEvent.handleTroopMsg(toolQ.account.user, pushMsg.msg.get())
                }
            }
            else if ("MessageSvc.PushForceOffline" == cmd && toolQ.botStatus == BotStatus.online) {
                val offLine =
                    JceHelper.decodePacket(packet.withSize(4), "req_PushForceOffline", RequestPushForceOffline());
                ToolQ.setBotStatus(toolQ, BotStatus.remoteLanding)
                handleEvent.handleDropEvent(offLine)
            }
            else if ("OnlinePush.PbPushTransMsg" == cmd) {
                fun hasReady(from : Long, id : Long) : Boolean {
                    return if(ignoreTransList.containsKey(from)) {
                        val list = ignoreTransList[from]!!
                        if(list.contains(id)) {
                            true
                        } else {
                            list.add(id)
                            false
                        }
                    } else {
                        ignoreTransList[from] = SafeVector<Long>().apply {
                            add(id)
                        }
                        false
                    }
                }
                val push = OnlinePushTrans.PbMsgInfo().parse(packet)
                if(!hasReady(push.from_uin.get(), push.msg_seq.get())) {
                    when (push.msg_type.get()) {
                        34 -> {
                            handleEvent.handleMemberDecr(push)
                        }
                        44 -> {
                            handleEvent.handleAdminChange(push)
                        }
                        else -> println("Unknown push text $push")
                    }
                }
            }
            else {
                // if(cmd == "MessageSvc.PbGetMsg")
                // if (packet.size > 1024 * 100) return
                handleWaiter(Packet(cmd, packet, msfSsoSeq))
            } // when
        }
    }

    fun addWaiter(waiter: Waiter): Waiter {
        waiterList.addElement(waiter)
        return waiter
    }

    /**
     * 销毁
     */
    fun removeWaiter(id: Long) {
        ignorePushList.add(id)
    }

    private fun handleWaiter(packet: Packet) {
        var i = 0
        val waiterListSize = waiterList.size
        while (i < waiterListSize) {
            val waiter = waiterList[i]
            if (ignorePushList.contains(waiter.id)) {
                ignorePushList.removeElement(waiter.id)
                waiterList.removeElement(waiter)
            } else if (waiter.cmd == packet.cmd && waiter.check(packet)) {
                if (!waiter.isUsed) {
                    waiter.setPacket(packet)
                    waiter.isUsed = true
                    waiter.push()
                }
                waiterList.removeElement(waiter)
                break
            }
            i++
        }
    }
}