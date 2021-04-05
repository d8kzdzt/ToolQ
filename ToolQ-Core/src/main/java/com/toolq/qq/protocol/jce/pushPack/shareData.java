package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class shareData extends Jce<shareData> {
    public String msgtail = "";
    public String picurl = "";
    public String pkgname = "";
    public String url = "";

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.pkgname, 0);
        jceOutputStream.write(this.msgtail, 1);
        jceOutputStream.write(this.picurl, 2);
        jceOutputStream.write(this.url, 3);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.pkgname = jceInputStream.readString(0, true);
        this.msgtail = jceInputStream.readString(1, true);
        this.picurl = jceInputStream.readString(2, true);
        this.url = jceInputStream.readString(3, true);
    }
}
