package com.toolq.qq.protocol.jce;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;

public class MsgType0x210 extends Jce<MsgType0x210> {
    static byte[] cache_pb_src = new byte[0];
    public long uSubMsgType;
    public byte[] vProtobuf;

    public void readFrom(JceInputStream jceInputStream) {
        this.uSubMsgType = jceInputStream.read(this.uSubMsgType, 0, true);
        this.vProtobuf = jceInputStream.read(cache_pb_src, 10, false);
    }
}
