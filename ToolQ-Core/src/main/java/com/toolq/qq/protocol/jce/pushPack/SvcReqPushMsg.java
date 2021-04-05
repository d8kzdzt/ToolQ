package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.toolq.qq.protocol.protobuf.message.msg_comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class SvcReqPushMsg extends Jce<SvcReqPushMsg> {
    static Map<String, byte[]> cache_mPreviews = new HashMap<>();
    static ArrayList<MsgInfo> cache_vMsgInfos = new ArrayList<>();
    static byte[] cache_vSyncCookie = new byte[1];
    static ArrayList<UinPairMsg> cache_vUinPairMsg = new ArrayList<>();
    public long lUin;
    public Map<String, byte[]> mPreviews;
    public int svrip;
    public long uMsgTime;
    public ArrayList<MsgInfo> vMsgInfos;
    public byte[] vSyncCookie;
    public ArrayList<msg_comm.UinPairMsg> vUinPairMsg;
    public int wGeneralFlag;
    public int wUserActive;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.lUin, 0);
        jceOutputStream.write(this.uMsgTime, 1);
        jceOutputStream.write(this.vMsgInfos, 2);
        jceOutputStream.write(this.svrip, 3);
        if (this.vSyncCookie != null) {
            jceOutputStream.write(this.vSyncCookie, 4);
        }
        if (this.vUinPairMsg != null) {
            jceOutputStream.write(this.vUinPairMsg, 5);
        }
        if (this.mPreviews != null) {
            jceOutputStream.write(this.mPreviews, 6);
        }
        jceOutputStream.write(this.wUserActive, 7);
        jceOutputStream.write(this.wGeneralFlag, 12);
    }

    static {
        cache_vMsgInfos.add(new MsgInfo());
        cache_vSyncCookie[0] = 0;
        cache_vUinPairMsg.add(new UinPairMsg());
        byte[] bArr = new byte[1];
        cache_mPreviews.put("", bArr);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.lUin = jceInputStream.read(this.lUin, 0, true);
        this.uMsgTime = jceInputStream.read(this.uMsgTime, 1, true);
        this.vMsgInfos = (ArrayList) jceInputStream.readV2(cache_vMsgInfos, 2, true);
        this.svrip = jceInputStream.read(this.svrip, 3, true);
        this.vSyncCookie = jceInputStream.read(cache_vSyncCookie, 4, false);
        this.vUinPairMsg = (ArrayList) jceInputStream.readV2(cache_vUinPairMsg, 5, false);
        this.mPreviews = (Map) jceInputStream.readV2(cache_mPreviews, 6, false);
        this.wUserActive = jceInputStream.read(this.wUserActive, 7, false);
        this.wGeneralFlag = jceInputStream.read(this.wGeneralFlag, 12, false);
    }
}
