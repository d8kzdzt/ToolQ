package com.toolq.helper.bot.tqcode

class TQCommonFace(val index : Int, val text : String = "") : TQValue() {
    override fun getType(): String = CFACE
    override fun isCommonFace(): Boolean = true

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("id=$index")
            .toString()
}