package com.toolq.qq.dataClass

data class FriendInfo(
    // QQ号码
    val uin : Long,
    // 昵称
    val nick : String,
    // 好友分组id
    val groupId : Byte,
    // 备注
    val remark : String,
    // 在线状态
    val status : Byte,
    // 显示在线状态
    val extStatus : Long
)
