package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 13:34
 */
public final class PBFixed32Field extends PBPrimitiveField<Integer> {
	public static final PBFixed32Field __repeatHelper__ = new PBFixed32Field(0, false);
	private int value = 0;

	public PBFixed32Field(int i, boolean z) {
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
			return CodedOutputStreamMicro.computeFixed32Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Integer num) {
		return CodedOutputStreamMicro.computeFixed32Size(i, num);
	}

	protected void copyFrom(PBField<Integer> pBField) {
		PBFixed32Field pBFixed32Field = (PBFixed32Field) pBField;
		set(pBFixed32Field.value, pBFixed32Field.has());
	}

	public int get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readFixed32();
		setHasFlag(true);
	}

	protected Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readFixed32();
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
			codedOutputStreamMicro.writeFixed32(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
		codedOutputStreamMicro.writeFixed32(i, num);
	}
}
