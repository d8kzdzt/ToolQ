package com.toolq;

import com.toolq.helper.android.Android;
import com.toolq.helper.bot.IQQ;
import com.toolq.helper.logger.TLog;
import com.toolq.qq.dataClass.ProtocolMode;
import com.toolq.qq.protocol.device.IDeviceLock;
import com.toolq.utils.ToolQUtil;

import java.io.File;

/**
 * @author luoluo
 * @date 2020/9/30 21:59
 */
public class BotConfig {
	/**
	 * 该项目SDK版本号
	 */
	public static final int sdkVersion = 10;

	/**
	 * 是否为中文
	 */
	public static final boolean isChinese = true;
	/**
	 * 发布时请设置为false
	 */
	public static final boolean debug = true;
	/**
	 * 是否接收好友信息
	 */
	private static boolean allowFriendMessage = true;

	/**
	 * 当前操作系统
	 */
	private static String os = "";
	/**
	 * 数据文件保存路径
	 */
	private static String dataPath = "";
	/**
	 * 是否并发处理消息
	 * 多个消息处理器同时处理消息，不分先后
	 */
	public static boolean concurrentHandle = false;

	/**
	 * 心跳周期
	 *
	 * 每多少毫秒执行一次心跳
	 */
	public static long heartBeatCycleTime = 60 * 1000 * 3;

	/**
	 * 普通消息包大小
	 *
	 * 超过该大小则上传到长消息服务器
	 */
	public static int commonMsgMaxSize = 3 * 1024;

	/**
	 * 协议类型
	 *
	 * 遗弃！！！
	 */
	public static ProtocolMode protocolMode = ProtocolMode.Android;

	/**
	 * 默认接包等待时间
	 */
	public static long defaultPacketWaitTime = 5000 * 2;

	/**
	 * 滑块Ticket
	 */
	public static IBotFunction IBotFunction = new IBotFunction() {
		@Override
		public String ticket(String url, IQQ iqq) {
			return null;
		}

		/**
		 * 提交短信验证码
		 *
		 * @param toolQ Bot实体对象
		 * @param bot   机器人QQ
		 * @param phone 手机号
		 * @param msg   提示
		 * @param dt104 通行证
		 * @param dt174 通行证
		 * @return 返回短信验证码
		 */
		@Override
		public String GetSMSCode(ToolQ toolQ, long bot, String phone, String msg, byte[] dt104, byte[] dt174) {
			return null;
		}
	};

	public static void init(String dataPath) {
		setDataPath(dataPath);
		boolean success = Android.loadByFile(dataPath);
		if(!success) {
			ToolQUtil.INSTANCE.initAndroid(dataPath);
		}
	}

	public static void setOs(String os) {
		TLog.INSTANCE.info("操作系统：" + os);
		BotConfig.os = os;
	}

	public static String getOs() {
		return os;
	}

	public static void setDataPath(String dataPath) {
		dataPath = new File(dataPath).getAbsolutePath();
		TLog.INSTANCE.info("数据路径：" + dataPath);
		BotConfig.dataPath = dataPath;
	}

	public static String getDataPath() {
		return dataPath;
	}

	public static boolean isAllowFriendMessage() {
		return allowFriendMessage;
	}

	public static void setAllowFriendMessage(boolean allowFriendMessage) {
		BotConfig.allowFriendMessage = allowFriendMessage;
	}

	/**
	 * 机器人函数类
	 *
	 * ticket Bot登陆需要使用滑块，url为滑块Url，iqq为协议信息
	 *
	 */
	public abstract static class IBotFunction extends IDeviceLock {
		public abstract String ticket(String url, IQQ iqq);
	}
}
