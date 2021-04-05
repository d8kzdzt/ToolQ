package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public class GetFriendListReq extends Jce<GetFriendListReq> {
    static ArrayList<Long> cache_uinList = new ArrayList<>();
    static byte[] cache_vec0xd50Req = new byte[1];
    static byte[] cache_vec0xd6bReq = new byte[1];
    static ArrayList<Long> cache_vecSnsTypelist = new ArrayList<>();
    public int eAppType = 0;
    public short getfriendCount;
    public byte getgroupCount;
    public byte groupid;
    public byte groupstartIndex;
    public byte ifGetBothFlag;
    public byte ifGetDOVId;
    public byte ifGetGroupInfo;
    public byte ifGetMSFGroup;
    public byte ifReflush;
    public byte ifShowTermType;
    public int reqtype;
    public short startIndex;
    public long uin;
    public ArrayList<Long> uinList;
    public byte[] vec0xd50Req;
    public byte[] vec0xd6bReq;
    public ArrayList<Long> vecSnsTypelist;
    public long version;

    public void writeTo(JceOutputStream out) {
        out.write(this.reqtype, 0);
        out.write(this.ifReflush, 1);
        out.write(this.uin, 2);
        out.write(this.startIndex, 3);
        out.write(this.getfriendCount, 4);
        out.write(this.groupid, 5);
        out.write(this.ifGetGroupInfo, 6);
        out.write(this.groupstartIndex, 7);
        out.write(this.getgroupCount, 8);
        out.write(this.ifGetMSFGroup, 9);
        out.write(this.ifShowTermType, 10);
        out.write(this.version, 11);
        if (this.uinList != null) {
            out.write(this.uinList, 12);
        }
        out.write(this.eAppType, 13);
        out.write(this.ifGetDOVId, 14);
        out.write(this.ifGetBothFlag, 15);
        if (this.vec0xd50Req != null) {
            out.write(this.vec0xd50Req, 16);
        }
        if (this.vec0xd6bReq != null) {
            out.write(this.vec0xd6bReq, 17);
        }
        if (this.vecSnsTypelist != null) {
            out.write(this.vecSnsTypelist, 18);
        }
    }

    static {
        cache_uinList.add(0L);
        cache_vec0xd50Req[0] = 0;
        cache_vec0xd6bReq[0] = 0;
        cache_vecSnsTypelist.add(0L);
    }
}
