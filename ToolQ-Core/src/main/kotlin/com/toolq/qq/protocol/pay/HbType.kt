package com.toolq.qq.protocol.pay

enum class HbType(val busId: Int ,
                  val channel : Int) {
    /**
     * 普通红包
     */
    Common(1, 1),

    /**
     * 拼手气红包
     */
    Lucky(2, 1),

    /**
     * 接龙红包
     */
    Proverb(2, 524288),

    /**
     * 专属普通
     */
    ExclusiveComm(1, 1024),

    /**
     * 专属拼手气
     */
    ExclusiveLucky(2, 1024),

    /**
     * 口令红包
     */
    Password(2, 32),

    /**
     * 语音红包
     */
    Voice(2, 65536)
}