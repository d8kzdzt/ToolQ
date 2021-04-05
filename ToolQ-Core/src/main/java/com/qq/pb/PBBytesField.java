package com.qq.pb;

import com.toolq.utils.HexUtil;

/**
 * @author luoluo
 * @date 2020/11/8 13:30
 */
public final class PBBytesField extends PBPrimitiveField<ByteStringMicro> {
	public static final PBBytesField __repeatHelper__ = new PBBytesField(ByteStringMicro.EMPTY, false);
	private ByteStringMicro value = ByteStringMicro.EMPTY;

	public PBBytesField(ByteStringMicro byteStringMicro, boolean z) {
		set(byteStringMicro, z);
	}

	public void clear(Object obj) {
		if (obj instanceof ByteStringMicro) {
			this.value = (ByteStringMicro) obj;
		} else {
			this.value = ByteStringMicro.EMPTY;
		}
		setHasFlag(false);
	}

	public int computeSize(int i) {
		if (has()) {
			return CodedOutputStreamMicro.computeBytesSize(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, ByteStringMicro byteStringMicro) {
		return CodedOutputStreamMicro.computeBytesSize(i, byteStringMicro);
	}

	protected void copyFrom(PBField<ByteStringMicro> pBField) {
		PBBytesField pBBytesField = (PBBytesField) pBField;
		set(pBBytesField.value, pBBytesField.has());
	}

	public ByteStringMicro get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readBytes();
		setHasFlag(true);
	}

	protected ByteStringMicro readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readBytes();
	}

	public void set(ByteStringMicro byteStringMicro) {
		set(byteStringMicro, true);
	}

	public void set(byte[] bytes) {
		set(ByteStringMicro.copyFrom(bytes), true);
	}

	public void set(byte[] bytes, boolean b) {
		set(ByteStringMicro.copyFrom(bytes), b);
	}

	public void set(String s) {
		set(s.getBytes());
	}

	public void set(String s, boolean z) {
		set(s.getBytes(), z);
	}

	public void set(ByteStringMicro byteStringMicro, boolean z) {
		this.value = byteStringMicro;
		setHasFlag(z);
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		if (has()) {
			codedOutputStreamMicro.writeBytes(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, ByteStringMicro byteStringMicro) {
		codedOutputStreamMicro.writeBytes(i, byteStringMicro);
	}

	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	@Override
	public String toString() {
		return toHex();
	}

	public String toHex() {
		return HexUtil.bytesToHexString(value.toByteArray());
	}
}
