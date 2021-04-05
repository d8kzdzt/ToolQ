package com.toolq.helper.bot.protocol

import com.google.gson.JsonObject
import com.toolq.helper.server.ToolQServer
import com.toolq.qq.protocol.AndroidQQ
import com.toolq.utils.HexUtil
import com.toolq.utils.fastCatch

class HttpQQ : AndroidQQ() {
    var isHd = false
    set(value) {
        field = value
        val latestVersion = this.all?.get(
            if(isHd) "hdLatest" else "latest"
        )?.asString
        this.data = this.all?.get(latestVersion)?.asJsonObject
    }

    private var data : JsonObject? = null
    private var all : JsonObject? = null

    init {
        fastCatch<Exception> {
            val server = ToolQServer.create("Main.protocolInfo")
            val response = server.request()
            if(response.ret == 200) {
                this.all = response.data
                val latestVersion = this.all?.get(
                    if(isHd) "hdLatest" else "latest"
                )?.asString
                this.data = this.all?.get(latestVersion)?.asJsonObject
            }
        }?.printStackTrace()
    }

    override fun appId(): Long {
        return if(data == null)
            super.appId()
        else
            data!!.get("appid").asLong
    }

    override fun packageName(): String {
        return if(data == null)
            super.packageName()
        else
            data!!.get("packageName").asString
    }

    // override fun miscBitmap(): Int = super.miscBitmap()

    override fun buildVersion(): String {
        return if(data == null)
            super.buildVersion()
        else
            data!!.get("buildVersion").asString
    }

    // override fun localId(): Int { }

    override fun buildTime(): Int {
        return if(data == null)
            super.buildTime()
        else
            data!!.get("buildTime").asInt
    }

    // override fun subAppId(): Int {}

    override fun msfSsoVersion(): Int {
        return if(data == null)
            super.msfSsoVersion()
        else
            data!!.get("msfSsoVer").asInt
    }

    override fun pingVersion(): Int {
        return if(data == null)
            super.pingVersion()
        else
            data!!.get("pingVersion").asInt
    }

    override fun tencentSdkMd5(): ByteArray {
        return if(data == null)
            super.tencentSdkMd5()
        else
            HexUtil.hexStringToBytes(data!!.get("sdkMd5").asString)
    }

    override fun dbVersion(): Int {
        return if(data == null)
            super.dbVersion()
        else
            data!!.get("dbVer").asInt
    }

    override fun tgtgVersion(): Int {
        return if(data == null)
            super.tgtgVersion()
        else
            data!!.get("tgtgVer").asInt
    }

    override fun ipVersion(): Int {
        return if(data == null)
            super.ipVersion()
        else
            data!!.get("ipVersion").asInt
    }

    override fun ssoVersion(): Int {
        return if(data == null)
            super.ssoVersion()
        else
            data!!.get("ssoVer").asInt
    }

    override fun agreementVersion(): String {
        return if(data == null)
            super.agreementVersion()
        else
            data!!.get("agreementVersion").asString
    }

    override fun packageVersion(): String {
        return if(data == null)
            super.packageVersion()
        else
            data!!.get("packageVersion").asString
    }
}