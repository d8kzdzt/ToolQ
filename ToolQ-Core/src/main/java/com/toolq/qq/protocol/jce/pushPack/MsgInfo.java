package com.toolq.qq.protocol.jce.pushPack;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public final class MsgInfo extends Jce<MsgInfo> {
    static TempMsgHead cache_stC2CTmpMsgHead;
    static shareData cache_stShareData;
    static byte[] cache_vAppShareCookie;
    static ArrayList<CPicInfo> cache_vCPicInfo;
    static byte[] cache_vMsg;
    static byte[] cache_vMsgCookies;
    static ArrayList<String> cache_vNickName;
    static byte[] cache_vRemarkOfSender;
    public long lFromInstId;
    public long lFromUin;
    public long lLastChangeTime = 1;
    public long lMsgUid;
    public int shMsgSeq;
    public short shMsgType;
    public TempMsgHead stC2CTmpMsgHead;
    public shareData stShareData;
    public String strFromMobile = "";
    public String strFromName = "";
    public String strMsg = "";
    public long uAppShareID;
    public long uMsgTime;
    public int uRealMsgTime;
    public byte[] vAppShareCookie;
    public ArrayList<CPicInfo> vCPicInfo;
    public byte[] vMsg;
    public byte[] vMsgCookies;
    public ArrayList<String> vNickName;
    public byte[] vRemarkOfSender;

    public long getLFromUin() {
        return this.lFromUin;
    }

    public void setLFromUin(long j) {
        this.lFromUin = j;
    }

    public long getUMsgTime() {
        return this.uMsgTime;
    }

    public void setUMsgTime(long j) {
        this.uMsgTime = j;
    }

    public short getShMsgType() {
        return this.shMsgType;
    }

    public void setShMsgType(short s) {
        this.shMsgType = s;
    }

    public int getShMsgSeq() {
        return this.shMsgSeq;
    }

    public void setShMsgSeq(short s) {
        this.shMsgSeq = s;
    }

    public String getStrMsg() {
        return this.strMsg;
    }

    public void setStrMsg(String str) {
        this.strMsg = str;
    }

    public int getURealMsgTime() {
        return this.uRealMsgTime;
    }

    public void setURealMsgTime(int i) {
        this.uRealMsgTime = i;
    }

    public byte[] getVMsg() {
        return this.vMsg;
    }

    public void setVMsg(byte[] bArr) {
        this.vMsg = bArr;
    }

    public long getUAppShareID() {
        return this.uAppShareID;
    }

    public void setUAppShareID(long j) {
        this.uAppShareID = j;
    }

    public byte[] getVMsgCookies() {
        return this.vMsgCookies;
    }

    public void setVMsgCookies(byte[] bArr) {
        this.vMsgCookies = bArr;
    }

    public byte[] getVAppShareCookie() {
        return this.vAppShareCookie;
    }

    public void setVAppShareCookie(byte[] bArr) {
        this.vAppShareCookie = bArr;
    }

    public long getLMsgUid() {
        return this.lMsgUid;
    }

    public void setLMsgUid(long j) {
        this.lMsgUid = j;
    }

    public long getLLastChangeTime() {
        return this.lLastChangeTime;
    }

    public void setLLastChangeTime(long j) {
        this.lLastChangeTime = j;
    }

    public ArrayList<CPicInfo> getVCPicInfo() {
        return this.vCPicInfo;
    }

    public void setVCPicInfo(ArrayList<CPicInfo> arrayList) {
        this.vCPicInfo = arrayList;
    }

    public shareData getStShareData() {
        return this.stShareData;
    }

    public void setStShareData(shareData sharedata) {
        this.stShareData = sharedata;
    }

    public long getLFromInstId() {
        return this.lFromInstId;
    }

    public void setLFromInstId(long j) {
        this.lFromInstId = j;
    }

    public byte[] getVRemarkOfSender() {
        return this.vRemarkOfSender;
    }

    public void setVRemarkOfSender(byte[] bArr) {
        this.vRemarkOfSender = bArr;
    }

    public String getStrFromMobile() {
        return this.strFromMobile;
    }

    public void setStrFromMobile(String str) {
        this.strFromMobile = str;
    }

    public String getStrFromName() {
        return this.strFromName;
    }

    public void setStrFromName(String str) {
        this.strFromName = str;
    }

    public ArrayList<String> getVNickName() {
        return this.vNickName;
    }

    public void setVNickName(ArrayList<String> arrayList) {
        this.vNickName = arrayList;
    }

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.lFromUin, 0);
        jceOutputStream.write(this.uMsgTime, 1);
        jceOutputStream.write(this.shMsgType, 2);
        jceOutputStream.write(this.shMsgSeq, 3);
        jceOutputStream.write(this.strMsg, 4);
        jceOutputStream.write(this.uRealMsgTime, 5);
        if (this.vMsg != null) {
            jceOutputStream.write(this.vMsg, 6);
        }
        jceOutputStream.write(this.uAppShareID, 7);
        if (this.vMsgCookies != null) {
            jceOutputStream.write(this.vMsgCookies, 8);
        }
        if (this.vAppShareCookie != null) {
            jceOutputStream.write(this.vAppShareCookie, 9);
        }
        jceOutputStream.write(this.lMsgUid, 10);
        jceOutputStream.write(this.lLastChangeTime, 11);
        if (this.vCPicInfo != null) {
            jceOutputStream.write(this.vCPicInfo, 12);
        }
        if (this.stShareData != null) {
            jceOutputStream.write(this.stShareData, 13);
        }
        jceOutputStream.write(this.lFromInstId, 14);
        if (this.vRemarkOfSender != null) {
            jceOutputStream.write(this.vRemarkOfSender, 15);
        }
        if (this.strFromMobile != null) {
            jceOutputStream.write(this.strFromMobile, 16);
        }
        if (this.strFromName != null) {
            jceOutputStream.write(this.strFromName, 17);
        }
        if (this.vNickName != null) {
            jceOutputStream.write(this.vNickName, 18);
        }
        if (this.stC2CTmpMsgHead != null) {
            jceOutputStream.write(this.stC2CTmpMsgHead, 19);
        }
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.lFromUin = jceInputStream.read(this.lFromUin, 0, true);
        this.uMsgTime = jceInputStream.read(this.uMsgTime, 1, true);
        this.shMsgType = jceInputStream.read(this.shMsgType, 2, true);
        this.shMsgSeq = jceInputStream.read(this.shMsgSeq, 3, true);
        this.strMsg = jceInputStream.readString(4, true);
        this.uRealMsgTime = jceInputStream.read(this.uRealMsgTime, 5, false);
        if (cache_vMsg == null) {
            cache_vMsg = new byte[1];
        }
        this.vMsg = jceInputStream.read(cache_vMsg, 6, false);
        this.uAppShareID = jceInputStream.read(this.uAppShareID, 7, false);
        if (cache_vMsgCookies == null) {
            cache_vMsgCookies = new byte[1];
        }
        this.vMsgCookies = jceInputStream.read(cache_vMsgCookies, 8, false);
        if (cache_vAppShareCookie == null) {
            cache_vAppShareCookie = new byte[1];
        }
        this.vAppShareCookie = jceInputStream.read(cache_vAppShareCookie, 9, false);
        this.lMsgUid = jceInputStream.read(this.lMsgUid, 10, false);
        this.lLastChangeTime = jceInputStream.read(this.lLastChangeTime, 11, false);
        if (cache_vCPicInfo == null) {
            cache_vCPicInfo = new ArrayList<>();
            cache_vCPicInfo.add(new CPicInfo());
        }
        this.vCPicInfo = (ArrayList) jceInputStream.readV2(cache_vCPicInfo, 12, false);
        if (cache_stShareData == null) {
            cache_stShareData = new shareData();
        }
        this.stShareData = (shareData) jceInputStream.read(cache_stShareData, 13, false);
        this.lFromInstId = jceInputStream.read(this.lFromInstId, 14, false);
        if (cache_vRemarkOfSender == null) {
            cache_vRemarkOfSender = new byte[1];
        }
        this.vRemarkOfSender = jceInputStream.read(cache_vRemarkOfSender, 15, false);
        this.strFromMobile = jceInputStream.readString(16, false);
        this.strFromName = jceInputStream.readString(17, false);
        if (cache_vNickName == null) {
            cache_vNickName = new ArrayList<>();
            cache_vNickName.add("");
        }
        this.vNickName = (ArrayList) jceInputStream.readV2(cache_vNickName, 18, false);
        if (cache_stC2CTmpMsgHead == null) {
            cache_stC2CTmpMsgHead = new TempMsgHead();
        }
        this.stC2CTmpMsgHead = (TempMsgHead) jceInputStream.read(cache_stC2CTmpMsgHead, 19, false);
    }
}
