package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 13:31
 */
public final class PBDoubleField extends PBPrimitiveField<Double> {
	public static final PBDoubleField __repeatHelper__ = new PBDoubleField(0.0d, false);
	private double value = 0.0d;

	public PBDoubleField(double d, boolean z) {
		set(d, z);
	}

	public void clear(Object obj) {
		if (obj instanceof Double) {
			this.value = (Double) obj;
		} else {
			this.value = 0.0d;
		}
		setHasFlag(false);
	}

	public int computeSize(int i) {
		if (has()) {
			return CodedOutputStreamMicro.computeDoubleSize(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Double d) {
		return CodedOutputStreamMicro.computeDoubleSize(i, d);
	}

	protected void copyFrom(PBField<Double> pBField) {
		PBDoubleField pBDoubleField = (PBDoubleField) pBField;
		set(pBDoubleField.value, pBDoubleField.has());
	}

	public double get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readDouble();
		setHasFlag(true);
	}

	protected Double readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readDouble();
	}

	public void set(double d) {
		set(d, true);
	}

	public void set(double d, boolean z) {
		this.value = d;
		setHasFlag(z);
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		if (has()) {
			codedOutputStreamMicro.writeDouble(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Double d) {
		codedOutputStreamMicro.writeDouble(i, d);
	}
}
