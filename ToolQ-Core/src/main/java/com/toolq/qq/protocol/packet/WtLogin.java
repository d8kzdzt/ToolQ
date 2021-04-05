package com.toolq.qq.protocol.packet;

import com.google.gson.JsonObject;
import com.qq.taf.client.ClientPow;
import com.toolq.ToolQ;
import com.toolq.helper.android.Android;
import com.toolq.helper.bot.IQQ;
import com.toolq.helper.bot.QQAccount;
import com.toolq.helper.bot.tlv.TlvBuilder;
import com.toolq.helper.logger.TLog;
import com.toolq.helper.packet.ByteBuilder;
import com.toolq.helper.server.ToolQServer;
import com.toolq.qq.ecdh.ECDHCrypt;
import com.toolq.qq.protocol.Tlv;
import com.toolq.utils.HexUtil;
import com.toolq.utils.PacketUtil;
import com.toolq.utils.TeaUtil;
import com.toolq.utils.ToolQUtil;

import java.util.Objects;

/**
 * @author luoluo
 * @date 2020/Tlv0/5 20:09
 */
public class WtLogin {
	private static final String fuckHead;
	static final ECDHCrypt ecdhCrypt = ECDHCrypt.getInstance();

	static {
		ToolQServer toolQServer =new ToolQServer("Main.Head");
		JsonObject data = toolQServer.request().getData();
		String rsaB64 = data.get("protobuf").getAsString();
		fuckHead = new String(Objects.requireNonNull(ToolQUtil.INSTANCE.luoDecrypt(rsaB64)));
	}

	public static byte[] PassVerify(int seq, ToolQ toolQ, byte[] dt104, byte[] dt402, byte[] dt403) {
		Tlv tlv = new Tlv(toolQ.getAccount(), toolQ.getAndroidQQ(), seq);
		byte[] t8 = tlv.t8();
		byte[] t104 = tlv.t104(dt104);
		byte[] t116 = tlv.t1162();
		byte[] t401 = tlv.t401(dt402);
		byte[] t402 = tlv.t402(dt402);
		byte[] t403 = tlv.t403(dt403);

		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(20);
		builder.writeShort(6);
		builder.writeBytes(t8);
		builder.writeBytes(t104);
		builder.writeBytes(t116);
		builder.writeBytes(t401);
		builder.writeBytes(t402);
		builder.writeBytes(t403);
		byte[] data = TeaUtil.encrypt(builder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder byteBuilder = new ByteBuilder();
		byteBuilder.writeShort(toolQ.getAndroidQQ().protocolVersion());
		byteBuilder.writeShort(0x810);
		byteBuilder.writeShort(1);
		byteBuilder.writeInt(toolQ.getAccount().getUser());
		byteBuilder.writeHex(fuckHead.replace("8", "0"));
		byteBuilder.writeBytes(Android.getRandKey());
		byteBuilder.writeShort(305);
		byteBuilder.writeShort(ecdhCrypt.get_pub_key_ver());
		byteBuilder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		byteBuilder.writeBytes(data);
		byteBuilder.writeByte(3);
		byteBuilder.writeShortSize(3);
		byteBuilder.reWriteByte(2);
		return PacketUtil.makeNoLoginPacket("wtlogin.login", byteBuilder.toByteArray(), seq, toolQ.getAccount().getUser(), toolQ.getAndroidQQ(), false);
	}

	public static byte[] VerifySMSCode(int seq, ToolQ toolQ, byte[] dt104, byte[] dt174, byte[] dt402, String code) {
		Tlv tlv = new Tlv(toolQ.getAccount(), toolQ.getAndroidQQ(), seq);
		byte[] t8 = tlv.t8();
		byte[] t104 = tlv.t104(dt104);
		byte[] t116 = tlv.t1162();
		byte[] t174 = tlv.t174(dt174);
		byte[] t17c = tlv.t17c(code);
		byte[] t401 = tlv.t401(dt402);
		byte[] t198 = tlv.t198();
		byte[] t542 = tlv.t542();

		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(7);
		builder.writeShort(8);
		builder.writeBytes(t8);
		builder.writeBytes(t104);
		builder.writeBytes(t116);
		builder.writeBytes(t174);
		builder.writeBytes(t17c);
		builder.writeBytes(t401);
		builder.writeBytes(t198);
		builder.writeBytes(t542);
		byte[] data = TeaUtil.encrypt(builder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder byteBuilder = new ByteBuilder();
		byteBuilder.writeShort(toolQ.getAndroidQQ().protocolVersion());
		byteBuilder.writeShort(0x810);
		byteBuilder.writeShort(1);
		byteBuilder.writeInt(toolQ.getAccount().getUser());
		byteBuilder.writeHex(fuckHead.replace("8", "0"));
		byteBuilder.writeBytes(Android.getRandKey());
		byteBuilder.writeShort(305);
		byteBuilder.writeShort(ecdhCrypt.get_pub_key_ver());
		byteBuilder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		byteBuilder.writeBytes(data);
		byteBuilder.writeByte(3);
		byteBuilder.writeShortSize(3);
		byteBuilder.reWriteByte(2);
		return PacketUtil.makeNoLoginPacket("wtlogin.login", byteBuilder.toByteArray(), seq, toolQ.getAccount().getUser(), toolQ.getAndroidQQ(), false);
	}

	public static byte[] GetSMSCode(int seq, ToolQ toolQ, byte[] dt104, byte[] dt174) {
		Tlv tlv = new Tlv(toolQ.getAccount(), toolQ.getAndroidQQ(), seq);
		byte[] t8 = tlv.t8();
		byte[] t104 = tlv.t104(dt104);
		byte[] t116 = tlv.t116();
		byte[] t174 = tlv.t174(dt174);
		byte[] t17a = tlv.t17a(9);
		byte[] t197 = tlv.t197();
		byte[] t542 = tlv.t542();
		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(8);
		builder.writeShort(7);
		builder.writeBytes(t8);
		builder.writeBytes(t104);
		builder.writeBytes(t116);
		builder.writeBytes(t174);
		builder.writeBytes(t17a);
		builder.writeBytes(t197);
		builder.writeBytes(t542);
		byte[] data = TeaUtil.encrypt(builder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder byteBuilder = new ByteBuilder();
		byteBuilder.writeShort(toolQ.getAndroidQQ().protocolVersion());
		byteBuilder.writeShort(0x810);
		byteBuilder.writeShort(1);
		byteBuilder.writeInt(toolQ.getAccount().getUser());
		byteBuilder.writeHex(fuckHead.replace("8", "0"));
		byteBuilder.writeBytes(Android.getRandKey());
		byteBuilder.writeShort(305);
		byteBuilder.writeShort(ecdhCrypt.get_pub_key_ver());
		byteBuilder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		byteBuilder.writeBytes(data);
		byteBuilder.writeByte(3);
		byteBuilder.writeShortSize(3);
		byteBuilder.reWriteByte(2);
		return PacketUtil.makeNoLoginPacket("wtlogin.login", byteBuilder.toByteArray(), seq, toolQ.getAccount().getUser(), toolQ.getAndroidQQ(), false);
	}

	public static byte[] GetStWithPassword(QQAccount account, IQQ iqq, int seq) {
		Tlv tlv = new Tlv(account, iqq, seq);

		ByteBuilder tlvbuilder = new ByteBuilder();

		tlvbuilder.writeShort(9);
		tlvbuilder.writeShort(25);
		tlvbuilder.writeBytes(tlv.t18());
		tlvbuilder.writeBytes(tlv.t1());
		tlvbuilder.writeBytes(tlv.t106());
		tlvbuilder.writeBytes(tlv.t116());
		tlvbuilder.writeBytes(tlv.t100());
		tlvbuilder.writeBytes(tlv.t107());
		tlvbuilder.writeBytes(tlv.t108(Android.getKsid()));

		/*
		ToolQServer toolQServer = new ToolQServer("Main.login");
		toolQServer.addParams("uin", String.valueOf(account.getUser()));
		toolQServer.addParams("iii", String.valueOf(Tlv.Companion.getIp()));
		toolQServer.addParams("t106", HexUtil.bytesToHexString(tlv.t106()));
		toolQServer.addParams("kid", HexUtil.bytesToHexString(Android.getKsid()));

		JsonObject ret = toolQServer.request().getData();
		String hex = ret.get("protobuf").getAsString();

		tlvbuilder.writeHex(hex);
		*/

		tlvbuilder.writeBytes(tlv.t142());
		tlvbuilder.writeBytes(tlv.t144());
		tlvbuilder.writeBytes(tlv.t145());
		tlvbuilder.writeBytes(tlv.t147());
		tlvbuilder.writeBytes(tlv.t154());
		// tlvbuilder.writeBytes(tlv.t16b());
		// 获取SKey时无效
		tlvbuilder.writeBytes(tlv.t141());
		tlvbuilder.writeBytes(tlv.t8());
		tlvbuilder.writeBytes(tlv.t511());
		tlvbuilder.writeBytes(tlv.t187());
		tlvbuilder.writeBytes(tlv.t400());
		tlvbuilder.writeBytes(tlv.t188());
		tlvbuilder.writeBytes(tlv.t191());
		tlvbuilder.writeBytes(tlv.t202());
		tlvbuilder.writeBytes(tlv.t177());
		tlvbuilder.writeBytes(tlv.t516());
		tlvbuilder.writeBytes(tlv.t521());
		tlvbuilder.writeBytes(tlv.t525());
		tlvbuilder.writeBytes(tlv.t544());



		byte[] data = TeaUtil.encrypt(tlvbuilder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(iqq.protocolVersion());
		builder.writeShort(0x810);
		builder.writeShort(1);
		builder.writeInt(account.getUser());
		builder.writeHex(fuckHead);
		builder.writeBytes(Android.getRandKey());
		builder.writeShort(305);
		builder.writeShort(ecdhCrypt.get_pub_key_ver()); // 00 02
		builder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		builder.writeBytes(data);
		builder.writeByte(3);
		builder.writeShortSize(3);
		builder.reWriteByte(2);

		return PacketUtil.makeNoLoginPacket("wtlogin.login", builder.toByteArray(), seq, account.getUser(), iqq, false);
	}

	public static byte[] GetStWithOutPassword(ToolQ toolQ, QQAccount account, IQQ iqq, int seq) {
		Tlv tlv = new Tlv(account, iqq, seq);
		ByteBuilder tlvbuilder = new ByteBuilder();
		tlvbuilder.writeShort(0xf);
		tlvbuilder.writeShort(0x1a);
		tlvbuilder.writeBytes(tlv.t18());
		tlvbuilder.writeBytes(tlv.t1());
		tlvbuilder.writeBytes(tlv.t106());
		tlvbuilder.writeBytes(tlv.t116());
		tlvbuilder.writeBytes(tlv.t100());
		tlvbuilder.writeBytes(tlv.t107());
		tlvbuilder.writeBytes(tlv.t108(Android.getKsid()));
		tlvbuilder.writeBytes(tlv.t144());
		tlvbuilder.writeBytes(tlv.t142());
		tlvbuilder.writeBytes(tlv.t145());
		tlvbuilder.writeBytes(tlv.t16a());
		tlvbuilder.writeBytes(tlv.t154());
		tlvbuilder.writeBytes(tlv.t141());
		tlvbuilder.writeBytes(tlv.t8());
		tlvbuilder.writeBytes(tlv.t511());
		tlvbuilder.writeBytes(tlv.t147());
		tlvbuilder.writeBytes(tlv.t177());
		tlvbuilder.writeBytes(tlv.t400());
		tlvbuilder.writeBytes(tlv.t187());
		tlvbuilder.writeBytes(tlv.t188());
		tlvbuilder.writeBytes(tlv.t194());
		tlvbuilder.writeBytes(tlv.t202());
		tlvbuilder.writeBytes(tlv.t516());
		tlvbuilder.writeBytes(tlv.t521());
		tlvbuilder.writeBytes(tlv.t525());
		tlvbuilder.writeBytes(tlv.t544());
		byte[] data = TeaUtil.encrypt(tlvbuilder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(iqq.protocolVersion());
		builder.writeShort(0x810);
		builder.writeShort(1);
		builder.writeInt(account.getUser());
		builder.writeHex(fuckHead.replace("87", "45"));
		builder.writeBytes(Android.getRandKey());
		builder.writeShort(305);
		builder.writeShort(ecdhCrypt.get_pub_key_ver()); // 00 02
		builder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		builder.writeBytes(data);
		builder.writeByte(3);
		builder.writeShortSize(3);
		builder.reWriteByte(2);
		builder.writeSize(4);
		return PacketUtil.makeOnlinePacket(0xB, 0x2, "wtlogin.exchange_emp", toolQ.getBotStatus(), builder.toByteArray(), new byte[16], seq, account.getUser(), false);
	}

	public static byte[] SendCaptcha(QQAccount account, IQQ iqq, String ticket, byte[] tlv104, byte[] t546, int seq) {
		Tlv tlv = new Tlv(account, iqq, seq);
		ByteBuilder byteBuilder = new ByteBuilder();
		byteBuilder.writeShort(2);
		byteBuilder.writeShort(5);

		byteBuilder.writeBytes(Tlv.t193(ticket)); // 1

		byteBuilder.writeBytes(tlv.t8()); // 2

		TlvBuilder t104 = new TlvBuilder(0x104);

		t104.writeBytes(tlv104);
		byteBuilder.writeBytes(t104.toByteArray()); // 3

		byteBuilder.writeBytes(tlv.t1162()); // 4

		TlvBuilder t547 = new TlvBuilder(0x547);
		if(t546 != null) {
			TLog.INSTANCE.warn("Calc Tlv547 by Tlv546...");
			t547.writeBytes(new ClientPow().getPow(t546)); // 5
		}
		byteBuilder.writeBytes(t547.toByteArray());

		byte[] data = TeaUtil.encrypt(byteBuilder.toByteArray(), ecdhCrypt.get_g_share_key());
		ByteBuilder builder = new ByteBuilder();
		builder.writeShort(iqq.protocolVersion());
		builder.writeShort(0x810);
		builder.writeShort(1);
		builder.writeInt(account.getUser());
		builder.writeHex(fuckHead.replace("8", "0"));
		builder.writeBytes(Android.getRandKey());
		builder.writeShort(305);
		builder.writeShort(ecdhCrypt.get_pub_key_ver());
		builder.writeBytesWithShortSize(ecdhCrypt.get_c_pub_key());
		builder.writeBytes(data);
		builder.writeByte(3);
		builder.writeShortSize(3);
		builder.reWriteByte(2);
		return PacketUtil.makeNoLoginPacket("wtlogin.login", builder.toByteArray(), seq, account.getUser(), iqq, false);
	}
}
