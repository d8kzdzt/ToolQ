package com.toolq.helper.bot.tqcode

import com.toolq.utils.RandomUtil

class TQMora(val number : Int = RandomUtil.randInt(0 .. 2)) : TQValue() {
    override fun isMora(): Boolean = true

    override fun getType(): String = MORA

    override fun toString(): String = getJoiner()
        .add(getType())
        .add("num=$number")
        .toString()
}


