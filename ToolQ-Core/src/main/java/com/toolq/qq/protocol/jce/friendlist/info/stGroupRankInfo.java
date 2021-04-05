package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public final class stGroupRankInfo extends Jce<stGroupRankInfo> {
    static ArrayList<stLevelRankPair> cache_vecRankMap = new ArrayList<>();
    static ArrayList<stLevelRankPair> cache_vecRankMapNew = new ArrayList<>();
    public byte cGroupRankSysFlag;
    public byte cGroupRankUserFlag;
    public byte cGroupRankUserFlagNew;
    public long dwGroupCode;
    public long dwGroupRankSeq;
    public long dwOfficeMode;
    public String strAdminName = "";
    public String strOwnerName = "";
    public ArrayList<stLevelRankPair> vecRankMap;
    public ArrayList<stLevelRankPair> vecRankMapNew;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.dwGroupCode, 0);
        jceOutputStream.write(this.cGroupRankSysFlag, 1);
        jceOutputStream.write(this.cGroupRankUserFlag, 2);
        if (this.vecRankMap != null) {
            jceOutputStream.write(this.vecRankMap, 3);
        }
        jceOutputStream.write(this.dwGroupRankSeq, 4);
        if (this.strOwnerName != null) {
            jceOutputStream.write(this.strOwnerName, 5);
        }
        if (this.strAdminName != null) {
            jceOutputStream.write(this.strAdminName, 6);
        }
        jceOutputStream.write(this.dwOfficeMode, 7);
        jceOutputStream.write(this.cGroupRankUserFlagNew, 8);
        if (this.vecRankMapNew != null) {
            jceOutputStream.write(this.vecRankMapNew, 9);
        }
    }

    static {
        cache_vecRankMap.add(new stLevelRankPair());
        cache_vecRankMapNew.add(new stLevelRankPair());
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.dwGroupCode = jceInputStream.read(this.dwGroupCode, 0, true);
        this.cGroupRankSysFlag = jceInputStream.read(this.cGroupRankSysFlag, 1, false);
        this.cGroupRankUserFlag = jceInputStream.read(this.cGroupRankUserFlag, 2, false);
        this.vecRankMap = (ArrayList) jceInputStream.readV2(cache_vecRankMap, 3, false);
        this.dwGroupRankSeq = jceInputStream.read(this.dwGroupRankSeq, 4, false);
        this.strOwnerName = jceInputStream.readString(5, false);
        this.strAdminName = jceInputStream.readString(6, false);
        this.dwOfficeMode = jceInputStream.read(this.dwOfficeMode, 7, false);
        this.cGroupRankUserFlagNew = jceInputStream.read(this.cGroupRankUserFlagNew, 8, false);
        this.vecRankMapNew = (ArrayList) jceInputStream.readV2(cache_vecRankMapNew, 9, false);
    }
}
