package com.toolq.helper.captcha

import com.toolq.utils.BotUtils
import com.toolq.utils.RandomUtil
import java.awt.image.BufferedImage
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * By Ice Cream && AutoLuo
 * date: 2020-8-19
 * # C5UI97
 */
object TenCentCaptchaUtils {
    private const val UA = "TW96aWxsYS81LjAgKExpbnV4OyBBbmRyb2lkIDEwOyBNMjAwMko5RSBCdWlsZC9RS1ExLjE5MTIyMi4wMDI7IHd2KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBWZXJzaW9uLzQuMCBDaHJvbWUvNzcuMC4zODY1LjEyMCBNUVFCcm93c2VyLzYuMiBUQlMvMDQ1MzMyIE1vYmlsZSBTYWZhcmkvNTM3LjM2IFYxX0FORF9TUV84LjQuMTBfMTUwNl9IREJNX1QgUVEvOC40LjEwLjQ4MzAgTmV0VHlwZS80RyBXZWJQLzAuMy4wIFBpeGVsLzEwODAgU3RhdHVzQmFySGVpZ2h0LzcwIFNpbXBsZVVJU3dpdGNoLzAgUVFUaGVtZS8xMDAwIEluTWFnaWNXaW4vMA=="
    private fun getCaptchaPictureUrl(
            appId: String, qq: String, imageId: String?, sig: String, sess: String?, sid: String, index: Int
    ) = "https://t.captcha.qq.com/hycdn?index=$index&image=$imageId?aid=$appId&captype=&curenv=inner&protocol=https&clientype=1&disturblevel=&apptype=2&noheader=0&color=&showtype=&fb=1&theme=&lang=2052&ua=$UA&enableDarkMode=0&grayscale=1&subsid=3&sess=$sess&fwidth=0&sid=$sid&forcestyle=undefined&wxLang=&tcScale=1&uid=$qq&cap_cd=$sig&rnd=${Random.nextInt(100000, 999999)}&TCapIframeLoadTime=60&prehandleLoadTime=135&createIframeStart=${Date().time}487&rand=${Random.nextInt(100000, 999999)}&websig=&vsig=&img_index=$index"

    private fun getCollect(width: Int, sid: String): Map<String, String> {
        val token: String = Random.nextLong(2067831491, 5632894513).toString()
        var sx: Int = Random.nextInt(700, 730)
        val sy: Int = Random.nextInt(295, 300)
        val ex = sx + (width - 55) / 2
        var sTime: Int = Random.nextInt(100, 300)
        val res = StringBuilder("[$sx,$sy,$sTime],")
        val randy = intArrayOf(0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 3, -1, -1, -1, -2)
        while (sx < ex) {
            val x: Int = Random.nextInt(3, 9)
            sx += x
            val y = randy[Random.nextInt(randy.size)]
            val time: Int = Random.nextInt(9, 18)
            sTime += time
            res.append("[$x,$y,$time],")
        }
        res.append("[0,0,${Random.nextInt(10, 25)}]")
        val jsResponse = OkHttpClientUtils.get("https://t.captcha.qq.com/tdc.js?app_data=$sid&t=508485387?${Date().time}")
        val js = OkHttpClientUtils.getStr(jsResponse)
        val base64Str = Base64.getEncoder().encodeToString(js.toByteArray())
        val response = OkHttpClientUtils.post("http://collect.qqzzz.net", OkHttpClientUtils.addForms(
                "script", base64Str,
                "tokenid", token,
                "slideValue", res.toString()
        ), OkHttpClientUtils.addUA(OkHttpClientUtils.QQ_UA))
        val collectData: String
        val eks: String
        val length: String
        if (response.code == 200) {
            val jsonObject = OkHttpClientUtils.getJson(response)
            collectData = URLDecoder.decode(jsonObject.get("collectdata").asString, "utf-8")
            eks = jsonObject.get("eks").asString
            length = collectData.length.toString()
        } else {
            collectData = "nIXLBDdHYhKzs/DpuAKh/q/xncVi259oy lhhF6ZZj NNILYRaA/ddVcg7XNV3vCVTrbqZmE9W oFhcOKrFqRFsrfocYSan2U EVz2etrit89QbKQcLm8SI/Z9meICCwYeiZkPo8OBf7RhLvsJaRVL7asZTh9CYuFI4PpiEcNzArOdoM2QtOHXBboMfQEqItUbqI4YseKYQKgEv0pjL9GUh8KU3Da2qjhArZWm5CBjzSn9hLqlPcBSxpOfzcpAos7DIV/cNZjqNewnobJsc3miRgiKant7B1sI6EKgFEgvdyWzpHL6NmPqPHHRLkp22USYBV3veSrmAHp hvhs1bUnovmFgsdTtcksyc8zCETiE9BKRR0nAWRd8oWOfW2lau7yEsba70gD3aKR/yL6fMxI6dQn2/lmgIl5TQYGWSBzNfaHGG7vwdE1U//9HO zVSKSiUXWmQTJP2FjZLQSUia6/xncVi259oFI4PpiEcNzDMwXM1gr2hoYeOYVEIUmcjapH56k9ykKZDMy7 IoDBMhDxhW6yAmgqU2uPrKq mNxr6NzjoqHW26/xncVi259o xLbqRK95Ikxtlii7eY0A8vvyY6u4DK9bfx4Gy9vG5tUMHU1eiRo8yBK5WKBYlFOKWYOMxJZSraq0hEM7ZurpaH0xi fZ8BLvcoO9RCbMR1KmQINEnrix//AzurahzS9jARAshgPILp bkOjnkfhuoJqdU5Y90g/AqniuToChF5vJC0r8BEhlm1ix88Xaxi4AY9hBCCo8Sc5A  VloOosyxpOfzcpAos C30FEfypkeyEIN5HVFbvMlVAezZaZhOMDLOYUWulr6GpwWmhGYMJK/xncVi259ob9G0HSGpInrL2O/5B3Yrc35eW2eKVr2dGIh7lG8FSce wdLUSsynjcjWj35 VDOl2E2NKeewBU7Rqlp5NAcBQPvAH245/lhAUr8 BkbjSlU7IzdBUlTepfZ2pIPeoUSslwbf1FfKmR6oAUlm6WBeZ4Tn5l2A0F7HTVm8mns pfjv1y2BlRf0mp68/WsFx9VD6RSs4iw1hL83jrMgxlCUwBDWSLdjMv8o2db8wsrRwrVsbmsp4kXYc4Tn5l2A0F7HQtO2/095TWUvU/ig26uXqBsoXRVivN Iw1cg5jrO/ ho2JLG0qeQzY7NwUfo5HuWBtyIXnjb78MOIE7tVxMQfFW7X /XltgehOfmXYDQXsdquBPxb3G53O/XLYGVF/SaRuJKob592HJHUJsseZ4TquCJuHPF9lUWjDhf/3putkSnlJt8VM 8dlYwNLjm/EasudZWFzWNuUtu47gcvUcMxcjWj35 VDOlZCtQnImNPqceOFouTvK/GTWJtKANPYFVw1cg5jrO/ juxeQWYnGmoM/yzIAh6dHCudZWFzWNuUteV61qeHu8zQGZ8JZh9XQYiGHzoWU9t0HI1o9 flQzpfGFFQaNDxzYZCtQnImNPqcvU/ig26uXqBsoXRVivN I8f1p7ijswxUxdPahSfcpJow4X/96brZEDhRTuNYTtGbsXk1/pxpdoFW7X /Xltgecjajn3lGQKHI1o9 flQzpWQrUJyJjT6nL1P4oNurl6hG4kqhvn3YchK6vPZFTfkZTh1dstLeIB3uxeQWYnGmoHTxYtD2lQrAX7HOMsSAXilWgpqdCVySFRxPM/C9ICFuyNaPfn5UM6VkK1CciY0 py9T KDbq5eoRuJKob592HISurz2RU35GU4dXbLS3iAdbR84SHIhvp3uxeQWYnGmoAafJWm4kt2h1WaDuPW4A1cBmfCWYfV0GFW7X /XltgeyNaPfn5UM6WK1tDDEOzW8Q2KNavPBklYymItofZtRSQq0P6zBpcKjztbblAdkucTeA6uOgWZoh/mFLoweZz1ZYyQQ0D5Pe7BBp8labiS3aFfsc4yxIBeKWA3bbheW1Zw/fqyFa8aDUadIBnWivxtAGQrUJyJjT6nymItofZtRSSZLQ 1Z4IRG0biSqG fdhyErq89kVN RnmFLoweZz1Ze7F5BZicaag2Um2K/ JlTjVZoO49bgDVw3Z5 Ht0OvfdqIaaJNBEDtj9M07Ucycy2QrUJyJjT6nymItofZtRSRlKrjENjo5rM jm/ooPHNdud6pxLUnw2ZtHzhIciG nW0fOEhyIb6dtlXYHALckMYNZOMI8XUwovy8c/EF/ZVLX7HOMsSAXinBYr r5LQdi/CiK5JMQMgeajgP8oNlPdc6tJC0/SNqPK8IBVeQjebewA8XNwcz9KegdrqbQDYzzTsjN0FSVN6lDWTjCPF1MKLZSbYr/4mVONjB/2Sofv0dfhQQ 4bwJ71fsc4yxIBeKQ3Z5 Ht0OvfY/TNO1HMnMtqOA/yg2U91y3COtRGyl7uLcI61EbKXu6ZLQ 1Z4IRG70f2gIDmGFZ5PX6/XMW1L4g8DofrsPo  MJZGO6voSh/Fh/0NrR4MvVZoO49bgDV0Dm1B39ep5SDdnn4e3Q69 dIBnWivxtADO11nFRqMuSLcI61EbKXu6ZLQ 1Z4IRG4KyPpYbFQE7ud6pxLUnw2bqO mAcc2gKdlJtiv/iZU4fhQQ 4bwJ73Ywf9kqH79HX4UEPuG8Ce91WaDuPW4A1cN2efh7dDr32P0zTtRzJzLDp3/OPM3UDtkK1CciY0 pxynVbXN4gQUcMkSZ6usdMwtNN1ZkOA11ReCMIy9GOk4ud6pxLUnw2bNTxIj0qjesOMJZGO6voShlblrTfk7eLFfsc4yxIBeKd5tzOIuCTQrnuGPLklRYbDI1o9 flQzpTO11nFRqMuSDtD043HUMpl3PBKyHwBxTQ25Q7RWKUBG1y0QXy51bOQNZOMI8XUwonF2SLSzVZyGX7HOMsSAXiklQk6QoR7Uc7rDoT6PBufWE3rDuzAbVI7f6qxotQfIvTSVY2so00rUOTzdA/c2YmU2IyUt/GJV11GAibQEF6An Q/iEw9USJpfsc4yxIBeKZ656vcV0wcnyNaPfn5UM6Whfg7ejneEOHDJEmerrHTMPmLGZ2tRB9653qnEtSfDZuX1GpeUPA9PkgOtc5i3VTZcOPL06Le0tCFnH1mpK /DszxgXIsaMRWLVUhHdjj148APFzcHM/Sn31dd/i5NMOHXLRBfLnVs5M66tkZFyOuxX7HOMsSAXikC9ftNMYQQtxdUOakhfgmE7AFqo1s1NP2Soz8C7JP2qY2oTEO0eXZZWlaKOw/VgngfoTXqR7nyqaz/w/JydWcdmrgzsNeRCV2JpGdE VoXsGdNLdS/ipWV6lyWrsBPsqnuEPscFXUaIKCLQdebWOlbFLRU28NAqoljVZzeRMJHTihw 3VPhI2oD81Lg0lnLny/JzN3G4mua4rbZPKzpi8PURwI6zjQYOav8Z3FYtufaK/xncVi259oD1fXd/sGWEyap2GmBZbNST0XE4Sy5oj7vPXABjrvl YtpQp1E38EbamrJHk6bzxyZzI7iJhLPoO14nlDvP6stKpILUbd2kzNYY94QeuIEk5jXUFMjkMagWPSzQxr0bxRQyGj53KvJO81YY1Wt4auWOK71o1lZsv6I2/YgkynBKeEssai 8EFGAlvUZbG5ApgAJSUmkzVvoQAlJSaTNW hAabOrL3pxOzAJSUmkzVvoTNTVUjioOGQEhROMdqlLywBps6svenE7MqSqxbUnqDHVvwmm0dqMQGHvGaX8i/EgzKdtJZsOgZOFsr9LTmvyrOWCd2Nxp/S8hXUBp20JsksgjdkzRP99sRiBnpOHE1Bmc4NhDyXddu4wRgUXNh7FeM9/naZHOWr4xCngEpRC830Dg2EPJd127jtK7AnceQVqceqmY/u0Ur9dErq8QdR MN0SurxB1H4w3RK6vEHUfjDXXGajcmulzLLs6sg6MqRx11xmo3Jrpcy/a4jDumertH55f  ZvVIHgeqmY/u0Ur9dErq8QdR MNp2UEXT2giPceqmY/u0Ur9XXGajcmulzLcYE5X5yT3yb2uIw7pnq7Rx6qZj 7RSv1kq/5BOMXRMX2uIw7pnq7R eX/vmb1SB4HqpmP7tFK/Xnl/75m9UgeB6qZj 7RSv1HqpmP7tFK/V1xmo3Jrpcy eX/vmb1SB40SurxB1H4w0eqmY/u0Ur9eeX/vmb1SB40SurxB1H4w0eqmY/u0Ur9eeX/vmb1SB455f  ZvVIHgeqmY/u0Ur9YwMeyIZfLJ755f  ZvVIHj2uIw7pnq7R eX/vmb1SB455f  ZvVIHhxgTlfnJPfJueX/vmb1SB49riMO6Z6u0cG3IheeNvvw eX/vmb1SB49riMO6Z6u0dxgTlfnJPfJu7v/b6Fzr0D7u/9voXOvQPu7/2 hc69A4wrTJg0mI53cYE5X5yT3yZxgTlfnJPfJva4jDumertHcYE5X5yT3yZxgTlfnJPfJnGBOV ck98muK5xZl9Ckh/RK6vEHUfjDe7v/b6Fzr0DY66dcoWy4gNsq9rN3dG6Pva4jDumertHwq9iKQ40iiZxO7ivjoDR6qmXWnAy ZjX8t4RlQU6occGWGqoZ3N5OuLjWbi0CUW93UL5i1AOsZkGWGqoZ3N5OvLeEZUFOqHHH1KyCOpB2nTt8YyToWjk4OX1GpeUPA9Pjs3BR jke5Zfsc4yxIBeKVw48vTot7S0Y/TNO1HMnMvf6qxotQfIveYw1EOqjmsTz6Ob ig8c13k9fr9cxbUvjsjN0FSVN6l Q/iEw9USJpfsc4yxIBeKVw48vTot7S0sMzLLcz3EnMqeSJzxzJajHDJEmerrHTMggpx9qQmt3gNuUO0VilARjN7sZbZLkrKX7HOMsSAXimnLTdMzOQWmKfxgV3Y54PqN1pinUwcUtHGR/LOq4KB wqA9ORqjUIQectpkzIuK3JUoLWfEVwGGyFgkD7tQms5"
            eks = "UKFp ryeZvXggx5kqYFMxvFv1zcMpCQpR/tNmmmUNeu7ItF6nMPVbcUXkZ0S bjoElJtNAE5conKhyyrXXcU/Yq8jLa92FDkEmgD/ASlHFjFQI2wnJcTZ83mHayjhmXUpXAxjPaVTZF9ZasX8zLpBzxSayJIAlrb4PAwk6HTIvYBWMwVrfIXDW67ZLqR5cIH9mE1Gjkc49v2yuKvuhZ4Asl4ELSlKP6h27S8c5ZRz3JYl5tBH5Gh5kj6lbf/K9BMHCQDv67q4dtU4kZklOnjxsqy48qT/E7C"
            length = "4928"
        }
        return mapOf("collectData" to collectData, "eks" to eks, "length" to length)
    }

    private fun getWidth(imageAUrl: String, imageBUrl: String): Int {
        val imageA: BufferedImage = ImageIO.read(URL(imageAUrl))
        val imageB: BufferedImage = ImageIO.read(URL(imageBUrl))
        val imgWidth = imageA.width
        val imgHeight = imageA.height
        var t = 0
        var r = 0
        for (i in 0 until imgHeight - 20) {
            for (j in 0 until imgWidth - 20) {
                val rgbA = imageA.getRGB(j, i)
                val rgbB = imageB.getRGB(j, i)
                if (abs(rgbA - rgbB) > 1800000) {
                    t++
                    r += j
                }
            }
        }
        return (r / t.toFloat()).roundToInt() - 55
    }

    private fun getCaptcha(appId: String, sig: String, qq: String): Map<String, String> {
        val firstResponse = OkHttpClientUtils.get("https://t.captcha.qq.com/cap_union_prehandle?aid=$appId&captype=&curenv=inner&protocol=https&clientype=1&disturblevel=&apptype=2&noheader=1&color=&showtype=&fb=1&theme=&lang=2052&ua=$UA&enableDarkMode=0&grayscale=1&cap_cd=$sig&uid=$qq&wxLang=&subsid=1&callback=_aq_431172&sess=")
        val jsonObject = OkHttpClientUtils.getJson(firstResponse, "\\{.*\\}")
        val url = "https://t.captcha.qq.com/cap_union_new_show?aid=$appId&captype=&curenv=inner&protocol=https&clientype=1&disturblevel=&apptype=2&noheader=1&color=&showtype=&fb=1&theme=&lang=2052&ua=$UA&enableDarkMode=0&grayscale=1&subsid=2&sess=${jsonObject.get("sess").asString}&fwidth=0&sid=${jsonObject.get("sid").asString}&forcestyle=undefined&wxLang=&tcScale=1&noBorder=noborder&uid=$qq&cap_cd=$sig&rnd=${RandomUtil.randInt(
            1000000 downTo 999999)}&TCapIframeLoadTime=271&prehandleLoadTime=356&createIframeStart=${Date().time}"
        val secondResponse = OkHttpClientUtils.get(url)
        val html = OkHttpClientUtils.getStr(secondResponse)
        val height = BotUtils.regex("(?<=spt:\\\")(\\d+)(?=\\\")", html)
        val collectName = BotUtils.regex("(?<=collectdata:\\\")([0-9a-zA-Z]+)(?=\\\")", html)
        val sess = BotUtils.regex("sess:\"", "\"", html)
        val imageId = BotUtils.regex("&image=", "\"", html)
        val imageAUrl = getCaptchaPictureUrl(appId, qq, imageId, sig, sess, jsonObject.get("sid").asString, 1)
        val imageBUrl = getCaptchaPictureUrl(appId, qq, imageId, sig, sess, jsonObject.get("sid").asString, 0)
        val width = getWidth(imageAUrl, imageBUrl)
        val ans = "$width,$height;"
        val collect = getCollect(width, jsonObject.get("sid").asString)
        val map = mutableMapOf("sess" to sess!!, "sid" to jsonObject.get("sid").asString,
                "qq" to qq, "sig" to sig, "ans" to ans, "collectName" to collectName!!,
                "cdata" to "0", "width" to width.toString(), "url" to url)
        map.putAll(collect)
        return map
    }

    private fun identifyCaptcha(appId: String, map: Map<String, String>): CommonResult<Map<String, String>> {
        val firstResponse = OkHttpClientUtils.get("https://ssl.captcha.qq.com/dfpReg?0=" + URLEncoder.encode(OkHttpClientUtils.QQ_UA, "UTF-8") + "&1=zh-CN&2=2.2&3=2.2&4=24&5=8&6=-480&7=1&8=1&9=1&10=u&11=function&12=u&13=Linux%20armv8l&14=0&15=e466827d3971a555235e032f6e6f19d2&16=e1bea39464adad65029a90621897e93c&17=a1f937b6ee969f22e6122bdb5cb48bde&18=0&19=040110b2b88b59674d8592a2c9c1c006&20=8733932487339324&21=2.75%3B&22=1%3B1%3B1%3B1%3B1%3B1%3B1%3B0%3B1%3Bobject0UTF-8&23=0&24=5%3B1&25=2ef8be5d2413046bf04e52c22e8e3b90&26=48000_2_1_0_2_explicit_speakers&27=c8205b36aba2b1f3b581d8170984e918&28=Adreno(TM)620&29=e0d41c1e16a161b6256e7256bede614d&30=cee2ad1773391cdf6e49e9964bc7303c&31=0&32=0&33=0&34=0&35=0&36=0&37=0&38=0&39=0&40=0&41=0&42=0&43=0&44=0&45=0&46=0&47=0&48=0&49=0&50=0&fesig=8080253204130898043&ut=1055&appid=0&refer=https%3A%2F%2Ft.captcha.qq.com%2Fcap_union_new_show&domain=t.captcha.qq.com&fph=11000E0D2D392DCB32D8D00B1FBAF33BA41BC20CF40DA603034A51B826C20F210B370ECBB3FF5557607346A1D1431A980F21&fpv=0.0.15&ptcz=04496fbd2c56005c042ec80882c90e462e9f9565745f87d2dc57f175aa75e132&callback=_fp_073502", OkHttpClientUtils.addReferer(map.getValue("url")))
        val fpSig = OkHttpClientUtils.getJson(firstResponse, "\\{.*\\}").get("fpsig").asString
        val secondResponse = OkHttpClientUtils.post("https://t.captcha.qq.com/cap_union_new_verify", OkHttpClientUtils.addForms(
                "aid", appId,
                "captype", "",
                "curenv", "inner",
                "protocol", "https",
                "clientype", "1",
                "disturblevel", "",
                "apptype", "2",
                "noheader", "1",
                "color", "",
                "showtype", "",
                "fb", "1",
                "theme", "",
                "lang", "2052",
                "ua", UA,
                "enableDarkMode", "0",
                "grayscale", "1",
                "subsid", "2",
                "sess", map.getValue("sess"),
                "fwidth", "0",
                "sid", map.getValue("sid"),
                "forcestyle", "undefined",
                "wxLang", "",
                "tcScale", "1",
                "noBorder", "noborder",
                "uid", map.getValue("qq"),
                "cap_cd", map.getValue("sig"),
                "rnd", Random.nextInt(100000, 999999).toString(),
                "TCapIframeLoadTime", "426",
                "prehandleLoadTime", "293",
                "createIframeStart", Date().time.toString(),
                "cdata", "0",
                "ans", map.getValue("ans"),
                "vsig", "",
                "websig", "",
                "subcapclass", "",
                map.getValue("collectName"), map.getValue("collectData"),
                "fpinfo", "fpsig=$fpSig",
                "eks", map.getValue("eks"),
                "tlg", map.getValue("collectData").length.toString(),
                "vlg", "0_0_1",
                "nonce", "eda1152f11f1daf0"
        ), OkHttpClientUtils.header(OkHttpClientUtils.add("user-agent", OkHttpClientUtils.QQ_UA).add("referer", map.getValue("url"))) )
        val jsonObject = OkHttpClientUtils.getJson(secondResponse)
        return when (jsonObject.get("errorCode").asInt) {
            0 -> CommonResult(200, "成功", mapOf("ticket" to jsonObject.get("ticket").asString, "randStr" to jsonObject.get("randstr").asString))
            else -> CommonResult(400, "验证码识别失败，请稍后重试！！", mapOf("ticket" to "null"))
        }
    }

    /**
     * qq空间pc版 ： 549000912
     * qq空间手机版： 549000929
     */
    private fun identify(appId: String, sig: String = "", qq: Long = 0L): CommonResult<Map<String, String>> {
        val map = getCaptcha(appId, sig, qq.toString())
        return identifyCaptcha(appId, map)
    }

    fun identifyByUrl(url: String): CommonResult<Map<String, String>> {
        val paras = url.split("?")[1].split("&")
        var aid = ""
        var sig = ""
        var qq = ""
        for (para in paras) {
            val kv = para.split("=")
            when (kv[0]) {
                "aid" -> aid = kv[1]
                "cap_cd" -> sig = kv[1]
                "uin" -> qq = kv[1]
            }
        }
        return identify(aid, sig, qq.toLong())
    }

}