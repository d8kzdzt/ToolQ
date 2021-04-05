package com.toolq.utils

import com.toolq.BotConfig
import java.lang.Exception
import java.net.*

/**
 * @author luoluo
 * @date 2020/9/30 22:02
 */
object SocketUtil {
    /**
     * @author luoluo
     * @date 2020/9/30
     * @param host 地址
     * @param port 端口
     * @return boolean
     * @desc 检测Socket服务是否可以连接成功！
     */
    fun isRunning(host: String, port: Int): Boolean {
        var sClient: Socket? = null
        try {
            val saAdd: SocketAddress = InetSocketAddress(host.trim { it <= ' ' }, port)
            sClient = Socket()
            sClient.connect(saAdd, BotConfig.defaultPacketWaitTime.toInt())
            return true
        } catch (e: Exception) {
        } finally {
            try {
                sClient?.close()
            } catch (ignore: Throwable) { }
        }
        return false
    }
}