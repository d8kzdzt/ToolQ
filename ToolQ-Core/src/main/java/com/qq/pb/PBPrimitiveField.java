package com.qq.pb;

/**
 * @author luoluo
 * @date 2020/11/8 13:00
 */
public abstract class PBPrimitiveField<T> extends PBField<T> {
	private boolean hasFlag = false;

	public final boolean has() {
		return this.hasFlag;
	}

	public final T setHasFlag(boolean z) {
		this.hasFlag = z;
		return (T) this;
	}
}
