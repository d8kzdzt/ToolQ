package com.toolq.qq.protocol.jce.friend;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceStruct;

public class VipOpenInfo extends JceStruct {
    public boolean bOpen;
    public int iVipFlag;
    public int iVipLevel = -1;
    public int iVipType = -1;
    public long lNameplateId;

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.bOpen = jceInputStream.read(this.bOpen, 0, true);
        this.iVipType = jceInputStream.read(this.iVipType, 1, true);
        this.iVipLevel = jceInputStream.read(this.iVipLevel, 2, true);
        this.iVipFlag = jceInputStream.read(this.iVipFlag, 3, false);
        this.lNameplateId = jceInputStream.read(this.lNameplateId, 4, false);
    }
}

