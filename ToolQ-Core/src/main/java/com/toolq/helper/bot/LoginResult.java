package com.toolq.helper.bot;

/**
 * @author luoluo
 * @date 2020/10/2 13:07
 */
public enum LoginResult {
	/**
	 * 登陆成功
	 */
	Success,
	/**
	 * 密码错误
	 */
	PasswordWrong,
	/**
	 * 滑块超时操作
	 */
	SliderTimeOut,
	/**
	 * 因为未知原因登录失败，请查看日志
	 */
	Fail,
	/**
	 * 滑块Ticket获取错误
	 * 错误原因 ：
	 * 1，IMEI（androidId）不合法
	 * 2，QQ协议信息错误
	 * 3，多次恶意登录
	 * 4，该设备不是QQ常用登录设备
	 */
	SliderGetTicketWrong,
	/**
	 * 该账号被回收
	 */
	Reclaimed,
	/**
	 * 上线失败，已登录但上线失败
	 */
	FailedToGoOnline,
	/**
	 * 协议包错误，一般错误为tlv格式或ssover等错误
	 */
	ProtocolWrong,
	/**
	 * 被冻结
	 */
	Freeze,
	/**
	 * 短信请求次数过多
	 */
	TooManySMSWrong,
	/**
	 * 验证码格式错误
	 *
	 * 多平台框架向ToolQ-sdk丢了一个为空的验证码字符串
	 */
	VerificationCodeFormatError,
	/**
	 * 短信验证码错误
	 */
	SMSWrong,
	/**
	 * 网络环境错误
	 */
	NetWorkError
}
