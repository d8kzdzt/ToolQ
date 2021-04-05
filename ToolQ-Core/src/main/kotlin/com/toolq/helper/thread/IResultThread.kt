package com.toolq.helper.thread

interface IResultThread<T> {
    @Throws(Throwable::class)
    fun on(): T
}