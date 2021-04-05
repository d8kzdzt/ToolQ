package com.toolq.helper.server

import com.google.gson.JsonObject

data class ToolQResponse(
    val ret: Int = -200,
    val data: JsonObject = JsonObject(),
    val msg: String = ""
)