package com.toolq.helper.bot

import com.toolq.ToolQ
import com.toolq.helper.bot.msg.MsgHelper
import com.toolq.helper.bot.msg.MsgSender
import com.toolq.helper.bot.tqcode.*
import com.toolq.helper.bot.tqcodev2.ToolQCodeParser
import com.toolq.utils.*
import java.io.File

@ExperimentalUnsignedTypes
class MessageBuilder(private val toolQ: ToolQ) : TQCode() {
    private val sender = MsgSender(toolQ)

    private var replyMsgId = 0
    private var isReply = false

    fun setReply(msgId : Int) : MessageBuilder {
        this.isReply = true
        this.replyMsgId = msgId
        return this
    }

    fun setReply(reply : Boolean) : MessageBuilder {
        this.isReply = reply
        return this
    }

    // 是否回复
    fun isReply() = isReply

    // 获取当前回复的Id
    fun getReplyId() = replyMsgId

    fun addMsg(msgCode: String) : MessageBuilder {
        addAll(ToolQCodeParser.parse(msgCode))
        return this
    }

    fun addText(vararg string: String?) : MessageBuilder {
        string.filter { !it.isNullOrEmpty() }
                .forEach { add(TQText(it!!)) }
        return this
    }

    fun addText(vararg any: Any?) : MessageBuilder {
        any.forEach {
            addText(it.toString())
        }
        return this
    }

    fun addAt(vararg uins: Long) : MessageBuilder {
        uins.filter { QQUtil.isRightQQ(it) }
                .forEach {
                    add(TQAt(it.toULong()))
                }
        return this
    }

    fun addAt(uin: Long, space : Boolean,) : MessageBuilder {
        add(TQAt(uin.toULong(), space))
        return this
    }

    fun addCommonFace(vararg index: Int) : MessageBuilder {
        index.forEach {
                add(TQCommonFace(it))
            }
        return this
    }

    fun addFlashText(text: String, id : Int = 2000) : MessageBuilder {
        add(TQFlashText(text, id))
        return this
    }

    fun addShake() : MessageBuilder {
        add(TQShake())
        return this
    }

    fun addImageByUrl(url : String) : MessageBuilder {
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        val response = okhttp.get(url)
        if (response != null) {
            if(response.code == 200) {
                val data = response.body!!.bytes()
                CacheUtil.saveUpImage(data)
                add(TQImage(data.toMd5Byte().toHex()))
            }
        }
        return this
    }

    fun addImageByFile(file : File) : MessageBuilder {
        val data = FileUtil.readFile(file)
        CacheUtil.saveUpImage(data)
        add(TQImage(data.toMd5Byte().toHex()))
        return this
    }

    // ==================== Flash Image
    fun addFlashImageByMd5(md5 : String) : MessageBuilder {
        add(TQFlashImage(md5))
        return this
    }

    fun addFlashImageByUrl(url : String) : MessageBuilder {
        val okhttp = OkhttpUtil()
        okhttp.defaultUserAgent()
        val response = okhttp.get(url)
        if (response != null) {
            if(response.code == 200) {
                val data = response.body!!.bytes()
                CacheUtil.saveUpImage(data)
                add(TQFlashImage(data.toMd5Byte().toHex()))
            }
        }
        return this
    }

    fun addFlashImageByFile(file : File) : MessageBuilder {
        val data = FileUtil.readFile(file)
        CacheUtil.saveUpImage(data)
        add(TQFlashImage(data.toMd5Byte().toHex()))
        return this
    }

    fun addFlashImageByByteArray(bytes : ByteArray) : MessageBuilder {
        CacheUtil.saveUpImage(bytes)
        add(TQFlashImage(bytes.toMd5Byte().toHex()))
        return this
    }
    // ==================

    fun addImageByByteArray(bytes : ByteArray) : MessageBuilder {
        CacheUtil.saveUpImage(bytes)
        add(TQImage(bytes.toMd5Byte().toHex()))
        return this
    }

    fun addImageByMd5(md5 : String) : MessageBuilder {
        add(TQImage(md5))
        return this
    }

    fun addJson(json : String) : MessageBuilder {
        add(TQJson(json))
        return this
    }

    fun addXml(xml : String) : MessageBuilder {
        add(TQXml(xml))
        return this
    }

    fun sendToFriend(uin: Long) : Boolean = sender.sendToFriend(uin.toULong(), this)

    fun sendToGroup(uin: Long) : Boolean = sender.sendToGroup(uin.toULong(), this)

    fun sendToFriend(uin: ULong) : Boolean = sender.sendToFriend(uin, this)

    fun sendToGroup(uin: ULong) : Boolean = sender.sendToGroup(uin, this)
}