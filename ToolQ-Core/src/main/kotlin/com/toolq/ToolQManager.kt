package com.toolq

import com.toolq.helper.bot.QQAccount
import java.util.concurrent.ConcurrentHashMap

/**
 * @author luoluo
 * @date 2020/10/1 21:25
 */
object ToolQManager {
    private val botMap = ConcurrentHashMap<Long, ToolQ>()

    @JvmStatic
    fun addBot(qq: Long, password: String, isHd : Boolean = false): ToolQ {
        if (botMap.containsKey(qq)) {
            return botMap[qq] as ToolQ
        }
        val account = QQAccount(qq, password)
        val toolQ = ToolQ(account, isHd)
        botMap[qq] = toolQ
        return toolQ
    }

    @JvmStatic
    fun getBot(qq: Long): ToolQ? {
        return botMap[qq]
    }

    @JvmStatic
    fun shutBot(qq: Long) : Boolean {
        if(botMap.containsKey(qq)) {
            val toolQ = botMap[qq] as ToolQ
            toolQ.shut()
            removeBot(toolQ, qq)
            return true
        }
        return false
    }

    @JvmStatic
    private fun removeBot(toolQ: ToolQ, qq: Long) {
        botMap.remove(qq, toolQ)
    }
}