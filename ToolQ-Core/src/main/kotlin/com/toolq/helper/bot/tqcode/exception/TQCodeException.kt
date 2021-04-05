package com.toolq.helper.bot.tqcode.exception

class TQCodeException : RuntimeException {
    constructor(th: Throwable?) : super(th)

    constructor(msg: String?) : super(msg)
}