package com.toolq.helper.thread

import com.toolq.helper.logger.TLog
import java.util.concurrent.BlockingQueue

/**
 * @author luoluo
 * @date 2020/10/2 0:48
 */
abstract class ResultThread<T> : Thread(), IResultThread<T> {
    private var queue: BlockingQueue<T>? = null
    override fun run() {
        try {
            val t = on()
            queue!!.add(t)
        } catch (e: Throwable) {
            TLog.error(e)
            e.printStackTrace()
        }
    }

    fun setQueue(queue: BlockingQueue<T>?) {
        this.queue = queue
    }

    @Throws(Throwable::class)
    abstract override fun on(): T
}