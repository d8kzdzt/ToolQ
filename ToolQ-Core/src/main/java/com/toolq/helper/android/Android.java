package com.toolq.helper.android;

import com.google.gson.JsonObject;
import com.toolq.helper.logger.TLog;
import com.toolq.utils.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author luoluo
 * @date 2020/10/2 18:52
 */
public final class Android {
	/**
	 * 安卓联网设备标识imsi
	 */
	public static String imsi = "460023785098616";
	/**
	 * 安卓标志（imei）
	 */
	public static String androidId = "53f156a0b5b89966";
	/**
	 * 设备名
	 */
	public static String machineName = "M2002J9E";
	/**
	 * 设备类型
	 * ios android windows
	 */
	public static final byte[] osType = "android".getBytes();
	/**
	 * 厂商
	 */
	public static String machineManufacturer = "Xiaomi";
	/**
	 * 系统版本
	 */
	public static String machineVersion = "11";
	/**
	 * 系统SDK版本
	 */
	public static int machineVersionNumber = 30;
	/**
	 * WIFI名
	 */
	public static final String wifiSSID = "SevenZeroFive";
	public static final byte[] wifiBSSID = "D8:9E:61:9C:3D:E0".getBytes();
	public static final String macAddress = "20:F4:78:6B:80:AA";
	/**
	 * 网络状态
	 * 建议看tlv_124.java
	 */
	public static final String apn = "wifi";
	/**
	 * 服务商名字
	 */
	public static String apnName = "中国移动";

	/**
	 * 一个对ToolQ没有什么用的对象
	 */
	private static byte[] ksid = HexUtil.hexStringToBytes("14751d8e7d633d9b06a392c357c675e5");
	private static byte[] tgt;
	private static byte[] tgtKey;
	/**
	 * 随机key
	 */
	private static final byte[] randKey = HexUtil.hexStringToBytes("EEBE5BC313F924C8A001A28C26849386");

	/**
	 * 从某路径下加载某个安卓设备环境文件
	 *
	 * @param path 路径
	 */
	public static boolean loadByFile(String path) {
		try {
			JsonObject data = JsonUtil.parseJsonObject( FileUtil.readFileString(path + "/android.info") );
			androidId = data.get("androidId") == null ? "abcdabcdabcdabcd".toLowerCase() : data.get("androidId").getAsString();
			machineName = data.get("machineName") == null ? "ToolQ" : data.get("machineName").getAsString();
			machineManufacturer = data.get("machineManufacturer") == null ? "二次元编程会社" : data.get("machineManufacturer").getAsString();
			machineVersion = data.get("machineVersion") == null ? "10" : data.get("machineVersion").getAsString();
			machineVersionNumber = data.get("machineVersionNumber") == null ? 29 : data.get("machineVersionNumber").getAsInt();
			apnName = data.get("apnName") == null ? "中国移动" : data.get("apnName").getAsString();
			return true;
		} catch(Exception e) {
			if(e.getClass() != FileNotFoundException.class)
				TLog.INSTANCE.warn("AndroidLoadError", e);
		}
		return false;
	}

	/**
	 * 使用随机器生成随机的安卓设备信息并保存于本地
	 *
	 * （不建议使用，随机出来的不是真实设备，容易出现环境异常）
	 *
	 * @param path 路径
	 */
	public static void loadByRand(String path) {
		JsonObject data = new JsonObject();
		data.addProperty("androidId", RandomUtil.getRandomString(16).toLowerCase());
		data.addProperty("machineName", "M2002J9E");
		data.addProperty("machineManufacturer", "Xiaomi");
		data.addProperty("machineVersion", "10");
		data.addProperty("machineVersionNumber", 29);
		data.addProperty("apnName", "中国移动");
		String json = JsonUtil.formatJson(data.toString());
		try {
			FileUtil.INSTANCE.saveFile(path + "/android.info", json);
			loadByFile(path);
		} catch(IOException e) {
			TLog.INSTANCE.warn("loadByRand", e);
			System.exit(1);
		}
	}

	public static byte[] getGuid() {
		return MD5.toMD5Byte(androidId + macAddress);
	}

	public static byte[] getTgtgKey() {
		return MD5.toMD5Byte(
			BytesUtil.byteMerger(
				MD5.toMD5Byte(macAddress) ,
				getGuid()
			)
		);
	}

	public static byte[] getKsid() {
		return ksid;
	}

	public static void setKsid(byte[] ksid) {
		if(ksid != null) {
			
			Android.ksid = ksid;
		}
	}

	public static byte[] getTgt() {
		return tgt;
	}

	public static void setTgt(byte[] tgt) {
		Android.tgt = tgt;
	}

	public static void setTgtKey(byte[] tgtKey) {
		Android.tgtKey = tgtKey;
	}

	public static byte[] getTgtKey() {
		return tgtKey;
	}

	public static byte[] getRandKey() {
		return randKey;
	}
}
