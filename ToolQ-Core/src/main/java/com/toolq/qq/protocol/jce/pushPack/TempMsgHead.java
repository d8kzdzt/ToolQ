package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class TempMsgHead extends Jce<TempMsgHead> {
    public int c2c_type;
    public int service_type;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.c2c_type, 0);
        jceOutputStream.write(this.service_type, 1);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.c2c_type = jceInputStream.read(this.c2c_type, 0, false);
        this.service_type = jceInputStream.read(this.service_type, 1, false);
    }
}
