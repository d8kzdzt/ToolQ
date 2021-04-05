package com.toolq.qq.protocol.friendlist

import com.toolq.helper.jce.JceHelper
import com.toolq.qq.protocol.jce.friendlist.GetFriendListReq
import com.toolq.utils.HexUtil

class GetFriendList {
    companion object {
        private val vec0xd50 = HexUtil.hexStringToBytes("08 92 4E C8 B7 2C 01 C8 A8 31 01 C8 DD 49 01 C8 B0 58 01 C8 C7 7A 01")

        fun makeGetFriendPacket(requestId : Int, uin : Long, start : Int, friendCount : Int) : ByteArray {
            val req = GetFriendListReq()
            req.reqtype = 0
            req.ifReflush = 1
            req.uin = uin
            req.startIndex = start.toShort()
            req.getfriendCount = friendCount.toShort()
            req.groupid = 0
            req.ifGetGroupInfo = 1
            req.groupstartIndex = 0
            req.getgroupCount = 0
            req.ifGetMSFGroup = 0
            req.ifShowTermType = 1
            req.version = 31
            req.eAppType = 0
            req.ifGetDOVId = 0
            req.vec0xd50Req = vec0xd50
            req.vecSnsTypelist = ArrayList()
            req.vecSnsTypelist.also {
                it.add(13580)
                it.add(13581)
                it.add(13582)
            }
            return JceHelper.encodePacket(req, "mqq.IMService.FriendListServiceServantObj", "GetFriendListReq", "FL", requestId)
        }
    }
}