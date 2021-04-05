package com.toolq.utils;

/**
 * @author luoluo
 * @date 2020/10/2 3:52
 */
public class StringUtil {
	public static String subString(String string, String str1, String str2){
		int beginIndex = string.indexOf(str1);
		int endIndex = string.indexOf(str2);
		if(beginIndex < 0){
			return null;
		} else if(endIndex < 0){
			return null;
		}
		if(beginIndex > endIndex){
			int tempIndex = beginIndex;
			beginIndex = endIndex;
			endIndex = tempIndex;
			str1 = str2;
		}
		return string.substring(beginIndex, endIndex).substring(str1.length());
	}


}
