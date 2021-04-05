package com.toolq.qq.protocol.jce.summary;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public class ReqHead extends Jce<ReqHead> {
    public int iVersion = 1;

    public ReqHead(int i) {
        this.iVersion = i;
    }

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.iVersion, 0);
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.iVersion = jceInputStream.read(this.iVersion, 0, true);
    }
}
