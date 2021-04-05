package com.toolq.helper.socket

interface SocketListener {
    fun onReceive(body: ByteArray?)
    fun onHighWayReceive(head: ByteArray?, data: ByteArray?)
}