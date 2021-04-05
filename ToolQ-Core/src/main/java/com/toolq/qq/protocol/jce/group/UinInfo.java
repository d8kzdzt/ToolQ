package com.toolq.qq.protocol.jce.group;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public class UinInfo extends Jce<UinInfo> {
    public byte gender;
    public long flag;
    public long uin;
    public String email = "";
    public String name = "";
    public String phone = "";
    public String remark = "";

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.uin, 0);
        jceOutputStream.write(this.flag, 1);
        jceOutputStream.write(this.name, 2);
        jceOutputStream.write(this.gender, 3);
        jceOutputStream.write(this.phone, 4);
        jceOutputStream.write(this.email, 5);
        jceOutputStream.write(this.remark, 6);
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.uin = jceInputStream.read(this.uin, 0, true);
        this.flag = jceInputStream.read(this.flag, 1, true);
        this.name = jceInputStream.readString(2, true);
        this.gender = jceInputStream.read(this.gender, 3, true);
        this.phone = jceInputStream.readString(4, true);
        this.email = jceInputStream.readString(5, true);
        this.remark = jceInputStream.readString(6, true);
    }
}
