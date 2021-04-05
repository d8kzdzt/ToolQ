package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:15
 */
public final class PBSInt64Field extends PBPrimitiveField<Long> {
	public static final PBSInt64Field __repeatHelper__ = new PBSInt64Field(0, false);
	private long value = 0;

	public PBSInt64Field(long j, boolean z) {
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
			return CodedOutputStreamMicro.computeSInt64Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Long l) {
		return CodedOutputStreamMicro.computeSInt64Size(i, l.longValue());
	}

	protected void copyFrom(PBField<Long> pBField) {
		PBSInt64Field pBSInt64Field = (PBSInt64Field) pBField;
		set(pBSInt64Field.value, pBSInt64Field.has());
	}

	public long get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readSInt64();
		setHasFlag(true);
	}

	protected Long readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readSInt64();
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
			codedOutputStreamMicro.writeSInt64(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Long l) {
		codedOutputStreamMicro.writeSInt64(i, l);
	}
}
