package com.toolq.utils;

import com.toolq.helper.android.Android;
import com.toolq.helper.bot.BotStatus;
import com.toolq.helper.bot.IQQ;
import com.toolq.helper.packet.ByteBuilder;

/**
 * @author luoluo
 * @date 2020/10/5 20:10
 */
public class PacketUtil {
	public static byte[] makeNoLoginPacket(String cmdName, byte[] data, int seq, long uin, IQQ iqq, boolean hasSize) {
		return makePacket(cmdName, data, BotStatus.notLoggedIn, null, new byte[16], seq, uin, iqq, Android.getTgt(), Android.getKsid(), !hasSize);
	}

	public static byte[] makeOnlinePacket(String cmdName, BotStatus status, byte[] body, byte[] key, int seq, long uin, boolean needSize) {
		return PacketUtil.makePacket(cmdName, body, status, null, key, seq, uin, null, null, null, needSize);
	}

	public static byte[] makePacket(String cmdName, byte[] data, BotStatus status, byte[] d2, byte[] key, int seq, long uin, IQQ iqq, byte[] tgt, byte[] ksid, boolean needSize) {
		int packetType = 0xA;
		int encryptType = 0x1;
		switch(status) {
			case hasLogged:
				break;
			case notLoggedIn:
				encryptType = 0x2;
				break;
			case online:
				packetType = 0xB;
				break;
			default:
				return null;
		}
		return makePacket(packetType, encryptType, cmdName, data, status, d2, key, seq, uin, iqq, tgt, ksid, needSize);
	}

	public static byte[] makeOnlinePacket(int packetType, int encType, String cmdName, BotStatus status, byte[] body, byte[] key, int seq, long uin, boolean needSize) {
		return PacketUtil.makePacket(packetType, encType, cmdName, body, status, null, key, seq, uin, null, null, null, needSize);
	}

	public static byte[] makePacket(int packetType, int encType, String cmdName, byte[] data, BotStatus status, byte[] d2, byte[] key, int seq, long uin, IQQ iqq, byte[] tgt, byte[] ksid, boolean needSize) {
		ByteBuilder builder = new ByteBuilder();
		int tgtType = tgt != null ? 256 : 0;
		switch(status) {
			case hasLogged:
				builder.writeInt(0xA);
				builder.writeByte(0x1);
				builder.writeBytesWithSize(d2, 4);
				break;
			case notLoggedIn:
				builder.writeInt(packetType);
				builder.writeByte(encType);
				builder.writeBytesWithSize(d2, 4);
				break;
			case online:
				builder.writeInt(packetType);
				builder.writeByte(encType);
				builder.writeInt(seq);
				break;
			default:
				System.exit(1);
		}
		builder.writeByte(0);
		builder.writeStringWithSize(String.valueOf(uin), 4);
		ByteBuilder body = new ByteBuilder();
		ByteBuilder head = new ByteBuilder();
		if( iqq != null) {
			head.writeInt(seq);
			head.writeInt(iqq.appId());
			head.writeInt(iqq.appId());
			head.writeInt(16777216);
			head.writeInt(0);
			head.writeInt(tgtType);
			head.writeBytesWithSize(tgt, 4);
			head.writeStringWithSize(cmdName, 4);
			head.writeBytesWithSize(BytesUtil.randomKey(4), 4);
			head.writeStringWithSize(Android.androidId, 4);
			head.writeBytesWithSize(ksid, 4);
			head.writeStringWithShortSize(iqq.agreementVersion(), 2);
			// head.writeInt(4);
		} else {
			head.writeStringWithSize(cmdName, 4);
			head.writeBytesWithSize(BytesUtil.randomKey(4), 4);
			head.writeInt(4);
		}
		head.writeSize(4);
		body.writeBytes(head.toByteArray());
		if(needSize)
			body.writeBytesWithSize(data, 4);
		else
			body.writeBytes(data);
		// System.out.println(HexUtil.bytesToHexString(body.toByteArray()));
		byte[] e_data = TeaUtil.encrypt(body.toByteArray(), key);
		builder.writeBytes(e_data);
		builder.writeSize(4);
		return builder.toByteArray();
	}
}
