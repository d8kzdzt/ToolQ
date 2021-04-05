package com.toolq.helper.server

import com.toolq.utils.JsonUtil
import com.toolq.utils.OkhttpUtil

class ToolQServer(private val cmd : String) {
    companion object {
        fun create(cmd : String) : ToolQServer = ToolQServer(cmd)

        @JvmStatic
        private val host : String = "https://luololi.cn/"
    }

    private val params : HashMap<String, String> = HashMap()

    // 超时时间（秒）
    private var timeoutMs : Long = 10

    fun addParams(name : String, value : String) = params.put(name, value)

    fun setTimeOutMs(timeout : Long) {
        this.timeoutMs = timeout
    }

    fun request() : ToolQResponse {
        val okhttp = OkhttpUtil(
            ssl = true,
            proxy = false
        )
        okhttp.defaultUserAgent()
        okhttp.readTimeOut = timeoutMs
        okhttp.connectTimeout = timeoutMs
        okhttp.writeTimeout = timeoutMs
        val response = okhttp.post("$host?s=$cmd", params)
        return if (response != null) {
            if(response.code == 200) {
                val body = response.body?.string()
                val json = JsonUtil.parseJsonObject(body)
                ToolQResponse(
                    json.get("ret").asInt,
                    json.get("data").asJsonObject,
                    json.get("msg").asString
                )
            } else {
                ToolQResponse()
            }
        } else ToolQResponse()
    }
}