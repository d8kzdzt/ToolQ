package com.toolq.win

import com.toolq.BotConfig
import com.toolq.helper.bot.IBotFunctionWindow
import com.toolq.utils.toFile
import com.toolq.win.GUIUtil.Companion.irretrievableReminder

class InitManager {
    companion object {
        fun init(path : String) {
            BotConfig.setOs("win")
            BotConfig.init(path.toFile().let {
                if(!it.exists()) {
                    if(!it.mkdirs()) irretrievableReminder("创建数据文件路径失败", "无法创建文件夹")
                } else if(it.isFile) {
                    if(!it.delete()  || !it.mkdirs() ) {
                        irretrievableReminder("创建数据文件夹失败", "无法创建")
                    }
                }
                if(!it.canExecute() || !it.canRead() || !it.canWrite()) {
                    irretrievableReminder("数据文件夹权限错误", "无读写执行权限")
                }
                it.absolutePath
            })
            BotConfig.setAllowFriendMessage(true)
            BotConfig.IBotFunction = IBotFunctionWindow()
        }


    }


}