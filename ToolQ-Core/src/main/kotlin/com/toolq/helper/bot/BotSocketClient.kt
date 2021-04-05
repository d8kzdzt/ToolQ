package com.toolq.helper.bot

import com.toolq.ToolQ
import com.toolq.ToolQManager
import com.toolq.helper.logger.TLog
import com.toolq.helper.socket.SocketClient
import com.toolq.helper.thread.ThreadManager
import com.toolq.utils.fastCatch
import java.net.SocketException

class BotSocketClient(private val toolQ: ToolQ) : SocketClient() {

    override fun allowReceive(threadManager: ThreadManager, type: Int) {
        super.allowRece = true
        super.receTask = threadManager.addTask(object : Thread("AllowReceive") {
            override fun run() {
                if(allowRece) body(type)
            }

            private fun body(type: Int) {
                val error = fastCatch<Throwable> {
                    if(allowRece) checkBody(type)
                }?.let { e ->
                    if ( (e is SocketException || e is java.io.EOFException) && toolQ.botStatus != BotStatus.remoteLanding) {
                        allowRece = false
                        // 断线
                        ToolQ.setBotStatus(toolQ, BotStatus.reconnecting)
                        // 重连
                        ToolQManager.shutBot(toolQ.account.user)
                        val startTime = System.currentTimeMillis()
                        var time = 0L
                        while (toolQ.botStatus != BotStatus.remoteLanding) {
                            if (System.currentTimeMillis() > (startTime + 24 * 60 * 60 * 1000)) break
                            val tq = ToolQManager.addBot(toolQ.account.user, toolQ.account.password)
                            if (tq.login() == LoginResult.Success) {
                                TLog.warn("断线重连尝试[$time],尝试成功！")
                                toolQ.eventVector.forEach {
                                    tq.addBotEvent(it)
                                }
                                ToolQ.setBotStatus(toolQ, BotStatus.ruin)
                                break
                            } else {
                                ToolQManager.shutBot(toolQ.account.user)
                                TLog.warn("断线重连尝试[$time],尝试失败！")
                            }
                            sleep(10 * 1000)
                            time++
                        }
                        ToolQ.setBotStatus(toolQ, BotStatus.ruin)
                    } else {
                        // e.printStackTrace()
                        TLog.warn("ERROR", e)
                        if(allowRece) body(type)
                    }
                }
            }


        })
    }

    private fun checkBody(type: Int) {
        while (!client!!.isInputShutdown && allowRece) {
            when (type) {
                Common -> {
                    val data = clientInPut!!.read()
                    if (listener != null && data != null) {
                        listener!!.onReceive(data)
                    }
                }
                HighWay -> {
                    val set = clientInPut!!.readHighWay()
                    if (listener != null && set != null) {
                        listener!!.onHighWayReceive(set[0], set[1])
                    }
                }
            }
        }
    }

}