package com.toolq.helper.bot.tqcode

class TQFace(val faceId : Int) : TQValue() {
    override fun isFace(): Boolean = true

    override fun getType(): String = FACE

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("id=$faceId")
            .toString()
}