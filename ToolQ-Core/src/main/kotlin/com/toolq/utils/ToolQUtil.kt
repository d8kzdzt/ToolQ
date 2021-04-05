package com.toolq.utils

import com.google.gson.JsonObject
import com.toolq.BotConfig
import com.toolq.helper.android.Android
import com.toolq.helper.logger.TLog
import com.toolq.helper.logger.TLog.warn
import com.toolq.helper.server.ToolQServer.Companion.create
import com.toolq.qq.protocol.Tlv
import com.toolq.utils.FileUtil.saveFile
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.IOException
import javax.crypto.Cipher

import java.security.KeyFactory
import java.security.Security

import java.security.spec.PKCS8EncodedKeySpec
import java.util.*


/**
 * @author luoluo
 * @date 2020/10/2 15:01
 */
object ToolQUtil {
    @JvmStatic
    private var tencentServer : TencentServer = TencentServer()

    fun luoDecrypt(value: String): ByteArray? {
        return try {
            Security.addProvider(BouncyCastleProvider())
            val privateKeyBuffer: ByteArray = HexUtil.hexStringToBytes("3082025D02010002818100BB48B9E30C387F093D8653FB0981F3E6D84CADAD1EB32EFC02966ACAFB2AC6F5EFA19FFFEE1D590C8C98F8F878EA8ECDDD57E78D4B28681E1526B7C227FAD957EA99B4F3F37CFDB6AEA99900BCD9EB3380E5AD739E206B8A53278A27E82CCA592DD22EFEDFBE14D8F7306F4E90D12EC3B88F728F6ABBC4C9A7D7B4B5E230325302030100010281807D3C34914F2AB848458BFDBC70AF2599C829FE3935BE3C3CE8BE0CCB8F091BE2794B6AE28927B6D45D702D8C79CEDF8F69E3CE42FD6F17B086144DECD72F37061C2103F2D37E3087EE1C1B215C8F338AEAA2772B56A6C85CCE1956222DDCD5B8E2326AA892ED0DB068D076CDD041D9199AB12F12F23EB188C04B1B12821C2F81024100F778241ECA07A895BE65FBA66EA57504AC24DE9E341A7F4A0E6C197CC99A02B52F98B68B616D0F941CC7F9B7C06117BD8501CC51EF557732C11C62340C9BBA3F024100C1BD76EBA4BB80460D4EDEB30D564B98EC9A1A5DB2D1DE65B5C9E705620F88CC82B107C30BCA0DCA00A149C3A2D43AC11BD7FD831B07224593868AB1848DBAED02410093950695860EC32F6A790F76D60DBE97A6C1E6319B0922585BF983C9B3C1315434AA7252F52B415B273EEB61F64CF1078946E53F07E88449B478E4E7FD8CF1A902410091F8907B2718E6A2E1300576DE9BA045CE2EA5A2E7325CE04141A840540BE444424BE3CF3F38B37EF94E8C674C6D9EFF145D7E72A390926FDDA1472A4CF70459024066404CC13746ECA3A631DEED9952B656670F1CDE973CFEADC8F910C53B03AD7D78AF20FC50206B215DEFE756D712FBAB8531077352F4C48B7504CE0D0A3B9638")
            val pkcs8 = PKCS8EncodedKeySpec(privateKeyBuffer)
            val keyFactory = KeyFactory.getInstance("RSA")
            val privateKey = keyFactory.generatePrivate(pkcs8)
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            cipher.doFinal(Base64.getDecoder().decode(value))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 向ToolQ服务器获取腾讯服务器
     */
    @JvmStatic
    fun getTencentServer() : TencentServer {
        if(!tencentServer.init) {
            try {
                initClientIp()
                val server = create("Main.tencentServer")
                val response = server.request()
                if (response.ret != 200) return tencentServer
                val host = response.data["ip"].asJsonArray
                tencentServer.server = (host[0].asString)
                tencentServer.port = host[1].asInt
                tencentServer.init = true
            } catch (e: Exception) {
                e.printStackTrace()
                TLog.warn(e)
            }
        }
        return tencentServer
    }

    private fun initClientIp() {
        val server = create("Main.clientIp")
        val response = server.request()
        if (response.ret == 200) {
            var ip = response.data["host"].asString
            if (ip.contains(",")) {
                ip = ip.split(",").toTypedArray()[0]
            }
            TLog.info(String.format("The Client Ip is '%s'.", ip))
            Tlv.ip = QQUtil.ipToLong(ip)
        }
    }

    fun groupCode2GroupUin(groupcode: Long): Long {
        var calc = groupcode / 1000000L
        loop@ while (true) calc += when (calc) {
            in 0..10 -> {
                (202 - 0).toLong()
            }
            in 11..19 -> {
                (480 - 11).toLong()
            }
            in 20..66 -> {
                (2100 - 20).toLong()
            }
            in 67..156 -> {
                (2010 - 67).toLong()
            }
            in 157..209 -> {
                (2147 - 157).toLong()
            }
            in 210..309 -> {
                (4100 - 210).toLong()
            }
            in 310..499 -> {
                (3800 - 310).toLong()
            }
            else -> {
                break@loop
            }
        }
        return calc * 1000000L + groupcode % 1000000L
    }

    fun groupUin2GroupCode(groupuin: Long): Long {
        var calc = groupuin / 1000000L
        loop@ while (true) calc -= when {
            calc >= 0 + 202 && calc + 202 <= 10 -> {
                (202 - 0).toLong()
            }
            calc >= 11 + 480 && calc <= 19 + 480 -> {
                (480 - 11).toLong()
            }
            calc >= 20 + 2100 && calc <= 66 + 2100 -> {
                (2100 - 20).toLong()
            }
            calc >= 67 + 2010 && calc <= 156 + 2010 -> {
                (2010 - 67).toLong()
            }
            calc >= 157 + 2147 && calc <= 209 + 2147 -> {
                (2147 - 157).toLong()
            }
            calc >= 210 + 4100 && calc <= 309 + 4100 -> {
                (4100 - 210).toLong()
            }
            calc >= 310 + 3800 && calc <= 499 + 3800 -> {
                (3800 - 310).toLong()
            }
            else -> {
                break@loop
            }
        }
        return calc * 1000000L + groupuin % 1000000L
    }

    fun initAndroid(path : String) {
        val server = create("Main.randomAndroid")
        server.addParams("skey", StringBuffer()
            .apply {
                val time = (System.currentTimeMillis() / 1000)
                append("$time$")
                append(BytesUtil.int64_to_buf32(time).toHex().toLowerCase())
            }
            .append("$${BotConfig.sdkVersion + 18271821}")
            .toString())
        val content = server.request().data["content"].asJsonObject
        val data = JsonObject()
        val androidSdk = content["machineVersionNumber"].asInt
        data.addProperty("androidId", content["imei"].asString)
        data.addProperty("machineName", content["machineName"].asString)
        data.addProperty("machineManufacturer", content["machineManufacturer"].asString)
        data.addProperty("machineVersion", when(androidSdk) {
            10 -> "2.3.3"
            11 -> "3.0.1"
            12 -> "3.1.6"
            13 -> "3.2"
            14 -> "4.01"
            15 -> "4.0.3"
            16 -> "4.1.1"
            17 -> "4.2.2"
            18 -> "4.3"
            19 -> "4.4"
            20 -> "4.9"
            21 -> "5"
            22 -> "5.1"
            23 -> "6"
            24 -> "7"
            25 -> "7.1"
            26 -> "8"
            27 -> "8.1"
            28 -> "9"
            29 -> "10"
            30 -> "11"
            else -> "10"
        })
        data.addProperty("machineVersionNumber", androidSdk)
        data.addProperty("apnName", content["apnName"].asString)
        try {
            // printlnVar("$path/android.info".toFile().absolutePath)
            saveFile("$path/android.info".toFile().absolutePath, JsonUtil.formatJson(data.toString()))
            Android.loadByFile(path)
        } catch (e: IOException) {
            warn("initAndroid", e)
        }
    }

    data class TencentServer(
        var init: Boolean = false,

        var server: String = "msfwifi.3g.qq.com",
        var port: Int = 8080,
    )
}