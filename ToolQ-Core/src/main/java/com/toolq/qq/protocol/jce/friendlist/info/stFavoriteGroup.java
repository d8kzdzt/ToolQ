package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class stFavoriteGroup extends Jce<stFavoriteGroup> {
    public long dwGroupCode;
    public long dwOpenTimestamp;
    public long dwSnsFlag = 1;
    public long dwTimestamp;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.dwGroupCode, 0);
        jceOutputStream.write(this.dwTimestamp, 1);
        jceOutputStream.write(this.dwSnsFlag, 2);
        jceOutputStream.write(this.dwOpenTimestamp, 3);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.dwGroupCode = jceInputStream.read(this.dwGroupCode, 0, true);
        this.dwTimestamp = jceInputStream.read(this.dwTimestamp, 1, false);
        this.dwSnsFlag = jceInputStream.read(this.dwSnsFlag, 2, false);
        this.dwOpenTimestamp = jceInputStream.read(this.dwOpenTimestamp, 3, false);
    }
}
