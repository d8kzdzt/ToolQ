package com.qq.taf;

public @interface Num {
	int order();
	String desc() default "";
	String method() default "";
}
