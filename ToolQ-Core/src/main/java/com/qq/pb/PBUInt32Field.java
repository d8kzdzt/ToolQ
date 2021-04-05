package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:17
 */
public final class PBUInt32Field extends PBPrimitiveField<Integer> {
	public static final PBUInt32Field __repeatHelper__ = new PBUInt32Field(0, false);
	private int value = 0;

	public PBUInt32Field(int i, boolean z) {
		set(i, z);
	}

	public void clear(Object obj) {
		if (obj instanceof Integer) {
			this.value = (Integer) obj;
		} else {
			this.value = 0;
		}
		setHasFlag(false);
	}

	public int computeSize(int i) {
		if (has()) {
			return CodedOutputStreamMicro.computeUInt32Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Integer num) {
		return CodedOutputStreamMicro.computeUInt32Size(i, num);
	}

	protected void copyFrom(PBField<Integer> pBField) {
		PBUInt32Field pBUInt32Field = (PBUInt32Field) pBField;
		set(pBUInt32Field.value, pBUInt32Field.has());
	}

	public int get() {
		return this.value;
	}

	public long getUInt() {
		return Integer.toUnsignedLong(this.value);
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readUInt32();
		setHasFlag(true);
	}

	protected Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readUInt32();
	}

	public void set(PBUInt32Field i) {
		set(i.get());
	}

	public void set(int i) {
		set(i, true);
	}

	public void set(int i, boolean z) {
		this.value = i;
		setHasFlag(z);
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		if (has()) {
			codedOutputStreamMicro.writeUInt32(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
		codedOutputStreamMicro.writeUInt32(i, num);
	}

	@Override
	public String toString() {
		return String.valueOf(get());
	}
}
