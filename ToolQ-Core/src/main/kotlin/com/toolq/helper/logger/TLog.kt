package com.toolq.helper.logger

object TLog {
    var debug : Boolean = false

    private var loggerEvent : LoggerEvent? = null

    fun setEvent(loggerEvent: LoggerEvent) {
        TLog.loggerEvent = loggerEvent
    }

    fun debug(message: Any?) {
        loggerEvent?.debug(message)
    }

    fun debug(message: Any?, t: Throwable?) {
        loggerEvent?.debug(message, t)
    }

    fun error(message: Any?) {
        loggerEvent?.error(message)
    }

    fun error(message: Any?, t: Throwable?) {
        loggerEvent?.error(message, t)
    }

    fun warn(message: Any?) {
        loggerEvent?.warn(message)
    }

    fun warn(message: Any?, t: Throwable?) {
        loggerEvent?.warn(message, t)
    }

    fun fatal(message: Any?) {
        loggerEvent?.fatal(message)
    }

    fun fatal(message: Any?, t: Throwable?) {
        loggerEvent?.fatal(message, t)
    }

    fun info(message: Any?) {
        loggerEvent?.info(message)
    }

    fun info(message: Any?, t: Throwable?) {
        loggerEvent?.info(message, t)
    }

    fun trace(message: Any?) {
        loggerEvent?.trace(message)
    }

    fun trace(message: Any?, t: Throwable?) {
        loggerEvent?.trace(message, t)
    }
}