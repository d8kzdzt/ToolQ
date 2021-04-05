package com.toolq.helper.bot.tqcode

import com.toolq.helper.bot.tqcodev2.ToolQCodeParser

@ExperimentalUnsignedTypes
open class TQCode : ArrayList<TQValue>() {
    @ExperimentalUnsignedTypes
    companion object {
        fun parse(str : String) : TQCode {
            val ret = TQCode()
            val list = ToolQCodeParser.parse(str)
            ret.addAll(list)
            return ret
        }
    }

    // 判断TQ码中是否保函图片
    fun hasImage() : Boolean {
        forEach {
         if(it.isImage()) return true
        }
        return false
    }

    fun getImages() : List<TQImage> {
        val list = ArrayList<TQImage>()
        forEach {
            if(it.isImage()) list.add(it.toImage())
        }
        return list
    }

    fun getText() : String {
        val builder = StringBuffer()
        forEach {
            if(it.isText()) builder.append(it.toText().text)
        }
        return builder.toString()
    }

    override fun toString(): String {
        val result = StringBuilder()
        this.forEach {
            result.append(it.toString())
        }
        return result.toString()
    }
}
