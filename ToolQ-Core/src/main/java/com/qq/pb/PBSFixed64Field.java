package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:14
 */
public final class PBSFixed64Field extends PBPrimitiveField<Long> {
	public static final PBSFixed64Field __repeatHelper__ = new PBSFixed64Field(0, false);
	private long value = 0;

	public PBSFixed64Field(long j, boolean z) {
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
			return CodedOutputStreamMicro.computeSFixed64Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Long l) {
		return CodedOutputStreamMicro.computeSFixed64Size(i, l.longValue());
	}

	protected void copyFrom(PBField<Long> pBField) {
		PBSFixed64Field pBSFixed64Field = (PBSFixed64Field) pBField;
		set(pBSFixed64Field.value, pBSFixed64Field.has());
	}

	public long get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readSFixed64();
		setHasFlag(true);
	}

	protected Long readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return Long.valueOf(codedInputStreamMicro.readSFixed64());
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
			codedOutputStreamMicro.writeSFixed64(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Long l) {
		codedOutputStreamMicro.writeSFixed64(i, l.longValue());
	}
}
