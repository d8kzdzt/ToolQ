package com.toolq.helper.bot

import com.google.gson.JsonObject
import com.qq.jce.wup.UniPacket
import com.toolq.ToolQ
import com.toolq.ToolQManager
import com.toolq.helper.bot.packet.Waiter
import com.toolq.helper.jce.JceHelper
import com.toolq.helper.packet.*
import com.toolq.qq.dataClass.*
import com.toolq.qq.protocol.friendlist.GetFriendList.Companion.makeGetFriendPacket
import com.toolq.qq.protocol.jce.favorite.Favorite
import com.toolq.qq.protocol.jce.friendlist.*
import com.toolq.qq.protocol.jce.group.GetTroopMemberList
import com.toolq.qq.protocol.jce.group.ModifyGroupCard
import com.toolq.qq.protocol.jce.group.UinInfo
import com.toolq.qq.protocol.jce.kqq.GroupMng
import com.toolq.qq.protocol.jce.summary.ReqHead
import com.toolq.qq.protocol.jce.summary.SummaryCard
import com.toolq.qq.protocol.jce.summary.SummaryCard.Rsp
import com.toolq.qq.protocol.pay.HbType
import com.toolq.qq.protocol.pay.LuckyPacket
import com.toolq.qq.protocol.protobuf.friendlist.SummaryCardBusiEntry
import com.toolq.qq.protocol.protobuf.group_member_info
import com.toolq.qq.protocol.protobuf.oidb.OIDBSSOPkg
import com.toolq.qq.protocol.protobuf.oidb.oidb_0x89a
import com.toolq.qq.protocol.protobuf.troop_honor
import com.toolq.utils.*
import com.toolq.utils.BotUtils.setBotCookie
import com.toolq.utils.BotUtils.toBkn
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.net.URLEncoder
import java.nio.ByteBuffer
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

@ExperimentalUnsignedTypes
open class Center(private val toolQ: ToolQ) : OperationCenterV2(toolQ) {
    val botUin: Long = toolQ.account.user

    /**
     * 批量获取群资料（机器人必须在该群内）
     *
     * @param groupId 群号
     * @return 群资料
     */
    override fun getMultiTroopInfo(vararg groupId: Long): List<MultiTroopInfo> {
        val info = ArrayList<MultiTroopInfo>()
        useJceByWaiter(sendJceAndWait("friendlist.GetMultiTroopInfoReq", GetMultiTroopInfo()) {
            it.setUin(botUin)
            it.setRichInfo(1.toByte())
            groupId.forEach { id ->
                it.addGroupCode(id)
            }
        }, GetMultiTroopInfo()) {
            for (troopInfo in it.troopInfo) {
                val element = MultiTroopInfo(
                    troopInfo.groupName,
                    troopInfo.groupCode,
                    troopInfo.groupUin,
                    troopInfo.memberNum,
                    troopInfo.certificationType,
                    troopInfo.groupOwnerUin
                )
                info.add(element)
            }
        }
        return info
    }

    /**
     * 搜索好友
     *
     * @param uin 目标
     * @return 目标信息
     */
    override fun searchFriend(uin: Long): UserInfo? {
        val waiter = sendJceAndWait("SummaryCard.ReqSummaryCard", 5 * 1000, arrayOf(SummaryCard.Req(), ReqHead(2)), arrayOf("ReqSummaryCard", "ReqHead")) {
            val search = it[0] as SummaryCard.Req
            search.uin = uin
            search.comeFrom = 12
            search.qzoneFeedTimestamp = 0
            search.isFriend = 0
            search.groupCode = 0
            search.groupUin = 0
            search.seed = ByteArray(1)
            search.searchName = ""
            search.getControl = 0
            search.addFriendSource = 10004
            search.reqTemplateInfo = "00 00 00 4C 10 03 2C 3C 4C 56 08 4D 43 61 72 6453 76 63 66 05 71 75 6572 79 7D 00 00 29 08 0001 06 03 72 65 71 1D 0000 1D 0A 00 01 12${BytesUtil.int64_to_buf32(toolQ.account.user).toHex()}23 00 00 00 00${BytesUtil.int64_to_buf32(uin).toHex()}36 05 38 2E 342E 38 40 0C 50 6D 0B 8C98 0C A8 0C".hexToBs()

            search.reqServices = arrayListOf()

            // "28 00 00 00 BB 00 00 00 0A 08 01 18 B1 AA B2 8E 05 20 B5 F0 D1 F7 0C 28 13 60 02 6A 0A 38 2E 35 2E 35 2E 35 31 30 35 70 F1 27 7A 97 01 08 08 12 90 01 32 39 38 42 45 44 33 41 36 36 36 41 30 43 46 38 41 46 46 45 45 32 34 30 31 42 42 30 31 46 35 45 36 43 37 41 44 42 37 33 38 34 36 46 32 46 38 33 43 45 42 32 35 42 33 31 43 46 34 39 37 46 35 46 31 35 30 44 41 31 43 36 44 42 31 34 45 45 46 34 35 35 32 31 34 33 35 37 38 34 37 33 33 42 32 37 31 41 46 46 38 38 30 37 32 38 30 37 43 36 45 41 39 37 44 34 43 43 45 35 34 30 33 32 36 34 31 36 42 32 34 45 35 37 33 44 41 33 44 42 41 43 32 37 18 10 08 B5 F0 D1 F7 0C 10 0A 28 01 29 ".hexToBs(),
            // "28 00 00 00 BB 00 00 00 0E 08 01 18 B1 AA B2 8E 05 20 B5 F0 D1 F7 0C 28 11 60 02 6A 0A 38 2E 35 2E 35 2E 35 31 30 35 70 F1 27 7A 97 01 08 08 12 90 01 32 39 38 42 45 44 33 41 36 36 36 41 30 43 46 38 41 46 46 45 45 32 34 30 31 42 42 30 31 46 35 45 36 43 37 41 44 42 37 33 38 34 36 46 32 46 38 33 43 45 42 32 35 42 33 31 43 46 34 39 37 46 35 46 31 35 30 44 41 31 43 36 44 42 31 34 45 45 46 34 35 35 32 31 34 33 35 37 38 34 37 33 33 42 32 37 31 41 46 46 38 38 30 37 32 38 30 37 43 36 45 41 39 37 44 34 43 43 45 35 34 30 33 32 36 34 31 36 42 32 34 45 35 37 33 44 41 33 44 42 41 43 32 37 18 10 0A 0A 33 34 37 32 31 32 38 30 35 33 10 01 29".hexToBs())

            repeat(3) {
                val builder = ByteBuilder()
                val comm = SummaryCardBusiEntry.comm()
                comm.ver.set(1)
                comm.fromuin.set(botUin)
                comm.touin.set(uin)
                comm.platform.set(2)
                var doFor : SummaryCardBusiEntry.DoFor? = null
                when (it) {
                    0 -> {
                        comm.service.set(2)
                    }
                    1 -> {
                        comm.service.set(4)
                    }
                    2 -> {
                        val cmdArray = intArrayOf(42334, 42340, 42344, 42354, 27375)
                        comm.service.set(16)
                        doFor = SummaryCardBusiEntry.DoFor()
                        doFor.ver.set(3)
                        doFor.userUin.set(SummaryCardBusiEntry.UserUin(uin))
                        doFor.userUin2.set(SummaryCardBusiEntry.UserUin(uin))
                        doFor.userUinV2.set(SummaryCardBusiEntry.UserUinV2().apply {
                            ver.set(4)
                            userUin.set(SummaryCardBusiEntry.UserUin(uin))
                        })
                        cmdArray.forEach { cmd ->
                            doFor.userUin3.add(SummaryCardBusiEntry.UserUinV3(cmd))
                        }
                        doFor.userUin4.set(SummaryCardBusiEntry.UserUin(uin))
                    }
                }
                comm.qqver.set("8.5.5.5105")
                comm.build.set(5105)
                val commData = comm.toByteArray()
                val doForData = if(doFor == null) byteArrayOf() else doFor.toByteArray()
                builder.writeString("(")
                builder.writeInt(commData.size)
                builder.writeInt(doForData.size)
                builder.writeBytes(commData)
                builder.writeBytes(doForData)
                builder.writeString(")")
                search.reqServices.add(builder.toByteArray())
            }

            search.secureSig = ByteArray(1)
            search.tinyId = 0
            search.likeSource = 0
            search.reqMedalWallInfo = 0
            search.req0x5ebFieldId = arrayListOf(27225, 27224, 42122, 42121, 42167, 42172, 40324, 42284, 42326, 42325, 42356, 42363, 42361, 42367, 42377, 42425, 42505, 42488, 27236, 27238, 42121)
            search.reqNearbyGodInfo = 1
            search.reqCommLabel = 0
            search.reqExtendCard = 1
            search.richCardNameVer = 1
        }
        return if(waiter.hasBody()) {
            val uni = UniPacket()
            uni.encodeName = "UTF-8"
            uni.decode(waiter.packet.bodyWithLength)
            val rsp = uni.getByClass("RespSummaryCard", Rsp())
            UserInfo(uin, rsp.Nick, rsp.level, rsp.voteCount, rsp.age.toInt(), rsp.birthday, rsp.country, rsp.Province, rsp.city, rsp.Email, rsp.photoCount, rsp.userFlag, rsp.face, rsp.autoRemark, rsp.QzoneFeedsDesc, rsp.Remark, rsp.ShowName)
        } else null
     }

    /**
     * 获取群列表
     */
    override fun getTroopList(): List<GroupInfo> {
        val result = ArrayList<GroupInfo>()
        val reqV2Simplify = GetTroopListReqV2Simplify(botUin, 0.toByte(), null, null, 1.toByte(), 9, 0, 1, 1.toByte())
        useJceByWaiter(sendJceAndWait("friendlist.GetTroopListReqV2", reqV2Simplify) {}, GetTroopListRespV2()) { respV2 ->
            if (respV2.result == 0) {
                val cacheMap = HashMap<Long, List<String>>()
                for (rankInfo in respV2.vecTroopRank) {
                    val list = ArrayList<String>()
                    for (pair in rankInfo.vecRankMap) list.add(pair.strRank)
                    for (pair in rankInfo.vecRankMapNew) list.add(pair.strRank)
                    cacheMap[rankInfo.dwGroupCode] = list
                }
                for (troopNum in respV2.vecTroopList) {
                    val groupInfo = GroupInfo(
                        troopNum.GroupCode,
                        troopNum.GroupUin,
                        troopNum.cFlag.toInt(),
                        troopNum.dwGroupOwnerUin,
                        troopNum.dwMemberNum,
                        troopNum.strGroupName,
                        troopNum.dwMaxGroupMemberNum,
                        cacheMap[troopNum.GroupCode]!!
                    )
                    result.add(groupInfo)
                }
            }
        }
        return result
    }

    /**
     * 获取好友列表
     */
    override fun getFriendList(): List<FriendInfo> {
        val result = ArrayList<FriendInfo>()
        val requestId = toolQ.recorder.nextRequestId()
        val seq = toolQ.recorder.nextSsoSeq()
        var startIndex = 0
        var getFriendCount = 100
        var everyGetSize = 100
        while (startIndex < getFriendCount) {
            val body = makeGetFriendPacket(requestId, toolQ.account.user, startIndex, everyGetSize)
            val data = PacketUtil.makeOnlinePacket(
                "friendlist.getFriendGroupList",
                getBotStatus(),
                body,
                getKey(),
                seq,
                botUin,
                false
            )
            val waiter = addWaiter(
                SeqWaiter(
                    seq,
                    "friendlist.getFriendGroupList"
                )
            )
            if (send(data) && waiter.wait("Time is quiet, time is endless!", 5 * 1000)) {
                val resp = JceHelper.decodePacket(waiter.getPacket().bodyWithLength, "FLRESP", GetFriendListResp())!!
                if (resp.result == 0) {
                    getFriendCount = resp.totoal_friend_count.toInt()
                    startIndex += everyGetSize
                    if (startIndex + everyGetSize > getFriendCount) {
                        everyGetSize = getFriendCount - startIndex
                    }
                    for (friendInfo in resp.vecFriendInfo) {
                        val info = FriendInfo(
                            friendInfo.friendUin,
                            friendInfo.nick,
                            friendInfo.groupId,
                            friendInfo.remark,
                            friendInfo.status,
                            friendInfo.uExtOnlineStatus
                        )
                        result.add(info)
                    }
                } else {
                    break
                }
            }
        }
        return result
    }

    /**
     * 获取群聊群友信息
     *
     * @param groupCode 群号
     * @param uin      对方QQ
     */
    override fun getTroopMemberCardInfo(groupCode: Long, uin: Long): GroupMemberInfo? {
        return usePbByWaiter(sendPbAndWait("group_member_card.get_group_member_card_info", group_member_info.ReqBody()) {
            uint64_group_code.set(groupCode)
            bool_new_client.set(true)
            uint64_uin.set(uin)
            uint32_rich_card_name_ver.set(1)
        }, group_member_info.RspBody()) {
            if (msg_meminfo.has() && msg_meminfo.str_nick.has()) {
                val memberInfo = msg_meminfo.get()
                return@usePbByWaiter GroupMemberInfo(
                    memberInfo.bool_is_friend.get(),
                    memberInfo.str_card.get().toStringUtf8(),
                    memberInfo.uint32_concern_type.get(),
                    memberInfo.uint32_role.get(),
                    memberInfo.str_nick.get().toStringUtf8(),
                    memberInfo.str_location.get().toStringUtf8(),
                    memberInfo.uint32_age.get(),
                    memberInfo.uint64_join.get(),
                    memberInfo.uint64_last_speak.get(),
                    memberInfo.bool_is_vip.get(),
                    memberInfo.bool_is_year_vip.get(),
                    memberInfo.bool_is_super_vip.get(),
                    memberInfo.bool_is_super_qq.get(),
                    memberInfo.uint32_vip_lev.get(),
                    memberInfo.bytes_job.get().toStringUtf8(),
                    memberInfo.bytes_phone_num.get().toStringUtf8(),
                    memberInfo.uint32_level.get(),
                    memberInfo.uint32_credit.get(),
                    memberInfo.bool_is_concerned.get(),
                    memberInfo.uint32_sex.get(),
                    memberInfo.bytes_special_title.get().toStringUtf8(),
                    memberInfo.uint32_special_title_expire_time.get().toLong()
                )
            } else return@usePbByWaiter null
        } as GroupMemberInfo?
    }

    /**
     * 获取某群聊的分享链接
     *
     * @param groupId 群号
     */
    override fun getTroopShareUrl(groupId: Long): String {
        val recorder = toolQ.recorder
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        okhttp.setBotCookie(botUin, recorder.getsKey().key, recorder.getpSkey().key, recorder.getpSKeyAndPt4Token()["qun.qq.com"]!!["p4token"]!!.key)
        okhttp.header("referer", "https://qun.qq.com/proxy.html?callback=1&id=1");
        val form = HashMap<String, String>();
        form["gc"] = groupId.toString()
        form["bkn"] = QQUtil.GetBkn(recorder.getsKey().key).toString()
        val res = okhttp.post("https://qun.qq.com/proxy/domain/admin.qun.qq.com/cgi-bin/qun_admin/get_join_k", form);
        if (res != null) {
            if(res.code == 200) {
                val json = JsonUtil.parseJsonObject(res.body!!.string())
                if(json["ec"].asInt == 0) {
                    return "https://qm.qq.com/cgi-bin/qm/qr?k=%s&jump_from=webapi".format(json["k"].asString)
                }
            }
        }
        return ""
    }

    /**
     * 点赞
     *
     * @param reqUin 点赞目标
     * @param time 点赞次数
     *
     */
    override fun giveFavorite(reqUin: Long, time : Int): Boolean {
        // if(uin in Long.MIN_VALUE .. Long.MAX_VALUE) return true
        // 永久废除，禁止点赞协议 -- 2020.1.19
        // 开放协议，仅仅允许好友点赞 -- 2021.1.30
        if(time in 1..20) {
            return sendJceAndWait("VisitorSvc.ReqFavorite", Favorite.Req()) {
                val head = com.toolq.qq.protocol.jce.favorite.ReqHead()
                head.lUIN = toolQ.account.user
                head.iSeq = requestId
                head.vCookies = HexUtil.hexStringToBytes("0C 18 00 01 06 01 31 16 01 31")
                req.lMID = reqUin
                req.emSource = 1
                req.iCount = time
                req.stHeader = head
            }.hasBody()
        }
        return false
    }

    /**
     * 获取目标的加好友设置
     *
     * @param uin 目标QQ
     */
    override fun getAddFriendSetting(uin: Long) : UserFriendInfo {
        return useJceByWaiter(sendJceAndWait("friendlist.getUserAddFriendSetting", GetUserAddFriendSetting.Req()) {
            it.uin = toolQ.account.user
            it.queryUin = uin
            it.sourceID = 3020
            it.sourceSubID = 4
            it.version = 1
        }, GetUserAddFriendSetting.Resp()) { resp ->
            if(resp.errorCode.toInt() == 0 && resp.result == 0) {
                val setting : AddFriendSetting = when(resp.queryUinSetting) {
                    0 -> AddFriendSetting.AllowAnyone
                    1 -> AddFriendSetting.NeedVerify
                    2 -> AddFriendSetting.NotAllow
                    3 -> AddFriendSetting.AnswerRightQuestion
                    4 -> AddFriendSetting.AnswerQuestionForMe
                    else -> AddFriendSetting.NotAllow
                }
                return@useJceByWaiter UserFriendInfo(resp.queryUin, resp.contact_bothway_friend, setting, resp.vecStrUserQuestion)
            }
            return@useJceByWaiter UserFriendInfo(uin)
        }.let {
            if(it == null) UserFriendInfo(uin) else it as UserFriendInfo
        }
    }

    // 获取加好友自动分组
    private fun getAutoInfo(uin: Long) : AutoInfo? {
        return useJceByWaiter(sendJceAndWait("friendlist.GetAutoInfoReq", GetAutoInfo.Req()) {
            it.uin = botUin
            it.dwFriendUin = uin
            it.cType = 1
            it.sourceID = 3020
            it.sourceSubID = 4
        }, GetAutoInfo.Resp()) {
            return@useJceByWaiter AutoInfo(uin, it.cGroupID, it.strGroupName, it.strRemark)
        } as AutoInfo?
    }

    // 加好友预备包
    private fun sendOidb0x147() {
        val seq = toolQ.recorder.nextSsoSeq()
        val oidb = OIDBSSOPkg()
        oidb.uint32_command.set(1142)
        oidb.uint32_service_type.set(147)
        oidb.bytes_bodybuffer.set(ByteBuffer.allocate(2 + 4).apply {
            putShort(1)
            putInt(botUin.toInt())
        }.array())
        val data = PacketUtil.makeOnlinePacket("OidbSvc.0x476_147", getBotStatus(), oidb.toByteArray(), getKey(), seq, toolQ.account.user, true)
        val waiter = addWaiter(SeqWaiter(seq, "OidbSvc.0x476_147"))
        send(data)
        waiter.wait("love for everyone is indifference to everyone", 3 * 1000)
    }

    // TODO("因为Cookie问题")
    override fun addFriend(uin: Long, friendAdder: FriendAdder) {
        sendOidb0x147()
        val userFriendInfo = getAddFriendSetting(uin)
        val seq = toolQ.recorder.nextSsoSeq()
        val requestId = toolQ.recorder.nextRequestId()
        val req = AddFriend.Req()
        val autoInfo = getAutoInfo(uin)
        val userInfo = searchFriend(uin)
        req.myFriendGroupId = autoInfo?.groupId ?: 0
        req.uin = toolQ.account.user
        req.addUin = uin
        req.showMyCard = 0
        when(userFriendInfo.addFriendSetting) {
            AddFriendSetting.AllowAnyone -> {
                req.addUinSetting = 0
                req.msgLen = 0
                req.msg = ""
                req.myAllowFlag = 1
                friendAdder.remark() ?.let { req.remark = it.toByteArray() }
                req.srcFlag = 0
                req.autoSend = 1
                req.sourceID = 3020
                req.sourceSubID = 4
                req.contactBothWayFriend = userFriendInfo.isFriend
            }
            AddFriendSetting.NeedVerify -> {
                req.myAllowFlag = 1
                friendAdder.remark() ?.let { req.remark = it.toByteArray() }
                req.srcFlag = 0
                req.autoSend = 1
                req.sourceID = 3020
                req.sourceSubID = 4
                req.contactBothWayFriend = userFriendInfo.isFriend
                val msg : String = friendAdder.msg().let { if(it.toByteArray().size > Byte.MAX_VALUE) it.substring(0, Byte.MAX_VALUE.toInt()) else it }
                req.msgLen = msg.toByteArray().size.toByte()
                req.msg = msg
                req.addUinSetting = 1
            }
            AddFriendSetting.AnswerRightQuestion -> {
                val msg : String = friendAdder.answer(userFriendInfo.userQuestion[0]).let {
                    if(it.length > Byte.MAX_VALUE) it.substring(0, Byte.MAX_VALUE.toInt()) else it
                }
                req.addUinSetting = 3
                req.msgLen = msg.toByteArray().size.toByte()
                req.msg = msg
            }
            AddFriendSetting.AnswerQuestionForMe -> {
                req.addUinSetting = 4
                repeat(userFriendInfo.userQuestion.size) {
                    val question = userFriendInfo.userQuestion[it]
                    val answer = friendAdder.answer(question)
                    when(it) {
                        0 -> {

                        }
                        1 -> {

                        }
                        2 -> {

                        }
                    }
                }
            }
            AddFriendSetting.NotAllow -> {
                return
            }
        }
        val body = JceHelper.encodePacket(req, "mqq.IMService.FriendListServiceServantObj", "AddFriendReq", "AF", requestId)
        val data = PacketUtil.makeOnlinePacket("friendlist.addFriend", getBotStatus(), body, getKey(), seq, toolQ.account.user, false)
        val waiter = addWaiter(SeqWaiter(seq, "friendlist.addFriend"))
        if( send(data) && waiter.wait("moisturize things silently", 10 * 1000)) {
            val resp = JceHelper.decodePacket(waiter.packet.bodyWithLength, "AFRESP", AddFriend.Resp())
            if(resp.result == 0) {
                if(resp.sig != null && resp.sig.isNotEmpty()) {
                    req.sig = resp.sig
                    req.msgLen = 0
                    req.msg = ""
                    send(PacketUtil.makeOnlinePacket("friendlist.addFriend", getBotStatus(), JceHelper.encodePacket(req, "mqq.IMService.FriendListServiceServantObj", "AddFriendReq", "AF", toolQ.recorder.nextRequestId()), getKey(), toolQ.recorder.nextSsoSeq(), toolQ.account.user, false))
                }


            }
        }
        // TODO("添加好友待实现")
    }

    /**
     * 设置群聊管理员
     *
     * @param troopId 群号
     * @param uin 目标QQ
     * @param  isAdd 是否添加
     */
    override fun setTroopAdmin(troopId: Long, uin: Long, isAdd: Boolean) : Boolean {
        return usePbByWaiter(sendPbAndWait("OidbSvc.0x55c_1", OIDBSSOPkg()) {
            val src = ByteBuffer.allocate(4 + 4 + 1)
            src.putInt(troopId)
            src.putInt(uin)
            src.putBoolean(isAdd)
            bytes_bodybuffer.set(src.array())
            uint32_command.set(0x55c)
            uint32_service_type.set(1)
        }, OIDBSSOPkg()) {
            return@usePbByWaiter true
        } as Boolean
    }

    /**
     * 修改群名片
     *
     * @param troop 群号
     * @param uin 目标QQ
     * @param  card 群名片
     */
    override fun modifyTroopCard(troop: Long, uin: Long, card: String): Boolean {
        return useJceByWaiter(sendJceAndWait("friendlist.ModifyGroupCardReq", ModifyGroupCard.Req()) {
            val uinInfos = ArrayList<UinInfo>()
            it.groupCode = troop
            it.newSeq = 0
            it.zero = 0
            val info = UinInfo()
            info.uin = uin
            info.flag = 1
            info.name = card
            info.gender = -1
            uinInfos.add(info)
            it.uinInfo = uinInfos
        }, ModifyGroupCard.Resp()) {
            return@useJceByWaiter it.result == 0
        } .let {
            if(it == null) false else it as Boolean
        }
    }

    /**
     * 获取群成员列表
     *
     * @param troop 群号
     */
    override fun getTroopMembers(troop: Long): MutableList<TroopMemberInfo> {
        val list = ArrayList<TroopMemberInfo>()
        useJceByWaiter(sendJceAndWait("friendlist.getTroopMemberList", GetTroopMemberList.Req()) {
            it.uin = botUin
            it.GroupCode = troop
            it.GroupUin = ToolQUtil.groupCode2GroupUin(troop)
            it.Version = 2
            it.ReqType = 1
            it.GetListAppointTime = 0
            it.cRichCardNameVer = 1
        }, GetTroopMemberList.Resp()) { resp ->
            if(resp.result == 0) resp.vecTroopMember.forEach {
                list.add(
                    TroopMemberInfo(it.MemberUin, it.cGender, it.Age, it.Nick, it.Status.toInt(), it.job, it.sPhone, it.sEmail, it.sMemo, it.strAutoRemark, it.dwMemberLevel, it.dwJoinTime, it.dwLastSpeakTime, it.dwCreditLevel, it.sSpecialTitle, it.dwSpecialTitleExpireTime, it.dwShutupTimestap, it.dwVipType, it.dwVipLevel, ArrayList<Int>().apply {
                        val honor = troop_honor.GroupUserCardHonor().parse(it.vecGroupHonor)
                        honor.id.get().forEach { id ->
                            add(id)
                        }
                    })
                )
            }
        }
        return list
    }

    /**
     * 群禁言
     *
     * @param groupId 群号
     * @param uin 目标
     * @param time 时间（秒）
     */
    override fun troopShutUp(groupId: Long, uin: Long, time: Long): Boolean {
        val seq = toolQ.recorder.nextSsoSeq()
        val oidb = OIDBSSOPkg()
        if(uin == 0L) {
            oidb.uint32_command.set(0x89a)
            oidb.uint32_service_type.set(0)
            oidb.bytes_bodybuffer.set(oidb_0x89a.ReqBody().apply {
                this.uint64_group_code.set(groupId)
                this.st_group_info.set(oidb_0x89a.groupinfo())
                this.st_group_info.uint32_shutup_time.set(if(time == 1L) 26843545 else 0)
            }.toByteArray())
            val data = PacketUtil.makeOnlinePacket("OidbSvc.0x89a_0", getBotStatus(), oidb.toByteArray(), getKey(), seq, toolQ.account.user, true)
            val waiter = addWaiter(SeqWaiter(seq, "OidbSvc.0x89a_0"))
            if(send(data) && waiter.wait("Don't want to work, don't want to get up, don't want this world", 3 * 1000)) {
                return true
            }
        } else {
            oidb.uint32_command.set(0x570)
            oidb.uint32_service_type.set(8)
            oidb.bytes_bodybuffer.set(ByteBuffer.allocate(4 + 1 + 2 + 4 + 4).apply {
                putInt(groupId)
                put(32.toByte())
                putShort(1)
                // 批量禁言
                putInt(uin)
                putInt(time)
            }.array())
            val data = PacketUtil.makeOnlinePacket("OidbSvc.0x570_8", getBotStatus(), oidb.toByteArray(), getKey(), seq, toolQ.account.user, true)
            val waiter = addWaiter(SeqWaiter(seq, "OidbSvc.0x570_8"))
            if(send(data) && waiter.wait("hard work will pay off", 3 * 1000)) {
                return true
            }
        }
        return false
    }

    /**
     * 全员禁言
     *
     * @param groupId 群号
     * @param isOpen 是否禁言
     */
    override fun allMembersMute(groupId: Long, isOpen: Boolean): Boolean = troopShutUp(groupId, 0, if(isOpen) 1 else 0 )

    /**
     * 获取机器人头像
     */
    override fun getBotFaceUrl(): String = String.format("http://q1.qlogo.cn/g?b=qq&nk=%s&s=640", toolQ.account.user)

    /**
     * 获取群公告列表
     *
     * @param groupId 群号
     */
    override fun getGroupAnnouncementList(groupId: Long) : JsonObject? {
        val skey = toolQ.recorder.getsKey().key
        val bkn = QQUtil.GetBkn(skey)
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType: MediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()!!
        val body: RequestBody = RequestBody.create(mediaType, "qid=$groupId&bkn=$bkn&ft=23&s=-1&n=10&i=1&ni=1")
        val request: Request = Request.Builder()
            .url("https://web.qun.qq.com/cgi-bin/announce/list_announce?bkn=$bkn")
            .method("POST", body)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("referer", "https://web.qun.qq.com/mannounce/index.html?_wv=1031&_bid=148")
            .addHeader("cookie", "uin=o${toolQ.account.user}; traceid=0df0b3f2bb")
            .addHeader("cookie", "skey=$skey")
            .addHeader("cookie", "p_uin=o${toolQ.account.user}")
            .addHeader("cookie", "p_skey=${toolQ.recorder.getpSkey().key}")
            .addHeader("cookie", "qq_locale_id=2052")
            .build()
        val response: Response = client.newCall(request).execute()
        return try {
            return JsonUtil.parseJsonObject(response.body!!.string())
        } catch (t: Throwable) {
            null
        }
    }

    /**
     * 添加群公告
     *
     * @param troopId 群号
     * @param text 内容
     * @param isTop 置顶
     * @param isTipWindows 是否弹窗展示
     * @param isConfirm 是否需要确认
     * @param showEdit 是否引导修改群名片
     * @param sendToNewMember 是否发送给新成员
     */
    override fun addTroopNotice(
        troopId: Long,
        text: String,
        isTop: Boolean,
        isTipWindows: Boolean,
        isConfirm: Boolean,
        showEdit: Boolean,
        sendToNewMember: Boolean
    ): JsonObject {
        val skey = toolQ.recorder.getsKey().key
        val map = toolQ.recorder.getpSKeyAndPt4Token()["qun.qq.com"]!!
        val bkn = skey.toBkn()
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        okhttp.setBotCookie(toolQ.account.user, skey, map["pskey"]!!.key, map["p4token"]!!.key)
        okhttp.header("content-type", "application/x-www-form-urlencoded")
        okhttp.header("referer", "https://web.qun.qq.com/mannounce/edit.html?&_wv=5127")
        val url = if(sendToNewMember) {
            "https://web.qun.qq.com/cgi-bin/announce/add_qun_instruction?bkn"
        } else {
            "https://web.qun.qq.com/cgi-bin/announce/add_qun_notice?bkn"
        }
        val mediaType: MediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()!!
        val content = "qid=$troopId&bkn=$bkn&text=${URLEncoder.encode(text)}&pinned=${if (isTop) "1" else "0"}&type=1&settings=${
            URLEncoder.encode(JsonObject().apply {
                add("is_show_edit_card", if(showEdit) "1" else "0")
                add("tip_window_type", if(isTipWindows) "1" else "0")
                add("confirm_required", if(isConfirm) "1" else "0")
            }.toString())
        }"
        val body: RequestBody = RequestBody.create(mediaType, content)
        val respond = okhttp.post("$url=${map["pskey"]!!.key.toBkn()}", body)
        return try {
            JsonUtil.parseJsonObject(respond!!.body!!.string())
        } catch (e : Exception) {
            JsonObject().apply {
                add("ec", -1)
            }
        }
    }

    /**
     * 删除群公告
     *
     * @param troopId 群号
     * @param fid 群公告id
     */
    override fun delTroopNotice(troopId: Long, fid: String): JsonObject {
        val skey = toolQ.recorder.getsKey().key
        val pskey = toolQ.recorder.getpSkey().key
        val bkn = skey.toBkn()
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        okhttp.setBotCookie(botUin, skey, pskey, toolQ.recorder.getpSKeyAndPt4Token()["qun.qq.com"]!!["p4token"]!!.key)
        okhttp.header("referer", "https://web.qun.qq.com/mannounce/detail.html?_wv=1031&_wvx=2")
        val respond = okhttp.post("https://web.qun.qq.com/cgi-bin/announce/del_feed?bkn=$bkn", HashMap<String, String>().apply {
            this["qid"] = troopId.toString()
            this["bkn"] = bkn.toString()
            this["fid"] = fid
        })
        return try {
            JsonUtil.parseJsonObject(respond!!.body!!.string())
        } catch (e : Exception) {
            JsonObject().apply {
                add("ec", -1)
            }
        }
    }

    /**
     * 获取机器人名字
     */
    override fun getBotName(): String {
        val recorder = toolQ.recorder
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        okhttp.setBotCookie(botUin, recorder.getsKey().key, recorder.getpSkey().key, recorder.getpSKeyAndPt4Token()["qun.qq.com"]!!["p4token"]!!.key)
        okhttp.header("referer", "https://qun.qq.com/join.html");
        val res = okhttp.get("https://qun.qq.com/cgi-bin/qunwelcome/myinfo?callback=?&bkn=${QQUtil.GetBkn(recorder.getsKey().key)}")
        return if (res != null) if(res.code == 200) JsonUtil.formatJson(res.body!!.string()) else "" else ""
    }

    /**
     * 获取群成员信息
     *
     * @param groupId 来源群号
     * @param uin QQ
     */
    override fun getTroopAppointRemark(groupId: Long, uin: Long): TroopUserAppointInfo {
        return useJceByWaiter(sendJceAndWait("friendlist.GetTroopAppointRemarkReq", GetTroopAppointRemark.Req()) {
            it.uin = botUin
            it.GroupCode = groupId
            it.GroupUin = groupId.codeToUin()
            it.cRichInfo = 5
            it.cRichCardNameVer = 1
            it.vecUinList = ArrayList<Long>().apply {
                add(uin)
            }
        }, GetTroopAppointRemark.Resp()) {
            var glamourLevel = 0L
            var job = ""
            var gender = 1.toByte()
            var email = ""
            var nick = ""
            var name = ""
            val honor = ArrayList<Int>()
            it.vecTroopRemark.forEach { info ->
                glamourLevel = info.GlamourLevel
                job = info.bytes_job
                gender = info.cGender
                email = info.sEmail
                nick = info.strNick
                name = info.sName
                honor.addAll(info.vecGroupHonor)
            }
            return@useJceByWaiter TroopUserAppointInfo(uin, it.GroupCode, glamourLevel, job, gender, email, nick, name, honor)
        }.let {
            it ?: TroopUserAppointInfo(uin, groupId)
        } as TroopUserAppointInfo
    }

    /**
     * 主动加群
     *
     * @param troopId 群号
     * @param backMsg 验证消息
     */
    override fun joinTroop(troopId: Long, backMsg : String) : Boolean {
        return useJceByWaiter(sendJceAndWait("ProfileService.GroupMngReq", GroupMng.Req()) {
            val answer = backMsg.let { if(it.toByteArray().size > Byte.MAX_VALUE) it.substring(0, Byte.MAX_VALUE.toInt()) else it }
            it.reqtype = 1
            it.uin = botUin
            val len = answer.toByteArray().size
            val buffer = ByteBuffer.allocate(len + 9)
            buffer.putInt(troopId.toInt())
            buffer.putInt(botUin.toInt())
            buffer.put(len.toByte())
            buffer.put(answer.toByteArray())
            it.vecBody = buffer.array()
            it.wSourceID = 3
            it.cCheckInGroup = 0
            it.sGroupLocation = ""
            it.cStatOption = 0
            it.wSourceSubID = 10012
            it.cIsSupportAuthQuestionJoin = 0
            it.cIfGetAuthInfo = 1
            it.dwDiscussUin = 0
            it.sJoinGroupPicUrl = ""
            it.sJoinGroupKey = ""
            it.sJoinGroupVerifyToken = ""
            it.dwJoinVerifyType = 0
        }, GroupMng.Resp()) {
            if(it.result.toInt() == 0 && it.reqtype == 1) return@useJceByWaiter true
            return@useJceByWaiter false
        }.let { it ?: false } as Boolean
    }

    /**
     * 发红包
     *
     * @param troopId 群号
     * @param type 红包类型
     * @param tittle 标题
     * @param amount 总金额（单位：分）
     * @param number 红包个数
     */
    fun troopRedEnvelopes(
        troopId: Long,
        type: HbType,
        tittle: String,
        amount: Int,
        number: Int
    ) = troopRedEnvelopes(troopId, type, tittle, amount, number, arrayOf())

    /**
     * 发红包
     *
     * @param troopId 群号
     * @param type 红包类型
     * @param tittle 标题
     * @param amount 总金额（单位：分）
     * @param number 红包个数
     * @param exclusive 专属红包目标
     */
    override fun troopRedEnvelopes(
        troopId: Long,
        type: HbType,
        tittle: String,
        amount: Int,
        number: Int,
        exclusive: Array<Long>
    ): String {
        var seq = 1
        val skey = toolQ.recorder.getsKey().key
        val word = toolQ.account.payWord
        LuckyPacket.getTrHbPack(uin = botUin, skey = skey, seq = seq, troopId = troopId, tittle = tittle, amount = amount, num = number, type = type.busId, channel = type.channel, edition = 70, grabUinList = exclusive)
        seq++
        val token = LuckyPacket.getTrHbPack(
            uin = botUin,
            skey = skey,
            seq = seq,
            troopId = troopId,
            tittle = tittle,
            amount = amount,
            num = number,
            type = type.busId,
            channel = type.channel,
            edition = 70,
            grabUinList = exclusive
        )
        seq++
        LuckyPacket.getTrHbGate(botUin, skey, token!!, seq, 70)
        seq++
        val gate = LuckyPacket.getTrHbGate(botUin, skey, token, seq, 70)
        seq++
        val balance = LuckyPacket.balanceHb(botUin, word, token, gate!!, seq)
        return balance?.retmsg ?: "failed"
    }

    private fun getBotStatus(): BotStatus = toolQ.botStatus

    /**
     * 获取消息构建器
     */
    open fun getMessageBuilder(): MessageBuilder = MessageBuilder(toolQ)

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


