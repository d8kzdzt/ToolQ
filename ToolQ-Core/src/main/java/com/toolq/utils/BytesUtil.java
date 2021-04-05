package com.toolq.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * @author luoluo
 * @date 2020/10/1 11:54
 */
public class BytesUtil {
	public static  byte[] byteMerger(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static byte[] int16_to_buf(int i) {
		byte[] out = new byte[2];
		out[1] = (byte) i;
		out[0] = (byte) (i >> 8);
		return out;
	}

	public static byte[] int32_to_buf(int i) {
		byte[] out = new byte[4];
		out[3] = (byte) i;
		out[2] = (byte) (i >> 8);
		out[1] = (byte) (i >> 16);
		out[0] = (byte) (i >> 24);
		return out;
	}

	public static byte[] int64_to_buf(long j) {
		byte[] out = new byte[8];
		out[7] = (byte) ((int) (j));
		out[6] = (byte) ((int) (j >> 8));
		out[5] = (byte) ((int) (j >> 16));
		out[4] = (byte) ((int) (j >> 24));
		out[3] = (byte) ((int) (j >> 32));
		out[2] = (byte) ((int) (j >> 40));
		out[1] = (byte) ((int) (j >> 48));
		out[0] = (byte) ((int) (j >> 56));
		return out;
	}

	public static byte[] int64_to_buf32(long j) {
		byte[] out = new byte[4];
		out[3] = (byte) ((int) (j));
		out[2] = (byte) ((int) (j >> 8));
		out[1] = (byte) ((int) (j >> 16));
		out[0] = (byte) ((int) (j >> 24));
		return out;
	}

	public static int buf_to_int8(byte[] bArr, int i) {
		return bArr[i] & 255;
	}

	public static int buf_to_int16(byte[] bArr, int i) {
		return ((bArr[i] << 8) & 65280) + ((bArr[i + 1]) & 255);
	}

	public static int buf_to_int32(byte[] bArr, int i) {
		return ((bArr[i] << 24) & -16777216) + ((bArr[i + 1] << 16) & 16711680) + ((bArr[i + 2] << 8) & 65280) + ((bArr[i + 3]) & 255);
	}

	public static long buf_to_int64(byte[] bArr, int i) {
		return ((((long) bArr[i]) << 56) & -72057594037927936L) + ((((long) bArr[i + 1]) << 48) & 71776119061217280L) + ((((long) bArr[i + 2]) << 40) & 280375465082880L) + ((((long) bArr[i + 3]) << 32) & 1095216660480L) + ((((long) bArr[i + 4]) << 24) & 4278190080L) + ((((long) bArr[i + 5]) << 16) & 16711680) + ((((long) bArr[i + 6]) << 8) & 65280) + ((((long) bArr[i + 7])) & 255);
	}

	public static byte[] subByte(byte[] b, int off, int length){
		if (b != null && b.length > 0){
			byte[] b1 = new byte[length];
			System.arraycopy(b, off, b1, 0, length);
			return b1;
		}
		return null;
	}

	public static byte[] randomKey(int size){
		byte[] key = new byte[size];
		new Random().nextBytes(key);
		return key;
	}
}
