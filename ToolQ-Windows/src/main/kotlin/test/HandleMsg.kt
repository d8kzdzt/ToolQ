package test

import com.toolq.ToolQManager
import com.toolq.helper.bot.BotEvent
import com.toolq.helper.bot.MsgInfo
import com.toolq.helper.bot.MsgStatus
import com.toolq.helper.bot.tqcode.TQValue.Companion.decodeOutput
import com.toolq.qq.dataClass.FriendAdder
import com.toolq.qq.protocol.pay.HbType
import com.toolq.qq.protocol.pay.LuckyPacket
import com.toolq.utils.JsonUtil
import com.toolq.utils.OkhttpUtil

@ExperimentalUnsignedTypes
class HandleMsg(botUin: Long) : BotEvent(botUin) {
    private val needTo = HashMap<Long, Boolean>()
    private val needTo2 = HashMap<Long, Boolean>()

    override fun friendEvent(fromNick: String, fromUin: Long, msgTime: Long, msgId: Int, msg: String, msgInfo: MsgInfo): MsgStatus {
        println("C2C收到：来自$fromNick[$fromUin]的$msg----时间$msgTime【$msgId】")
        val builder = getMessageBuilder()
            .addMsg(msg)
            .addText("||")
            .addText(msg)
        builder.sendToFriend(fromUin)
        return MsgStatus.IGNORE
    }

    override fun groupEvent(
        fromGroupName: String,
        fromGroup: Long,
        fromGroupLevel: Int,
        fromNick: String,
        fromUin: Long,
        msgTime: Long,
        msgId: Int,
        msg: String,
        msgInfo: MsgInfo
    ): MsgStatus {
        println("群：$msg")
        when {
            needTo[fromUin] == true -> {
                needTo[fromUin] = false
                getMessageBuilder().addText(msg).sendToGroup(fromGroup)
            }
            needTo2[fromUin] == true -> {
                needTo2[fromUin] = false
                getMessageBuilder().addMsg(decodeOutput(msg)).sendToGroup(fromGroup)
            }
            else -> {
                val msf = msg.split(" ")
                when(msf[0]) {
                    "noticeList" -> {
                        getMessageBuilder().addText(getGroupAnnouncementList(fromGroup).toString()).sendToGroup(fromGroup)
                    }
                    "notice" -> {
                        getMessageBuilder().addText(addTroopNotice(fromGroup, msf[1],
                            isTop = false,
                            isTipWindows = true,
                            isConfirm = true,
                            showEdit = false,
                            sendToNewMember = false
                        ).toString()).sendToGroup(fromGroup)
                    }
                    "ban" -> {
                        troopShutUp(fromGroup, msf[1].toLong(), 10 * 60)
                    }
                    "relieve" -> {
                        troopShutUp(fromGroup, msf[1].toLong(), 0)
                    }
                    "shut" -> {
                        allMembersMute(fromGroup, true)
                    }
                    "open" -> {
                        allMembersMute(fromGroup, false)
                    }
                    "botName" -> {
                        getMessageBuilder().addText(botName).sendToGroup(fromGroup)
                    }
                    "troopShareUrl" -> {
                        getMessageBuilder().addText(getTroopShareUrl(fromGroup)).sendToGroup(fromGroup)
                    }
                    "searchFriend" -> {
                        getMessageBuilder().addText(searchFriend(msf[1].toLong()).toString()).sendToGroup(fromGroup)
                    }
                    "flashText" -> {
                        getMessageBuilder().addFlashText(msf[1], msf[2].toInt()).sendToGroup(fromGroup)
                    }
                    "changeCard" -> {
                        modifyTroopCard(fromGroup, fromUin, msf[1])
                        getMessageBuilder().addText("修改群名片成功！").sendToGroup(fromGroup)
                    }
                    "voteMe" -> {
                        giveFavorite(fromUin, 10)
                        getMessageBuilder().addAt(fromUin).addText("\n点赞成功！！！").sendToGroup(fromGroup)
                    }
                    "friendList" -> {
                        getMessageBuilder().addText(friendList.toString()).sendToGroup(fromGroup)
                    }
                    "upMe" -> {
                        println(setTroopAdmin(fromGroup, fromUin, true))
                    }
                    "downMe" -> {
                        println(setTroopAdmin(fromGroup, fromUin, false))
                    }
                    "addMe" -> {
                        addFriend(fromUin, object : FriendAdder {
                            override fun msg(): String = "你好，我是伏秋洛"

                            override fun remark(): String = "秋洛洛"

                            override fun answer(question: String): String {
                                getMessageBuilder().addAt(fromUin).addText("\n问题：$question").sendToGroup(fromGroup)
                                return "不知道。"
                            }
                        })
                    }
                    "cu" -> {
                        getMessageBuilder().addImageByUrl(OkhttpUtil().get("http://www.dmoe.cc/random.php?return=json").let {
                            JsonUtil.parseJsonObject(it!!.body!!.string())["imgurl"].asString
                        }).sendToGroup(fromGroup)
                    }
                    "troopMember" -> {
                        getMessageBuilder().addText(getTroopMembers(fromGroup)).sendToGroup(fromGroup)
                    }
                    "replyMe" -> {
                        getMessageBuilder().setReply(msgId)
                            // .addAt(fromUin)
                            .addText(" 回复你！！！").addAt(fromUin).sendToGroup(fromGroup)
                    }
                    "#F" -> {
                        needTo[fromUin] = true
                        getMessageBuilder().addAt(fromUin).addText("\n请发送一条消息进行转码！！！").sendToGroup(fromGroup)
                    }
                    "#C" -> {
                        needTo2[fromUin] = true
                        getMessageBuilder().addAt(fromUin).addText("\n请发送一条消息进行反码！！！").sendToGroup(fromGroup)
                    }
                    "发个红包" -> {
                        val ret = troopRedEnvelopes(fromGroup, HbType.Voice, "我喜欢你", 1, 1)
                        getMessageBuilder().setReply(msgId).addMsg(ret).sendToGroup(fromGroup)
                    }
                    else -> {
                        // if(fromGroup == 1038106737L) getMessageBuilder().addMsg(msg).sendToGroup(fromGroup)
                    }
                }
            }
        }
        return MsgStatus.IGNORE
    }

    override fun messageSentEvent(result: Int, errMsg: String) {
        println("消息发送返回：$result[错误：$errMsg]")
    }

    override fun dropEvent(tittle: String, tip: String, sameDevice: Boolean) {
        println("$tittle===$tip[$sameDevice]")
    }

    override fun troopMsgWithdrawalEvent(
        troopId: ULong,
        uin: ULong,
        time: ULong,
        authorUin: ULong,
        msgSeq: UInt,
        msgTime: ULong,
        tip: String
    ) {
        getMessageBuilder().addText("群[$troopId]在[$time]由[$uin]撤回了一条[$authorUin]在[$msgSeq-$msgTime]发的消息！").sendToGroup(troopId)
    }

    override fun friendMsgWithdrawalEvent(uin: ULong, time: ULong, msgSeq: UInt, msgTime: ULong, tip: String) {
        getMessageBuilder().addText("好友[$uin]在[$time]由[$uin]撤回了一条在[$msgSeq-$msgTime]发的消息！").sendToFriend(uin)
    }

    override fun adminIncreaseAndDecreaseEvent(
        isDelete: Boolean,
        fromGroup: Long,
        toUin: Long,
        msgTime: Long,
        msgId: Int,
        msgUid: Long
    ) {
        getMessageBuilder().addText("群聊[$fromGroup]的【$toUin】在($msgTime-$msgId-$msgUid)被${
            if(!isDelete) "设为管理" else "取消管理"
        }").sendToGroup(fromGroup)
    }

    override fun troopNameChanged(troopId: Long, content: String, time: Long, sender: Long) {
        getMessageBuilder().addText("群名在[$time]被【$sender】改为$content").sendToGroup(troopId)
    }

    override fun groupBanEvent(troopId: Long, sender: Long, time: Long, banUin: Long, banTime: Long) {
        getMessageBuilder().addText("[$banUin]在[$time]被[$sender]禁言【$banTime】秒").sendToGroup(troopId)
    }

    override fun groupMemberJoin(
        troopId: Long,
        joinUin: Long,
        uinNick: String,
        joinTime: Long,
        isInvited: Boolean,
        inviteUin: Long
    ) {
        if(isInvited) {
            getMessageBuilder().addText("群聊[$troopId]在[$joinTime]被邀请来了一位[$joinUin]叫[$uinNick]的人").sendToGroup(troopId)
        } else getMessageBuilder().addText("群聊[$troopId]在[$joinTime]来了一位[$joinUin]叫[$uinNick]的人").sendToGroup(troopId)
    }

    override fun groupMemberLeave(troopId: Long, leaveUin: Long, leaveTime: Long, uin: Long) {
        if(leaveUin == uin) {
            getMessageBuilder().addText("[$leaveUin]在【$leaveTime】离开了我们").sendToGroup(troopId)
        } else {
            getMessageBuilder().addText("[$leaveUin]在【$leaveTime】被[$uin]踢出本群").sendToGroup(troopId)
        }
    }

    override fun groupFileUp(
        troopId: Long,
        fromUin: Long,
        msgTime: Long,
        fileName: String,
        fileSize: Long,
        fileDeadTime: Long,
        fileMd5: String,
        filePath: String
    ) {
        getMessageBuilder().addText("[$fromUin]在【$msgTime】上传了一个叫【$fileName】大小为【$fileSize Byte】过期时间为【$fileDeadTime】的文件").sendToGroup(troopId)
    }

}