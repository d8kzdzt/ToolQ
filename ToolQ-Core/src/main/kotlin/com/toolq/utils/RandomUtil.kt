package com.toolq.utils

import java.lang.StringBuilder
import java.util.*

/**
 * @author luoluo
 * @date 2020/10/4 1:17
 */
object RandomUtil {
    @JvmStatic
    fun getRandomString(length: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuilder()
        for (i in 0 until length) {
            val number = random.nextInt(62)
            sb.append(str[number])
        }
        return sb.toString()
    }

    @JvmStatic
    fun randInt(intRange: IntRange) = randInt(intRange.first, intRange.last)

    @JvmStatic
    fun randInt(min: Int, max: Int): Int {
        return Random().nextInt(max - min) + min
    }

    @JvmStatic
    fun randomMd5(): String {
        return getRandomString(16).toLowerCase()
    }

    fun randInt(intRange: IntProgression): Int = randInt(intRange.first, intRange.last)
}