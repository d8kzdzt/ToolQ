package com.toolq.helper.bot

data class MsgInfo(
    private val shareId : Int,
    private val pkgName : String,

    val fromAppid : Int,
    val msgUid : Long) {

    var isReply = false
    var replyUin = 0L
    var replyMsgId = 0

    // AppShare消息信息
    val appShare = AppShare(shareId, pkgName)
    // 个性diy气泡ID
    var bubbleDiyTextId = 0
}

data class AppShare(val shareId : Int, val pkgName : String)