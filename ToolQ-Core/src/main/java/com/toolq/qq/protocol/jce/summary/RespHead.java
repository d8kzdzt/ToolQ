package com.toolq.qq.protocol.jce.summary;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceStruct;

import java.util.Arrays;

/**
 * @author luoluo
 * @date 2020/8/26 22:20
 */
public class RespHead extends JceStruct {
	static byte[] cache_vCookies;
	public int iResult;
	public int iVersion;
	public String strErrorMsg = "";
	public byte[] vCookies;

	@Override
	public void readFrom(JceInputStream jceInputStream) {
		this.iVersion = jceInputStream.read(this.iVersion, 0, true);
		this.iResult = jceInputStream.read(this.iResult, 1, true);
		this.strErrorMsg = jceInputStream.readString(2, true);
		if (cache_vCookies == null) {
			cache_vCookies = new byte[1];
		}
		this.vCookies = jceInputStream.read(cache_vCookies, 3, false);
	}

	@Override
	public String toString() {
		return "RespHead{" +
			"iResult=" + iResult +
			", iVersion=" + iVersion +
			", strErrorMsg='" + strErrorMsg + '\'' +
			", vCookies=" + Arrays.toString(vCookies) +
			'}';
	}
}
