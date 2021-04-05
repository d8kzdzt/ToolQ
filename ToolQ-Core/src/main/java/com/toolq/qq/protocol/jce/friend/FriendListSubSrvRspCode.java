package com.toolq.qq.protocol.jce.friend;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public class FriendListSubSrvRspCode extends JceStruct {
    public short wGetIntimateInfoRspCode;
    public short wGetMutualMarkRspCode;

    public FriendListSubSrvRspCode() {
    }

    @Override
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.wGetMutualMarkRspCode, 0);
        jceOutputStream.write(this.wGetIntimateInfoRspCode, 1);
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.wGetMutualMarkRspCode = jceInputStream.read(this.wGetMutualMarkRspCode, 0, false);
        this.wGetIntimateInfoRspCode = jceInputStream.read(this.wGetIntimateInfoRspCode, 1, false);
    }
}

