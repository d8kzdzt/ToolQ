package com.toolq.helper.bot.tqcode

class TQFlashText(val txt : String, val id : Int = 2000) : TQValue() {
    override fun isFlashText(): Boolean = true

    override fun getType(): String = FLASH_TEXT

    override fun toString(): String = getJoiner().add(getType()).add("text=${encodeInput(txt)}").also {
        if(id != 2000) it.add("id=$id")
    }.toString()
}