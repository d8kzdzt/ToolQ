package com.toolq.helper.packet;

import com.toolq.utils.BytesUtil;
import com.toolq.utils.HexUtil;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luoluo
 * @date 2020/10/6 5:37
 */
public class ByteReader extends ByteBuilder {
	public ByteReader(byte[] data) {
		super(data);
		if(data == null)
			throw new RuntimeException("The data is null");
	}

	private AtomicInteger pos = new AtomicInteger(0);

	public byte readByte() {
		byte b = (byte) BytesUtil.buf_to_int8(toByteArray(), getPos());
		next(1);
		return b;
	}

	public boolean readBoolean() {
		return readByte() != 0;
	}

	public int readShort() {
		int i = BytesUtil.buf_to_int16(toByteArray(), getPos());
		next(2);
		return i;
	}

	public int readInt() {
		int i = BytesUtil.buf_to_int32(toByteArray(), getPos());
		next(4);
		return i;
	}

	public long readULong() {
		int i = BytesUtil.buf_to_int32(toByteArray(), getPos());
		next(4);
		return Integer.toUnsignedLong(i);
	}

	public long readLong() {
		long j = BytesUtil.buf_to_int64(toByteArray(), getPos());
		next(8);
		return j;
	}

	public byte[] readBytes(int length) {
		if(length == 0) return new byte[0];
		byte[] bs = BytesUtil.subByte(toByteArray(), getPos(), length);
		next(length);
		return bs;
	}

	public byte[] readBytesByInt() {
		return readBytesByInt(0);
	}

	public byte[] readBytesByInt(int dev) {
		int length = readInt() - dev;
		return readBytes(length);
	}

	public ByteReader readReader(int length) {
		return new ByteReader(readBytes(length));
	}

	public byte[] readRestBytes() {
		if(isEmpty()) return new byte[0];
		return readBytes(length() - getPos());
	}

	public String readRestString() {
		return new String(readRestBytes(), StandardCharsets.UTF_8);
	}

	public String readString(int length) {
		return new String(readBytes(length));
	}

	public String readStringByInt() {
		return readStringByInt(0);
	}

	public String readStringByInt(int dev) {
		int length = readInt() - dev;
		return readString(length);
	}

	public ByteReader dis(int length) {
		next(length);
		return this;
	}

	private void next(int i) {
		pos.addAndGet(i);
	}

	public int getPos() {
		return pos.get();
	}

	public void setPos(int pos) {
		this.pos = new AtomicInteger(pos);
	}

	public String readStringByShort() {
		return new String(readBytesByShort());
	}

	public String readStringByShort(int dev) {
		return new String(readBytesByShort(dev));
	}

	public byte[] readBytesByShort() {
		return readBytesByShort(0);
	}

	public byte[] readBytesByShort(int dev) {
		return readBytes(readShort() - dev);
	}

	public boolean isEmpty() {
		return super.data.length == 0;
	}

	@Override
	public String toString() {
		return HexUtil.bytesToHexString(this.toByteArray());
	}
}
