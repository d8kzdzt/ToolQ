package com.toolq.helper.bot.tqcode

class TQAt @ExperimentalUnsignedTypes constructor(val uin : ULong, val hasSpace : Boolean = false) : TQValue() {
    var flag : Byte = 0
    var strLength : Long = 0;
    var startPos : Int = 0

    override fun isAt(): Boolean = true
    override fun getType(): String = AT

    @ExperimentalUnsignedTypes
    override fun toString(): String = getJoiner().add(getType())
            .also {
                it.add("uin=${uin.toString()}")
                if(hasSpace) {
                    it.add("space=$hasSpace")
                }
            }
            .toString()
}