package com.toolq.utils

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import com.toolq.utils.OkhttpUtil.Companion.SSLSocketClient.getHostnameVerifier
import com.toolq.utils.OkhttpUtil.Companion.SSLSocketClient.getMySSLSocketFactory
import com.toolq.utils.OkhttpUtil.Companion.SSLSocketClient.getSSLSocketFactory
import com.toolq.utils.OkhttpUtil.Companion.SSLSocketClient.getX509TrustManager
import java.io.ByteArrayInputStream
import java.io.IOException
import java.lang.Exception
import java.net.Proxy
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import kotlin.collections.HashMap

class OkhttpUtil(private val ssl : Boolean = false, private val proxy : Boolean = true) {
    private val clientBuilder = OkHttpClient.Builder().apply {
        readTimeout(readTimeOut, TimeUnit.SECONDS)
        connectTimeout(connectTimeout, TimeUnit.SECONDS)
        writeTimeout(writeTimeout, TimeUnit.SECONDS)
        if(ssl) {
            sslSocketFactory(getMySSLSocketFactory()!!, getX509TrustManager()!!)
        } else {
            sslSocketFactory(getSSLSocketFactory()!!, getX509TrustManager()!!)
        }
        hostnameVerifier(getHostnameVerifier())
        if(!proxy) proxy(Proxy.NO_PROXY)
    }
    private val requestBuilder = Request.Builder()

    private val cookies : HashMap<String, String> = HashMap()

    var readTimeOut = 60L
        set(value) {
            field = value
            clientBuilder.readTimeout(field, TimeUnit.SECONDS)
        }
    var connectTimeout = 60L
        set(value) {
            field = value
            clientBuilder.connectTimeout(field, TimeUnit.SECONDS)
        }
    var writeTimeout = 60L
        set(value) {
            field = value
            clientBuilder.writeTimeout(field, TimeUnit.SECONDS)
        }

    fun cookie(content : String) = header("cookie", content)

    fun removeCookie() = removeHeader("cookie")

    fun header(key : String, content : String) = requestBuilder.addHeader(key, content)

    fun removeHeader(key: String) = requestBuilder.removeHeader(key)

    fun get(url : String) : Response? {
        val request = requestBuilder.get().url(url).build()
        val call = clientBuilder.build().newCall(request)
        var response: Response? = null
        try {
            response = call.execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    fun getSync(url : String, netCall: NetCall) {
        val request = requestBuilder.get().url(url).build()
        val call = clientBuilder.build().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = netCall.failed(call, e)

            override fun onResponse(call: Call, response: Response) = netCall.success(call, response)
        })
    }

    fun post(url : String, body : RequestBody) : Response? {
        val call = clientBuilder.build().newCall(requestBuilder.post(body).url(url).build())
        var response: Response? = null
        try {
            response = call.execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    fun post(url : String, bodyParams : Map<String, String>) : Response? {
        val body = toRequestBody(bodyParams)
        return post(url, body)
    }

    fun postSync(url : String, bodyParams : Map<String, String>, netCall: NetCall) {
        val body = toRequestBody(bodyParams)
        clientBuilder.build().newCall(requestBuilder.post(body).url(url).build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = netCall.failed(call, e)

            override fun onResponse(call: Call, response: Response) = netCall.success(call, response)
        })
    }

    fun postData(url : String, data : String) : Response? {
        val body = RequestBody.create("text/html;charset=utf-8".toMediaTypeOrNull(), data)
        val call = clientBuilder.build().newCall(requestBuilder.post(body).url(url).build())
        var response: Response? = null
        try {
            response = call.execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    fun postDataSync(url : String, data: String, netCall: NetCall) {
        val body = RequestBody.create("text/html;charset=utf-8".toMediaTypeOrNull(), data)
        val call = clientBuilder.build().newCall(requestBuilder.post(body).url(url).build())
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = netCall.failed(call, e)

            override fun onResponse(call: Call, response: Response) = netCall.success(call, response)
        })
    }

    fun postJson(url : String, json : String) : Response? {
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val call = clientBuilder.build().newCall(requestBuilder.post(body).url(url).build())
        var response: Response? = null
        try {
            response = call.execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    fun postJsonSync(url : String, json: String, netCall: NetCall) {
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val call = clientBuilder.build().newCall(requestBuilder.post(body).url(url).build())
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = netCall.failed(call, e)

            override fun onResponse(call: Call, response: Response) = netCall.success(call, response)
        })
    }

    fun defaultUserAgent() = header("User-Agent", DefaultUserAgent)

    companion object {
        const val DefaultUserAgent = "Mozilla/5.0 (Linux; Android 11; M2002J9E Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045514 Mobile Safari/537.36 V1_AND_SQ_8.5.5_1630_YYB_D A_8050500 QQ/8.5.5.5105 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/69 SimpleUISwitch/0 QQTheme/1000 InMagicWin/0"

        /**
         * post的请求参数，构造RequestBody
         *
         * @param bodyParams 请求参数
         */
        private fun toRequestBody(bodyParams: Map<String, String>?): RequestBody {
            val formEncodingBuilder = FormBody.Builder()
            if (bodyParams != null) {
                val iterator = bodyParams.keys.iterator()
                var key = ""
                while (iterator.hasNext()) {
                    key = iterator.next()
                    bodyParams[key]?.let { formEncodingBuilder.add(key, it) }
                }
            }
            return formEncodingBuilder.build()
        }

        /**
         * 自定义网络回调接口
         */
        interface NetCall {
            @Throws(IOException::class)
            fun success(call: Call, response: Response)
            fun failed(call: Call, e: IOException)
        }

        object SSLSocketClient {
            fun getMySSLSocketFactory(): SSLSocketFactory? {
                return try {
                    val cf = CertificateFactory.getInstance("X.509")
                    var ca: Certificate?
                    ByteArrayInputStream("-----BEGIN CERTIFICATE-----\nMIIFLjCCBBagAwIBAgISBPEtQj85i5FSrPwWLgoo2GLoMA0GCSqGSIb3DQEBCwUA\nMDIxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQD\nEwJSMzAeFw0yMTAyMTcxMDU0NTVaFw0yMTA1MTgxMDU0NTVaMBkxFzAVBgNVBAMT\nDnd3dy5sdW9sb2xpLmNuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA\n4lJk2s4lroXymGvkfqj90MzbK3pUkoK5gq5ydeu8kfCTHWf9ykX9rF0FizRJUID8\nX8EatCnbfl0gOn3puYindqiC/MfaEADP5YaNexDj6YWPk36NytVI8CdKbgLj/RlS\n9Ojy87eqBezTbAvUZs6y60Kz0d0yAQrrAYqXYCz5+lxM1hkUfgG4Bi6+zEqg58Qd\nz8vUwxuY1+QC8Kyqz8UPnXQkbZKxv29EhYDZDbQY3sNb1RRcwE3GC2WWTZvCjAtG\nyxW20NmQuv4YrKQ32uE+xHHvfDwAT9JDSe1G24t+x3EZ3fzvc9w7nML1JDO75SzM\n0KA5giQbzX+dmZPuvwflMQIDAQABo4ICVTCCAlEwDgYDVR0PAQH/BAQDAgWgMB0G\nA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAMBgNVHRMBAf8EAjAAMB0GA1Ud\nDgQWBBTCDvX2qp1SPWUTbUWSAnwEfc39sTAfBgNVHSMEGDAWgBQULrMXt1hWy65Q\nCUDmH6+dixTCxjBVBggrBgEFBQcBAQRJMEcwIQYIKwYBBQUHMAGGFWh0dHA6Ly9y\nMy5vLmxlbmNyLm9yZzAiBggrBgEFBQcwAoYWaHR0cDovL3IzLmkubGVuY3Iub3Jn\nLzAlBgNVHREEHjAcggpsdW9sb2xpLmNugg53d3cubHVvbG9saS5jbjBMBgNVHSAE\nRTBDMAgGBmeBDAECATA3BgsrBgEEAYLfEwEBATAoMCYGCCsGAQUFBwIBFhpodHRw\nOi8vY3BzLmxldHNlbmNyeXB0Lm9yZzCCAQQGCisGAQQB1nkCBAIEgfUEgfIA8AB2\nAJQgvB6O1Y1siHMfgosiLA3R2k1ebE+UPWHbTi9YTaLCAAABd6/X3MIAAAQDAEcw\nRQIhAN9TMaFNcQcF6XhSnwFnDNKOnLINYT0OeSdMkL7NRXLzAiAtPFFr7wUE5QEj\noJXH4J7xwS3Avr9ewk0SGzNbx/qdPAB2AH0+8viP/4hVaCTCwMqeUol5K8UOeAl/\nLmqXaJl+IvDXAAABd6/X3NsAAAQDAEcwRQIhAPJRfyvg/wYVKDiKJObYiiQNqI6+\nm6P5HIMdEBLJnjY9AiBj2g5nfIPYlcJfHZ/uQgdr/E20uJT1TJ4ndmTO0pWYdDAN\nBgkqhkiG9w0BAQsFAAOCAQEAmL0GrTRuSEjg7oIjHH0mITN1n6+YDREWjag6yELI\nH0JFTQ/mHEOtvicizyrKdxt2R4ZCnlQ6i5Go+Ndw80c/7YdY3cDUQOXR2PH7fxvJ\nGcgOURVSQ37k2qmhdWpUr9sLdyw/xQit7iBtg1ORZ3K7FR5F1vuffycd0FmpupFv\n8c4ZHcJuOmnyVUgSCYlWJ8IdIVjAOO25CeNSvoQiEA1MFbwWRUi7Q5cDmcJDlyDO\norpmR6Vcru1f1WNPNkNDbZTNRyzJgsroCMdjd4uOvpiw5obIRh9bu8agZIQzF69F\nxSwKCga9n3EpERZq/xAkrMio8HOEszCu2NTjeRCNg5dvNg==\n-----END CERTIFICATE-----\n\n\n-----BEGIN CERTIFICATE-----\nMIIEZTCCA02gAwIBAgIQQAF1BIMUpMghjISpDBbN3zANBgkqhkiG9w0BAQsFADA/\nMSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\nDkRTVCBSb290IENBIFgzMB4XDTIwMTAwNzE5MjE0MFoXDTIxMDkyOTE5MjE0MFow\nMjELMAkGA1UEBhMCVVMxFjAUBgNVBAoTDUxldCdzIEVuY3J5cHQxCzAJBgNVBAMT\nAlIzMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuwIVKMz2oJTTDxLs\njVWSw/iC8ZmmekKIp10mqrUrucVMsa+Oa/l1yKPXD0eUFFU1V4yeqKI5GfWCPEKp\nTm71O8Mu243AsFzzWTjn7c9p8FoLG77AlCQlh/o3cbMT5xys4Zvv2+Q7RVJFlqnB\nU840yFLuta7tj95gcOKlVKu2bQ6XpUA0ayvTvGbrZjR8+muLj1cpmfgwF126cm/7\ngcWt0oZYPRfH5wm78Sv3htzB2nFd1EbjzK0lwYi8YGd1ZrPxGPeiXOZT/zqItkel\n/xMY6pgJdz+dU/nPAeX1pnAXFK9jpP+Zs5Od3FOnBv5IhR2haa4ldbsTzFID9e1R\noYvbFQIDAQABo4IBaDCCAWQwEgYDVR0TAQH/BAgwBgEB/wIBADAOBgNVHQ8BAf8E\nBAMCAYYwSwYIKwYBBQUHAQEEPzA9MDsGCCsGAQUFBzAChi9odHRwOi8vYXBwcy5p\nZGVudHJ1c3QuY29tL3Jvb3RzL2RzdHJvb3RjYXgzLnA3YzAfBgNVHSMEGDAWgBTE\np7Gkeyxx+tvhS5B1/8QVYIWJEDBUBgNVHSAETTBLMAgGBmeBDAECATA/BgsrBgEE\nAYLfEwEBATAwMC4GCCsGAQUFBwIBFiJodHRwOi8vY3BzLnJvb3QteDEubGV0c2Vu\nY3J5cHQub3JnMDwGA1UdHwQ1MDMwMaAvoC2GK2h0dHA6Ly9jcmwuaWRlbnRydXN0\nLmNvbS9EU1RST09UQ0FYM0NSTC5jcmwwHQYDVR0OBBYEFBQusxe3WFbLrlAJQOYf\nr52LFMLGMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjANBgkqhkiG9w0B\nAQsFAAOCAQEA2UzgyfWEiDcx27sT4rP8i2tiEmxYt0l+PAK3qB8oYevO4C5z70kH\nejWEHx2taPDY/laBL21/WKZuNTYQHHPD5b1tXgHXbnL7KqC401dk5VvCadTQsvd8\nS8MXjohyc9z9/G2948kLjmE6Flh9dDYrVYA9x2O+hEPGOaEOa1eePynBgPayvUfL\nqjBstzLhWVQLGAkXXmNs+5ZnPBxzDJOLxhF2JIbeQAcH5H0tZrUlo5ZYyOqA7s9p\nO5b85o3AM/OJ+CktFBQtfvBhcJVd9wvlwPsk+uyOy2HI7mNxKKgsBTt375teA2Tw\nUdHkhVNcsAKX1H7GNNLOEADksd86wuoXvg==\n-----END CERTIFICATE-----\n".toByteArray()).use { inputStream ->
                        ca = cf.generateCertificate(inputStream)
                    }
                    val keyStoreType = KeyStore.getDefaultType()
                    val keyStore = KeyStore.getInstance(keyStoreType)
                    keyStore.load(null, null)
                    keyStore.setCertificateEntry("ca", ca)
                    val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
                    val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
                    tmf.init(keyStore)
                    val s = SSLContext.getInstance("TLS")
                    s.init(null, tmf.trustManagers, SecureRandom())
                    s.socketFactory
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }

            fun getSSLSocketFactory(): SSLSocketFactory? {
                return try {
                    val sslContext = SSLContext.getInstance("SSL")
                    sslContext.init(null, getTrustManager(), SecureRandom())
                    sslContext.socketFactory
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }

            //获取TrustManager
            private fun getTrustManager(): Array<TrustManager> {
                return arrayOf(
                    object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            }

            fun getHostnameVerifier(): HostnameVerifier {
                return HostnameVerifier { s: String?, _: SSLSession? -> true }
            }

            fun getX509TrustManager(): X509TrustManager? {
                var trustManager: X509TrustManager? = null
                try {
                    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    trustManagerFactory.init(null as KeyStore?)
                    val trustManagers = trustManagerFactory.trustManagers
                    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                        "Unexpected default trust managers:" + Arrays.toString(
                            trustManagers
                        )
                    }
                    trustManager = trustManagers[0] as X509TrustManager
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return trustManager
            }
        }
    }
}