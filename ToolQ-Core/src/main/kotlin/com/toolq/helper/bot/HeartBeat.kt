package com.toolq.helper.bot

import com.toolq.BotConfig
import com.toolq.ToolQ
import com.toolq.helper.android.Android
import com.toolq.helper.logger.TLog
import com.toolq.helper.packet.ByteBuilder
import java.lang.System.currentTimeMillis

@ExperimentalUnsignedTypes
class HeartBeat(private val toolQ: ToolQ) : Thread("HeartBeat") {
    companion object {
        fun addHeartBeat(toolQ: ToolQ) {
            toolQ.threadManager.addTask(HeartBeat(toolQ))
        }
    }

    override fun run() {
        while (true) {
            // 防止心跳因为大量的事件监听器而出现误差
            var deviation = BotConfig.heartBeatCycleTime

            if(toolQ.botStatus == BotStatus.online) {
                val st = currentTimeMillis()
                val time = currentTimeMillis()


                toolQ.socketClient.clientOutPut?.send(toolQ.threadManager, makeHeartBeatPacket(toolQ.recorder.nextSsoSeq()))
                toolQ.center.getMultiTroopInfo(1065759865)
                TLog.info("A heartbeat packet was sent!")
                for (event in toolQ.eventVector) {
                    event.heartbeatEvent(time)
                }
                deviation -= (currentTimeMillis() - st)
            } else if(toolQ.botStatus == BotStatus.ruin) break
            sleep(deviation.let {
                if(it < 0) 1000 else it
            } )
        }
    }

    private fun makeHeartBeatPacket(seq: Int) : ByteArray {
        val builder = ByteBuilder()
        builder.writeInt(0xA)
        builder.writeByte(0.toByte())
        builder.writeBytesWithSize(byteArrayOf(), 4)
        builder.writeByte(0.toByte())
        builder.writeStringWithSize(toolQ.account.user.toString(), 4)

        val headBuilder = ByteBuilder()
        headBuilder.writeInt(seq)
        headBuilder.writeInt(toolQ.androidQQ.appId())
        headBuilder.writeInt(toolQ.androidQQ.appId())
        headBuilder.writeInt(16777216)
        headBuilder.writeInt(0)
        headBuilder.writeInt(256)
        headBuilder.writeBytesWithSize(byteArrayOf(), 4)
        headBuilder.writeStringWithSize("Heartbeat.Alive", 4)
        headBuilder.writeBytesWithSize(byteArrayOf(), 4)
        headBuilder.writeStringWithSize(Android.androidId, 4)
        headBuilder.writeBytesWithSize(Android.getKsid(), 4)
        headBuilder.writeStringWithShortSize(toolQ.androidQQ.agreementVersion(), 2)
        headBuilder.writeStringWithSize("", 4)

        builder.writeBytesWithSize(headBuilder.toByteArray(), 4)

        builder.writeInt(4)

        builder.writeSize(4)
        return builder.toByteArray()
    }
}