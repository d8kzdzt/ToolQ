package com.toolq.helper.bot.tlv;

import com.toolq.helper.packet.ByteBuilder;

import java.nio.ByteBuffer;

/**
 * Tlv包构建器
 *
 * @author luoluo
 * @date 2020/10/4 23:17
 */
public class TlvBuilder extends ByteBuilder {
	private final int ver;

	/**
	 * @param tlvVersion tlv标识
	 */
	public TlvBuilder(int tlvVersion) {
		this.ver = tlvVersion;
	}

	@Override
	public byte[] toByteArray() {
		byte[] data = super.toByteArray();
		ByteBuffer buffer = ByteBuffer.allocate(data.length + 4);
		buffer.putShort((short) ver);
		buffer.putShort((short) data.length);
		buffer.put(data);
		return buffer.array();
	}
}
