package com.toolq.helper.bot;

/**
 * @author luoluo
 * @date 2020/10/2 0:28
 */
public enum BotStatus {
	/**
	 * 机器人因为冻结，密码错误等各种原因无法登录，导致已销毁
	 */
	destroyed,
	/**
	 * 已登录但没有上线
	 */
	hasLogged,
	/**
	 * 在线
	 */
	online,
	/**
	 * 未登录
	 */
	notLoggedIn,
	/**
	 * Bot被另外一个设备登录，导致本设备掉线
	 */
	remoteLanding,
	/**
	 * 报废状态
	 *
	 * 产生原因：
	 * ToolQ网络连接突然断开
	 * 被手动shut
	 */
	ruin,
	/**
	 * 正在断线重连
	 */
	reconnecting
}
