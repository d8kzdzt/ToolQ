package com.toolq.qq.dataClass

data class GroupMemberInfo(
    // 是否为Bot的好友
    val isFriend : Boolean,
    val card : String,
    val concernType : Int,
    val role : Int,
    val nick : String,
    val location : String,
    val age : Int,
    val joinTime : Long,
    val lastSpeakTime : Long,
    val isVip : Boolean,
    val isYearVip : Boolean,
    val isSuperVip : Boolean,
    val isSuperQQ : Boolean,
    val vipLevel : Int,
    val job : String,
    val phone : String,
    val level : Int,
    val credit : Int,
    val isConcerned : Boolean,
    val sex : Int,
    val specialTitle : String = "",
    val specialTitleExpireTime : Long = 0
)
