package com.toolq.qq.dataClass

data class TroopUserAppointInfo(
    val uin : Long,
    val groupId : Long,
    val glamourLevel : Long = 0,
    val job : String = "",
    val gender : Byte = 0,
    val email : String = "",
    val nick : String = "",
    val name : String = "",
    val groupHonor : ArrayList<Int> = ArrayList()
)
