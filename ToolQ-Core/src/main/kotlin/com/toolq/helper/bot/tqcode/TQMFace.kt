package com.toolq.helper.bot.tqcode

class TQMFace(val faceId : String) : TQValue() {
    override fun getType(): String = MFACE
    override fun isMFace(): Boolean = true

    val url = "https://i.gtimg.cn/club/item/parcel/item/" +
                faceId.substring(0, 2) +
                "/" + faceId + "/300x300.png"

    override fun toString(): String = getJoiner()
            .add(getType())
            .add("id=${encodeInput(faceId)}")
            .toString()
}