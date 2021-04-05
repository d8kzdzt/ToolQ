package com.toolq.helper.bot.tqcode

class TQPoke(val id : Int, val size : Int = 0, val extId : Long = 4294967295L) : TQValue() {

    override fun isPoke(): Boolean = true

    override fun getType(): String = POKE

    override fun toString(): String = getJoiner()
        .add(getType())
        .add("id=$id")
        .apply {
            if(size != 0) add("size=$size")
            if(extId != 4294967295) add("extId=$extId")
        }
        .toString()
}