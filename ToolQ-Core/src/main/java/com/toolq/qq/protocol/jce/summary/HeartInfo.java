package com.toolq.qq.protocol.jce.summary;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public class HeartInfo extends JceStruct {
    public int iHeartCount;

    public HeartInfo() {
    }

    public HeartInfo(int i) {
        this.iHeartCount = i;
    }

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.iHeartCount, 0);
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.iHeartCount = jceInputStream.read(this.iHeartCount, 0, false);
    }
}
