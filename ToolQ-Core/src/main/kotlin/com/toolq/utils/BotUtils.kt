package com.toolq.utils

import kotlin.random.Random

object BotUtils {
    fun regex(regex: String, text: String): String? {
        val r = Regex(regex)
        val find = r.find(text)
        return find?.value
    }

    fun regex(first: String, last: String , text: String): String? {
        val regex = "(?<=$first).*?(?=$last)"
        return regex(regex, text)
    }

    fun OkhttpUtil.setBotCookie(uin : Long, skey : String, pskey : String, p4token : String = "") {
        header("cookie", "uin=o$uin")
        header("cookie", "skey=$skey")
        header("cookie", "p_uin=o$uin")
        header("cookie", "p_skey=$pskey")
        header("cookie", "p4token=$p4token")
        header("cookie", "qq_locale_id=2052")
    }

    fun String.toBkn() : Long = QQUtil.GetBkn(this)
}