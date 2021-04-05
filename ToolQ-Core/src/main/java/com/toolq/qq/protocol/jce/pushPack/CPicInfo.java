package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class CPicInfo extends Jce<CPicInfo> {
    static byte[] cache_vHost;
    static byte[] cache_vPath;
    public byte[] vHost;
    public byte[] vPath;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.vPath, 0);
        if (this.vHost != null) {
            jceOutputStream.write(this.vHost, 1);
        }
    }

    public void readFrom(JceInputStream jceInputStream) {
        if (cache_vPath == null) {
            cache_vPath = new byte[1];
        }
        this.vPath = jceInputStream.read(cache_vPath, 0, true);
        if (cache_vHost == null) {
            cache_vHost = new byte[1];
        }
        this.vHost = jceInputStream.read(cache_vHost, 1, false);
    }
}
