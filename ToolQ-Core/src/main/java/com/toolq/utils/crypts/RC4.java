package com.toolq.utils.crypts;

/**
 * @author luoluo
 * @date 2020/10/25 6:17
 */
public class RC4 {
	/** 加密 **/
	public static byte[] encrypt(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return encrypt_byte(data, key);
	}

	/** 解密 **/
	public static byte[] decrypt(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return RC4Base(data, key);
	}

	/** 加密字节码 **/
	public static byte[] encrypt_byte(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return RC4Base(data, key);
	}

	private static byte[] initKey(String aKey) {
		byte[] b_key = aKey.getBytes();
		byte[] state = new byte[256];

		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key.length == 0) {
			b_key = "Skyblade".getBytes();
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static byte[] RC4Base(byte[] input, String mKkey) {
		int x = 0;
		int y = 0;
		byte[] key = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}
}
