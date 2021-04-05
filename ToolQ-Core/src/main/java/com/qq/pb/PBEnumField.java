package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 13:33
 */
public final class PBEnumField extends PBPrimitiveField<Integer> {
	public static final PBEnumField __repeatHelper__ = new PBEnumField(0, false);
	private int value = 0;

	public PBEnumField(int i, boolean z) {
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
			return CodedOutputStreamMicro.computeEnumSize(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Integer num) {
		return CodedOutputStreamMicro.computeEnumSize(i, num);
	}

	protected void copyFrom(PBField<Integer> pBField) {
		PBEnumField pBEnumField = (PBEnumField) pBField;
		set(pBEnumField.value, pBEnumField.has());
	}

	public int get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readEnum();
		setHasFlag(true);
	}

	protected Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readEnum();
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
			codedOutputStreamMicro.writeEnum(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
		codedOutputStreamMicro.writeEnum(i, num);
	}

}
