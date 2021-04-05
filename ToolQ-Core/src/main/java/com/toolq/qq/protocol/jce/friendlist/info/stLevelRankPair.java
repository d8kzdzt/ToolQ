package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class stLevelRankPair extends Jce<stLevelRankPair> {
    public long dwLevel;
    public String strRank = "";

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.dwLevel, 0);
        if (this.strRank != null) {
            jceOutputStream.write(this.strRank, 1);
        }
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.dwLevel = jceInputStream.read(this.dwLevel, 0, false);
        this.strRank = jceInputStream.readString(1, false);
    }
}
