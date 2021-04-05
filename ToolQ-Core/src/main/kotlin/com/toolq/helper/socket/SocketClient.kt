package com.toolq.helper.socket

import com.toolq.helper.logger.TLog.info
import com.toolq.helper.logger.TLog.warn
import com.toolq.resource.TString
import com.toolq.BotConfig
import com.toolq.helper.thread.ThreadManager
import kotlin.Throws
import com.toolq.utils.BytesUtil
import com.toolq.helper.thread.ResultThread
import com.toolq.helper.packet.ByteBuilder
import com.toolq.utils.fastCatch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * @author luoluo
 * @date 2020/9/30 22:01
 */
open class SocketClient {
    protected var client: Socket? = null
    var writeChannel: DataOutputStream? = null
        private set
    var readChannel: DataInputStream? = null
        private set
    private var readTimeOut = 1000
    var listener: SocketListener? = null
        protected set
    protected var receTask: Runnable? = null
    private var address: SocketAddress? = null

    protected var allowRece = false

    fun connect(address: SocketAddress) {
        fastCatch<Exception> {
            info(String.format(TString.socket_connect.get(), address.ip, address.port))
            client = Socket(address.ip, address.port)
            success()
        }?.let {
            throw ConnectException(it)
        }
    }

    fun connect(ip: String, port: Int) {
        connect(SocketAddress(ip, port))
    }

    fun connect(address: SocketAddress, timeout: Int) {
        fastCatch<Exception> {
            info(String.format(TString.socket_connect.get(), address.ip, address.port))
            client = Socket(address.ip, address.port)
            success()
        }?.let {
            throw ConnectException(it)
        }
    }

    fun connect(ip: String, port: Int, timeout: Int) {
        connect(SocketAddress(ip, port), timeout)
    }

    private fun success() {
        fastCatch<Exception> {
            isKeepAlive = true
            client!!.soTimeout = readTimeOut
            writeChannel = DataOutputStream(client!!.getOutputStream())
            readChannel = DataInputStream(client!!.getInputStream())
        }?.let {
            if (BotConfig.debug) it.printStackTrace()
        }
    }

    fun getReadTimeOut(): Int {
        return readTimeOut
    }

    fun setReadTimeOut(readTimeOut: Int) {
        fastCatch<Exception> {
            this.readTimeOut = readTimeOut
            client!!.soTimeout = readTimeOut
        }?.let {
            if (BotConfig.debug) it.printStackTrace()
        }
    }

    var isKeepAlive: Boolean
        get() = try {
            client!!.keepAlive
        } catch (e: SocketException) {
            throw SocketClientException(e)
        }
        set(keep) {
            try {
                client!!.keepAlive = keep
            } catch (e: SocketException) {
                throw SocketClientException(e)
            }
        }

    fun close(): Boolean {
        fastCatch<IOException> {
            client!!.shutdownInput()
            client!!.shutdownOutput()
            client!!.close()
        }?.let {
            return false
        }
        return true
    }

    fun setSocketClientListener(listener: SocketListener?): SocketListener? {
        this.listener = listener
        return listener
    }

    open fun allowReceive(threadManager: ThreadManager, type: Int) {
        allowRece = true
        receTask = threadManager.addTask {
            fastCatch<Throwable> {
                client!!.soTimeout = getReadTimeOut()
                while (!client!!.isInputShutdown && allowRece) {
                    when (type) {
                        Common -> {
                            val data = clientInPut!!.read()
                            if (listener != null && data != null) {
                                listener!!.onReceive(data)
                            }
                        }
                        HighWay -> {
                            val set = clientInPut!!.readHighWay()
                            if (listener != null && set != null) {
                                listener!!.onHighWayReceive(set[0], set[1])
                            }
                        }
                    }
                }
            }?.let {
                warn("ERROR", it)
            }
        }
    }

    /**
     * 停止接包
     */
    fun prohibitReceive() {
        allowRece = false
    }

    var clientOutPut: ClientOutPut? = null
        get() {
            if (field == null) field = ClientOutPut(this)
            return field
        }
        private set

    var clientInPut: ClientInPut? = null
        get() {
            if (field == null) field = ClientInPut(this)
            return field
        }
        private set

    class ClientInPut internal constructor(private val client: SocketClient) {
        @Throws(Throwable::class)
        fun read(): ByteArray? {
            try {
                val len = client.readChannel!!.readInt() - 4
                val ret = ByteArray(len)
                client.readChannel!!.readFully(ret)
                return BytesUtil.byteMerger(
                    BytesUtil.int64_to_buf32((len + 4).toLong()),
                    ret)
            } catch (e: Exception) {
                if (e !is SocketTimeoutException) throw e
            }
            return null
        }

        @Throws(Throwable::class)
        fun readHighWay(): Array<ByteArray?>? {
            try {
                val readChannel = client.readChannel
                val start = readChannel!!.readByte()
                if (start.toInt() == 0x28) {
                    val ret = arrayOfNulls<ByteArray>(2)
                    val headLen = readChannel.readInt()
                    val dataLen = readChannel.readInt()
                    val head = ByteArray(headLen)
                    val data = ByteArray(dataLen)
                    readChannel.readFully(head)
                    readChannel.readFully(data)
                    if (readChannel.readByte().toInt() == 0x29) {
                        info("Success to read a highway packet...")
                    }
                    ret[0] = head
                    ret[1] = data
                    return ret
                }
            } catch (e: Exception) {
                if (e !is SocketTimeoutException) throw e
            }
            return null
        }
    }

    class ClientOutPut internal constructor(private val client: SocketClient) {
        /**
         * @param data 包体
         * 发送Byte[] 到服务器
         */
        @Throws(IOException::class)
        fun send(threadManager: ThreadManager, data: ByteArray?) {
            val e = threadManager.addTaskWithResult(object : ResultThread<IOException?>() {
                @Throws(Throwable::class)
                override fun on(): IOException {
                    try {
                        if (data != null) {
                            client.writeChannel!!.write(data, 0, data.size)
                            client.writeChannel!!.flush()
                            info("Send a pack at " + System.currentTimeMillis() / 1000)
                        } else {
                            info("Can't send a null...")
                            return IOException("ignore")
                        }
                    } catch (e: IOException) {
                        return e
                    }
                    return IOException("ignore")
                }
            }, IOException("ignore"))
            if (e != null && "ignore" != e.message) throw e
        }

        fun sendHighWay(threadManager: ThreadManager, head: ByteArray, data: ByteArray) {
            threadManager.addTask {
                try {
                    val builder = ByteBuilder()
                    builder.writeString("(")
                    builder.writeInt(head.size)
                    builder.writeInt(data.size)
                    builder.writeBytes(head)
                    builder.writeBytes(data)
                    builder.writeString(")")
                    client.writeChannel!!.write(builder.toByteArray())
                    client.writeChannel!!.flush()
                    info("Send a pack at " + System.currentTimeMillis() / 1000)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val Common = 0
        const val HighWay = 1
    }
}