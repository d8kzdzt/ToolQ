package com.toolq.qq.protocol.jce.favorite;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public class ReqHead extends Jce<ReqHead> implements Cloneable {
    public byte bReqType = 1;
    public byte bTriggered = 0;
    public int iSeq;
    public long lUIN;
    public short shVersion = 1;
    public byte[] vCookies;

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.lUIN, 0);
        jceOutputStream.write(this.shVersion, 1);
        jceOutputStream.write(this.iSeq, 2);
        jceOutputStream.write(this.bReqType, 3);
        jceOutputStream.write(this.bTriggered, 4);
        if (this.vCookies != null) {
            jceOutputStream.write(this.vCookies, 5);
        }
    }
}

