package com.toolq.helper.bot.packet;

import com.toolq.helper.packet.ByteBuilder;
import com.toolq.helper.packet.ByteReader;

/**
 * @author luoluo
 * @date 2020/10/7 4:42
 */
public final class Packet {
	public Packet(String cmd, byte[] body, int ssoSeq) {
		this.cmd = cmd;
		this.body = body;
		this.ssoSeq = ssoSeq;
	}

	private final String cmd;
	private final byte[] body;
	private final int ssoSeq;

	public int getSsoSeq() {
		return ssoSeq;
	}

	public byte[] getBody() {
		return body;
	}

	public byte[] getBodyWithLength() {
		return new ByteBuilder().writeInt(body.length + 4).writeBytes(body).toByteArray();
	}

	public ByteReader toByteReader() {
		return new ByteReader(getBody());
	}

	public String getCmd() {
		return cmd;
	}
}
