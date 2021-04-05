package com.toolq.qq.protocol

import com.toolq.helper.android.Android
import com.toolq.helper.bot.QQAccount
import com.toolq.helper.bot.IQQ
import com.toolq.helper.bot.tlv.TlvBuilder
import com.toolq.helper.packet.ByteBuilder
import com.toolq.qq.protocol.protobuf.DeviceReport
import com.toolq.utils.*
import java.nio.ByteBuffer
import kotlin.experimental.or

/**
 * @author luoluo
 * @date 2020/10/2 18:50
 */
class Tlv(private val account: QQAccount, private val iqq: IQQ, private val seq: Int) {
    private val time = System.currentTimeMillis() / 1000

    fun t1(): ByteArray = TlvBuilder(0x1)
        .writeShort(iqq.ipVersion())
        .writeInt(0)
        .writeInt(account.user)
        .writeInt(time)
        .writeInt(ip)
        .writeShort(0)
        .toByteArray()

    fun t8(): ByteArray = TlvBuilder(0x8)
        .writeShort(0)
        // 固定
        .writeInt(iqq.localId())
        // 2052
        .writeShort(0)
        // 固定
        .toByteArray()

    fun t18(): ByteArray = TlvBuilder(0x18)
        .writeShort(iqq.pingVersion())
        .writeInt(iqq.ssoVersion())
        .writeInt(iqq.subAppId())
        .writeInt(0)
        .writeInt(account.user)
        .writeShort(0)
        .writeShort(0)
        .toByteArray()

    fun t100(): ByteArray = TlvBuilder(0x100)
        .writeShort(iqq.dbVersion())
        .writeInt(iqq.msfSsoVersion())
        .writeInt(iqq.subAppId())
        .writeInt(iqq.appId())
        .writeInt(0)
        .writeInt(0x21410e0)
        .toByteArray()

    fun t104(dt104: ByteArray?): ByteArray = TlvBuilder(0x104)
        .writeBytes(dt104)
        .toByteArray()

    fun t107(): ByteArray = TlvBuilder(0x107)
        .writeShort(0)
        .writeByte(0.toByte())
        .writeShort(0)
        .writeByte(1.toByte())
        .toByteArray()

    fun t108(ksid: ByteArray?): ByteArray = TlvBuilder(0x108)
        .writeBytes(ksid ?: "BF F3 F1 1C 63 EE 2C B1 7D 96 77 02 A3 6E 25 12".hexToBs())
        .toByteArray()

    private fun t109(): ByteArray = TlvBuilder(0x109)
        .writeBytes(MD5.toMD5Byte(Android.androidId))
        .toByteArray()

    fun t116(): ByteArray = TlvBuilder(0x116)
        .writeByte(0.toByte())
        // ver
        .writeInt(iqq.miscBitmap())
        .writeInt(iqq.subSigMap()).apply {
            val appidArray = intArrayOf(0x5f5e10e2)
            writeByte(appidArray.size)
            for (appid in appidArray) {
                writeInt(appid)
            }
        }
        .toByteArray()

    fun t1162(): ByteArray = TlvBuilder(0x116)
        .writeByte(0.toByte())
        .writeInt(0x08F7FF7C)
        .writeInt(66560).apply {
            val appidArray = intArrayOf(0x5f5e10e2)
            writeByte(appidArray.size)
            for (appid in appidArray) {
                writeInt(appid)
            }
        }
        .toByteArray()

    fun t106(): ByteArray {
        val tlvBuilder = TlvBuilder(0x106)
        val builder = ByteBuilder()
        builder.writeShort(iqq.tgtgVersion())
        builder.writeInt(RandomUtil.randInt(0, 828922931))
        builder.writeInt(iqq.msfSsoVersion())
        builder.writeInt(iqq.subAppId())
        builder.writeInt(0)
        builder.writeInt(0)
        builder.writeInt(account.user)
        builder.writeInt(time)
        builder.writeInt(ip)
        builder.writeByte(1.toByte())
        builder.writeBytes(account.md5Password)
        builder.writeBytes(Android.getTgtgKey())
        builder.writeInt(0)
        builder.writeBoolean(iqq.isGuidAvailable)
        builder.writeBytes(Android.getGuid())
        builder.writeInt(iqq.appId())
        builder.writeInt(iqq.loginType())
        builder.writeStringWithShortSize(account.user.toString())
        builder.writeShort(0)
        val data = TeaUtil.encrypt(builder.toByteArray(), account.md5PasswordWithQQ)
        tlvBuilder.writeBytes(data)
        return tlvBuilder.toByteArray()
    }

    private fun t124(): ByteArray = TlvBuilder(0x124)
        .writeBytesWithShortSize(Android.osType)
        .writeStringWithShortSize(Android.machineVersion)
        .writeShort(when(Android.apn) {
            "5g", "wap", "net", "4gnet", "3gwap", "cncc", "cmcc" -> 1
            "wifi" -> 2
            else -> 0
        })
        // networkType
        .writeStringWithShortSize(Android.apnName)
        .writeStringWithSize(Android.apn)
        .toByteArray()

    private fun t128(): ByteArray = TlvBuilder(0x128)
        .writeShort(0)
        .writeBoolean(iqq.isGuidFromFileNull)
        .writeBoolean(iqq.isGuidAvailable)
        .writeBoolean(iqq.isGuidChange)
        .writeInt(0x01000000)
        .writeStringWithShortSize(Android.machineName)
        .writeBytesWithShortSize(Android.getGuid())
        .writeStringWithShortSize(Android.machineManufacturer)
        .toByteArray()

    fun t141(): ByteArray = TlvBuilder(0x141)
        .writeShort(1)
        // version
        .writeStringWithShortSize(Android.apnName)
        .writeShort(when(Android.apn) {
            "5g", "wap", "net", "4gnet", "3gwap", "cncc", "cmcc" -> 1
            "wifi" -> 2
            else -> 0
        })
        .writeStringWithShortSize(Android.apn)
        .toByteArray()

    fun t142(): ByteArray = TlvBuilder(0x142)
        .writeShort(0)
        .writeStringWithShortSize(iqq.packageName())
        .toByteArray()

    fun t144(): ByteArray {
        val tlvBuilder = TlvBuilder(0x144)
        val builder = ByteBuilder()
        builder.writeShort(5)
        builder.writeBytes(t109())
        builder.writeBytes(t52d())
        builder.writeBytes(t124())
        builder.writeBytes(t128())
        builder.writeBytes(t16e())
        val data = TeaUtil.encrypt(builder.toByteArray(), Android.getTgtgKey())
        tlvBuilder.writeBytes(data)
        return tlvBuilder.toByteArray()
    }

    fun t145(): ByteArray = TlvBuilder(0x145)
        .writeBytes(Android.getGuid())
        .toByteArray()

    fun t147(): ByteArray = TlvBuilder(0x147)
        .writeInt(iqq.subAppId())
        .writeStringWithShortSize(iqq.packageVersion())
        .writeBytesWithShortSize(iqq.tencentSdkMd5())
        .toByteArray()

    fun t154(): ByteArray = TlvBuilder(0x154)
        .writeInt(seq)
        .toByteArray()

    fun t16a(): ByteArray = TlvBuilder(0x16a)
        .writeHex("20 B5 33 79 18 79 9C AB E4 4A 8E F8 0D 66 84 B81F 8C 15 24 AD 46 D6 D7 7A AF 24 6A 09 16 0A 59AF 22 ED 5B 14 A8 B4 78 36 F2 AC 9A 34 61 15 3A")
        .toByteArray()

    /**
     * 登录过 设备所显示名称
     */
    fun t16b(): ByteArray {
        val tlvBuilder = TlvBuilder(0x16b)
        val domains = arrayOf(
            "tenpay.com",
            "qzone.qq.com",
            "qun.qq.com",
            "mail.qq.com",
            "openmobile.qq.com",
            "qzone.com",
            "game.qq.com",
            "vip.qq.com"
        )
        tlvBuilder.writeShort(domains.size)
        for (s in domains) {
            tlvBuilder.writeBytesWithShortSize(s.toByteArray())
        }
        return tlvBuilder.toByteArray()
    }

    private fun t16e(): ByteArray {
        val tlvBuilder = TlvBuilder(0x16e)
        tlvBuilder.writeString(Android.machineName)
        return tlvBuilder.toByteArray()
    }

    fun t174(dt174: ByteArray?): ByteArray {
        val tlvBuilder = TlvBuilder(0x174)
        tlvBuilder.writeBytes(dt174)
        return tlvBuilder.toByteArray()
    }

    fun t177(): ByteArray {
        val tlvBuilder = TlvBuilder(0x177)
        tlvBuilder.writeBoolean(true)
        tlvBuilder.writeInt(iqq.buildTime())
        tlvBuilder.writeStringWithShortSize(iqq.buildVersion())
        return tlvBuilder.toByteArray()
    }

    fun t17a(i: Long): ByteArray {
        val tlvBuilder = TlvBuilder(0x17a)
        tlvBuilder.writeInt(i)
        return tlvBuilder.toByteArray()
    }

    fun t17c(code: String): ByteArray {
        val tlvBuilder = TlvBuilder(0x17c)
        tlvBuilder.writeShort(code.length)
        tlvBuilder.writeString(code)
        return tlvBuilder.toByteArray()
    }

    fun t187(): ByteArray {
        val tlvBuilder = TlvBuilder(0x187)
        tlvBuilder.writeBytes(MD5.toMD5Byte(Android.macAddress))
        return tlvBuilder.toByteArray()
    }

    fun t188(): ByteArray {
        val tlvBuilder = TlvBuilder(0x188)
        tlvBuilder.writeBytes(MD5.toMD5Byte(Android.androidId))
        return tlvBuilder.toByteArray()
    }

    fun t191(): ByteArray {
        val tlvBuilder = TlvBuilder(0x191)
        tlvBuilder.writeByte(0x82)
        return tlvBuilder.toByteArray()
    }

    fun t194(): ByteArray {
        val tlvBuilder = TlvBuilder(0x194)
        tlvBuilder.writeBytes(MD5.toMD5Byte(Android.imsi))
        return tlvBuilder.toByteArray()
    }

    fun t197(): ByteArray {
        val tlvBuilder = TlvBuilder(0x197)
        tlvBuilder.writeByte(0.toByte())
        return tlvBuilder.toByteArray()
    }

    fun t198(): ByteArray {
        val tlvBuilder = TlvBuilder(0x198)
        tlvBuilder.writeByte(0.toByte())
        return tlvBuilder.toByteArray()
    }

    fun t202(): ByteArray {
        val tlvBuilder = TlvBuilder(0x202)
        tlvBuilder.writeBytesWithShortSize(MD5.toMD5Byte(Android.wifiBSSID))
        tlvBuilder.writeStringWithShortSize("\"" + Android.wifiSSID + "\"")
        return tlvBuilder.toByteArray()
    }

    fun t400(): ByteArray {
        val tlvBuilder = TlvBuilder(0x400)
        tlvBuilder.writeHex("D1387BC477015873D624BB495618F37A3096BCB21757E66741E1E5E090E6DD293C402D0003B169879C5B95BB5A21028062CD406335AFE249A508144C26A18A42B3FF12D1A1EB95E8")
        return tlvBuilder.toByteArray()
    }

    fun t401(dt402: ByteArray?): ByteArray {
        val tlvBuilder = TlvBuilder(0x401)
        val builder = ByteBuilder()
        builder.writeBytes(Android.getGuid())
        builder.writeBytes(QQUtil.get_mpasswd().toByteArray())
        builder.writeBytes(dt402)
        tlvBuilder.writeBytes(MD5.toMD5Byte(builder.toByteArray()))
        return tlvBuilder.toByteArray()
    }

    fun t402(dt402: ByteArray?): ByteArray {
        val tlvBuilder = TlvBuilder(0x402)
        tlvBuilder.writeBytes(dt402)
        return tlvBuilder.toByteArray()
    }

    fun t403(dt403: ByteArray?): ByteArray {
        val tlvBuilder = TlvBuilder(0x403)
        tlvBuilder.writeBytes(dt403)
        return tlvBuilder.toByteArray()
    }

    fun t511(): ByteArray {
        val tlvBuilder = TlvBuilder(0x511)
        val domains = arrayOf(
            "office.qq.com",
            "qun.qq.com",
            "gamecenter.qq.com",
            "docs.qq.com",
            "mail.qq.com",
            "ti.qq.com",
            "vip.qq.com",
            "tenpay.qq.com",
            "qqqweb.qq.com",
            "qzone.qq.com",
            "mma.qq.com",
            "game.qq.com",
            "openmobile.qq.com",
            "conect.qq.com" // "y.qq.com",
            // "v.qq.com"
        )
        tlvBuilder.writeShort(domains.size)
        for (domain in domains) {
            val start = domain.indexOf('(')
            val end = domain.indexOf(')')
            var b: Byte = 1
            if (start == 0 || end > 0) {
                val i = domain.substring(start + 1, end).toInt()
                b = if (1048576 and i > 0) {
                    1
                } else {
                    0
                }
                if (i and 0x08000000 > 0) {
                    b = (b or 2.toByte())
                }
            }
            tlvBuilder.writeByte(b)
            tlvBuilder.writeStringWithShortSize(domain)
        }
        return tlvBuilder.toByteArray()
    }

    fun t516(): ByteArray {
        val tlvBuilder = TlvBuilder(0x516)
        tlvBuilder.writeInt(0)
        return tlvBuilder.toByteArray()
    }

    fun t521(): ByteArray {
        val tlvBuilder = TlvBuilder(0x521)
        tlvBuilder.writeInt(0)
        tlvBuilder.writeShort(0)
        return tlvBuilder.toByteArray()
    }

    fun t525(): ByteArray {
        val tlvBuilder = TlvBuilder(0x525)
        tlvBuilder.writeShort(1)
        tlvBuilder.writeBytes(t536())
        return tlvBuilder.toByteArray()
    }

    private fun t536(): ByteArray {
        val tlvBuilder = TlvBuilder(0x536)
        tlvBuilder.writeHex(
            """
    01 03 00 00 00 00 6F E2 
    BD 2E 04 DF 68 5C 58 5F 
    8A F7 FE 20 02 F8 A1 00 
    00 00 00 99 68 42 96 04 
    DF 68 5C 58 5F 8A FD 41 
    20 02 F8 A1 00 00 00 00 
    62 5A BF 50 04 DF 68 5C 
    58 5F 8A FD 42 20 02 F8 
    A1
    """.trimIndent()
        )
        return tlvBuilder.toByteArray()
    }

    private fun t52d(): ByteArray {
        val tlvBuilder = TlvBuilder(0x52d)
        tlvBuilder.writeBytes(DeviceReport().toByteArray())
        return tlvBuilder.toByteArray()
    }

    fun t542(): ByteArray {
        val tlvBuilder = TlvBuilder(0x542)
        tlvBuilder.writeByte(0.toByte())
        tlvBuilder.writeByte(0.toByte())
        return tlvBuilder.toByteArray()
    }

    fun t544(): ByteArray {
        val tlvBuilder = TlvBuilder(0x544)
        tlvBuilder.writeHex(
            """
    00 00 07 D9 00 00 00 00 
    00 2E 00 20 15 97 BF B2 
    50 07 9C 86 AF 7A FB 53 
    64 4F 39 97 E9 0A 15 91 
    83 AD F1 20 CC 89 F8 75 
    28 63 5C 3E 00 08 00 00 
    00 00 00 00 50 C9 00 03 
    01 00 00 00 04 00 00 00 
    03 00 00 00 01 75 37 33 
    F4 38 29 75 6F 47 67 32 
    76 48 33 70 00 14 63 6F 
    6D 2E 74 65 6E 63 65 6E 
    74 2E 6D 6F 62 69 6C 65 
    71 71 41 36 42 37 34 35 
    42 46 32 34 41 32 43 32 
    37 37 35 32 37 37 31 36 
    46 36 46 33 36 45 42 36 
    38 44 05 E7 AD 8C 00 00 
    00 00 00 00 02 00 00 01 
    00 10 D8 44 E7 DC BB E2 
    17 CB 9C 77 7F B0 FF B7 
    B7 42
    """.trimIndent()
        )
        return tlvBuilder.toByteArray()
    }

    companion object {
        var ip: Long = 0

        @JvmStatic
		fun t193(ticket: String?): ByteArray {
            val tlvBuilder = TlvBuilder(0x193)
            tlvBuilder.writeString(ticket)
            return tlvBuilder.toByteArray()
        }
    }
}