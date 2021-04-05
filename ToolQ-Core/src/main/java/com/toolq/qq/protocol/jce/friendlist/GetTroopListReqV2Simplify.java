package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceOutputStream;
import com.toolq.qq.protocol.jce.friendlist.info.stTroopNumSimplify;

import java.util.ArrayList;

public final class GetTroopListReqV2Simplify extends Jce<GetTroopListReqV2Simplify> {
    static ArrayList<stTroopNumSimplify> cache_vecGroupInfo = new ArrayList<>();
    public byte bGetLongGroupName;
    public byte bGetMSFMsgFlag;
    public byte bGroupFlagExt;
    public long dwCompanyId;
    public int shVersion;
    public long uin;
    public byte[] vecCookies;
    public ArrayList<stTroopNumSimplify> vecGroupInfo;
    public long versionNum;

    public GetTroopListReqV2Simplify(long j, byte b, byte[] bArr, ArrayList<stTroopNumSimplify> arrayList, byte b2, int i, long j2, long j3, byte b3) {
        this.uin = j;
        this.bGetMSFMsgFlag = b;
        this.vecCookies = bArr;
        this.vecGroupInfo = arrayList;
        this.bGroupFlagExt = b2;
        this.shVersion = i;
        this.dwCompanyId = j2;
        this.versionNum = j3;
        this.bGetLongGroupName = b3;
    }

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.uin, 0);
        jceOutputStream.write(this.bGetMSFMsgFlag, 1);
        if (this.vecCookies != null) {
            jceOutputStream.write(this.vecCookies, 2);
        }
        if (this.vecGroupInfo != null) {
            jceOutputStream.write(this.vecGroupInfo, 3);
        }
        jceOutputStream.write(this.bGroupFlagExt, 4);
        jceOutputStream.write(this.shVersion, 5);
        jceOutputStream.write(this.dwCompanyId, 6);
        jceOutputStream.write(this.versionNum, 7);
        jceOutputStream.write(this.bGetLongGroupName, 8);
    }

    static {
        cache_vecGroupInfo.add(new stTroopNumSimplify());
    }
}
