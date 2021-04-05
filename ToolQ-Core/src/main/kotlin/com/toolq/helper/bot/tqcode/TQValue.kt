package com.toolq.helper.bot.tqcode

import java.util.*

open class TQValue {
    companion object {
        const val TEXT = "text"
        const val FACE  = "face"
        const val IMAGE = "image"
        const val AT = "at"
        const val CFACE = "cface"
        const val SHAKE = "shake"
        const val XML = "xml"
        const val JSON = "json"
        const val MFACE = "mface"
        const val PTT = "ptt"
        const val FLASH_IMAGE = "fimg"
        const val FLASH_TEXT = "ftxt"
        const val POKE = "poke"
        const val DICE = "dice"
        const val MORA = "mora"

        // 加密大括号内的值
        @JvmStatic
        fun encodeInput(string: String) : String = string
            .replace("%", "%1")
            .replace("[", "%2")
            .replace("]", "%3")
            .replace(",", "%4")

        // 加密大括号外的值
        @JvmStatic
        fun encodeOutput(string: String) : String = string
            .replace("%", "%1")
            .replace("[", "%2")
            .replace("]", "%3")

        @JvmStatic
        fun decodeOutput(string: String) : String = string
            .replace("%2", "[")
            .replace("%3", "]")
            .replace("%1", "%")

        @JvmStatic
        fun decodeInput(string: String) : String = string
            .replace("%4", ",")
            .replace("%3", "]")
            .replace("%2", "[")
            .replace("%1", "%")

        @JvmStatic
        fun getJoiner() = StringJoiner(",", "[TQ:", "]")
    }

    open fun isText() : Boolean = false
    open fun isFace() : Boolean = false
    open fun isImage() : Boolean = false
    open fun isAt() : Boolean = false
    open fun isCommonFace() : Boolean = false
    open fun isShake() : Boolean = false
    open fun isXml() : Boolean = false
    open fun isJson() : Boolean = false
    open fun isMFace() : Boolean = false
    open fun isPtt() : Boolean = false
    open fun isFlashImage() : Boolean = false
    open fun isFlashText() : Boolean = false
    open fun isPoke() : Boolean = false
    open fun isDice() : Boolean = false
    open fun isMora() : Boolean = false
    open fun isTable() : Boolean = false

    open fun getType() : String = "PRIVATE"

    fun toText() : TQText = this as TQText
    fun toFace() : TQFace = this as TQFace
    fun toImage() : TQImage = this as TQImage
    fun toAt() : TQAt = this as TQAt
    fun toCommonFace() : TQCommonFace = this as TQCommonFace
    fun toShake() : TQShake = this as TQShake
    fun toXml() : TQXml = this as TQXml
    fun toJson() : TQJson = this as TQJson
    fun toMFace() : TQMFace = this as TQMFace
    fun toPtt() : TQPtt = this as TQPtt
    fun toFlashImage() : TQFlashImage = this as TQFlashImage
    fun toFlashText() : TQFlashText = this as TQFlashText
    fun toPoke() : TQPoke = this as TQPoke
    fun toDice() : TQDice = this as TQDice
    fun toMora() : TQMora = this as TQMora
    // fun toTable() : TQText = this as TQText

    override fun toString(): String = ""
}