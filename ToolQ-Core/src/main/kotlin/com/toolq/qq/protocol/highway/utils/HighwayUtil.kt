package com.toolq.qq.protocol.highway.utils

import com.toolq.qq.protocol.protobuf.highway.CSDataHighwayHead
import com.toolq.qq.protocol.protobuf.highway.CSDataHighwayHead.DataHighwayHead

class HighwayUtil {
    companion object {
        fun makeReqDataHighwayHead(size: Long, uKey: ByteArray, md5: ByteArray, uin : Long, cmd : String, seq: Int, appid : Long, dataFlag : Int, cmdId : Int, localeId : Int) : CSDataHighwayHead.ReqDataHighwayHead {
            val head : CSDataHighwayHead.ReqDataHighwayHead = CSDataHighwayHead.ReqDataHighwayHead()
            head.msg_basehead.set(
                makeDataHighWayHead(uin, cmd, seq, appid, dataFlag, cmdId, localeId)
            )
            head.msg_seghead.set(
                makeSegHead(md5, uKey, size)
            )
            return head
        }

        private fun makeSegHead(md5 : ByteArray, ticket : ByteArray, size : Long) : CSDataHighwayHead.SegHead {
            val segHead = CSDataHighwayHead.SegHead()
            segHead.bytes_file_md5.set(md5)
            segHead.bytes_md5.set(md5)
            segHead.uint64_dataoffset.set(0)
            segHead.bytes_serviceticket.set(ticket)
            segHead.uint64_filesize.set(size)
            segHead.uint32_datalength.set(size.toInt())
            return segHead
        }

        fun makeDataHighWayHead(uin : Long, cmd : String, seq: Int, appid : Long, dataFlag : Int, cmdId : Int, localeId : Int) : DataHighwayHead {
            val head = DataHighwayHead()
            head.uint32_version.set(1)
            head.bytes_uin.set(uin.toString())
            head.bytes_command.set(cmd)
            head.uint32_seq.set(seq)
            head.uint64_appid.set(appid)
            head.uint32_dataflag.set(dataFlag)
            head.uint32_command_id.set(cmdId)
            head.locale_id.set(localeId)
            return head
        }

    }
}