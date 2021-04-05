package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:14
 */
public final class PBSInt32Field extends PBPrimitiveField<Integer> {
	public static final PBSInt32Field __repeatHelper__ = new PBSInt32Field(0, false);
	private int value = 0;

	public PBSInt32Field(int i, boolean z) {
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
			return CodedOutputStreamMicro.computeSInt32Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Integer num) {
		return CodedOutputStreamMicro.computeSInt32Size(i, num);
	}

	protected void copyFrom(PBField<Integer> pBField) {
		PBSInt32Field pBSInt32Field = (PBSInt32Field) pBField;
		set(pBSInt32Field.value, pBSInt32Field.has());
	}

	public int get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readSInt32();
		setHasFlag(true);
	}

	protected Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readSInt32();
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
			codedOutputStreamMicro.writeSInt32(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
		codedOutputStreamMicro.writeSInt32(i, num);
	}
}
