package com.toolq.utils

import java.nio.ByteBuffer
import java.security.Key
import javax.crypto.Cipher

import javax.crypto.spec.SecretKeySpec




object DesECBUtil {
    /**
     * 加密数据
     * @param encryptString  注意：这里的数据长度只能为8的倍数
     * @param encryptKey
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptDES(encryptString: String, encryptKey: String): String {
        val key = SecretKeySpec(getKey(encryptKey), "DES")
        val cipher = Cipher.getInstance("DES/ECB/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedData = cipher.doFinal(setStuff(encryptString.toByteArray()))
        return encryptedData.toHex()
    }

    /**
     * 自定义一个key
     * @param
     */
    private fun getKey(keyRule: String): ByteArray {
        val key: Key?
        val keyByte = keyRule.toByteArray()
        // 创建一个空的八位数组,默认情况下为0
        val byteTemp = ByteArray(8)
        // 将用户指定的规则转换成八位数组
        var i = 0
        while (i < byteTemp.size && i < keyByte.size) {
            byteTemp[i] = keyByte[i]
            i++
        }
        key = SecretKeySpec(byteTemp, "DES")
        return key.getEncoded()
    }

    /***
     * 解密数据
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decryptDES(decryptString: String, decryptKey: String): String {
        val key = SecretKeySpec(getKey(decryptKey), "DES")
        val cipher = Cipher.getInstance("DES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedData = cipher.doFinal(decryptString.hexToBs())
        return String(decryptedData)
    }

    //填充数据
    private fun setStuff(msg: ByteArray): ByteArray {
        val i = msg.size % 8
        return if (i == 0) {
            msg
        } else {
            val a = 8 - i
            val sb = StringBuffer()
            for (s in 0 until a) {
                sb.append(" ")
            }
            var data = sb.toString().toByteArray()
            data = ByteBuffer.allocate(msg.size + data.size).put(msg).put(data).array()
            data
        }
    }
}