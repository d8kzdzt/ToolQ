package com.toolq.qq.protocol.jce.favorite;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;

public class RespHead extends Jce<RespHead> {
    public int iReplyCode;
    public int iSeq;
    public long lUIN;
    public short shVersion;
    public String strResult = "";

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.shVersion = jceInputStream.read(this.shVersion, 0, true);
        this.iSeq = jceInputStream.read(this.iSeq, 1, true);
        this.lUIN = jceInputStream.read(this.lUIN, 2, true);
        this.iReplyCode = jceInputStream.read(this.iReplyCode, 3, true);
        this.strResult = jceInputStream.readString(4, false);
    }
}

