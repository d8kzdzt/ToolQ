package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 15:03
 */
public final class PBInt32Field extends PBPrimitiveField<Integer> {
	public static final PBInt32Field __repeatHelper__ = new PBInt32Field(0, false);
	private int value = 0;

	public PBInt32Field(int i, boolean z) {
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
			return CodedOutputStreamMicro.computeInt32Size(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Integer num) {
		return CodedOutputStreamMicro.computeInt32Size(i, num);
	}

	protected void copyFrom(PBField<Integer> pBField) {
		PBInt32Field pBInt32Field = (PBInt32Field) pBField;
		set(pBInt32Field.value, pBInt32Field.has());
	}

	public int get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readInt32();
		setHasFlag(true);
	}

	protected Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readInt32();
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
			codedOutputStreamMicro.writeInt32(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
		codedOutputStreamMicro.writeInt32(i, num);
	}
}
