package com.toolq.qq.protocol.jce.statsvc;

import com.toolq.ToolQ;
import com.toolq.helper.android.Android;
import com.toolq.helper.jce.JceHelper;
import com.toolq.helper.logger.TLog;
import com.toolq.utils.HexUtil;
import com.toolq.utils.RandomUtil;
import com.qq.taf.Jce;
import com.qq.taf.Num;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.Objects;

/**
 * @author luoluo
 * @date 2020/10/30 19:11
 */
public final class Register {
	public static Req make(long uin, int status, int localeId, long extOnlineStatus) {
		return new Req(uin, status, localeId, extOnlineStatus);
	}

	public static int parse(byte[] src){
		if(src == null) TLog.INSTANCE.warn("Register.parse input param is null!!!");
		return Objects.requireNonNull(JceHelper.decodePacket(src, "SvcRespRegister", new Resp())).replyCode;
	}

	public static final class Req extends Jce<Req> {
		public Req(long uin, int status, int localeId, long extOnlineStatus) {
			this.uin = uin;
			this.status = status;
			this.localeId = localeId;
			this.extOnlineStatus = extOnlineStatus;
		}

		@Num(order = 0)
		public final long uin;
		@Num(order = 1)
		public final long bid = 1 | 2 | 4;
		@Num(order = 2)
		public final byte connType = 0;
		@Num(order = 3)
		public final String other = "";
		/**
		 * 11 上线
		 * 21 离线
		 * 31 离开
		 * 41 隐身
		 * 51 忙碌
		 */
		@Num(order = 4)
		public final int status;
		@Num(order = 5)
		public final byte onlinePush = 0;
		@Num(order = 6)
		public final byte isOnline = 0;
		@Num(order = 7)
		public final byte isShowOnline = 0;
		@Num(order = 8)
		public final byte kikPC = 0;
		@Num(order = 9)
		public final byte kikWeak = 0;
		@Num(order = 10)
		public final long timeStamp = 189 + RandomUtil.randInt(0, 100);
		@Num(order = 11)
		public final long osVersion = 0;
		@Num(order = 12)
		public final byte netType = 0;
		@Num(order = 13)
		public final String buildVer = "";
		@Num(order = 14)
		public final byte regType = 1;
		@Num(order = 15)
		public byte[] devParam = null;
		@Num(order = 17)
		public final int localeId;
		@Num(order = 18)
		public final byte slientPush = 0;
		@Num(order = 22)
		public byte openPush = 1;
		@Num(order = 28)
		public String channelNo = "";
		@Num(order = 29)
		public long cpId = 0;
		@Num(order = 34)
		public byte isSetStatus = 0;
		@Num(order = 36)
		public byte setMute = 0;
		@Num(order = 38)
		public final long extOnlineStatus;
		@Num(order = 39)
		public int batteryStatus = 0;

		@Override
		public void writeTo(JceOutputStream out) {
			out.write(uin, 0);
			// qq number
			out.write(bid, 1);
			out.write(connType, 2);
			out.write(other, 3);
			out.write(status, 4);
			out.write(onlinePush, 5);
			out.write(isOnline, 6);
			out.write(isShowOnline, 7);
			out.write(kikPC, 8);
			out.write(kikWeak, 9);
			out.write(timeStamp, 10);
			out.write(osVersion, 11);
			out.write(netType, 12);
			out.write(buildVer, 13);
			out.write(regType, 14);
			if (devParam != null) {
				out.write(devParam, 15);
			}
			out.write(Android.getGuid(), 16);
			out.write(localeId, 17);
			out.write(slientPush, 18);
			out.write(Android.machineName, 19);
			out.write(Android.machineManufacturer, 20);
			out.write(Android.machineVersion, 21);
			out.write(openPush, 22);
			out.write(0, 23);
			out.write(0, 24);
			// out.write(this.vecBindUin, 25);
			out.write(0, 26);
			out.write(0, 27);
			out.write(channelNo, 28);
			out.write(cpId, 29);
			out.write(String.format("[u]%s", Android.machineName), 30);
			out.write("?LMY48G test-keys;ao", 31);
			out.write("", 32);
			out.write(HexUtil.hexStringToBytes("0A 04 08 2E 10 00 0A 05 08 9B 02 10 00 "), 33);
			out.write(isSetStatus, 34);
			// out.write(severBuf, 35);
			out.write(setMute, 36);
			out.write(extOnlineStatus, 38);
			out.write(batteryStatus, 39);
		}
	}

	public static final class Resp extends Jce<Resp> {
		public int replyCode = 0;

		@Override
		public void readFrom(JceInputStream input) {
			replyCode = input.read(replyCode, 2, true);
		}
	}
}
