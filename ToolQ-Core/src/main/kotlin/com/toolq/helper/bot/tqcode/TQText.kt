package com.toolq.helper.bot.tqcode

class TQText(val text : String) : TQValue() {
    override fun isText() : Boolean = true

    override fun getType(): String = TEXT

    override fun toString(): String = encodeOutput(text)
}