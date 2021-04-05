package com.toolq.helper.bot;

public interface IQQ {
	/**
	 * 协议应用标识
	 */
	long appId();

	/**
	 * 基础标识
	 */
	int subAppId();

	/**
	 * Ping版本
	 */
	int pingVersion();

	/**
	 * IP版本
	 */
	int ipVersion();

	/**
	 * OICQ版本
	 */
	int ssoVersion();

	/**
	 * 协议Tlv版本
	 */
	int msfSsoVersion();

	int tgtgVersion();
	int miscBitmap();
	int subSigMap();
	int dbVersion();

	/**
	 * 协议发布时间
	 */
	int buildTime();
	int localId();
	int protocolVersion();

	/**
	 * 登录方法
	 * 1 密码登录
	 */
	int loginType();

	boolean isGuidAvailable();
	boolean isGuidFromFileNull();
	boolean isGuidChange();

	/**
	 * 构建版本
	 */
	String buildVersion();

	/**
	 * 上传协议版本
	 */
	String agreementVersion();

	/**
	 * 协议应用包名
	 */
	String packageName();

	/**
	 * 协议版本号
	 */
	String packageVersion();

	/**
	 * 协议组件标识
	 */
	byte[] tencentSdkMd5();
}
