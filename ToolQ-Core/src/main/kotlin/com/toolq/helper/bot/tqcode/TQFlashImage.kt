package com.toolq.helper.bot.tqcode

import com.toolq.utils.*

// QQ闪图
class TQFlashImage private constructor() : TQValue() {
    override fun isFlashImage(): Boolean = true
    override fun getType(): String = FLASH_IMAGE

    var md5 : String = ""
    var url : String = ""

    constructor(md5 : String) : this() {
        if (md5.matches("([a-f\\d]{16,36}|[A-F\\d]{16,36})")) {
            this.md5 = md5
            this.url = getPicUrl()
        } else {
            throw RuntimeException("Be sure to give a correct MD5!")
        }
    }

    // 如果图片为群聊接收的图片则可通过md5向腾讯群聊图片服务器获取该图片
    fun getPicUrl() : String = "http://gchat.qpic.cn/gchatpic_new/0/0-0-${md5.toUpperCase()}/0?term=2"

    // 如果图片为私聊接收的图片则可通过md5向腾讯私聊图片服务器获取该图片
    // fun getC2CUrl() : String = "http://c2cpicdw.qpic.cn/offpic_new/0/0-0-${md5.toUpperCase()}/0?term=2"
    // 实验证明即使是私聊产生的聊天图片依然可以使用群聊图片服务器获取该图片，跳过resid的获取

    override fun toString(): String = getJoiner()
        .add(getType())
        .add("md5=$md5")
        .toString()
}