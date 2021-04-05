package com.toolq.helper.bot.tqcodev2

import com.toolq.helper.bot.tqcode.*
import com.toolq.helper.bot.tqcode.TQValue.Companion.decodeInput
import com.toolq.helper.bot.tqcode.TQValue.Companion.decodeOutput
import com.toolq.helper.bot.tqcode.exception.TQCodeException
import com.toolq.helper.logger.TLog

@ExperimentalUnsignedTypes
class ToolQCodeParser {
    @ExperimentalUnsignedTypes
    companion object {
        private const val START = "[TQ:"
        private const val END = "]"

        fun parse(code : String) : List<TQValue> {
            return cut(code).map { cutCode ->
                if(cutCode.isCode) {
                    try {
                        val hashCode = parseValue(cutCode.text)
                        val map = hashCode.map
                        when(hashCode.key.trim()) {
                            TQValue.AT -> TQAt(map["uin"]!!.toULong(), map.getOrDefault("space", "false").toBoolean())
                            TQValue.TEXT -> TQText(decodeOutput(map["text"]!!))
                            TQValue.IMAGE -> TQImage(map["md5"]!!)
                            TQValue.FACE -> TQFace(map["id"]!!.toInt())
                            TQValue.FLASH_IMAGE -> TQFlashImage(map["md5"]!!)
                            TQValue.FLASH_TEXT -> TQFlashText(decodeInput(map.getOrDefault("text", "")), map.getOrDefault("id", "2000").toInt())
                            TQValue.CFACE -> TQCommonFace(map["id"]!!.toInt())
                            TQValue.SHAKE -> TQShake()
                            TQValue.XML -> TQXml(decodeInput(map["content"]!!))
                            TQValue.JSON -> TQJson(decodeInput(map["content"]!!))
                            TQValue.MFACE -> TQMFace(map["id"]!!)
                            TQValue.PTT -> TQPtt(map["md5"]!!).apply {
                                if(map.containsKey("magic")) {
                                    this.magicVoice = map["magic"]!!.toBoolean()
                                }
                                if(map.containsKey("time")) {
                                    this.time = map["time"]!!.toInt()
                                }
                            }
                            TQValue.POKE -> TQPoke (map["id"]!!.toInt(), map.getOrDefault("size", "0").toInt(), map.getOrDefault("extId", "4294967295").toLong())
                            TQValue.DICE -> TQDice(map["size"]!!.toInt())
                            TQValue.MORA -> TQMora(map["num"]!!.toInt())

                            else -> {
                                TLog.warn("不支持的TQ码[${hashCode.key}]")
                                TQText("")
                            }
                        }
                    } catch (t : Throwable) {
                        throw TQCodeException(t)
                    }
                }
                else TQText(decodeOutput(cutCode.text))
            }
        }

        private fun parseValue(text : String) : HashCode {
            var code = text.trim()
            if(code.startsWith(START) && code.endsWith(END)) {
                code = code.let {
                    val tmp = it.substring(START.length, it.length)
                    tmp.substring(0, tmp.length - 1)
                }
                val cut = code.split(",")
                var key = ""
                val map = HashMap<String, String>()
                repeat(cut.size) {
                    if(it == 0) {
                        key = cut[it]
                    } else {
                        val kv = cut[it].split("=")
                        map[kv[0]] = kv.let {
                            var v = ""
                            for (i in 1 until kv.size) {
                                v += "${if(i % 2 == 0) "=" else ""}${kv[i]}${if(i % 2 == 0 && i != kv.size - 1) "=" else ""}"
                            }
                            v
                        }
                    }
                }
                return HashCode(key, map)
            }
            else {
                return HashCode(TQValue.TEXT, mapOf("text" to code))
            }
        }

        private fun cut(string: String) : List<CutCode> {
            var isReading = false
            val cs: CharArray = string.toCharArray()
            val list = ArrayList<CutCode>()
            var sb = java.lang.StringBuilder()
            for (c in cs) {
                when (c) {
                    '[' -> {
                        if (isReading) {
                            throw TQCodeException("Repeat the first grammatical error.")
                        }
                        isReading = true
                        if (sb.isNotEmpty()) {
                            list.add(CutCode(sb.toString()))
                            sb = StringBuilder()
                        }
                        sb.append(c)
                    }
                    ']' -> {
                        if (!isReading) {
                            throw TQCodeException("A grammatical error that ends without beginning.")
                        }
                        isReading = false
                        sb.append(c)
                        list.add(CutCode(sb.toString(), true))
                        sb = StringBuilder()
                    }
                    else -> {
                        sb.append(c)
                    }
                }
            } // for cs
            if(sb.isNotEmpty()) list.add(CutCode(sb.toString()))
            return list
        }

        private data class CutCode(val text: String, val isCode : Boolean = false)

        private data class HashCode(val key : String, val map : Map<String, String> = HashMap())
    }
}

