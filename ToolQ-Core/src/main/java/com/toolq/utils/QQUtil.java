package com.toolq.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author luoluo
 * @date 2020/10/1 21:50
 */
public class QQUtil {
	public static boolean checkAccount(Long qq, String password) {
		return qq != null && password != null && qq >= 10000 && qq <= 4000000000L;
	}

	public static boolean isRightQQ(Long qq) {
		return checkAccount(qq, "");
	}

	public static String randomAndroidId() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 15; i++) {
			builder.append(new Random().nextInt(10));
		}
		return builder.toString();
	}

	public static long ipToLong(String ip) {
		String[] ipArray = ip.split("\\.");
		long[] ipNums = new long[4];
		for(int i = 0; i < 4; ++i) {
			ipNums[i] = Long.parseLong(ipArray[i].trim());
		}
		return ipNums[0] * 256L * 256L * 256L
				+ ipNums[1] * 256L * 256L
				+ ipNums[2] * 256L
				+ ipNums[3];
	}

	public static String get_mpasswd() {
		try {
			byte[] seed = SecureRandom.getSeed(16);
			StringBuilder ret = new StringBuilder();
			for(int i = 0;i < 16; i++) {
				ret.append((char) ((new Random().nextBoolean() ? 97 : 65) + Math.abs(seed[i] % 26)));
			}
			return ret.toString();
		} catch (Throwable th) {
			return "1234567890123456";
		}
	}

	public static long GetBkn(String skey) {
		int hash = 5381;
		for (int i = 0, len = skey.length(); i < len; ++i)
			hash += (hash << 5) + (int)skey.charAt(i);
		return hash & 2147483647;
	}

}
