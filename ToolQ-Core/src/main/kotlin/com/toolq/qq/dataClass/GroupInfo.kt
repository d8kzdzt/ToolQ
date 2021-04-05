package com.toolq.qq.dataClass

data class GroupInfo(
    // 群号
    val groupCode : Long,
    val groupUin : Long,
    // 群类型
    val groupFlag : Int,
    // 群主
    val ownerUin : Long,
    // 当前群成员数量
    val memberNumber : Long,
    // 群昵称
    val groupName : String,
    // 最大群成员数量
    val maxMemberNumber : Long,
    // 等级划分表
    val rankArray : List<String> = ArrayList()
)

