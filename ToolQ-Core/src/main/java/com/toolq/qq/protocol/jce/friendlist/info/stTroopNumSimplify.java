package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class stTroopNumSimplify extends Jce<stTroopNumSimplify> {
    public long GroupCode;
    public long dwGroupFlagExt;
    public long dwGroupInfoExtSeq;
    public long dwGroupInfoSeq;
    public long dwGroupRankSeq;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.GroupCode, 0);
        jceOutputStream.write(this.dwGroupInfoSeq, 1);
        jceOutputStream.write(this.dwGroupFlagExt, 2);
        jceOutputStream.write(this.dwGroupRankSeq, 3);
        jceOutputStream.write(this.dwGroupInfoExtSeq, 4);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.GroupCode = jceInputStream.read(this.GroupCode, 0, true);
        this.dwGroupInfoSeq = jceInputStream.read(this.dwGroupInfoSeq, 1, false);
        this.dwGroupFlagExt = jceInputStream.read(this.dwGroupFlagExt, 2, false);
        this.dwGroupRankSeq = jceInputStream.read(this.dwGroupRankSeq, 3, false);
        this.dwGroupInfoExtSeq = jceInputStream.read(this.dwGroupInfoExtSeq, 4, false);
    }
}
