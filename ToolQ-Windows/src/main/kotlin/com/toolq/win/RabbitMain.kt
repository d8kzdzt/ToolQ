package com.toolq.win

import com.toolq.helper.logger.TLog
import java.io.File

class RabbitMain {
    private fun gui() {

    }

    companion object {
        // 控制台模式
        const val cmdMode = false

        @JvmStatic
        fun main(args: Array<String>) {
            TLog.debug = true
            // args[0] 数据文件路径
            InitManager.init(args[0])
            if(cmdMode) {

            } else RabbitMain().gui()
        }
    }
}