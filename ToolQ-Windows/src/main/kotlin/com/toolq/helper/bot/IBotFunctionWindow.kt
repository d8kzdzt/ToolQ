package com.toolq.helper.bot

import com.toolq.BotConfig
import com.toolq.ToolQ
import com.toolq.helper.logger.TLog
import com.toolq.utils.SwtUtil
import org.eclipse.swt.widgets.Shell

class IBotFunctionWindow : BotConfig.IBotFunction() {
    override fun GetSMSCode(toolQ: ToolQ, bot: Long, phone: String?, msg: String?, dt104: ByteArray?, dt174: ByteArray?): String? {
        var code : String? = null
        SwtUtil.sms_code("手机号：%s\n 需要验证码验证登录，是否获取验证码?\n本次验证过后，只要不变更ToolQ版本，一般不需要重新验证！\nToolQ不会以任何形式收集您的个人信息！".format(phone),
            object : SwtUtil.DeviceLockEvent() {
                override fun getSMSCode() : Boolean = submitAVerificationCodeRequest(toolQ, dt104, dt174)
                override fun checkCode(shell: Shell, sCode: String?) {
                    if(!sCode.isNullOrEmpty()) {
                        code = sCode
                        if(code == null) {
                            TLog.warn("Kotlin与Java交互出现问题！")
                        } else shell.close()
                    } else TLog.warn("输入的验证码为空或Null")
                }
            })
        return code
    }

    // windows 专用ticket获取
    override fun ticket(url: String, iqq: IQQ?): String? {
        return SwtUtil.ticket(url)
    }
}