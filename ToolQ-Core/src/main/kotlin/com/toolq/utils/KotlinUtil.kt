package com.toolq.utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.toolq.helper.packet.ByteBuilder
import com.toolq.helper.thread.ThreadManager
import java.io.File
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.util.*

fun printlnVar(vararg any : Any?) = any.forEach(System.out::println)

// 异步also
fun <T> T?.asynAlso(threadManager: ThreadManager, block: (T?) -> Unit) : T? {
    threadManager.addTask {
        val t : T? = this.also(block)
    }
    return this
}

// 异步ForEach
inline fun <T> Iterable<T>.forAsynEach(crossinline action: (T) -> Unit): Unit {
    for (element in this) {
        ThreadManager.instance.addTask {
            action(element)
        }
    }
}

// 简介大方的tryCatch
fun <T : Throwable?, U : Throwable?> T.catch(u : U?, block: () -> Unit) : T {
    block()
    return this
}

infix fun <T : Throwable?> T.finally(block: () -> Unit) : T {
    block()
    return this
}

fun <T : Throwable> fastCatch(block: () -> Unit) : T? {
    return fastCatch(block, {})
}

fun <T : Throwable> fastCatch(block: () -> Unit, finally : () -> Unit = {}) : T? {
    try {
        block()
    } catch (t : Throwable) {
        return t as T
    } finally {
        finally()
    }
    return null
}

fun currentTimeSecond() = System.currentTimeMillis() / 1000

fun Long.codeToUin() : Long = ToolQUtil.groupCode2GroupUin(this)

fun Long.uinToCode() : Long = ToolQUtil.groupUin2GroupCode(this)

fun String.matches(regex: String): Boolean {
    return Regex(regex).matches(this)
}

fun String.toFile() : File = File(this)

fun String.formatToJson() : String {
    return String(toByteArray().toHex().replace("00".toRegex(), "").hexToBs())
}

fun ByteBuffer.getString(len : Int) = String(get(ByteArray(len)).array())

fun ByteArray.toHex(): String = HexUtil.bytesToHexString(this)

fun ByteArray.toMd5Byte() : ByteArray = MD5.toMD5Byte(this)

fun ByteArray.withSize(add : Int) : ByteArray = ByteBuilder(this).writeSize(add).toByteArray()

fun ByteArray.withSize() : ByteArray = withSize(0)

fun ByteBuffer.putBoolean(z : Boolean) = this.put(if(z) 1.toByte() else 0.toByte())

fun ByteBuffer.putInt(j : Long) = this.putInt(j.toInt())

infix fun Long.toIp(def: String) : String {
    return try {
        (this and 0x000000FF).toString() + "." + (this and 0x0000FFFF shr 8) + "." + (this and 0x00FFFFFF shr 16) + "." + (this shr 24)
    } catch (e: Exception) {
        def
    }
}

fun String.toIpLong() : Long = QQUtil.ipToLong(this)

fun String.hexToBs() : ByteArray = HexUtil.hexStringToBytes(this)

fun <E> ArrayList<E>.addAll(elements: Array<E>) {
    elements.forEach {
        this.add(it)
    }
}

fun ArrayList<Int>.addAll(elements: ByteArray) {
    elements.forEach {
        this.add(it.toInt())
    }
}

fun JsonObject.add(k: String, v : String?) = addProperty(k, v)

@ExperimentalUnsignedTypes
fun JsonObject.add(k: String, v : UInt) = addProperty(k, v.toInt())

@ExperimentalUnsignedTypes
fun JsonObject.add(k: String, v : ULong) = addProperty(k, v.toLong())

fun JsonObject.add(k: String, v : Number) = addProperty(k, v)

fun JsonObject.add(k: String, v : Boolean) = addProperty(k, v)

fun JsonObject.add(k: String, v : Char) = addProperty(k, v)

fun JsonObject.addArray(k: String, v : EasyJsonArray) {
    val jsonArray = JsonArray()
    v.doInit(jsonArray)
    add(k, jsonArray)
}

fun JsonObject.addObject(k: String, v : EasyJsonObject) {
    val jsonObject = JsonObject()
    v.doInit(jsonObject)
    add(k, jsonObject)
}

fun JsonArray.addObject(v : EasyJsonObject) {
    val jsonObject = JsonObject()
    v.doInit(jsonObject)
    add(jsonObject)
}

fun interface EasyJsonArray {
    fun doInit(jsonArray: JsonArray)
}

fun interface EasyJsonObject {
    fun doInit(jsonObject: JsonObject)
}