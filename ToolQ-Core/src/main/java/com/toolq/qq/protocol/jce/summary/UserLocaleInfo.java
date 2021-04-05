package com.toolq.qq.protocol.jce.summary;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public class UserLocaleInfo extends JceStruct {
    public long latitude;
    public long longitude;

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.longitude, 1);
        jceOutputStream.write(this.latitude, 2);
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.longitude = jceInputStream.read(this.longitude, 1, false);
        this.latitude = jceInputStream.read(this.latitude, 2, false);
    }

}
