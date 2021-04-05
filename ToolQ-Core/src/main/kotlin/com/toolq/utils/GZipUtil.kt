package com.toolq.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


class GZipUtil {
    companion object {
        fun compress(bytes: ByteArray?): ByteArray? {
            if (bytes == null || bytes.isEmpty()) {
                return null
            }
            val out = ByteArrayOutputStream()
            val gzip: GZIPOutputStream
            try {
                gzip = GZIPOutputStream(out)
                gzip.write(bytes)
                gzip.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return out.toByteArray()
        }

        fun uncompress(bytes: ByteArray?): ByteArray? {
            if (bytes == null || bytes.isEmpty()) {
                return null
            }
            val out = ByteArrayOutputStream()
            val `in` = ByteArrayInputStream(bytes)
            try {
                val ungzip = GZIPInputStream(`in`)
                val buffer = ByteArray(256)
                var n: Int
                while (ungzip.read(buffer).also { n = it } >= 0) {
                    out.write(buffer, 0, n)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return out.toByteArray()
        }


    }
}