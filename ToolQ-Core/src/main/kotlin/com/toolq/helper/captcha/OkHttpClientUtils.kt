package com.toolq.helper.captcha

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.toolq.utils.BotUtils
import okhttp3.*
import java.util.concurrent.TimeUnit

object OkHttpClientUtils {
    private const val TIME_OUT = 10L
    const val QQ_UA = "Mozilla/5.0 (Linux; Android 10; M2002J9E Build/QKQ1.191222.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045332 Mobile Safari/537.36 V1_AND_SQ_8.4.10_1506_HDBM_T QQ/8.4.10.4830 NetType/4G WebP/0.3.0 Pixel/1080 StatusBarHeight/70 SimpleUISwitch/0 QQTheme/1000 InMagicWin/0"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

    fun get(url: String, headers: Headers = Headers.Builder().build()): Response{
        val request = Request.Builder().url(url).headers(headers).build()
        return okHttpClient.newCall(request).execute()
    }

    fun post(url: String, requestBody: RequestBody = FormBody.Builder().build(), headers: Headers = Headers.Builder().build()): Response{
        val request = Request.Builder().url(url).post(requestBody).headers(headers).build()
        return okHttpClient.newCall(request).execute()
    }

    fun put(url: String, requestBody: RequestBody = FormBody.Builder().build(), headers: Headers = Headers.Builder().build()): Response{
        val request = Request.Builder().url(url).put(requestBody).headers(headers).build()
        return okHttpClient.newCall(request).execute()
    }

    fun delete(url: String, requestBody: RequestBody = FormBody.Builder().build(), headers: Headers = Headers.Builder().build()): Response{
        val request = Request.Builder().url(url).delete(requestBody).headers(headers).build()
        return okHttpClient.newCall(request).execute()
    }

    fun addHeader(name: String, value: String) = Headers.Builder().add(name, value).build()

    fun addCookie(value: String) = addHeader("cookie", value)

    fun addReferer(value: String) = addHeader("Referer", value)

    fun addUA(value: String) = addHeader("user-agent", value)

    fun header(build : Headers.Builder): Headers {
        return build.build()
    }

    fun add(name : String,  value : String): Headers.Builder {
        return Headers.Builder().add(name, value)
    }

    fun getCookie(response: Response, vararg names: String): Map<String, String>{
        val map = HashMap<String, String>()
        val cookies = response.headers("Set-Cookie")
        for (cookie in cookies){
            val newCookie = BotUtils.regex(".*?;", cookie)?.removeSuffix(";")
            val arr = newCookie!!.split("=")
            for (name in names){
                if (name == arr[0] && arr[1] != "") {
                    map[arr[0]] = arr[1]
                }
            }
        }
        return map
    }

    fun getCookie(response: Response): String{
        val sb = StringBuilder()
        val cookies = response.headers("Set-Cookie")
        for (cookie in cookies){
            if ("deleted" in cookie) continue
            sb.append("${BotUtils.regex(".*?;", cookie)} ")
        }
        return sb.toString()
    }

    @JvmStatic
    fun addHeaders(vararg headers: String): Headers{
        val builder = Headers.Builder()
        for (i in headers.indices step 2){
            builder.add(headers[i], headers[i+1])
        }
        return builder.build()
    }

    fun addForms(vararg forms: String): RequestBody{
        val builder = FormBody.Builder()
        for (i in forms.indices step 2){
            builder.add(forms[i], forms[i+1])
        }
        return builder.build()
    }

    fun getStr(response: Response) = response.body!!.string()

    fun getStr(response: Response, regex: String) = BotUtils.regex(regex, getStr(response))

    fun getJson(response: Response): JsonObject = JsonParser.parseString(getStr(response)) as JsonObject

    fun getJson(response: Response, regex: String): JsonObject = JsonParser.parseString(getStr(response, regex)) as JsonObject

    fun getBytes(response: Response) = response.body!!.bytes()

}

data class CommonResult<T>(
        val code: Int,
        val msg: String,
        val t: T? = null
)