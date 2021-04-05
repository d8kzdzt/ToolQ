package com.toolq.qq.protocol.pay

import com.google.gson.Gson
import com.toolq.helper.android.Android
import com.toolq.helper.logger.TLog
import com.toolq.helper.packet.ByteBuilder
import com.toolq.qq.dataClass.QPayBalance
import com.toolq.qq.dataClass.QPayHbGate
import com.toolq.qq.dataClass.QPayHbPack
import com.toolq.qq.dataClass.QPayWallet
import com.toolq.utils.*
import java.text.SimpleDateFormat
import java.util.*

object LuckyPacket {
    private val desKeys = arrayOf(
        "9973e345",
        "5dac6cf7",
        "f5c88847",
        "f02c91bd",
        "3c0c3ea1",
        "8b00b67f",
        "c28931b2",
        "c8510256",
        "c42bfdef",
        "890fe53c",
        "0d181064",
        "0ef940b7",
        "10d75d6d",
        "c5d8e9f6",
        "66c3987e",
        "c48cebe3"
    )

    private const val HbPackUrl = "https://mqq.tenpay.com/cgi-bin/hongbao/qpay_hb_pack.cgi?ver=2.0&chv=3"
    private const val HbGateUrl = "https://myun.tenpay.com/cgi-bin/clientv1.0/qpay_gate.cgi?ver=2.0&chv=3"
    private const val HbWallet = "https://myun.tenpay.com/cgi-bin/clientv1.0/qwallet.cgi?ver=2.0&chv=3"
    private const val HbBalanceUrl = "https://myun.tenpay.com/cgi-bin/clientv1.0/qpay_balance.cgi?ver=2.0&chv=3\n"

    private const val shaMd5 = "F0D6C4CEE093903BFD05D6303A581B97E8442ABD"

    // 查询钱包余额（可用）
    fun checkBalances(uin: Long, skey: String, seq: Int = 1) : Double {
        val text = mapOf(
            // "pskey" to pskey,
            "pskey_scene" to "client",
            "skey_type" to "2",
            "skey" to skey,
            "app_info" to "appid%230%7Cbargainor_id%230%7Cchannel%23wallet",
            "need_suggest" to "1",
            "uin" to uin.toString(),
            "h_net_type" to "WIFI",
            "h_model" to "android_mqq",
            "h_edition" to "70",
            "h_location" to "${MD5.hexdigest(Android.androidId)}||${Android.machineName}|${Android.machineVersion},sdk${Android.machineVersionNumber}|${MD5.hexdigest(Android.androidId + Android.macAddress)}|7C9809E2D6C9B9277643C6088BCD181C|${
                // 这个0代表支付环境是否有root
                "1"
            }|",
            "h_qq_guid" to Android.getGuid().toHex(),
            "h_qq_appid" to "537067450",
            // 写死
            "h_exten" to ""
        )
        val reqText =  DesECBUtil.encryptDES(text.toString().replace("\\{([\\S\\s]+)\\}".toRegex(), "$1").replace(", ".toRegex(), "&"), desKeys[0])
        val okhttp = OkhttpUtil()
        val result = okhttp.post(
            HbWallet, mapOf(
                "req_text" to reqText,
                "skey_type" to "2",
                "random" to "0",
                // 密钥的标识
                "msgno" to "$uin${getTime()}${BytesUtil.int32_to_buf(seq).toHex()}",
                "skey" to skey
            ))
        if(result?.code == 200) {
            val data = DesECBUtil.decryptDES(result.body!!.string(), desKeys[0]).formatToJson()
            val hbWallet = Gson().fromJson(data, QPayWallet::class.java)
            result.close()
            return hbWallet.balance.toInt() * 0.01
        }
        return 0.0
    }

    fun balanceHb(uin: Long,
                  mkey : String,
                  tokenId: String,
                  gate : QPayHbGate,
                  seq : Int = 1) : QPayBalance? {
        val trans = gate.trans_seq.toInt()
        val text = mapOf(
            "p" to HbRSA.RSAEncrypt(mkey),
            "token_id" to tokenId,
            "is_reentry" to "0",
            "skey" to gate.skey,
            "timestamp" to currentTimeSecond().toString(),
            "h_net_type" to "WIFI",
            "h_model" to "android_mqq",
            "h_edition" to 70.toString(),
            "h_location" to "${MD5.hexdigest(Android.androidId)}||${Android.machineName}|${Android.machineVersion},sdk${Android.machineVersionNumber}|${MD5.hexdigest(Android.androidId + Android.macAddress)}|7C9809E2D6C9B9277643C6088BCD181C|${
                // 这个0代表支付环境是否有root
                "1"
            }|",
            "h_qq_guid" to Android.getGuid().toHex(),
            "h_qq_appid" to "537067450",
            // 写死
            "h_exten" to ""
        )
        val reqText =  DesECBUtil.encryptDES(text.toString().replace("\\{([\\S\\s]+)\\}".toRegex(), "$1").replace(", ".toRegex(), "&"), desKeys[trans])
        val okhttp = OkhttpUtil()
        val result = okhttp.post(
            HbBalanceUrl, mapOf(
                "req_text" to reqText,
                // 密钥的标识
                "msgno" to "$uin${getTime()}${BytesUtil.int32_to_buf(seq).toHex()}",
                "skey" to gate.skey
            ))
        if(result?.code == 200) {
            val data = DesECBUtil.decryptDES(result.body!!.string(), desKeys[trans]).formatToJson()
            result.close()
            return Gson().fromJson(data, QPayBalance::class.java)
        }
        return null
    }

    fun getTrHbGate(
        uin: Long,
        skey: String,
        tokenId: String,
        seq : Int = 1,
        edition: Int = 70
    ) : QPayHbGate? {
        val text = mapOf(
            // 可隐藏 "pskey" to pskey,
            "pskey_scene" to "client",
            "skey_type" to "2",
            "come_from" to "2",
            "token_id" to tokenId,
            "skey" to skey,
            "uin" to uin.toString(),
            "sdk_channel" to "0",
            "model_xml" to "<deviceinfo><MANUFACTURER name=\"${Android.machineManufacturer}\"><MODEL name=\"${Android.machineName}\"><VERSION_RELEASE name=\"${Android.machineVersion}\"><VERSION_INCREMENTAL name=\"21.3.16\"><DISPLAY name=\"RKQ1.200826.002 test-keys\"></DISPLAY></VERSION_INCREMENTAL></VERSION_RELEASE></MODEL></MANUFACTURER></deviceinfo>",
            "device_id" to Android.androidId,
            "h_net_type" to "WIFI",
            "h_model" to "android_mqq",
            "h_edition" to edition.toString(),
            "h_location" to "${MD5.hexdigest(Android.androidId)}||${Android.machineName}|${Android.machineVersion},sdk${Android.machineVersionNumber}|${MD5.hexdigest(Android.androidId + Android.macAddress)}|7C9809E2D6C9B9277643C6088BCD181C|${
                // 这个0代表支付环境是否有root
                "1"
            }|",
            "h_qq_guid" to Android.getGuid().toHex(),
            "h_qq_appid" to "537067450",
            // 写死
            "h_exten" to ""
        )
        val reqText =  DesECBUtil.encryptDES(text.toString().replace("\\{([\\S\\s]+)\\}".toRegex(), "$1").replace(", ".toRegex(), "&"), desKeys[0])
        val okhttp = OkhttpUtil()
        val result = okhttp.post(
            HbGateUrl, mapOf(
            "req_text" to reqText,
            "skey_type" to "2",
            "random" to "0",
            // 密钥的标识
            "msgno" to "$uin${getTime()}${BytesUtil.int32_to_buf(seq).toHex()}",
            "skey" to skey
        ))
        if(result?.code == 200) {
            val data = DesECBUtil.decryptDES(result.body!!.string(), desKeys[0]).formatToJson()
            val gson = Gson()
            val hbGate = gson.fromJson(data, QPayHbGate::class.java)
            if(hbGate.retcode == 0) {
                return hbGate
            }
            result.close()
        }
        return null
    }

    fun getTrHbPack(
        // 参数被隐藏 pskey: String,
        skey: String,
        uin: Long,
        troopId: Long,
        tittle: String,
        // 红包大小（单位：分）
        amount: Int,
        // 红包个数
        num: Int,
        // 红包的类型
        type: Int,
        channel: Int,
        /**
         * 专属红包用的目标列表
         */
        grabUinList: Array<Long> = arrayOf(),
        seq: Int = 1,
        /**
         * 70 群红包 (反编译发现被写死为70)
         */
        edition: Int = 70
    ) : String? {
        //bus_type=2
         val text = hashMapOf(
            // 可隐藏 "pskey" to pskey,
            "subchannel" to "0",
            "hb_from_type" to "0",
            "skin_id" to "0",
            "bus_type" to type.toString(),
            // 可改变(红包类型)
            "channel" to channel.toString(),
            "type" to "1",
            "wishing" to tittle,
            "skey_type" to "2",
            "total_amount" to amount.toString(),
            // 金额
            "recv_type" to "3",
            "total_num" to num.toString(),
            // 数量
            "recv_uin" to troopId.toString(),
            // 可隐藏 "name" to "机器人名字"
            "skey" to skey,
            "uin" to uin.toString(),
            "h_net_type" to "WIFI",
            "h_model" to "android_mqq",
            "h_edition" to edition.toString(),
            "h_location" to "${MD5.hexdigest(Android.androidId)}||${Android.machineName}|${Android.machineVersion},sdk${Android.machineVersionNumber}|${MD5.hexdigest(Android.androidId + Android.macAddress)}|7C9809E2D6C9B9277643C6088BCD181C|${
                // 这个0代表支付环境是否有root
                "1"
            }|",
            "h_qq_guid" to Android.getGuid().toHex(),
            "h_qq_appid" to "537067450",
            // 写死
            "h_exten" to ""
        ).apply {
            if(channel == 1024) this["grab_uin_list"] = grabUinList.let {
                val buffer = StringBuffer()
                it.forEachIndexed {  i, uin ->
                    buffer.append(if(i == 0) uin else "|$uin")
                }
                buffer.toString()
            }
         }
        val reqText =  DesECBUtil.encryptDES(text.toString().replace("\\{([\\S\\s]+)\\}".toRegex(), "$1").replace(", ".toRegex(), "&"), desKeys[0])
        val okhttp = OkhttpUtil()
        val result = okhttp.post(HbPackUrl, mapOf(
            "req_text" to reqText,
            "skey_type" to "2",
            "random" to "0",
            // 密钥的标识
            "msgno" to "$uin${getTime()}${BytesUtil.int32_to_buf(seq).toHex()}",
            "skey" to skey
        ))
        if(result?.code == 200) {
            val data = DesECBUtil.decryptDES(result.body!!.string(), desKeys[0]).formatToJson()
            val gson = Gson()
            val hbPack = gson.fromJson(data, QPayHbPack::class.java)
            if(hbPack.retcode == 0) {
                return hbPack.token_id
            }
            TLog.warn("红包TokenId获取失败：$hbPack")
            result.close()
        }
        return null
    }

    private fun tryToDecrypt(data : String) {
        desKeys.forEach {
            DesECBUtil.decryptDES(data, it).let {
                if(it.startsWith("p") || it.startsWith("u") || it.startsWith("s")) {
                    println(it)
                }
            }
        }
    }

    private fun getTime(): String {
        val data = Date()
        val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sd.format(data).replace("-".toRegex(), "").replace(":".toRegex(), "").replace(" ".toRegex(), "")
    }
}

