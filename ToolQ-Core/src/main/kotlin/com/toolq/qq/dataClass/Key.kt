package com.toolq.qq.dataClass

data class StringKey(val key : String, val creationTime : Long, val expiredTime : Long)

data class BytesKey(val key : ByteArray, val creationTime : Long, val expiredTime : Long) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BytesKey

        if (!key.contentEquals(other.key)) return false
        if (creationTime != other.creationTime) return false
        if (expiredTime != other.expiredTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.contentHashCode()
        result = 31 * result + creationTime.hashCode()
        result = 31 * result + expiredTime.hashCode()
        return result
    }
}

data class UserSt(
    var webSig : BytesKey,
    var sig : BytesKey,
    var key : BytesKey
)