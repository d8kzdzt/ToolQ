package test

import com.google.gson.Gson
import com.toolq.ToolQManager
import com.toolq.helper.logger.LoggerEvent
import com.toolq.helper.logger.TLog
import com.toolq.qq.protocol.pay.HbType
import com.toolq.qq.protocol.pay.LuckyPacket
import com.toolq.utils.*
import com.toolq.win.RabbitMain
import okio.ByteString.Companion.decodeHex


@ExperimentalUnsignedTypes
object Main {
    lateinit var config: Config

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        RabbitMain.main(
            arrayOf(
                "data"
            )
        )

        config = Gson().fromJson(
            FileUtil.readFileString("config.dev.json"),
            Config::class.java
        )

        TLog.setEvent(object : LoggerEvent {
            override fun debug(message: Any?) {
                println(message)
            }

            override fun debug(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

            override fun info(message: Any?) {
                println(message)
            }

            override fun info(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

            override fun error(message: Any?) {
                println(message)
            }

            override fun error(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

            override fun warn(message: Any?) {
                println(message)
            }

            override fun warn(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

            override fun fatal(message: Any?) {
                println(message)
            }

            override fun fatal(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

            override fun trace(message: Any?) {
                println(message)
            }

            override fun trace(message: Any?, th: Throwable?) {
                println("$message ==> $th")
            }

        })

        test()

    }

    private fun test() {
        val testTroopId = 1038106737L

        TLog.info("Start running...")
        val toolQ = ToolQManager.addBot(
            config.qq,
            config.password,
            isHd = false)
        toolQ.addBotEvent(HandleMsg(config.qq))

        toolQ.setPayPassword(config.payWord)

        println("登录状态：" + toolQ.login())
        println(toolQ.botStatus)
        println("=====================================")
    }
}

