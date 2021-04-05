package com.toolq.helper.bot.msg

import com.qq.pb.PBRepeatMessageField
import com.toolq.ToolQ
import com.toolq.helper.bot.BotRecorder
import com.toolq.helper.bot.OperationCenter
import com.toolq.helper.bot.tqcode.*
import com.toolq.helper.packet.ByteBuilder
import com.toolq.qq.protocol.highway.HighWay
import com.toolq.qq.protocol.protobuf.message.hummer_commelem
import com.toolq.qq.protocol.protobuf.message.im_msg_body
import com.toolq.qq.protocol.protobuf.message.msg_comm
import com.toolq.utils.*
import okhttp3.internal.wait
import kotlin.random.Random

@ExperimentalUnsignedTypes
class MsgHelper(private val tqCode: TQCode, private val msgType: Int, private val toolQ: ToolQ) {
    companion object {
        const val MSG_C2C = 0x18782
        const val MSG_GRP = 0x88abd

        // 是否发送提示性窗口抖动
        const val isTipShake = false

        @JvmStatic
        fun sendPicToTencent(toolQ: ToolQ, data: ByteArray) {
            sendPicToTencent(MSG_GRP, toolQ, data)
            sendPicToTencent(MSG_C2C, toolQ, data)
        }

        @JvmStatic
        fun sendPicToTencent(msgType: Int, toolQ: ToolQ, data : ByteArray) {
            toolQ.threadManager.addTask {
                val pics = arrayListOf(data)
                val highway = HighWay(toolQ.account.user)
                when(msgType) {
                    MSG_GRP -> highway.groupImg(toolQ.account.user, pics)
                    MSG_C2C -> highway.c2cImg(toolQ.account.user, pics)
                }
            }
        }
    }

    private var action = false

    fun toProtocolMsg(uin : ULong = 0u, isReply : Boolean = false, replyMsgId : Int = 0) : im_msg_body.RichText {
        val richText = im_msg_body.RichText().setHasFlag(true)
        val elems = arrayListOf<im_msg_body.Elem>()
        var actionMsg = arrayListOf<im_msg_body.Elem>()
        val ptt : TQPtt? = hasPtt()
        if(ptt != null) {
            richText.ptt.set(im_msg_body.Ptt().apply {
                this.bytes_pb_reserve.set(byteArrayOf())
                this.uint32_file_type.set(4)
                this.bytes_file_md5.set(ptt.md5.hexToBs())
                this.bytes_file_name.set("${ptt.md5}.amr")
                this.uint32_file_size.set(0)
                this.uint32_file_id.set(83821)
                this.bool_valid.set(true)
                when(msgType) {
                    MSG_C2C -> {
                        this.uint64_src_uin.set(uin.toLong())
                        this.bytes_file_uuid.set(byteArrayOf())
                        // this.bytes_group_file_key.set(null)
                    }
                    MSG_GRP -> {
                        this.uint64_src_uin.set(toolQ.account.user)
                        // this.bytes_group_file_key.set(null)
                    }
                }
                this.uint32_time.set(3)
                // 外部显示的语音发送时长
            })
        } else {
            // 添加回复Elem
            if(isReply && replyMsgId != 0) richText.elems.addAll(replyElem(replyId = replyMsgId, toUin = uin.toLong()))
            tqCode.forEach { value ->
                val elem = arrayListOf<im_msg_body.Elem>()
                createElem(value, elem, uin)
                if(action) { actionMsg = elem; return@forEach }
                else elems.addAll(elem)
            }
            richText.elems.addAll(if(action) actionMsg else elems)
        }
        richText.elems.add(flagElem())
        return richText
    }

    private fun createElem(value : TQValue, elem : ArrayList<im_msg_body.Elem>, groupUin: ULong = 0u) {
        val any : Any = when {
            value.isText() -> textElem(value.toText().text)
            value.isAt() && msgType != MSG_C2C -> value.toAt().let {
                atElem(it.uin.toLong(), it.hasSpace, msgType, toolQ.center, toolQ.recorder, groupUin)
            }
            value.isFace() -> faceElem(value.toFace().faceId)
            value.isCommonFace() -> commonFace(value.toCommonFace().index)
            value.isShake() -> shakeElem(toolQ.account.user)
            value.isFlashText() -> flashText(value.toFlashText())
            value.isImage() -> imageElem(toolQ, msgType, value.toImage().md5.hexToBs())
            value.isJson() -> jsonElem(value.toJson().format())
            value.isXml() -> xmlElem(value.toXml().context)
            value.isFlashImage() -> flashImage(toolQ, msgType, HexUtil.hexStringToBytes(value.toFlashImage().md5))
            value.isPoke() -> pokeElem(value.toPoke())
            value.isMFace() -> mfaceElem(value.toMFace())
            value.isDice() -> diceElem(value.toDice().size)
            value.isMora() -> moraElem(value.toMora().number)

            else -> IllegalAccessError("尚未支持发送该消息")
        }
        if(any is Array<*>) {
            any.forEach {
                elem.add(it as im_msg_body.Elem)
            }
        } else {
            elem.add(any as im_msg_body.Elem)
        }
    }

    private fun moraElem(number: Int): Array<im_msg_body.Elem> {
        return arrayOf(
            im_msg_body.Elem().apply {
                market_face.bytes_face_name.set("[猜拳]")
                market_face.uint32_item_type.set(6)
                market_face.uint32_face_info.set(1)
                market_face.bytes_face_id.set("83C8A293AE65CA140F348120A77448EE".hexToBs())
                market_face.uint32_tab_id.set(11415)
                market_face.uint32_sub_type.set(3)
                market_face.bytes_key.set("7de39febcf45e6db")
                market_face.uint32_media_type.set(0)
                market_face.uint32_image_width.set(200)
                market_face.uint32_image_height.set(200)
                market_face.bytes_mobileparam.set("rscType?1;value=$number")
                market_face.bytes_pb_reserve.set(im_msg_body.MarketFace.ResvAttr().apply {
                    uint32_emoji_type.set(1)
                }.toByteArray())
                market_face.setHasFlag(true)
            },
            textElem("[猜拳]")
        )
    }

    private fun diceElem(size: Int): Array<im_msg_body.Elem> {
        return arrayOf(
            im_msg_body.Elem().apply {
                market_face.bytes_face_name.set("[骰子]")
                market_face.uint32_item_type.set(6)
                market_face.uint32_face_info.set(1)
                market_face.bytes_face_id.set("4823D3ADB15DF08014CE5D6796B76EE1".hexToBs())
                market_face.uint32_tab_id.set(11464)
                market_face.uint32_sub_type.set(3)
                market_face.bytes_key.set("409e2a69b16918f9")
                market_face.uint32_media_type.set(0)
                market_face.uint32_image_width.set(200)
                market_face.uint32_image_height.set(200)
                market_face.bytes_mobileparam.set("rscType?1;value=${size-1}")
                market_face.bytes_pb_reserve.set(im_msg_body.MarketFace.ResvAttr().apply {
                    uint32_emoji_type.set(1)
                    str_back_color.set("#00000000")
                    str_volume_color.set("#00000000")
                }.toByteArray())
                market_face.setHasFlag(true)
            },
            textElem("[骰子]")
        )
    }

    private fun mfaceElem(face: TQMFace) : im_msg_body.Elem {
        val http = OkhttpUtil()
        http.defaultUserAgent()
        val res = http.get(face.url)
        if(res != null && res.code == 200) {
            val data = res.body!!.bytes()
            CacheUtil.saveUpImage(data)
            return imageElem(toolQ, msgType, data.toMd5Byte())
        }
        return im_msg_body.Elem()
    }

    private fun pokeElem(poke: TQPoke) : Array<im_msg_body.Elem> {
        val name = when(poke.id) {
            1 -> "戳一戳"
            2 -> "比心"
            3 -> "点赞"
            4 -> "心碎"
            5 -> "666"
            6 -> "放大招"
            126 -> {
                when(poke.extId.toInt()) {
                    2000 -> "敲门"
                    2001 -> "抓一下"
                    2002 -> "碎屏"
                    2003 -> "勾引"
                    2004 -> "手雷"
                    2005 -> "结印"
                    2006 -> "召唤术"
                    2008 -> "新年快乐"
                    2007 -> "玫瑰花"
                    2009 -> "让你皮"
                    2010 -> "嗨起来"
                    2011 -> "宝贝球"
                    else -> "Unknown"
                }
            }
            else -> "Unknown"
        }
        return arrayOf(
            im_msg_body.Elem().also {
                it.common_elem.setHasFlag(true)
                it.common_elem.uint32_service_type.set(2)
                it.common_elem.bytes_pb_elem.set(hummer_commelem.MsgElemInfo_servtype2().also { elem ->
                    elem.uint32_poke_type.set(poke.id)
                    elem.uint32_double_hit.set(0)
                    elem.uint32_vaspoke_id.set(poke.extId)
                    if(poke.id == 126) {
                        elem.bytes_vaspoke_name.set(name)
                        elem.bytes_vaspoke_minver.set("7.2.0")
                    }
                    elem.uint32_poke_strength.set(poke.size)
                    elem.uint32_poke_flag.set(0)
                }.toByteArray())
                it.common_elem.uint32_business_type.set(poke.id)
                this.action = true
            },
            textElem("[$name]请使用最新版手机QQ体验新功能。")
        )
    }

    private fun flashText(flashText: TQFlashText) = arrayOf(
        im_msg_body.Elem().also {
            it.common_elem.setHasFlag(true)
            it.common_elem.uint32_service_type.set(14)
            it.common_elem.bytes_pb_elem.set(hummer_commelem.MsgElemInfo_servtype14().also { elem ->
                elem.uint32_id.set(flashText.id)
                elem.text = flashText.txt
            }.toByteArray())
            this.action = true
        },
        textElem(flashText.txt)
    )

    private fun flashImage(toolQ: ToolQ, msgType: Int, md5 : ByteArray) = arrayOf(
        im_msg_body.Elem().also {
            it.common_elem.setHasFlag(true)
            it.common_elem.uint32_service_type.set(3)
            it.common_elem.bytes_pb_elem.set(hummer_commelem.MsgElemInfo_servtype3().also { elem ->
                when(msgType) {
                    MSG_C2C -> {
                        val notOnlineImage = im_msg_body.NotOnlineImage()
                        notOnlineImage.file_path.set("${md5.toHex()}.jpg")
                        notOnlineImage.file_len.set(0)
                        notOnlineImage.download_path.set("/${toolQ.account.user}-${RandomUtil.randInt(124723472, 185878567)}-${md5.toHex()}")
                        notOnlineImage.img_type.set(1003)
                        notOnlineImage.pic_md5.set(md5)
                        notOnlineImage.pic_height.set(1920)
                        notOnlineImage.pic_width.set(890)
                        notOnlineImage.res_id.set(notOnlineImage.download_path.get())
                        notOnlineImage.original.set(0)
                        notOnlineImage.biz_type.set(0)
                        notOnlineImage.uint32_show_len.set(0)
                        notOnlineImage.uint32_download_len.set(0)
                        val resv = im_msg_body.NotOnlineImage.ResvAttr()
                        resv.uint32_image_biz_type.set(0)
                        resv.uint32_customface_type.set(0)
                        resv.string_text.setHasFlag(true)
                        resv.uint32_emoji_from.set(0)
                        resv.uint32_source.set(6)
                        notOnlineImage.bytes_pb_reserve.set(resv.toByteArray())
                        elem.flash_c2c_pic.set(notOnlineImage)
                    }
                    MSG_GRP -> {
                        val custom = im_msg_body.CustomFace()
                        custom.str_file_path.set("${md5.toHex()}.jpg")
                        custom.uint32_file_id.set(0)
                        custom.uint32_server_ip.set(HighWay.getServer().toIpLong().toInt())
                        custom.uint32_server_port.set(HighWay.getPort())
                        custom.uint32_file_type.set(66)
                        custom.bytes_signature.set(RandomUtil.randomMd5())
                        custom.uint32_useful.set(1)
                        custom.bytes_md5.set(md5)
                        custom.biz_type.set(0)
                        custom.image_type.set(1003)
                        custom.uint32_height.set(1920)
                        custom.uint32_width.set(890)
                        custom.uint32_source.set(200)
                        custom.uint32_size.set(0)
                        custom.uint32_origin.set(0)
                        custom.uint32_show_len.set(0)
                        custom.uint32_download_len.set(0)
                        elem.flash_troop_pic.set(custom)
                    }
                }
            }.toByteArray())
            this.action = true
        },
        textElem("[闪照]请使用最新版手机QQ查看闪照。")
    )

    fun xmlElem(context: String, resId : String? = null) = im_msg_body.Elem().also {
        it.rich_msg.setHasFlag(true)
        it.rich_msg.xmlSrc = context.toByteArray()
        it.rich_msg.uint32_rand.set(Random.nextInt())
        it.rich_msg.uint32_service_id.set(XmlUtil.serviceId(context))
        resId?.let { id -> it.rich_msg.bytes_msg_resid.set(id) }
        this.action = true
    }

    private fun jsonElem(json : String) = im_msg_body.Elem().also {
        val jsonObject = JsonUtil.parseJsonObject(json)
        if(jsonObject.get("app").asString.contains("com.tencent.miniapp")) {
            it.common_elem.setHasFlag(true)
            it.common_elem.uint32_service_type.set(20)
            it.common_elem.uint32_business_type.set(1)
            it.common_elem.bytes_pb_elem.set(hummer_commelem.MsgElemInfo_servtype20().also { elem ->
                elem.data = json.toByteArray()
            }.toByteArray())
        } else {
            it.light_app.json = json
            it.light_app.setHasFlag(true)
        }
        this.action = true
    }

    private fun imageElem(toolQ: ToolQ, msgType: Int, md5 : ByteArray) = im_msg_body.Elem().also { elem ->
        toolQ.threadManager.addTask { CacheUtil.readUpImage(md5)?.let { sendPicToTencent(msgType, toolQ, it) } }
        when(msgType) {
            MSG_C2C -> {
                elem.not_online_image.setHasFlag(true)
                elem.not_online_image.file_path.set("${md5.toHex()}.jpg")
                elem.not_online_image.file_len.set(0)
                elem.not_online_image.download_path.set("/${toolQ.account.user}-${RandomUtil.randInt(124723472, 185878567)}-${md5.toHex()}")
                elem.not_online_image.img_type.set(1000)
                elem.not_online_image.pic_md5.set(md5)
                elem.not_online_image.pic_height.set(1920)
                elem.not_online_image.pic_width.set(890)
                elem.not_online_image.res_id.set(elem.not_online_image.download_path.get())
                elem.not_online_image.original.set(0)
                elem.not_online_image.biz_type.set(0)
                elem.not_online_image.uint32_show_len.set(0)
                elem.not_online_image.uint32_download_len.set(0)
            }
            MSG_GRP -> {
                elem.custom_face.str_file_path.set("${md5.toHex()}.jpg")
                elem.custom_face.uint32_file_id.set(Random.nextInt())
                elem.custom_face.uint32_server_ip.set(HighWay.getServer().toIpLong().toInt())
                elem.custom_face.uint32_server_port.set(HighWay.getPort())
                elem.custom_face.uint32_file_type.set(66)
                elem.custom_face.bytes_signature.set(RandomUtil.randomMd5())
                elem.custom_face.uint32_useful.set(1)
                elem.custom_face.bytes_md5.set(md5)
                elem.custom_face.biz_type.set(0)
                elem.custom_face.image_type.set(1003)
                elem.custom_face.uint32_height.set(1920)
                elem.custom_face.uint32_width.set(890)
                elem.custom_face.uint32_source.set(200)
                elem.custom_face.uint32_size.set(0)
                elem.custom_face.uint32_origin.set(0)
                elem.custom_face.uint32_show_len.set(0)
                elem.custom_face.uint32_download_len.set(0)
                elem.custom_face.setHasFlag(true)
            }
        }
    }

    private fun shakeElem(uin: Long) = im_msg_body.Elem().also {
        if(isTipShake) {
            it.common_elem.setHasFlag(true)
            it.common_elem.uint32_service_type.set(2)
            it.common_elem.uint32_business_type.set(1)
            it.common_elem.bytes_pb_elem.set(byteArrayOf())
        } else {
            it.shake_window.setHasFlag(true)
            it.shake_window.uint64_uin.set(uin)
            it.shake_window.uint32_type.set(0)
            it.shake_window.uint32_reserve.set(1)
        }
        this.action = true
    }

    private fun commonFace(index: Int) = im_msg_body.Elem().also {
        it.common_elem.setHasFlag(true)
        it.common_elem.uint32_service_type.set(33)
        it.common_elem.uint32_business_type.set(1)
        it.common_elem.bytes_pb_elem.set(hummer_commelem.MsgElemInfo_servtype33().also { eleminfoServtype33 ->
            eleminfoServtype33.uint32_index.set(index)
            val text = FaceUtil.check(index) ?: "[未知]"
            eleminfoServtype33.bytes_text.set(text)
            eleminfoServtype33.bytes_compat.set(text)
        }.toByteArray())
    }

    private fun faceElem(index : Int) = im_msg_body.Elem().also {
        it.face.index.set(index)
        it.face.setHasFlag(true)
    }

    fun textElem(string: String) = im_msg_body.Elem().also {
        it.text.str.set(string);
        it.text.setHasFlag(true)
    }

    private fun atElem(uin: Long, space: Boolean, msgType: Int, center: OperationCenter, botRecorder: BotRecorder, groupUin : ULong = 0u, strLength: Boolean = true) = im_msg_body.Elem().also {
        fun getNick() : String {
            val cacheNick = botRecorder.getNick(uin, groupUin.toLong())
            val nick = if (cacheNick != null) cacheNick.nick else {
                val info = center.getTroopMemberCardInfo(groupUin.toLong(), uin)
                if(info.card.isEmpty()) {
                    info?.nick ?: "unknown"
                } else info.card
            }
            if(nick.isNotEmpty() && nick != "unknown") botRecorder.setNick(uin, groupUin.toLong(), nick)
            return nick
        }
        val name : String = when {
            uin == 0L -> "全体成员"
            msgType == MSG_GRP -> getNick()
            else -> center.searchFriend(uin)!!.nick!!
        }
        it.text.str.set(
            "@$name" +
                    if (space) " " else ""
        )
        val bo = ByteBuilder()
        bo.writeShort(1) // start pos
        bo.writeInt(if(strLength) (name.length + 1) else name.toByteArray().size)
        // textLen
        bo.writeByte((if (uin == 0L) 1 else 0).toByte())
        // flag 0 艾特某人 1 艾特全体
        bo.writeInt(uin)
        bo.writeShort(0)
        it.text.attr_6_buf.set(bo.toByteArray())
        it.text.setHasFlag(true)
    }

    fun flagElem(resId: String? = null) = im_msg_body.Elem().also {
        it.general_flags.apply {
            this.uint64_pendant_id.set(0)
            this.bytes_pb_reserve.set(im_msg_body.GeneralFlags.ResvAttr().apply {
                this.uint32_mobile_custom_font.set(65536)
                this.uint32_diy_font_timestamp.set(0)
                this.uint64_subfont_id.set(0L)
            }.toByteArray())
            resId?.let {
                long_text_flag.set(1)
                long_text_resid.set(it)
                uint64_pendant_id.set(0)
                uint32_glamour_level.set(0)
            }
        }
        it.general_flags.setHasFlag(true)
    }

    private fun replyElem(replyId : Int, toUin : Long) : Array<im_msg_body.Elem> {
        val msg = when(msgType) {
            MSG_GRP -> CacheUtil.readTroopMsg(toUin, replyId)
            MSG_C2C -> CacheUtil.readFriendMsg(toUin, replyId)
            else -> throw IllegalAccessError("Other reply type is not access")
        }
        val replyUin = msg.msg_head.from_uin.get()
        return arrayOf(
            sourceElem(replyId, replyUin, msg),
            atElem(replyUin, false, msgType, toolQ.center, toolQ.recorder, toUin.toULong()),
            textElem(" ")
        )
    }

    private fun sourceElem(replyMsgId: Int, replyUin : Long, msg : msg_comm.Msg) = im_msg_body.Elem().apply {
        fun elemsToSReplyElem(elem: PBRepeatMessageField<im_msg_body.Elem>) : List<im_msg_body.Elem> {
            val ret = ArrayList<im_msg_body.Elem>()
            elem.get().forEach {
                when {
                    it.face.has() || it.custom_face.has() || it.market_face.has() || it.online_image.has() || it.text.has() ->
                        ret.add(it)
                    it.light_app.has() || it.rich_msg.has() ->
                        ret.add(textElem("[卡片消息]"))
                    it.shake_window.has() ->
                        ret.add(textElem("[窗口抖动]"))
                }
            }
            return ret
        }
        this.src_msg.run {
            this.uint32_orig_seqs.add(replyMsgId)
            val resv = im_msg_body.SourceMsg.ResvAttr()
            when(msgType) {
                MSG_GRP -> {
                    if(msg.msg_head.has()) {
                        this.uint64_sender_uin.set(replyUin)
                        this.uint32_time.set(msg.msg_head.msg_time.get())
                        resv.uint64_orig_uids.add(msg.msg_head.msg_uid.get())
                        this.elems.addAll(elemsToSReplyElem(msg.msg_body.rich_text.elems))
                    } else {
                        this.uint64_sender_uin.set(0)
                        this.uint32_time.set( System.currentTimeMillis() / 1000)
                        this.elems.add(im_msg_body.Elem().also {
                            it.text.setHasFlag(true)
                            it.text.str.set("Unknown")
                        })
                        resv.uint64_orig_uids.add(0)
                    }
                    this.uint64_to_uin.set(0)
                }
                MSG_C2C -> {
                    if(msg.msg_head.has()) {
                        this.uint64_sender_uin.set(replyUin)
                        this.uint64_sender_uin.set(msg.msg_head.from_uin.get())
                        this.uint32_time.set(msg.msg_head.msg_time.get())
                        resv.uint64_orig_uids.add(msg.msg_head.msg_uid.get())
                        this.elems.addAll(elemsToSReplyElem(msg.msg_body.rich_text.elems))
                    } else {
                        this.uint64_sender_uin.set(replyUin)
                        this.uint32_time.set( System.currentTimeMillis() / 1000)
                        this.elems.add(im_msg_body.Elem().also {
                            it.text.setHasFlag(true)
                            it.text.str.set("Unknown")
                        })
                        resv.uint64_orig_uids.add(0)
                    }
                    this.uint64_to_uin.set(toolQ.account.user)
                }
                else -> throw IllegalArgumentException()
            }
            this.uint32_flag.set(1)
            this.uint32_type.set(0)
            this.bytes_pb_reserve.set(resv.toByteArray())
            this.setHasFlag(true)
        }
    }

    private fun hasPtt() : TQPtt? {
        tqCode.forEach {
            if(it.isPtt()) {
                return it.toPtt()
            }
        }
        return null
    }
}

