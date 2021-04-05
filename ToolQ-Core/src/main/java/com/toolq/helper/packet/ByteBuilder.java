package com.toolq.helper.packet;

import com.toolq.helper.logger.TLog;
import com.toolq.utils.BytesUtil;
import com.toolq.utils.HexUtil;
import com.toolq.utils.MD5;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author luoluo
 * @date 2020/10/1 11:52
 */
public class ByteBuilder {
	public byte[] data;
	public int pos = 0;

	public ByteBuilder() {
		this(0);
	}

	public ByteBuilder(int size) {
		this.data = new byte[size];
	}

	public ByteBuilder(byte[] data) {
		this.data = data;
	}

	public ByteBuilder writeByte(byte b) {
		writeEnd(new byte[]{
			b
		});
		return this;
	}

	public ByteBuilder writeByte(Integer b) {
		writeEnd(new byte[]{
			b.byteValue()
		});
		return this;
	}

	public ByteBuilder reWriteByte(Integer b) {
		writeStart(new byte[]{
			b.byteValue()
		});
		return this;
	}

	public ByteBuilder writeBoolean(boolean b) {
		return writeByte(b ? 1 : 0);
	}

	public ByteBuilder writeBytes(byte[] bs) {
		writeEnd(bs);
		return this;
	}

	public ByteBuilder writeShort(int i) {
		writeEnd(BytesUtil.int16_to_buf(i));
		return this;
	}

	public ByteBuilder reWritInt(Integer i) {
		writeStart(
				BytesUtil.int32_to_buf(i)
		);
		return this;
	}

	public ByteBuilder writeInt(int i) {
		writeEnd(BytesUtil.int32_to_buf(i));
		return this;
	}

	public ByteBuilder writeInt(long j) {
		writeEnd(BytesUtil.int64_to_buf32(j));
		return this;
	}

	public ByteBuilder writeLong(long j) {
		writeEnd(BytesUtil.int64_to_buf(j));
		return this;
	}

	public ByteBuilder writeString(String str) {
		writeEnd(str.getBytes());
		return this;
	}

	public ByteBuilder writeString(String str, String charsetName) {
		try {
			writeEnd(str.getBytes(charsetName));
		} catch(UnsupportedEncodingException e) {
			TLog.INSTANCE.warn("writeString", e);
		}
		return this;
	}

	public ByteBuilder writeHex(String hex) {
		if (hex != null) {
			writeBytes(HexUtil.hexStringToBytes(hex));
		} else {
			TLog.INSTANCE.warn("writeHex --> input value is null");
		}
		return this;
	}

	public ByteBuilder writeSize(int add) {
		writeStart(BytesUtil.int32_to_buf(add + data.length));
		return this;
	}

	public ByteBuilder writeSize() {
		return writeSize(0);
	}

	public ByteBuilder writeShortSize(int add) {
		writeStart(BytesUtil.int16_to_buf(add + data.length));
		return this;
	}

	public ByteBuilder writeShortSize() {
		return writeShortSize(0);
	}

	private void writeEnd(byte[] end) {
		data = BytesUtil.byteMerger(data, end);
	}

	private void writeStart(byte[] start) {
		data = BytesUtil.byteMerger(start, data);
	}

	public ByteBuilder writeBytesWithShortSize(byte[] body, int add) {
		writeShort(body.length + add);
		writeBytes(body);
		return this;
	}

	public ByteBuilder writeBytesWithShortSize(byte[] body) {
		return writeBytesWithShortSize(body, 0);
	}

	public ByteBuilder writeBytesWithSize(byte[] body, int add) {
		if(body == null) body = new byte[0];
		writeInt(body.length + add);
		writeBytes(body);
		return this;
	}

	public ByteBuilder writeBytesWithSize(byte[] body) {
		return writeBytesWithSize(body, 0);
	}

	public ByteBuilder writeStringWithSize(String str) {
		return writeBytesWithSize( str.getBytes() );
	}

	public ByteBuilder writeStringWithSize(String str, int add) {
		return writeBytesWithSize( str.getBytes(), add);
	}

	public ByteBuilder writeStringWithShortSize(String str) {
		return writeBytesWithShortSize( str.getBytes() );
	}

	public ByteBuilder writeStringWithShortSize(String str, int add) {
		return writeBytesWithShortSize( str.getBytes(), add);
	}

	public byte[] toByteArray() {
		return Arrays.copyOf(data, data.length);
	}

	public byte[] md5() {
		return MD5.toMD5Byte(toByteArray());
	}

	public int length() {
		return data.length;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ByteBuilder clean() {
		data = new byte[0];
		return this;
	}

	public String toHex() {
		return HexUtil.bytesToHexString(toByteArray());
	}
}
