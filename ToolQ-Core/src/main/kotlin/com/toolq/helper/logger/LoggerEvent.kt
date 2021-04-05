package com.toolq.helper.logger

interface LoggerEvent {
    fun debug(message : Any?)

    fun debug(message : Any?, th: Throwable?)

    fun info(message: Any?)

    fun info(message : Any?, th: Throwable?)

    fun error(message: Any?)

    fun error(message : Any?, th: Throwable?)

    fun warn(message: Any?)

    fun warn(message : Any?, th: Throwable?)

    fun fatal(message: Any?)

    fun fatal(message : Any?, th: Throwable?)

    fun trace(message: Any?)

    fun trace(message : Any?, th: Throwable?)
}