package com.toolq.utils

class ImageUtil {
    companion object {
        fun ByteArray?.isGif() : Boolean {
            this?.let {
                if(it.size > 6) return it[0].toInt() == 0x47
                        && it[1].toInt() == 0x49
                        && it[2].toInt() == 0x46
                        && it[3].toInt() == 0x38
                        && (it[4].toInt() == 0x39 || it[4].toInt() == 0x37 )
                        && it[5].toInt() == 0x61
                // GIF文件版本号：
                // 87a - 1987年5月　　　　　　
                // 89a - 1989年7月
            }
            return false
        }

        fun ByteArray?.isJpeg() : Boolean {
            this?.let {
                if(it.isNotEmpty()) {
                    return it[0].toInt() + it[1].toInt() == 0xff + 0xd8 &&
                            it[6] == 'J'.toByte() &&
                            it[7] == 'F'.toByte() &&
                            it[8] == 'I'.toByte() &&
                            it[6] == 'F'.toByte()
                }
            }
            return false
        }

        fun ByteArray?.isPng() : Boolean {
            this?.let {
                if(it.isNotEmpty()) {
                    return it[0].toInt() == 0x89 &&
                            it[1] == 'P'.toByte() &&
                            it[2] == 'N'.toByte() &&
                            it[3] == 'G'.toByte() &&
                            it[4].toInt() == 0x0d &&
                            it[5].toInt() == 0x0a &&
                            it[6].toInt() == 0x1a &&
                            it[7].toInt() == 0x0a
                }
            }
            return false
        }
    }
}