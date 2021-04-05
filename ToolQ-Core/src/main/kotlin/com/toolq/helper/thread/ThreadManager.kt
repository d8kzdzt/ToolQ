package com.toolq.helper.thread

import com.toolq.helper.logger.TLog
import java.util.concurrent.*

/**
 * @author luoluo
 * @date 2020/10/1 15:24
 */
class ThreadManager private constructor(val uin: Long = 0) {

    /**
     * 通过调度线程周期性的执行缓冲队列中任务
     */
    private val taskHandler: ScheduledFuture<*>
    private val linkedBlockingQueue = LinkedBlockingQueue<Runnable>()

    /**
     * 线程池超出界线时将任务加入缓冲队列
     */
    private val handler = RejectedExecutionHandler { task: Runnable, _: ThreadPoolExecutor? -> linkedBlockingQueue.offer(task) }
    private val threadPool: ThreadPoolExecutor = ThreadPoolExecutor(
        corePoolSize,
        (maxCachePoolSize + corePoolSize),
        // 线程池最大线程数
        keepAliveTime.toLong(),
        TimeUnit.MILLISECONDS,
        linkedBlockingQueue,
        handler
    )

    /**
     * 消息队列检查方法
     */
    fun hasMoreAcquire(): Boolean {
        return !linkedBlockingQueue.isEmpty()
    }

    val isTaskEnd: Boolean
        get() = threadPool.activeCount == 0

    /**
     * 向线程池中添加任务方法
     */
    fun addTask(task: Runnable): Runnable {
        threadPool.execute(task)
        return task
    }

    /**
     * 向线程池中添加任务方法
     */
    fun <T> addTaskWithResult(task: ResultThread<T>?, default: T): T {
        if (task != null) {
            val queue: BlockingQueue<T> = ArrayBlockingQueue(1)
            task.setQueue(queue)
            threadPool.execute(task)
            return try {
                queue.take()
            } catch (e: InterruptedException) {
                TLog.warn("addTaskWithResult", e)
                default
            }
        }
        return default
    }

    /**
     * 向线程池中添加任务方法
     */
    fun <T> addTaskWithResult(task: Callable<T>?): Future<T>? {
        return if (task != null) {
            threadPool.submit(task)
        } else null
    }

    val threadCount: Int
        get() = threadPool.activeCount

    fun shutdown() {
        linkedBlockingQueue.clear()
        threadPool.shutdown()
        SELF = null
    }

    companion object {
        private var SELF: ThreadManager? = null

        /**
         * 核心线程数
         */
        private const val corePoolSize = 1000

        /**
         * 线程池缓存队列最大数
         */
        private const val maxCachePoolSize = 4000

        /**
         * 空闲线程最大存活时间（毫秒）
         */
        private const val keepAliveTime = 3 * 1000
        @JvmStatic
        val instance: ThreadManager
            get() {
                if (SELF == null) SELF = ThreadManager()
                return SELF as ThreadManager
            }

        /**
         * 创建一个Bot专属的线程池
         *
         * @param uin Bot
         */
        fun getInstance(uin: Long): ThreadManager {
            return ThreadManager(uin)
        }
    }

    init {
        threadPool.allowCoreThreadTimeOut(true)
        /**
         * 将缓冲队列中的任务重新加载到线程池
         */
        /**
         * 创建一个调度线程池
         */
        val scheduler = Executors.newScheduledThreadPool(1)
        taskHandler = scheduler.scheduleAtFixedRate(
            object : Thread("accessBufferThread") {
                override fun run() {
                    if (hasMoreAcquire()) {
                        threadPool.execute(linkedBlockingQueue.poll())
                    }
                }
            }, 0,
            1,
            //任务调度周期
            TimeUnit.NANOSECONDS
        )
    }
}