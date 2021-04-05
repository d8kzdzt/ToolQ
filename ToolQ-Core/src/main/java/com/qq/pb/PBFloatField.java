package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 14:45
 */
public final class PBFloatField extends PBPrimitiveField<Float> {
	public static final PBFloatField __repeatHelper__ = new PBFloatField(0.0f, false);
	private float value = 0.0f;

	public PBFloatField(float f, boolean z) {
		set(f, z);
	}

	public void clear(Object obj) {
		if (obj instanceof Float) {
			this.value = (Float) obj;
		} else {
			this.value = 0.0f;
		}
		setHasFlag(false);
	}

	public int computeSize(int i) {
		if (has()) {
			return CodedOutputStreamMicro.computeFloatSize(i, this.value);
		}
		return 0;
	}

	protected int computeSizeDirectly(int i, Float f) {
		return CodedOutputStreamMicro.computeFloatSize(i, f);
	}

	protected void copyFrom(PBField<Float> pBField) {
		PBFloatField pBFloatField = (PBFloatField) pBField;
		set(pBFloatField.value, pBFloatField.has());
	}

	public float get() {
		return this.value;
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		this.value = codedInputStreamMicro.readFloat();
		setHasFlag(true);
	}

	protected Float readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		return codedInputStreamMicro.readFloat();
	}

	public void set(float f) {
		set(f, true);
	}

	public void set(float f, boolean z) {
		this.value = f;
		setHasFlag(z);
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		if (has()) {
			codedOutputStreamMicro.writeFloat(i, this.value);
		}
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Float f) {
		codedOutputStreamMicro.writeFloat(i, f);
	}
}
