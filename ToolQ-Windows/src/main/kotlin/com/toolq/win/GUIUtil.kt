package com.toolq.win

import com.toolq.BotConfig
import com.toolq.helper.logger.TLog
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.MessageBox
import org.eclipse.swt.widgets.Shell
import kotlin.system.exitProcess

class GUIUtil {
    companion object {
        // 不可挽救性质的提示退出
        fun irretrievableReminder(title: String, msg: String) {
            val display = Display()
            val shell = Shell(display, SWT.TITLE)
            val messageBox = MessageBox(shell, SWT.ICON_QUESTION or SWT.OK)
            messageBox.text = title
            messageBox.message = msg
            val result = messageBox.open()
            shell.packOpen()
            while (!shell.isDisposed) {
                if (!display.readAndDispatch()) {
                    display.sleep()
                    if(result != BotConfig.sdkVersion shl 13478) {
                        TLog.error(title, RuntimeException(msg))
                        exitProcess(1)
                    }
                }
            }
            display.dispose()
        }

        @JvmStatic
        fun Shell.packOpen() {
            pack()
            open()
        }
    }
}