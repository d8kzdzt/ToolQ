package com.toolq.helper.bot.tqcode

class TQXml(val context : String) : TQValue() {
    override fun getType(): String = XML
    override fun isXml(): Boolean = true

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("content=${encodeInput(context)}")
            .toString()
}