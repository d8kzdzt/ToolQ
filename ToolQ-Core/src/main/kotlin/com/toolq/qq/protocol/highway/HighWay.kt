package com.toolq.qq.protocol.highway

import com.toolq.ToolQ
import com.toolq.ToolQManager
import com.toolq.helper.bot.packet.Packet
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.packet.SeqWaiter
import com.toolq.helper.socket.SocketClient
import com.toolq.qq.protocol.highway.utils.HighwayUtil.Companion.makeDataHighWayHead
import com.toolq.qq.protocol.highway.utils.HighwayUtil.Companion.makeReqDataHighwayHead
import com.toolq.qq.protocol.protobuf.LongMsg
import com.toolq.qq.protocol.protobuf.message.im_msg_body
import com.toolq.qq.protocol.protobuf.message.msg_comm
import com.toolq.qq.protocol.protobuf.oidb.cmd0x352
import com.toolq.qq.protocol.protobuf.oidb.cmd0x388
import com.toolq.utils.GZipUtil
import com.toolq.utils.*
import com.toolq.utils.PacketUtil
import kotlin.random.Random


@ExperimentalUnsignedTypes
class HighWay(private val uin : Long) {
    companion object {
        private var server = "112.60.8.41"
        private var port = 80

        fun getPort() : Int = port
        fun getServer(): String = server

        fun getClient(ip : String = getServer(), port : Int = getPort()) : SocketClient {
            return SocketClient().also {
                it.connect(ip, port)
                HighWay.server = ip
                HighWay.port = port
            }
        }
    }

    fun echo(client: SocketClient) {
        val toolQ = ToolQManager.getBot(uin) ?: throw RuntimeException("the highway of the bot could not be created")
        val recorder = recorder(toolQ)
        val head = makeDataHighWayHead(uin, "PicUp.Echo", recorder.nextHwSeq(), appid(toolQ), 4096, 0, 2052)
        client.clientOutPut?.sendHighWay(toolQ.threadManager, head.toByteArray(), byteArrayOf())
    }

    // 事实证明好友长消息可以和群聊长消息共用
    fun groupLongMsg(groupCode: Long, msgBody: im_msg_body.MsgBody): String {
        val toolQ = ToolQManager.getBot(uin) ?: throw RuntimeException("the highway of the bot could not be created")
        val recorder = recorder(toolQ)
        val space = LongMsg.MsgSpace().also {
            val msg = msg_comm.Msg().also { msg ->
                // Initialize Head
                msg.msg_head.setHasFlag(true)
                msg.msg_head.from_uin.set(uin)
                msg.msg_head.msg_type.set(82)
                msg.msg_head.msg_seq.set(recorder.nextMessageSeq().toLong())
                msg.msg_head.msg_time.set(Math.toIntExact(System.currentTimeMillis() / 1000).toLong())
                msg.msg_head.msg_uid.set(Random.nextLong())
                val gInfo = msg_comm.GroupInfo()
                gInfo.group_code.set(groupCode)
                gInfo.group_card.set("ToolQ")
                msg.msg_head.group_info.set(gInfo)
                val mHead = msg_comm.MutilTransHead()
                mHead.status.set(0)
                mHead.msgId.set(1)
                msg.msg_head.mutiltrans_head.set(mHead)
                msg.msg_body.set(msgBody)
            }
            it.msg.set(msg)
        }
        val reqLongMsg = LongMsg.ReqBody()
        reqLongMsg.uint32_subcmd.set(1)
        reqLongMsg.uint32_term_type.set(5)
        reqLongMsg.uint32_platform_type.set(9)
        reqLongMsg.uint32_busi_type.set(1)
        val upMsgReq = LongMsg.MsgUpReq()
        upMsgReq.uint64_dst_uin.set(groupCode)
        upMsgReq.uint32_msg_type.set(3)
        upMsgReq.uint32_store_type.set(2)
        upMsgReq.bytes_msg_content.set(GZipUtil.compress(space.toByteArray()))
        reqLongMsg.rpt_msg_up_req.add(upMsgReq)
        val data = reqLongMsg.toByteArray()
        val head = makeReqDataHighwayHead(data.size.toLong(),
            recorder.getuKey(),
            data.toMd5Byte(),
            uin,
            "PicUp.DataUp",
            recorder.nextHwSeq(),
            appid(toolQ),
            4096,
            77,
            2052)
        val client = getClient()
        client.clientOutPut?.sendHighWay(toolQ.threadManager, head.toByteArray(), data)
        val result: ByteArray = client.clientInPut!!.readHighWay()!![0]!!
        val r = LongMsg.MsgResId().parse(result)
        return r.resId.id.get().toStringUtf8()
    }

    fun groupImg(toUin : Long, pics : List<ByteArray> = ArrayList()) {
        val imgs : List<ByteArray> = pics.filter { it.size <= 20 * 1024 * 1024 }
        val md5s = imgs.map { it.toMd5Byte() }
        if(imgs.isNotEmpty()) return groupImg(toUin, imgs, md5s)
    }

    // 上传群聊图片
    private fun groupImg(toUin : Long, pics : List<ByteArray> = ArrayList(), md5s : List<ByteArray> = ArrayList()) {
        if(pics.size > 20) throw java.lang.RuntimeException("Exceed the one-time upload limit!")
        val toolQ = ToolQManager.getBot(uin) ?: throw RuntimeException("the highway of the bot could not be created")
        val recorder = recorder(toolQ)
        val req0x388 = cmd0x388.ReqBody()
        req0x388.uint32_net_type.set(3)
        req0x388.uint32_subcmd.set(1)
        for ((index, src : ByteArray) in pics.withIndex()) {
            val tryUpReq = cmd0x388.TryUpImgReq()
            tryUpReq.uint64_group_code.set(toUin)
            tryUpReq.uint64_src_uin.set(uin)
            tryUpReq.uint64_file_id.set(0)
            tryUpReq.bytes_file_md5.set(md5s[index])
            tryUpReq.uint64_file_size.set(src.size.toLong())
            tryUpReq.bytes_file_name.set("${md5s[index].toHex()}.jpg")
            tryUpReq.uint32_src_term.set(5)
            tryUpReq.uint32_platform_type.set(9)
            tryUpReq.uint32_bu_type.set(1)
            tryUpReq.uint32_pic_height.set(1920)
            tryUpReq.uint32_pic_width.set(890)
            tryUpReq.uint32_pic_type.set(1003)
            tryUpReq.bytes_build_ver.set(toolQ.androidQQ.packageVersion())
            tryUpReq.uint32_app_pic_type.set(1052)
            tryUpReq.uint32_srv_upload.set(0)
            req0x388.rpt_msg_tryup_img_req.add(tryUpReq)
        }
        val seq = recorder.nextSsoSeq()
        val waiter = toolQ.listener.addWaiter(SeqWaiter(seq, "ImgStore.GroupPicUp"))
        send(toolQ, PacketUtil.makeOnlinePacket("ImgStore.GroupPicUp", toolQ.botStatus, req0x388.toByteArray(), recorder.d2Key, seq, uin, true))
        if(waiter.wait("The world is so big, why not take a look?", 3 * 1000) ) {
            val resp = cmd0x388.RspBody().parse(waiter.packet.body)
            val rsvps : List<cmd0x388.TryUpImgRsp> = resp.rpt_msg_tryup_img_rsp.get()
            for((index, rsp : cmd0x388.TryUpImgRsp) in rsvps.withIndex()) {
                if( !(rsp.bool_file_exit.has() && rsp.bool_file_exit.get()) ) {
                    val src = pics[index]
                    val md5 = md5s[index]
                    var ip : String = rsp.rpt_uint32_up_ip.get(rsp.rpt_uint32_up_ip.size() - 1) toIp "112.60.8.142"
                    printlnVar(rsp.rpt_uint32_up_ip.get(rsp.rpt_uint32_up_ip.size() - 1))
                    var port = rsp.rpt_uint32_up_port.get(rsp.rpt_uint32_up_ip.size() - 1)
                    if(ip.contains("0.0")) {
                        ip = getServer()
                        port = getPort()
                    }
                    val head = makeReqDataHighwayHead(src.size.toLong(), rsp.bytes_up_ukey.get().toByteArray(), md5, uin, "PicUp.DataUp", recorder.nextHwSeq(), appid(toolQ), 4096, 2, 2052)
                    val client = getClient(ip, port)
                    client.clientOutPut?.sendHighWay(toolQ.threadManager, head.toByteArray(), src)
                }
            }
        }
    }

    // 上传私聊图片
    fun c2cImg(toUin : Long, pics : List<ByteArray> = ArrayList()) {
        val imgs : List<ByteArray> = pics.filter { it.size <= 20 * 1024 * 1024 }
        val md5s = imgs.map { it.toMd5Byte() }
        if(imgs.isNotEmpty()) return c2cImg(toUin, imgs, md5s)
    }

    private fun c2cImg(toUin : Long, pics : List<ByteArray> = ArrayList(), md5s : List<ByteArray> = ArrayList()) {
        if(pics.size > 20) throw java.lang.RuntimeException("Exceed the one-time upload limit!")
        val toolQ = ToolQManager.getBot(uin) ?: throw RuntimeException("the highway of the bot could not be created")
        val recorder = recorder(toolQ)
        val req0x352 = cmd0x352.ReqBody()
        req0x352.uint32_subcmd.set(1)
        req0x352.uint32_net_type.set(3)
        for ((index, src : ByteArray) in pics.withIndex()) {
            val tryUpReq = cmd0x352.TryUpImgReq()
            tryUpReq.uint64_src_uin.set(toolQ.account.user)
            tryUpReq.uint64_dst_uin.set(toUin)
            tryUpReq.uint64_file_id.set(0)
            tryUpReq.bytes_file_md5.set(md5s[index])
            tryUpReq.uint64_file_size.set(src.size.toLong())
            tryUpReq.bytes_file_name.set("${md5s[index].toHex()}.jpg")
            tryUpReq.uint32_src_term.set(5)
            tryUpReq.uint32_platform_type.set(9)
            tryUpReq.bool_address_book.set(false)
            tryUpReq.uint32_bu_type.set(1)
            tryUpReq.bool_pic_original.set(false)
            tryUpReq.uint32_pic_height.set(1920)
            tryUpReq.uint32_pic_width.set(890)
            tryUpReq.uint32_pic_type.set(1003)
            tryUpReq.bytes_build_ver.set(toolQ.androidQQ.packageVersion())
            tryUpReq.bool_reject_tryfast.set(false)
            tryUpReq.uint32_srv_upload.set(0)
            // ===========================================
            // ===========================================
            req0x352.rpt_msg_tryup_img_req.add(tryUpReq)
        }
        val seq = recorder.nextSsoSeq()
        val waiter = toolQ.listener.addWaiter(object : Waiter("LongConn.OffPicUp") {
            override fun check(packet: Packet?): Boolean = seq == packet!!.ssoSeq
        })
        send(toolQ, PacketUtil.makeOnlinePacket("LongConn.OffPicUp", toolQ.botStatus, req0x352.toByteArray(), recorder.d2Key, seq, uin, true))
        if(waiter.wait("The world is so big, why not take a look?", 3 * 1000)) {
            val resp = cmd0x352.RspBody().parse(waiter.packet.body)
            val rsvps : List<cmd0x352.TryUpImgRsp> = resp.rpt_msg_tryup_img_rsp.get()
            for((index, rsp : cmd0x352.TryUpImgRsp) in rsvps.withIndex()) {
                if(!(rsp.bool_file_exit.has() && rsp.bool_file_exit.get())) {
                    val src = pics[index]
                    val md5 = md5s[index]
                    var ip : String = rsp.rpt_uint32_up_ip.get(0) toIp "112.60.8.142"
                    var port = rsp.rpt_uint32_up_port.get(0)
                    if(ip.contains("0.0")) {
                        ip = getServer()
                        port = getPort()
                    }
                    val head = makeReqDataHighwayHead(src.size.toLong(), rsp.bytes_up_ukey.get().toByteArray(), md5, uin, "PicUp.DataUp", recorder.nextHwSeq(), appid(toolQ), 4096, 1, 2052)
                    val client = getClient(ip, port)
                    client.clientOutPut?.sendHighWay(toolQ.threadManager, head.toByteArray(), src)
                }
            }

        }
    }

    private fun recorder(toolQ: ToolQ) = toolQ.recorder

    private fun appid(toolQ: ToolQ) : Long = toolQ.androidQQ.appId()

    private fun send(toolQ: ToolQ, body : ByteArray) = toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, body)

}

