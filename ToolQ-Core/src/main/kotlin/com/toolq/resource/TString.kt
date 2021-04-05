package com.toolq.resource

import com.toolq.BotConfig

/**
 * @author luoluo
 * @date 2020/9/30 22:11
 */
object TString {
    @JvmField
    val socket_connect_fail = Couple("Socket连接失败！", "Socket connection failed!")
    @JvmField
    val socket_error = Couple("Socket出现错误", "Socket error")
    @JvmField
    val socket_connect = Couple("正在连接%s:%s...", "Connect %s:%s...")
    @JvmField
    val slider_get_ticket = Couple("请滑动滑块", "Sliding slider")
    @JvmField
    val sms_get_code = Couple("获取验证码", "Get verification code")
    var sms_get_code_button = Couple("获取", "Get")
    @JvmField
    val bot_account_error = Couple("账号密码不合法", "Invalid account password")
    val bot_server_error = Couple("无法获取机器人服务器", "Unable to get the robot server")
    @JvmField
    val jce_packet_type_error = Couple("该对象不是继承Jce类。", "The object does not inherit the Jce class.")
    val acgLeague = Couple("二次元编程社团", "https://blog.acgp.club/index.php/archives/42/")
    val acgLeagueAgreement = Couple("二次元编程社团规则", "ToolQ为二次元编程社团的内部项目虽然属于二次元编程社团但仅为首开发者拥有，代码贡献者仅仅拥有对部分代码的改写权！")
    @JvmField
    val bot_not_found = Couple("Bot在ToolQManager中不存在", "Bot does not exist in Tool Q Manager")

    /**
     * 字符串对集
     */
    class Couple(val chinese: String, val english: String) {
        fun get(): String {
            return if (BotConfig.isChinese) chinese else english
        }
    }
}