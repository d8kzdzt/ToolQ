package com.toolq.qq.dataClass

interface FriendAdder {
    // 加好友验证消息
    fun msg() : String

    // 给好友的备注
    fun remark() : String?

    // 回答问题
    fun answer(question : String) : String
}