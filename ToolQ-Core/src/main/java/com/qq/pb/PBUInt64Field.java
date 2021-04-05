package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:17
 */
public final class PBUInt64Field extends PBPrimitiveField<Long> {
	public static final PBUInt64Field __repeatHelper__ = new PBUInt64Field(0, false);
	private long value = 0;

	public PBUInt64Field(long j, boolean z) {
		set(j, z);
	}

	public void clear(Object obj) {
		if (obj instanceof Long) {
			this.value = (Long) obj;
		} else {
			this.value = 0;
		}
		setHasFlag(false);
	}

	public int computeSize(int i) {
		if (has()) {
			return CodedOutputStreamMicro.computeUInt64Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Long l) {
		return CodedOutputStreamMicro.computeUInt64Size(i, l);
	}

	protected void copyFrom(PBField<Long> pBField) {
		PBUInt64Field pBUInt64Field = (PBUInt64Field) pBField;
		set(pBUInt64Field.value, pBUInt64Field.has());
	}

	public long get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readUInt64();
		setHasFlag(true);
	}

	protected Long readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readUInt64();
	}

	public void set(PBUInt64Field j) {
		set(j.get());
	}

	public void set(long j) {
		set(j, true);
	}

	public void set(long j, boolean z) {
		this.value = j;
		setHasFlag(z);
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		if (has()) {
			codedOutputStreamMicro.writeUInt64(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Long l) {
		codedOutputStreamMicro.writeUInt64(i, l);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
