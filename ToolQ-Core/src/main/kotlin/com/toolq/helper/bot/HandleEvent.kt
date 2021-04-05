package com.toolq.helper.bot

import com.qq.taf.jce.JceInputStream
import com.toolq.BotConfig
import com.toolq.ToolQ
import com.toolq.helper.bot.tqcode.*
import com.toolq.helper.logger.TLog
import com.toolq.helper.packet.ByteReader
import com.toolq.helper.thread.ThreadManager
import com.toolq.helper.vector.SafeVector
import com.toolq.qq.protocol.jce.MsgType0x210
import com.toolq.qq.protocol.jce.statsvc.RequestPushForceOffline
import com.toolq.qq.protocol.protobuf.message.*
import com.toolq.qq.protocol.protobuf.onlinepush.OnlinePushTrans
import com.toolq.qq.protocol.protobuf.pushmsg.Submsgtype0x8a
import com.toolq.qq.protocol.protobuf.tips.TroopTips0x857
import com.toolq.utils.*

@ExperimentalUnsignedTypes
class HandleEvent(private val eventVector: SafeVector<IBotEvent>) {
    private val eventArray : List<IBotEvent> = eventVector.toList()

    fun handleMemberDecr(push : OnlinePushTrans.PbMsgInfo) {
        val time = push.real_msg_time.get()
        val reader = ByteReader(push.msg_data.get().toByteArray())
        val troopId = reader.readULong()
        if((reader.readByte().toInt() and 0xff) == 1) {
            val authUin = reader.readULong()
            var uin = authUin
            when(val type = reader.readByte().toInt() and 0xff) {
                0x83 -> uin = reader.readULong()
            }
            eventArray.forEach {
                ThreadManager.instance.addTask {
                    it.groupMemberLeave(
                        troopId = troopId,
                        leaveTime = time,
                        leaveUin = authUin,
                        uin = uin
                    )
                }
            }
        }
    }

    // 管理变化
    fun handleAdminChange(push: OnlinePushTrans.PbMsgInfo) {
        val reader = ByteReader(push.msg_data.get().toByteArray())
        val troopUin = reader.readULong()
        val toUin = reader.dis(2).readULong()
        val isAdd = (reader.readByte().toInt() == 0)
        eventArray.forEach {
            ThreadManager.instance.addTask {
                it.adminIncreaseAndDecreaseEvent(
                    isDelete = isAdd,
                    fromGroup = troopUin,
                    toUin = toUin,
                    msgTime = push.msg_time.get(),
                    msgId = push.msg_seq.get().toInt(),
                    msgUid = push.msg_uid.get()
                )
            }
        }
    }

    // 处理广播信息
    fun handlePushMsg(ignoreTroopPushMsg : HashMap<Long, SafeVector<Long>>, ignoreFriendPushMsg : HashMap<Long, SafeVector<Long>>, msgTime: Long, msgInfos: List<com.toolq.qq.protocol.jce.pushPack.MsgInfo>) {
        msgInfos.forEach {
            val reader = ByteReader(it.vMsg)
            when(it.shMsgType.toInt()) {
                // 群聊广播
                732 -> {
                    val groupId = reader.readULong()
                    val groupUin = it.lFromUin
                    if(!hasItBeenProcessed(groupUin, it.getShMsgSeq().toLong(), ignoreTroopPushMsg)) {
                        val type = reader.readByte().toInt() and 0xff
                        val uinType = reader.readByte().toInt()
                        if( uinType == 1) {
                            val senderUin = reader.readULong()
                            val senderTime = reader.readULong()
                            when(type) {
                                0xc -> {
                                    val banSize = reader.readShort()
                                    var tmp = 0
                                    repeat(banSize) { i ->
                                        val banUin = reader.readULong()
                                        val banTime = reader.readULong()
                                        eventArray.forEach { event ->
                                            ThreadManager.instance.addTask {
                                                event.groupBanEvent(groupId, senderUin, senderTime, banUin, banTime)
                                            }
                                        }
                                        if(tmp == i) return@repeat else tmp++
                                    }
                                }
                                else -> println("Can't parse PushMsg(($type)):${it.toByteArray().toHex()}")
                            }
                        } else {
                            when(type) {
                                // 消息撤回监测
                                0x11 -> {
                                    val pbSrc = reader.readBytes(reader.readByte().toInt() and 0xff)
                                    val tips = TroopTips0x857.NotifyMsgBody().parse(pbSrc)
                                    if(tips.opt_msg_recall.has()) {
                                        val recall = tips.opt_msg_recall.get()
                                        val uin = recall.uint64_uin.get()
                                        recall.uint32_recalled_msg_list.get().forEach { msgMate ->
                                            val authorUin = msgMate.uint64_author_uin.get()
                                            val sourceMsgTime = msgMate.uint32_time.get()
                                            val msgSeq = msgMate.uint32_seq.get()
                                            eventArray.forEach { event ->
                                                ThreadManager.instance.addTask {
                                                    event.troopMsgWithdrawalEvent(
                                                        groupId.toULong(),
                                                        uin.toULong(),
                                                        msgTime.toULong(),
                                                        authorUin.toULong(),
                                                        msgSeq.toUInt(),
                                                        sourceMsgTime.toULong(),
                                                        if(recall.msg_wording_info.has())
                                                            recall.msg_wording_info.string_item_name.get()
                                                        else "撤回了一条消息。"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                // 群名更改事件
                                0x10 -> {
                                    val pbSrc = reader.readBytes(reader.readByte().toInt())
                                    val tips = TroopTips0x857.NotifyMsgBody().parse(pbSrc)
                                    when(tips.opt_enum_type.get()) {
                                        1 -> {
                                            when(tips.uint32_service_type.get()) {
                                                12 -> {
                                                    val grayTips = tips.opt_msg_graytips.get()
                                                    if(grayTips.has()) {
                                                        eventArray.forEach {
                                                            ThreadManager.instance.addTask {
                                                                it.troopNameChanged(tips.opt_uint64_group_code.get(), grayTips.opt_bytes_content.get().toStringUtf8(), tips.opt_uint64_msg_time.get(), tips.opt_uint64_sender_uin.get())
                                                            }
                                                        }
                                                    }
                                                } // --> case 12
                                                else -> println("tips.uint32_service_type -> ${tips.uint32_service_type.get()}")
                                            }
                                        } // --> case 1
                                        else -> println("tips.opt_enum_type --> ${tips.opt_enum_type.get()}")
                                    }


                                }
                                else -> println("Can't parse PushMsg[[$type]]:${it.toByteArray().toHex()}")
                            }
                        }
                    }
                } // msgType == 732
                // 私聊广播
                528 -> {
                    if(!hasItBeenProcessed(it.lFromUin, it.getShMsgSeq().toLong(), ignoreFriendPushMsg)) {
                        val input = JceInputStream(reader.toByteArray())
                        val msg0x210 : MsgType0x210 = MsgType0x210().also { msg ->
                            msg.readFrom(input)
                        }
                        when(msg0x210.uSubMsgType.toInt()) {
                            // 私聊撤回
                            139 , 138 -> {
                                Submsgtype0x8a.ReqBody().parse(msg0x210.vProtobuf).msg_info.get().forEach { msgInfo ->
                                    eventArray.forEach { event ->
                                        ThreadManager.instance.addTask {
                                            event.friendMsgWithdrawalEvent(
                                                msgInfo.uint64_from_uin.get().toULong(),
                                                msgTime.toULong(),
                                                msgInfo.uint32_msg_seq.get().toUInt(),
                                                msgInfo.uint64_msg_time.get().toULong(),
                                                if (msgInfo.msg_wording_info.has())
                                                    msgInfo.msg_wording_info.string_item_name.get()
                                                else "撤回了一条消息。"
                                            )
                                        }
                                    }
                                }
                            }
                            277 -> return@forEach
                            else -> println("Can't parse 528-PushMsg[${msg0x210.uSubMsgType}]:${msg0x210.vProtobuf.toHex()}")
                        }
                    }
                } // msgType == 528
                else -> println("unknown pushMsg[${it.shMsgType}]: ${reader.toByteArray().toHex()}")
            }

        }
    }

    // 处理群聊信息
    fun handleTroopMsg(botUin : Long, msg: msg_comm.Msg) {
        CacheUtil.saveTroopMsg(msg)
        val head = msg.msg_head.get()
        val uin = head.from_uin.get()
        if(uin == botUin) return
        val msgSeq = head.msg_seq.get()
        val msgTime = head.msg_time.get()
        val msgInfo = MsgInfo(
            msg.appshare_info.get().appshare_id.get(),
            msg.appshare_info.get().appshare_resource.get().pkg_name.get(),
            head.from_appid.get(),
            // 消息来源者Appid，电脑固定为1
            head.msg_uid.get()
        )
        val groupInfo = head.group_info.get()
        val groupCode = groupInfo.group_code.get()
        val fromNick = groupInfo.group_card.get().toStringUtf8()
        val groupLevel = groupInfo.group_level.get()
        val groupName = groupInfo.group_name.get().toStringUtf8()
        var code = ""
        msg.msg_body.rich_text.ptt?.also {
            var isPtt = false
            if(it.has() && it.bytes_file_md5.has()) {
                val ptt = TQPtt(it.bytes_file_md5.toHex().toUpperCase())
                ptt.downUrl = it.bytes_down_para.toString()
                ptt.time = it.uint32_time.get()
                ptt.uuid = it.bytes_file_uuid.get().toStringUtf8()
                ptt.uin = it.uint64_src_uin.get()
                if(it.bytes_pb_reserve.has()) {
                    val reserve = im_msg_body.ReserveStruct().parse(it.bytes_pb_reserve.get().toByteArray())
                    ptt.magicVoice = reserve.uint32_change_voice.get() == 1
                }
                code = ptt.toString()
                isPtt = true
            }
            code += toTQCodeString(groupCode, uin, msgTime, msg.msg_body.get(), msgInfo, isPtt)
        }
        if(code.isEmpty()) return
        // 空消息 不处理
        if(isConcurrent()) {
            eventArray.forEach { botEvent ->
                val ret : MsgStatus = try {
                    botEvent.groupEvent(
                        groupName,
                        groupCode,
                        groupLevel,
                        fromNick,
                        uin,
                        msgTime,
                        msgSeq.toInt(),
                        code,
                        msgInfo
                    )
                } catch (t : Throwable) {
                    MsgStatus.IGNORE
                }
                // 消息被劫持
                if(ret == MsgStatus.HIJACK) return@forEach
            }
        } else {
            // 并发处理无消息劫持(多线程)
            eventArray.forEach { botEvent ->
                // 允许使用系统线程管理器
                ThreadManager.instance.addTask {
                    botEvent.groupEvent(
                        groupName,
                        groupCode,
                        groupLevel,
                        fromNick,
                        uin,
                        msgTime,
                        msgSeq.toInt(),
                        code,
                        msgInfo
                    )
                }
            }
        }

    }

    // 处理好友发来的各种消息
    fun handleC2CMsg(toolQ: ToolQ, msg : msg_comm.Msg) {
        CacheUtil.saveFriendMsg(msg)
        when(msg.msg_head.msg_type.get()) {
            33, 34 -> {
                val head = msg.msg_head
                val time = head.msg_time.get()
                // 进群的憨批
                val authUin = head.auth_uin.get()
                if(authUin == toolQ.account.user) {
                    TODO("接收邀请入群未添加")
                } else {
                    // 憨批的名字
                    val authNick = head.auth_nick.get()
                    val reader = ByteReader(msg.msg_body.msg_content.get().toByteArray())
                    // println(reader.toString())
                    val troopCode = reader.readULong()
                    val unknownByte = reader.readByte()
                    // 不知道是什么东西，固定1
                    if(unknownByte == 1.toByte()) {
                        // 入群的憨批的QQ
                        val joinUin = reader.readULong()
                        var isInvited = false
                        var inviter = 0L
                        when(val type = reader.readByte().toInt() and 0xff) {
                            // 直接入群，审核验证通过
                            0x82 -> { }
                            // 邀请进群
                            0x83 -> {
                                // 邀请人
                                inviter = reader.readULong()
                                isInvited = true
                            }
                            else -> println("else UserJoin[$type] Event：${msg.toHex()}")
                        }
                        eventArray.forEach {
                            toolQ.threadManager.addTask {
                                it.groupMemberJoin(troopCode, authUin, authNick, time, isInvited, inviter)
                            }
                        }
                    } else {
                        println("unknownByte was changed.")
                    }
                }
            }
            // 申请人群
            84 -> {

            }
            // 好友消息(普通消息)
            166 -> {
                c2cMsg(msg)
            }
            208 -> {
                // 好友语音消息
                c2cMsg(msg, msg.msg_body.rich_text.ptt)
            }
            528 -> {
                // 私聊一起听开关事件

            }
            else -> println("Can't parse ${msg.msg_head.msg_type.get()}")
        }
    }

    // 处理信息已推送事件
    fun handleMsgSentEvent(packet : ByteArray) {
        val rsp = msg_svc.PbSendMsgResp().parse(packet)
        val result = rsp.result.get()
        val msg = rsp.errmsg.get()
        eventVector.toArray()
        eventArray.forEach {
            ThreadManager.instance.addTask {
                it.messageSentEvent(result, msg)
            }
        }
    }

    fun handleDropEvent(offline: RequestPushForceOffline) {
        eventArray.forEach {
            ThreadManager.instance.addTask {
                it.dropEvent(offline.strTitle, offline.strTips, offline.bSameDevice.toInt() != 0)
            }
        }
    }

    private fun c2cMsg(msg : msg_comm.Msg, pttObj: im_msg_body.Ptt? = msg.msg_body.rich_text.ptt) {
        val head = msg.msg_head.get()
        val msgInfo = MsgInfo(
                msg.appshare_info.get().appshare_id.get(),
                msg.appshare_info.get().appshare_resource.get().pkg_name.get(),
                head.from_appid.get(), // 消息来源者Appid，电脑固定为1
                head.msg_uid.get()
        )
        var tqMsg = ""
        pttObj?.let {
            var isPtt = false
            if(it.has() && it.bytes_file_md5.has()) {
                val ptt = TQPtt(it.bytes_file_md5.toHex().toUpperCase())
                ptt.downUrl = it.bytes_down_para.toString()
                ptt.time = it.uint32_time.get()
                ptt.uuid = it.bytes_file_uuid.get().toStringUtf8()
                ptt.uin = it.uint64_src_uin.get()
                if(it.bytes_pb_reserve.has()) {
                    val reserve = im_msg_body.ReserveStruct().parse(it.bytes_pb_reserve.get().toByteArray())
                    ptt.magicVoice = reserve.uint32_change_voice.get() == 1
                }
                tqMsg = ptt.toString()
                isPtt = true
            }
            tqMsg += toTQCodeString(head.from_uin.get(), head.from_uin.get(), head.msg_time.get(), msg.msg_body.get(), msgInfo, isPtt)
        }
        if(tqMsg.isEmpty()) return
        // 空消息 不处理
        if(isConcurrent()) {
            eventArray.forEach { botEvent ->
                val ret : MsgStatus = try {
                    handleFriendEvent(
                        botEvent,
                        head.from_nick.get(),
                        head.from_uin.get(),
                        head.msg_time.get(),
                        head.msg_seq.get().toInt(),
                        tqMsg,
                        msgInfo
                    )
                } catch (t : Throwable) {
                    MsgStatus.IGNORE
                }
                // 消息被劫持
                if(ret == MsgStatus.HIJACK) return@forEach
            }
        } else {
            // 并发处理无消息劫持(多线程)
            eventArray.forEach { botEvent ->
                // 允许使用系统线程管理器
                ThreadManager.instance.addTask {
                    handleFriendEvent(
                            botEvent,
                            head.from_nick.get(),
                            head.from_uin.get(),
                            head.msg_time.get(),
                            head.msg_seq.get().toInt(),
                            tqMsg,
                            msgInfo
                    )
                }
            }
        }
    }

    @ExperimentalUnsignedTypes
    private fun toTQCode(uin: Long, fromUin: Long,
                         msgTime: Long, msg : im_msg_body.MsgBody, msgInfo: MsgInfo, hasAction : Boolean = false) : TQCode {
        val result = TQCode()
        val ret = TQCode()
        val richText = msg.rich_text.get()
        if(richText.elems.has()) {
            var action = hasAction
            var ignoreNextMsg = false
            var actionMsg = TQValue()
            // 如果出现行为消息，将不再解析其它非行为消息
            //  && !action 如果有了一个行为消息将不再解析
            var isReply = false
            richText.elems.get().forEach {
                if(ignoreNextMsg) {
                    ignoreNextMsg = false
                } else {
                    var elem : TQValue? = null
                    when {
                        it.text.has() && !action -> {
                            if (it.text.attr_6_buf.has()) {
                                try {
                                    val pat = ByteReader(it.text.attr_6_buf.get().toByteArray())
                                    val startPos: Int = pat.readShort()
                                    val strLen: Long = pat.readULong()
                                    val flag: Byte = pat.readByte()
                                    val uin: ULong = pat.readULong().toULong()
                                    val at = TQAt(uin)
                                    at.startPos = startPos
                                    at.strLength = strLen
                                    at.flag = flag
                                    elem = at
                                } catch (e : Exception) {
                                    elem = TQText(it.text.str.get().toStringUtf8())
                                }
                            } else
                                elem = TQText(it.text.str.get().toStringUtf8())
                        }
                        it.face.has() && !action -> {
                            elem = TQFace(it.face.index.get())
                        }
                        it.not_online_image.has() && !action -> {
                            val img = it.not_online_image.get()
                            val tqImage = TQImage(img.pic_md5.let { pic ->
                                if (pic.has())
                                    HexUtil.bytesToHexString(pic.get().toByteArray())
                                else
                                    img.file_path.get().toStringUtf8().split(".")[0]
                            })
                            tqImage.url = img.url
                            elem = tqImage
                        }
                        it.market_face.has() && !action -> {
                            it.market_face.let { mFace ->
                                ignoreNextMsg = true
                                elem = when(mFace.id) {
                                    "4823d3adb15df08014ce5d6796b76ee1" -> {
                                        val str = mFace.bytes_mobileparam.get().toStringUtf8()
                                        val size = str.split("value=")[1].substring(0, 1).toInt() + 1
                                        TQDice(size)
                                    }
                                    // mora
                                    "83c8a293ae65ca140f348120a77448ee" -> {
                                        val str = mFace.bytes_mobileparam.get().toStringUtf8()
                                        val size = str.split("value=")[1].substring(0, 1).toInt()
                                        TQMora(size)
                                    }
                                    else -> TQMFace(mFace.id)
                                }
                            }
                        }
                        it.custom_face.has() && !action -> {
                            val img = it.custom_face.get()
                            val tqImage = TQImage(img.bytes_md5.let { md5 ->
                                if (md5.has())
                                    md5.get().toHex()
                                else
                                    img.str_file_path.get().split(".")[0]
                            })
                            elem = tqImage
                        }
                        it.shake_window.has() && !action -> {
                            action = true
                            elem = TQShake()
                        }
                        it.light_app.has() && !action -> {
                            val lightApp = it.light_app
                            if(lightApp.has()) {
                                action = true
                                elem = TQJson(lightApp.json)
                            }
                        }
                        it.rich_msg.has() && !action -> {
                            val richMsg = it.rich_msg
                            if (richMsg.bytes_template_1.has()) {
                                val xml = richMsg.bytes_template_1.get()
                                var content = xml.toStringUtf8()
                                val reader = ByteReader(xml.toByteArray())
                                if (reader.readByte() == 1.toByte()) {
                                    content = String(ZipUtil.uncompress(reader.readRestBytes()))
                                }
                                action = true
                                elem = TQXml(content)
                            }
                        }
                        it.extra_info.has() || it.elem_flags2.has() -> System.nanoTime()
                        it.general_flags.has() -> {
                            it.general_flags.let { generalFlags ->
                                when {
                                    generalFlags.uint32_bubble_diy_text_id.has() -> msgInfo.bubbleDiyTextId = generalFlags.uint32_bubble_diy_text_id.get()

                                }
                            }
                        }
                        it.common_elem.has() && !action -> {
                            when (it.common_elem.uint32_service_type.get()) {
                                2 -> {
                                    action = true
                                    elem = if(it.common_elem.bytes_pb_elem.has()) {
                                        val bytes = it.common_elem.bytes_pb_elem.get().toByteArray()
                                        if(bytes.isNotEmpty()) {
                                            val s2 = hummer_commelem.MsgElemInfo_servtype2().parse(bytes)
                                            TQPoke(
                                                s2.uint32_poke_type.get(),
                                                s2.uint32_poke_strength.get(),
                                                s2.uint32_vaspoke_id.get()
                                            )
                                        } else {
                                            TQShake()
                                        }
                                    } else {
                                        TQShake()
                                    }
                                }
                                3 -> {
                                    // 闪图
                                    val s3 = hummer_commelem.MsgElemInfo_servtype3().parse(it.common_elem.bytes_pb_elem.get())
                                    when {
                                        s3.flash_c2c_pic.has() -> {
                                            val img = s3.flash_c2c_pic.get()
                                            val flashImage = TQFlashImage(img.pic_md5.let { pic ->
                                                if (pic.has())
                                                    HexUtil.bytesToHexString(pic.get().toByteArray())
                                                else
                                                    img.file_path.get().toStringUtf8().split(".")[0]
                                            })
                                            elem = flashImage
                                            action = true
                                        }
                                        s3.flash_troop_pic.has() -> {
                                            val img = s3.flash_troop_pic.get()
                                            val tqImage = TQFlashImage(img.bytes_md5.let { md5 ->
                                                if (md5.has())
                                                    md5.get().toHex()
                                                else
                                                    img.str_file_path.get().split(".")[0]
                                            })
                                            elem = tqImage
                                            action = true
                                        }
                                        else -> {
                                            TLog.warn("Wrong flash image message body!")
                                        }
                                    }
                                }
                                14 -> {
                                    action = true
                                    val s14 = hummer_commelem.MsgElemInfo_servtype14().parse(it.common_elem.bytes_pb_elem.get())
                                    elem = TQFlashText(s14.text, s14.uint32_id.get())
                                }
                                20 -> {
                                    action = true
                                    val s20 = hummer_commelem.MsgElemInfo_servtype20().parse(it.common_elem.bytes_pb_elem.get())
                                    elem = TQJson(String(s20.data))
                                }
                                33 -> {
                                    // QQ新版小表情
                                    val cface = hummer_commelem.MsgElemInfo_servtype33().parse(it.common_elem.bytes_pb_elem.get())
                                    val id = cface.uint32_index.get()
                                    val name = cface.bytes_text.get().toStringUtf8()
                                    if(FaceUtil.exitsFace(id)) {
                                        FaceUtil.addFace(id, name)
                                    }
                                    elem = TQCommonFace(id, name)
                                }

                                else -> println("Can't CommonElem is ${HexUtil.bytesToHexString(it.toByteArray())}")
                            }
                        }
                        it.src_msg.has() -> {
                            isReply = true
                            msgInfo.replyUin = it.src_msg.get().uint64_sender_uin.get()
                            msgInfo.replyMsgId = it.src_msg.get().uint32_orig_seqs.get().let { seqs ->
                                var seq = 0
                                seqs.forEach { ss -> seq = ss }
                                seq
                            }
                        }
                        it.trans_elem_info.has() -> {
                            when(it.trans_elem_info.elem_type.get()) {
                                24 -> {
                                    val reader = ByteReader(it.trans_elem_info.elem_value.get().toByteArray())
                                    if(reader.readByte().toUInt() == 1u) {
                                        val bytes = reader.readBytes(reader.readShort())
                                        val objMsg = obj_msg.ObjMsg().mergeFrom(bytes)
                                        objMsg.msg_content_info.get()
                                            .filter { info -> info.msg_file.has() }
                                            .forEach { info ->
                                                val file = info.msg_file.get()
                                                eventArray.forEach {
                                                    ThreadManager.instance.addTask {
                                                        it.groupFileUp(uin,
                                                            fromUin,
                                                            msgTime,
                                                            file.str_file_name.get(),
                                                            file.uint64_file_size.get(),
                                                            file.int64_dead_time.get(),
                                                            file.bytes_file_md5.get().toStringUtf8(),
                                                            file.bytes_file_path.get().toStringUtf8()
                                                        )
                                                    }
                                                }
                                            }
                                    }
                                }
                                else -> println("不支持其它的奇怪的Trans消息：${it.toByteArray().toHex()}")
                            }
                        }
                        else -> if(!action) println("Can't elem is ${HexUtil.bytesToHexString(it.toByteArray())}")
                    }
                    if(!action && elem != null) {
                        result.add(elem!!)
                    } else if(action && elem != null) {
                        actionMsg = elem as TQValue
                        // if(!action) action = true
                    }
                }
            }
            if(action) {
                ret.add(actionMsg)
            } else {
                var tmp = false
                result.forEach {
                    if(isReply && it.isAt()) {
                        msgInfo.isReply = isReply
                        isReply = false
                        tmp = true
                    } else if(tmp && it.isText()) {
                        var text = it.toText().text
                        if(text.startsWith(" ")) {
                            text = text.substring(1)
                        }
                        ret.add(TQText(text))
                        tmp = false
                    } else ret.add(it)
                }
            }
        }
        return ret
    }

    @ExperimentalUnsignedTypes
    private fun toTQCodeString(uin: Long, fromUin: Long,
                               msgTime: Long, msg : im_msg_body.MsgBody, msgInfo: MsgInfo, hasAction : Boolean) : String {
        return toTQCode(uin, fromUin, msgTime, msg, msgInfo, hasAction).toString()
    }

    private fun isConcurrent() : Boolean = BotConfig.concurrentHandle
}

@ExperimentalUnsignedTypes
private fun handleFriendEvent(botEvent : IBotEvent, fromNick : String, fromUin : Long, msgTime : Long, msgSeq : Int, msg : String, msgInfo: MsgInfo) : MsgStatus = botEvent.friendEvent(
    fromNick,
    fromUin,
    msgTime,
    msgSeq,
    msg,
    msgInfo
)

// 重复处理方法
private fun hasItBeenProcessed(uin : Long, uid : Long, map : HashMap<Long, SafeVector<Long>>) : Boolean {
    //========================================== 防止重复消息
    if (map.containsKey(uin)) {
        val vector = map[uin]
        if(vector!!.contains(uid)) {
            return true
        } else {
            if(vector.size > Int.MAX_VALUE - 1000) {
                map[uin] = SafeVector<Long>().also { newVector ->
                    newVector.add(uid)
                }
            } else vector.add(uid)
        }
    } else {
        map[uin] = SafeVector<Long>().also { vector ->
            vector.add(uid)
        }
    }
    //==========================================
    return false
}