package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.toolq.qq.protocol.jce.friendlist.info.stFavoriteGroup;
import com.toolq.qq.protocol.jce.friendlist.info.stGroupRankInfo;
import com.toolq.qq.protocol.jce.friendlist.info.stTroopNum;

import java.util.ArrayList;

public final class GetTroopListRespV2 extends Jce<GetTroopListRespV2> {
    static int cache_result = 0;
    static byte[] cache_vecCookies = new byte[1];
    static ArrayList<stFavoriteGroup> cache_vecFavGroup = new ArrayList<>();
    static ArrayList<Long> cache_vecGroupInfoExt = new ArrayList<>();
    static ArrayList<stTroopNum> cache_vecTroopList = new ArrayList<>();
    static ArrayList<stTroopNum> cache_vecTroopListDel = new ArrayList<>();
    static ArrayList<stTroopNum> cache_vecTroopListExt = new ArrayList<>();
    static ArrayList<stGroupRankInfo> cache_vecTroopRank = new ArrayList<>();
    public short errorCode;
    public int result;
    public short troopcount;
    public long uin;
    public byte[] vecCookies;
    public ArrayList<stFavoriteGroup> vecFavGroup;
    public ArrayList<Long> vecGroupInfoExt;
    public ArrayList<stTroopNum> vecTroopList;
    public ArrayList<stTroopNum> vecTroopListDel;
    public ArrayList<stTroopNum> vecTroopListExt;
    public ArrayList<stGroupRankInfo> vecTroopRank;

    public GetTroopListRespV2() {
    }

    public GetTroopListRespV2(long j, short s, int i, short s2, byte[] bArr, ArrayList<stTroopNum> arrayList, ArrayList<stTroopNum> arrayList2, ArrayList<stGroupRankInfo> arrayList3, ArrayList<stFavoriteGroup> arrayList4, ArrayList<stTroopNum> arrayList5, ArrayList<Long> arrayList6) {
        this.uin = j;
        this.troopcount = s;
        this.result = i;
        this.errorCode = s2;
        this.vecCookies = bArr;
        this.vecTroopList = arrayList;
        this.vecTroopListDel = arrayList2;
        this.vecTroopRank = arrayList3;
        this.vecFavGroup = arrayList4;
        this.vecTroopListExt = arrayList5;
        this.vecGroupInfoExt = arrayList6;
    }

    static {
        cache_vecTroopList.add(new stTroopNum());
        cache_vecTroopListDel.add(new stTroopNum());
        cache_vecTroopRank.add(new stGroupRankInfo());
        cache_vecFavGroup.add(new stFavoriteGroup());
        cache_vecTroopListExt.add(new stTroopNum());
        cache_vecGroupInfoExt.add(0L);
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.uin = jceInputStream.read(this.uin, 0, true);
        this.troopcount = jceInputStream.read(this.troopcount, 1, true);
        this.result = jceInputStream.read(this.result, 2, true);
        this.errorCode = jceInputStream.read(this.errorCode, 3, false);
        this.vecCookies = jceInputStream.read(cache_vecCookies, 4, false);
        this.vecTroopList = (ArrayList<stTroopNum>) jceInputStream.readV2(cache_vecTroopList, 5, false);
        this.vecTroopListDel = (ArrayList<stTroopNum>) jceInputStream.readV2(cache_vecTroopListDel, 6, false);
        this.vecTroopRank = (ArrayList<stGroupRankInfo>) jceInputStream.readV2(cache_vecTroopRank, 7, false);
        this.vecFavGroup = (ArrayList<stFavoriteGroup>) jceInputStream.readV2(cache_vecFavGroup, 8, false);
        this.vecTroopListExt = (ArrayList<stTroopNum>) jceInputStream.readV2(cache_vecTroopListExt, 9, false);
        this.vecGroupInfoExt = (ArrayList<Long>) jceInputStream.readV2(cache_vecGroupInfoExt, 10, false);
    }


}
