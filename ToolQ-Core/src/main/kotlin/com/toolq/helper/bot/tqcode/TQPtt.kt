package com.toolq.helper.bot.tqcode

import com.toolq.utils.*

class TQPtt(md5: String) : TQValue() {
    val md5: String

    init {
        if(md5.matches("([a-f\\d]{16,36}|[A-F\\d]{16,36})"))
            this.md5 = md5
        else {
            throw RuntimeException("Be sure to give a correct MD5!")
        }
    }

    override fun isPtt(): Boolean = true
    override fun getType(): String = PTT

    // 是否是魔法语音
    var magicVoice : Boolean = false

    // 音频时长（发送音频可以提供）
    var time : Int = 0

    // 下载链接（发送音频无需提供）
    var downUrl = ""

    // uuid 特别标识
    // uin 语音上传者
    // 仅在接收音频时提供
    var uuid : String? = null
    var uin : Long? = null

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("md5=$md5")
            .run {
                if(magicVoice) add("magic=$magicVoice") else this
            }
            .toString()
}