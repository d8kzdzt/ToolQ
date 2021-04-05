package com.toolq.qq.protocol;

import com.toolq.helper.bot.IQQ;
import com.toolq.utils.HexUtil;

/**
 * @author luoluo
 * @date 2020/10/3 21:43
 */
public class AndroidQQ implements IQQ {
	private static final AndroidQQ Instance = new AndroidQQ();

	public static AndroidQQ getInstance() {
		return Instance;
	}

	public AndroidQQ() {}

	@Override
	public long appId() {
		return 0x2002f77f;
	}

	@Override
	public int subAppId() {
		return 16;
	}

	@Override
	public int pingVersion() {
		return 1;
	}

	@Override
	public int ipVersion() {
		return 0x1;
	}

	@Override
	public int ssoVersion() {
		return 1536;
	}

	@Override
	public int msfSsoVersion() {
		return 12;
	}

	@Override
	public int tgtgVersion() {
		return 4;
	}

	@Override
	public int miscBitmap() {
		return 0x0af7ff7c;
	}

	@Override
	public int subSigMap() {
		return 66560;
	}

	@Override
	public int dbVersion() {
		return 1;
	}

	@Override
	public int buildTime() {
		return 0x5f433810;
	}

	@Override
	public int localId() {
		return 2052;
	}

	@Override
	public int protocolVersion() {
		return 8001;
	}

	@Override
	public int loginType() {
		return 1;
	}

	@Override
	public boolean isGuidAvailable() {
		return true;
	}

	@Override
	public boolean isGuidFromFileNull() {
		return false;
	}

	@Override
	public boolean isGuidChange() {
		return false;
	}

	@Override
	public String buildVersion() {
		return "6.0.0.2436";
	}

	@Override
	public String agreementVersion() {
		return "|454001228437590|A8.4.8.94cf45ad";
	}

	@Override
	public String packageName() {
		return "com.tencent.mobileqq";
	}

	@Override
	public String packageVersion() {
		return "8.4.8";
	}

	@Override
	public byte[] tencentSdkMd5() {
		return HexUtil.hexStringToBytes("A6B745BF24A2C277527716F6F36EB68D");
	}
}
