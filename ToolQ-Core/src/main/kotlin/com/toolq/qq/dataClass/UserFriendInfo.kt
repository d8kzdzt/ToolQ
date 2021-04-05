package com.toolq.qq.dataClass

data class UserFriendInfo(
    // qq
    val uin : Long,
    // 是否是双向好友
    val isFriend : Boolean = false,
    // 对方添加好友设置
    val addFriendSetting : AddFriendSetting = AddFriendSetting.NotAllow,
    // 加好友问题
    val userQuestion : ArrayList<String> = ArrayList()
)

enum class AddFriendSetting {
    // 允许任何人添加 0
    AllowAnyone,
    // 需要验证消息 1
    NeedVerify,
    // 需要回答正确问题 3
    AnswerRightQuestion,
    // 回答问题并由我确认 4
    AnswerQuestionForMe,
    // 不允许任何人 2
    NotAllow
}