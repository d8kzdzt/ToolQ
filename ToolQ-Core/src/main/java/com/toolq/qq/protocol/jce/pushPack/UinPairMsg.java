package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public final class UinPairMsg extends Jce<UinPairMsg> {
    static ArrayList<MsgInfo> cache_vMsgInfos;
    public long lPeerUin;
    public long uLastReadTime;
    public long uMsgCompleted;
    public ArrayList<MsgInfo> vMsgInfos;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.uLastReadTime, 1);
        jceOutputStream.write(this.lPeerUin, 2);
        jceOutputStream.write(this.uMsgCompleted, 3);
        if (this.vMsgInfos != null) {
            jceOutputStream.write(this.vMsgInfos, 4);
        }
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.uLastReadTime = jceInputStream.read(this.uLastReadTime, 1, false);
        this.lPeerUin = jceInputStream.read(this.lPeerUin, 2, false);
        this.uMsgCompleted = jceInputStream.read(this.uMsgCompleted, 3, false);
        if (cache_vMsgInfos == null) {
            cache_vMsgInfos = new ArrayList<>();
            cache_vMsgInfos.add(new MsgInfo());
        }
        this.vMsgInfos = (ArrayList) jceInputStream.readV2(cache_vMsgInfos, 4, false);
    }
}
