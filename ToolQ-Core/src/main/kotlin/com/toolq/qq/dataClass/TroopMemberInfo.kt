package com.toolq.qq.dataClass

data class TroopMemberInfo(
    // qq
    val uin : Long,
    // 性别
    val gender : Byte,
    // 年龄
    val age : Byte,
    // 名称
    val nick : String,
    // 在线状态
    val status : Int,
    // 工作
    val job : String,
    // 手机号
    val phone : String,
    // 邮件
    val email : String,
    // 介绍
    val memo : String,
    // 默认备注
    val autoRemark : String,
    // 群成员等级
    val memberLevel : Long,
    // 加入时间
    val joinTime : Long,
    // 最后发言时间
    val lastTime : Long,
    // 信用等级
    val creditLevel : Long,
    // 头衔
    val specialTitle : String,
    // 头衔过期时间
    val specialTitleExpireTime : Long,
    // 禁言结束时间
    val shutUpTimeStamp : Long,
    // vip类型
    val vipType : Long,
    // vip等级
    val vipLevel : Long,
    // 群荣誉
    val groupHonor : List<Int>
)
