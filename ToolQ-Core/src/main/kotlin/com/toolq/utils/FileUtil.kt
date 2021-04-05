package com.toolq.utils

import com.toolq.helper.logger.TLog.warn
import kotlin.Throws
import java.io.*

/**
 * @author luoluo
 * @date 2020/10/3 21:50
 */
object FileUtil {
    @Throws(IOException::class)
    fun readFile(f: File): ByteArray {
        return readFile(f.absolutePath)
    }

    @Throws(IOException::class)
    fun readFile(filename: String?): ByteArray {
        return readFileBytes(filename)
    }

    @Throws(IOException::class)
    fun readFileString(f: File): String {
        return String(readFile(f))
    }

    @JvmStatic
    @Throws(IOException::class)
    fun readFileString(path: String?): String {
        return String(readFileBytes(path))
    }

    @Throws(IOException::class)
    fun readFileBytes(path: String?): ByteArray {
        return readFileBytes(FileInputStream(path))
    }

    @Throws(IOException::class)
    fun readFileBytes(inputStream: InputStream): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        while (true) {
            val read = inputStream.read()
            if (read == -1) {
                inputStream.close()
                val byteArray = byteArrayOutputStream.toByteArray()
                byteArrayOutputStream.close()
                return byteArray
            }
            byteArrayOutputStream.write(read)
        }
    }

    @Throws(IOException::class)
    fun saveFile(path: String, content: String) {
        saveFile(path, content.toByteArray())
    }

    @Throws(IOException::class)
    fun saveFile(path: String, content: ByteArray) {
        val file = File(path)
        if (!file.exists()) {
            if (!file.createNewFile()) {
                warn(String.format("【%s】文件创建失败！", path))
                return
            }
        }
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(content)
        fileOutputStream.close()
    }

}