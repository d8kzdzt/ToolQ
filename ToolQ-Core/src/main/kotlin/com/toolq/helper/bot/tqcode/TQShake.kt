package com.toolq.helper.bot.tqcode

class TQShake : TQValue() {
    override fun isShake(): Boolean = true
    override fun getType(): String = SHAKE

    override fun toString(): String = getJoiner().add(getType()).toString()
}