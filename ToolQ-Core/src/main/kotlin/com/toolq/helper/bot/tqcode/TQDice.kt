package com.toolq.helper.bot.tqcode

import com.toolq.utils.RandomUtil

class TQDice(val size : Int = RandomUtil.randInt(1 .. 6)) : TQValue() {

    override fun isDice(): Boolean = true

    override fun getType(): String = DICE

    override fun toString(): String = getJoiner()
        .add(getType())
        .add("size=$size")
        .toString()
}