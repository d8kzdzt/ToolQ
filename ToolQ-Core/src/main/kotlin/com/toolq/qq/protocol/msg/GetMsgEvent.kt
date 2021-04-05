package com.toolq.qq.protocol.msg

import com.toolq.ToolQ
import com.toolq.helper.bot.BotRecorder
import com.toolq.helper.bot.HandleEvent
import com.toolq.helper.packet.SeqWaiter
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.vector.SafeVector
import com.toolq.qq.protocol.protobuf.message.SyncFlag
import com.toolq.qq.protocol.protobuf.message.msg_comm
import com.toolq.qq.protocol.protobuf.message.msg_svc
import com.toolq.qq.protocol.protobuf.message.msg_svc.PbDeleteMsgReq.MsgItem
import com.toolq.utils.PacketUtil
import com.toolq.utils.asynAlso
import com.toolq.utils.forAsynEach
import java.util.concurrent.ConcurrentHashMap

class GetMsgEvent {
    @ExperimentalUnsignedTypes
    companion object {
        private val cacheC2CMsg : ConcurrentHashMap<Long, SafeVector<Long>> = ConcurrentHashMap()

        @JvmStatic
        fun getMsg (toolQ: ToolQ) {
            var ps = get(toolQ, SyncFlag.START, ArrayList(), 0)
            val peerUin = ArrayList<Long>()
            understand(toolQ, peerUin, ps.uin_pair_msgs.get(), toolQ.recorder)
            if(ps.sync_flag.get() == 2 || ps.uin_pair_msgs.isEmpty) {

                // get(toolQ, SyncFlag.STOP, peerUin, toolQ.recorder.last_read_time)
                return
            }
            // 不处理之前未处理的消息，选择放弃消息
            while (ps.result.get() == 0 && ps.sync_flag.get() == 0 or 1) {
                ps = get(toolQ, SyncFlag.CONTINUE, peerUin, toolQ.recorder.last_read_time)
                understand(toolQ, peerUin, ps.uin_pair_msgs.get(), toolQ.recorder)
                if(ps.sync_flag.get() == 2 || ps.sync_flag.get() == 3 || ps.uin_pair_msgs.isEmpty) {
                    // get(toolQ, SyncFlag.STOP, peerUin, toolQ.recorder.last_read_time)
                    break
                }
            }
            // get(toolQ, SyncFlag.FINISH, peerUin, toolQ.recorder.last_read_time)
        }

        @JvmStatic
        private fun understand(toolQ: ToolQ, peer_uin: ArrayList<Long>, source: MutableList<msg_comm.UinPairMsg>, recorder: BotRecorder) {
            val handleEvent = HandleEvent(toolQ.eventVector)
            source.filterNot { it.msg.isEmpty }
                .flatMap {
                    it.msg.get().filter { msg ->
                        recorder.last_read_time = it.last_read_time.get()
                        msg.msg_head.msg_time.get() >= it.last_read_time.get() && msg.msg_head.from_uin.get() != toolQ.account.user
                    }
                }.asynAlso(toolQ.threadManager) {
                    deleteMsg(toolQ, it!!.map { msg ->
                        val head = msg.msg_head
                        MsgItem().apply {
                            from_uin.set(head.from_uin)
                            msg_seq.set(head.msg_seq)
                            msg_type.set(head.msg_type)
                            msg_uid.set(head.msg_uid)
                            to_uin.set(head.to_uin)
                        }
                    })
                }!!.forAsynEach { msg ->
                    val head = msg.msg_head
                    if(isNotHasMsg(head.from_uin.get(), head.msg_uid.get())) {
                        val msgTime = head.msg_time.get()
                        // 多线程处理多条消息
                        handleEvent.handleC2CMsg(toolQ, msg)
                    }
                }
        }

        @JvmStatic
        fun deleteMsg(toolQ: ToolQ, msgItems: List<MsgItem>) {
            val req = msg_svc.PbDeleteMsgReq()
            req.msgItems.addAll(msgItems)
            val recorder = toolQ.recorder
            toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, PacketUtil.makeOnlinePacket("MessageSvc.PbDeleteMsg", toolQ.botStatus, req.toByteArray(), recorder.d2Key, recorder.nextSsoSeq(), toolQ.account.user, true))
        }

        // 防止重复消息
        private fun isNotHasMsg(uin : Long, uid : Long) : Boolean {
            if(cacheC2CMsg.containsKey(uin)) {
                val uids = cacheC2CMsg[uin]!!
                if(uids.contains(uid)) {
                    if(uids.size > Int.MAX_VALUE - 3600) {
                        // 可持续化方案
                        cacheC2CMsg[uin] = SafeVector()
                    }
                    return false
                } else {
                    uids.add(uid)
                }
            } else {
                cacheC2CMsg[uin] = SafeVector<Long>().apply {
                    add(uid)
                }
            }
            return true
        }

        @JvmStatic
        private fun get(toolQ: ToolQ, syncFlag: SyncFlag, peer_uin: ArrayList<Long>, last_read_time: Long) : msg_svc.PbGetMsgResp {
            val recorder = toolQ.recorder
            val seq = recorder.nextSsoSeq()
            val req = msg_svc.PbGetMsgReq(0, 0, 0)
            val syncCookie = recorder.syncCookie ?: byteArrayOf()
            when(syncFlag) {
                SyncFlag.START -> req.sync_flag.set(0)
                SyncFlag.CONTINUE -> req.sync_flag.set(1)
                SyncFlag.STOP -> return msg_svc.PbGetMsgResp(2)
                SyncFlag.FINISH -> return msg_svc.PbGetMsgResp(2)
            }
            req.sync_cookie.set(syncCookie)
            recorder.msgCtrlBuf.let {
                if(it.isNotEmpty())
                    req.msg_ctrl_buf.set(it)
            }
            recorder.pubAccCookie.let {
                if(it.isNotEmpty())
                    req.pub_account_cookie.set(it)
            }
            val data = PacketUtil.makeOnlinePacket("MessageSvc.PbGetMsg", toolQ.botStatus, req.toByteArray(), recorder.d2Key, seq, toolQ.account.user, true)
            return if (syncFlag == SyncFlag.STOP) {
                msg_svc.PbGetMsgResp(2)
            } else {
                val waiter: Waiter = toolQ.listener.addWaiter(
                    SeqWaiter(
                        seq,
                        "MessageSvc.PbGetMsg"
                    )
                )
                toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, data)
                if(waiter.wait("The world is mine.", 3 * 1000)) {
                    reportReaded(toolQ, syncCookie, peer_uin)
                    msg_svc.PbGetMsgResp().mergeFrom(waiter.getPacket().body)
                } else {
                    // 因为网络重新错误，终止消息接收
                    msg_svc.PbGetMsgResp(2)
                }
            } .let {
                if(it.sync_cookie.has() && !it.sync_cookie.isEmpty) {
                    when(it.msg_rsp_type.get()) {
                        0 -> {
                            recorder.syncCookie = it.sync_cookie.get().toByteArray()
                            recorder.pubAccCookie = it.pub_account_cookie.get().toByteArray()
                        }
                        1 -> recorder.syncCookie = it.sync_cookie.get().toByteArray()
                        2 -> recorder.pubAccCookie = it.pub_account_cookie.get().toByteArray()
                    }
                    recorder.msgCtrlBuf = it.msg_ctrl_buf.get().toByteArray()
                }
                it
            }
        }

        @JvmStatic
        private fun reportReaded(toolQ: ToolQ, syncCookie: ByteArray, peer_uin: ArrayList<Long>) {
            val recorder = toolQ.recorder
            // 报告消息已读消除红色泡泡
            val msgReadedReportReq = msg_svc.PbMsgReadedReportReq()
            val c2CReadedReportReq = msg_svc.PbC2CReadedReportReq()
            for (uin : Long in peer_uin) {
                val uni = msg_svc.PbC2CReadedReportReq.UinPairReadInfo()
                uni.peer_uin.set(uin)
                uni.last_read_time.set(System.currentTimeMillis() / 1000)
                c2CReadedReportReq.pair_info.add(uni)
            }
            c2CReadedReportReq.sync_cookie.set(syncCookie)
            msgReadedReportReq.c2c_read_report.set(c2CReadedReportReq)
            toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, PacketUtil.makeOnlinePacket("PbMessageSvc.PbMsgReadedReport", toolQ.botStatus, msgReadedReportReq.toByteArray(), recorder.d2Key, recorder.nextSsoSeq(), toolQ.account.user, true))
        }
    }
}

