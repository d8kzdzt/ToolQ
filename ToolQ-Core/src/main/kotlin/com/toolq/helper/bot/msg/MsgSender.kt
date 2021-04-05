package com.toolq.helper.bot.msg

import com.toolq.BotConfig
import com.toolq.BotConfig.commonMsgMaxSize
import com.toolq.ToolQ
import com.toolq.helper.bot.BotRecorder
import com.toolq.helper.bot.BotStatus
import com.toolq.helper.bot.MessageBuilder
import com.toolq.helper.bot.msg.MsgHelper.Companion.MSG_C2C
import com.toolq.helper.bot.msg.MsgHelper.Companion.MSG_GRP
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.bot.tqcode.TQCode
import com.toolq.qq.protocol.highway.HighWay
import com.toolq.qq.protocol.protobuf.message.im_msg_body
import com.toolq.qq.protocol.protobuf.message.msg_svc
import com.toolq.utils.PacketUtil

@ExperimentalUnsignedTypes
class MsgSender(private val toolQ: ToolQ) {
    fun sendToFriend(uin: ULong, tqCode: TQCode) : Boolean {
        if (tqCode.size <= 0) return false
        val msgBuilder = tqCode as MessageBuilder
        val msgSeq = nextMsgSeq()
        val requestId = getRecorder().nextRequestId()
        val req = msg_svc.PbSendMsgReq()
        req.routing_head.setC2CUin(uin.toLong())
        req.content_head.set(1, 0, 0)

        val msgHelper = MsgHelper(tqCode, MSG_C2C, toolQ)
        req.msg_body.setRichText(msgHelper.toProtocolMsg(uin, msgBuilder.isReply(), msgBuilder.getReplyId()))
        // req.msg_body.setRichText(msgHelper.toProtocolMsg(uin, false, 0))

        req.msg_seq.set(msgSeq)
        req.msg_rand.set(java.util.Random().nextInt())
        if(getRecorder().syncCookie.isNotEmpty())
            req.sync_cookie.set(getRecorder().syncCookie)
        req.msg_via.set(1)
        val packet = req.toByteArray()
        if(packet.size > commonMsgMaxSize) {
            val highWay = HighWay(toolQ.account.user)
            val resId = highWay.groupLongMsg(uin.toLong(), req.msg_body.get())
            longMsgElem(msgHelper, req.msg_body, resId)
        }
        return sendPacket("MessageSvc.PbSendMsg", req.toByteArray(), requestId)
    }

    fun sendToGroup(groupId : ULong, tqCode: TQCode) : Boolean {
        if (tqCode.size <= 0) return false
        val msgBuilder = tqCode as MessageBuilder

        val msgSeq = nextMsgSeq()
        val requestId = getRecorder().nextRequestId()
        val req = msg_svc.PbSendMsgReq()
        req.routing_head.setGrpUin(groupId.toLong())
        req.content_head.set(1, 0, 0)

        val msgHelper = MsgHelper(tqCode, MSG_GRP, toolQ)
        req.msg_body.setRichText(msgHelper.toProtocolMsg(groupId, msgBuilder.isReply(), msgBuilder.getReplyId()))
        if(req.msg_body.rich_text.elems.size() <= 0 && !req.msg_body.rich_text.ptt.has()) {
            return false
        }
        req.msg_seq.set(msgSeq)
        req.msg_rand.set(java.util.Random().nextInt())
        if(getRecorder().syncCookie.isNotEmpty())
            req.sync_cookie.set(getRecorder().syncCookie)
        req.msg_via.set(1)
        val packet = req.toByteArray()
        if(packet.size > commonMsgMaxSize) {
            val highWay = HighWay(toolQ.account.user)
            val resId = highWay.groupLongMsg(groupId.toLong(), req.msg_body.get())
            longMsgElem(msgHelper, req.msg_body, resId)
        }
        return sendPacket("MessageSvc.PbSendMsg", req.toByteArray(), requestId)
    }

    private fun sendPacket(cmdName: String, body: ByteArray, seq : Int) : Boolean {
        return if(toolQ.botStatus != BotStatus.online) false
        else {
            val data = PacketUtil.makeOnlinePacket(cmdName, toolQ.botStatus, body, getKey(), seq, toolQ.account.user, true)
            send(data)
        }
    }

    private fun getKey(): ByteArray = toolQ.recorder.d2Key

    private fun send(data: ByteArray): Boolean {
        try {
            toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, data)
        } catch (e: Throwable) {
            return false
        }
        return true
    }

    private fun nextMsgSeq() : Int = getRecorder().nextMessageSeq()

    private fun getRecorder() : BotRecorder = toolQ.recorder

    private fun removeWaiter(id: Long) = toolQ.listener.removeWaiter(id)

    private fun addWaiter(waiter: Waiter): Waiter = toolQ.listener.addWaiter(waiter)

    private fun longMsgElem(msgHelper: MsgHelper, msgBody: im_msg_body.MsgBody, resId : String) {
        msgBody.rich_text.elems.clear()
        msgBody.rich_text.elems.add(msgHelper.xmlElem("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"35\" templateID=\"1\" action=\"viewMultiMsg\" brief=\"[长消息]来自QQ的分享\" m_resid=\"$resId\" m_fileName=\"6844889514862377669\" sourceMsgId=\"0\" url=\"\" flag=\"3\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"1\"><title size=\"26\" color=\"#777777\" maxLines=\"2\" lineSpace=\"12\">点击查看长消息...</title><hr hidden=\"false\" style=\"0\" /><summary>点击查看完整消息</summary></item><source name=\"聊天记录\" icon=\"\" action=\"\" appid=\"-1\" /></msg>", resId))
        msgBody.rich_text.elems.add(msgHelper.textElem("[长消息]请使用新版QQ查看。"))
        msgBody.rich_text.elems.add(msgHelper.flagElem(resId))
    }
}