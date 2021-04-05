package com.toolq.utils

import java.util.*

class XmlUtil {
    companion object {
        fun serviceId(xml : String) : Int {
            val noSpace = xml.replace(" ", "")
            val lowCase = noSpace.toLowerCase()
            val splitStart = lowCase.split("serviceid=\"")[1]
            val splitEnd = splitStart.split("\"")[0]
            return splitEnd.trim().toInt()
        }
    }
}