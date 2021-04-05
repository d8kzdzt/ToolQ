package com.qq.pb;

import com.toolq.utils.HexUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author luoluo
 * @date 2020/11/8 12:38
 */
public class ByteStringMicro {
	public static final ByteStringMicro EMPTY = new ByteStringMicro(new byte[0]);
	private final byte[] bytes;
	private volatile int hash = 0;


	private ByteStringMicro(byte[] bytes) {
		this.bytes = bytes;
	}

	public static ByteStringMicro copyFrom(String s, String charsetName) {
		try {
			return new ByteStringMicro(s.getBytes(charsetName));
		} catch(UnsupportedEncodingException e) {
			return new ByteStringMicro(s.getBytes());
		}
	}

	public static ByteStringMicro copyFrom() {
		return copyFrom(new byte[0]);
	}

	public static ByteStringMicro copyFrom(byte[] bArr) {
		return copyFrom(bArr, 0, bArr.length);
	}

	public static ByteStringMicro copyFrom(byte[] bArr, int i, int i2) {
		byte[] bArr2 = new byte[i2];
		System.arraycopy(bArr, i, bArr2, 0, i2);
		return new ByteStringMicro(bArr2);
	}

	public static ByteStringMicro copyFromUtf8(String str) {
		return new ByteStringMicro(str.getBytes(StandardCharsets.UTF_8));
	}

	public byte byteAt(int i) {
		return this.bytes[i];
	}

	public void copyTo(byte[] bArr, int i) {
		System.arraycopy(this.bytes, 0, bArr, i, this.bytes.length);
	}

	public void copyTo(byte[] bArr, int i, int i2, int i3) {
		System.arraycopy(this.bytes, i, bArr, i2, i3);
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ByteStringMicro)) {
			return false;
		}
		ByteStringMicro byteStringMicro = (ByteStringMicro) obj;
		int length = this.bytes.length;
		if (length != byteStringMicro.bytes.length) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			if (this.bytes[i] != byteStringMicro.bytes[i]) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int i = this.hash;
		if (i == 0) {
			int length = this.bytes.length;
			int i2 = 0;
			i = length;
			while (i2 < length) {
				i2++;
				i = this.bytes[i2] + (i * 31);
			}
			i = 1;
			this.hash = i;
		}
		return i;
	}

	public boolean isEmpty() {
		return this.bytes.length == 0;
	}

	public int size() {
		return this.bytes.length;
	}

	public byte[] toByteArray() {
		int length = this.bytes.length;
		byte[] bArr = new byte[length];
		System.arraycopy(this.bytes, 0, bArr, 0, length);
		return bArr;
	}

	public String toHex() {
		return HexUtil.bytesToHexString(toByteArray()).toUpperCase();
	}

	public String toString(String str) {
		try {
			return new String(this.bytes, str);
		} catch(UnsupportedEncodingException e) {
			return new String(this.bytes);
		}
	}

	public String toStringUtf8() {
		return new String(this.bytes, StandardCharsets.UTF_8);
	}
}
