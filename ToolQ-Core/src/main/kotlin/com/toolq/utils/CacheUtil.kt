package com.toolq.utils

import com.toolq.BotConfig
import com.toolq.helper.logger.TLog
import com.toolq.qq.protocol.protobuf.message.msg_comm
import java.io.File

object CacheUtil {
    fun saveUpImage(data : ByteArray) {
        val path = (BotConfig.getDataPath() + "/cache/upImage").toFile()
        if(!path.exists()) path.mkdirs()
        val md5 = data.toMd5Byte().toHex()
        val name = "$md5.jpg"
        if(data.size <= 20 * 1024 * 1024)
            FileUtil.saveFile("${path.absolutePath}/$name", data)
    }

    fun readUpImage(md5 : ByteArray) : ByteArray? {
        val file = (BotConfig.getDataPath() + "/cache/upImage/${md5.toHex()}.jpg").toFile()
        if(file.exists()) {
            return FileUtil.readFile(file).apply {
                file.delete()
            }
        }
        return null
    }

    fun saveTroopMsg(msg: msg_comm.Msg) {
        val msgSeq = msg.msg_head.get().msg_seq.get()
        val groupInfo = msg.msg_head.get().group_info.get()
        val groupCode = groupInfo.group_code.get()
        val path = (BotConfig.getDataPath() + "/cache/msg/group/$groupCode/").toFile()
        if(!path.exists()) path.mkdirs()
        val name = "$msgSeq.bin"
        FileUtil.saveFile("${path.absolutePath}/$name", GZipUtil.compress(msg.toByteArray())!!)
    }

    fun saveFriendMsg(msg: msg_comm.Msg) {
        val msgSeq = msg.msg_head.get().msg_seq.get()
        val path = (BotConfig.getDataPath() + "/cache/msg/friend/${msg.msg_head.get().from_uin.get()}/").toFile()
        if(!path.exists()) path.mkdirs()
        val name = "$msgSeq.bin"
        FileUtil.saveFile("${path.absolutePath}/$name", GZipUtil.compress(msg.toByteArray())!!)
    }

    fun readTroopMsg(groupId : Long, seq : Int) : msg_comm.Msg {
        val path = (BotConfig.getDataPath() + "/cache/msg/group/$groupId/").toFile()
        if(!path.exists()) path.mkdirs()
        val ret = msg_comm.Msg()
        try {
            val file = File("${path.absolutePath}/$seq.bin")
            val bytes = GZipUtil.uncompress(FileUtil.readFile(file))
            ret.parse(bytes)
        } catch (e : Exception) {
            TLog.info("无法读取缓存的群聊聊天记录")
        }
        return ret
    }

    fun readFriendMsg(uin : Long, seq : Int) : msg_comm.Msg {
        val path = (BotConfig.getDataPath() + "/cache/msg/friend/$uin/").toFile()
        if(!path.exists()) path.mkdirs()
        val ret = msg_comm.Msg()
        try {
            val file = File("${path.absolutePath}/$seq.bin")
            val bytes = GZipUtil.uncompress(FileUtil.readFile(file))
            ret.parse(bytes)
        } catch (e : Exception) {
            TLog.info("无法读取缓存的好友聊天记录")
        }
        return ret
    }
}