package com.toolq.helper.bot.tqcode

import com.google.gson.JsonParser

class TQJson(val context : String) : TQValue() {
    init {
        try {
            JsonParser().parse(context)
        } catch (e : Exception) {
            throw RuntimeException("This json message is illegal...\n${e.message}")
        }
    }

    override fun getType(): String = JSON
    override fun isJson(): Boolean = true

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("content=${encodeInput(context)}")
            .toString()

    fun format() : String = JsonParser().parse(context).toString()
}