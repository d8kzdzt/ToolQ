package com.toolq.qq.protocol.jce.statsvc;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;

public class RequestPushForceOffline extends Jce<RequestPushForceOffline> {
    public byte bSameDevice;
    public long lUin;
    public String strTips = "";
    public String strTitle = "";

    public void readFrom(JceInputStream jceInputStream) {
        this.lUin = jceInputStream.read(this.lUin, 0, true);
        this.strTitle = jceInputStream.readString(1, false);
        this.strTips = jceInputStream.readString(2, false);
        this.bSameDevice = jceInputStream.read(this.bSameDevice, 3, false);
    }
}
