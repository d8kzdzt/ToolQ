package com.toolq.helper.packet

import com.qq.pb.MessageMicro
import com.qq.taf.Jce
import com.toolq.ToolQ
import com.toolq.helper.bot.BotStatus
import com.toolq.helper.bot.OperationCenter
import com.toolq.helper.bot.packet.Packet
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.jce.JceHelper
import com.toolq.helper.logger.TLog
import com.toolq.utils.PacketUtil
import com.toolq.BotConfig

@ExperimentalUnsignedTypes
abstract class OperationCenterV2(private val toolQ: ToolQ) : OperationCenter {
    fun <T : MessageMicro<T>?> usePbByWaiter(waiter: Waiter, pb : MessageMicro<T>, block : T.() -> Any?) : Any? {
        val resp = pb.parse(waiter.packet.body)
        return if (resp == null) null else block(resp)
    }

    fun <T : MessageMicro<T>?> sendPbAndWait(command : String, pb : MessageMicro<T>, inter : T.() -> Unit) = sendPbAndWait(command, BotConfig.defaultPacketWaitTime, pb, inter)

    fun <T : MessageMicro<T>?> sendPbAndWait(command : String, timeOut : Long, pb : MessageMicro<T>, inter : T.() -> Unit) : Waiter {
        inter(pb.get())
        return sendAndWait(command, pb.get()!!.toByteArray(), timeOut, true)
    }

    fun <T> useJceByWaiter(waiter: Waiter?, resp : Jce<T>, packetParser: (T) -> Any?) : Any? {
        if(waiter == null) return null
        val rsp = JceHelper.decodePacket(waiter.packet.bodyWithLength, JceRes[waiter.cmd]!!.inName, resp)
        return if(rsp == null) null else packetParser(rsp.self)
    }

    fun <T> sendJceAndWait(command: String, req: Jce<T>, inter : JceInter<T>.() -> Unit) : Waiter {
        val couple = JceRes[command]!!
        val requestId = toolQ.recorder.nextRequestId()
        inter( JceInter(req.self, requestId) )
        return sendAndWait(command, JceHelper.encodePacket(req, couple.servantName, couple.funcName, couple.outName, requestId), BotConfig.defaultPacketWaitTime, false)
    }

    fun <T> sendJceAndWait(command: String, timeOut: Long, req: Jce<T>, inter : JceInter<T>.() -> Unit) : Waiter {
        val couple = JceRes[command]!!
        val requestId = toolQ.recorder.nextRequestId()
        inter( JceInter(req.self, requestId) )
        return sendAndWait(command, JceHelper.encodePacket(req, couple.servantName, couple.funcName, couple.outName, requestId), timeOut, false)
    }

    fun sendJceAndWait(command: String, timeOut: Long, req: Array<Jce<*>>, outName: Array<String>, inter : JceInter<Array<Jce<*>>>.() -> Unit) : Waiter {
        val couple = JceRes[command]!!
        val requestId = toolQ.recorder.nextRequestId()
        inter( JceInter(req, requestId) )
        return sendAndWait(command, JceHelper.encodePacket(req, couple.servantName, couple.funcName, outName, requestId), timeOut, false)
    }

    private fun sendAndWait(command : String, body : ByteArray, timeOut : Long = BotConfig.defaultPacketWaitTime, needSize : Boolean) : Waiter {
        val seq = toolQ.recorder.nextSsoSeq()
        val data = PacketUtil.makeOnlinePacket(command, getBotStatus(), body, getKey(), seq, toolQ.account.user, needSize)
        val waiter = addWaiter(SeqWaiter(seq, command))
        if( !(send(data) && waiter.wait("fastPacketSender", timeOut))) {
            TLog.error("Packet[$command][$seq] send failed!")
        }
        return waiter
    }

    private fun getBotStatus(): BotStatus = toolQ.botStatus

    private fun getKey(): ByteArray = toolQ.recorder.d2Key

    private fun send(data: ByteArray): Boolean {
        try {
            toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, data)
        } catch (e: Throwable) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun removeWaiter(id: Long) = toolQ.listener.removeWaiter(id)

    private fun addWaiter(waiter: Waiter): Waiter = toolQ.listener.addWaiter(waiter)
}

class SeqWaiter(private val seq: Int, cmd: String?) : Waiter(cmd) {
    override fun check(packet: Packet): Boolean = seq == packet.ssoSeq
}

class JceInter<T>(private val self : T, val requestId : Int, val it : T = self, val req: T = self)

const val FriendListServant = "mqq.IMService.FriendListServiceServantObj"

private val JceRes = HashMap<String, JceCouple>().also {
    it["friendlist.GetMultiTroopInfoReq"] = JceCouple(FriendListServant, "GetMultiTroopInfoReq", "GMTIREQ", "GMTIRESP")
    it["friendlist.GetTroopListReqV2"] = JceCouple(FriendListServant, "GetTroopListReqV2Simplify", "GetTroopListReqV2Simplify", "GetTroopListRespV2")
    it["friendlist.getFriendGroupList"] = JceCouple("", "", "", "")
    it["VisitorSvc.ReqFavorite"] = JceCouple("VisitorSvc", "ReqFavorite", "ReqFavorite", "")
    it["friendlist.getUserAddFriendSetting"] = JceCouple(FriendListServant, "GetUserAddFriendSettingReq", "FS", "FSRESP")
    it["friendlist.GetAutoInfoReq"] = JceCouple(FriendListServant, "GetAutoInfoReq", "GAIREQ", "GAIRESP")
    it["friendlist.ModifyGroupCardReq"] = JceCouple(FriendListServant, "ModifyGroupCardReq", "MGCREQ", "MGCRESP")
    it["friendlist.getTroopMemberList"] = JceCouple(FriendListServant, "GetTroopMemberListReq", "GTML", "GTMLRESP")
    it["friendlist.GetTroopAppointRemarkReq"] = JceCouple(FriendListServant, "GetTroopAppointRemarkReq", "GTA", "GTARESP")
    it["SummaryCard.ReqSummaryCard"] = JceCouple("SummaryCardServantObj", "ReqSummaryCard", "", "")
    it["ProfileService.GroupMngReq"] = JceCouple("KQQ.ProfileService.ProfileServantObj", "GroupMngReq", "GroupMngReq", "GroupMngRes")
}

data class JceCouple (
    val servantName : String,
    val funcName : String,
    val outName : String,
    val inName : String
)

