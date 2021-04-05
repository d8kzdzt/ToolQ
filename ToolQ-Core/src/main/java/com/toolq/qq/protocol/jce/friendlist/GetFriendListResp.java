package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.toolq.qq.protocol.jce.friend.FriendInfo;
import com.toolq.qq.protocol.jce.friend.FriendListSubSrvRspCode;
import com.toolq.qq.protocol.jce.friend.GroupInfo;

import java.util.ArrayList;

public class GetFriendListResp extends Jce<GetFriendListResp> {
    static FriendInfo cache_stSelfInfo = new FriendInfo();
    static FriendListSubSrvRspCode cache_stSubSrvRspCode = new FriendListSubSrvRspCode();
    static ArrayList<FriendInfo> cache_vecFriendInfo = new ArrayList<>();
    static ArrayList<GroupInfo> cache_vecGroupInfo = new ArrayList<>();
    static ArrayList<GroupInfo> cache_vecMSFGroupInfo = new ArrayList<>();
    public byte cHasOtherRespFlag;
    public byte cRespType;
    public byte cShowPcIcon;
    public short errorCode;
    public short friend_count;
    public short getfriendCount;
    public byte getgroupCount;
    public byte group_count;
    public byte groupid;
    public byte groupstartIndex;
    public byte ifGetGroupInfo;
    public byte ifReflush;
    public short online_friend_count;
    public int reqtype;
    public int result;
    public long serverTime;
    public short sqqOnLine_count;
    public FriendInfo stSelfInfo;
    public FriendListSubSrvRspCode stSubSrvRspCode;
    public short startIndex;
    public short totoal_friend_count;
    public short totoal_group_count;
    public long uin;
    public ArrayList<FriendInfo> vecFriendInfo;
    public ArrayList<GroupInfo> vecGroupInfo;
    public ArrayList<GroupInfo> vecMSFGroupInfo;
    public short wGetExtSnsRspCode;

    static {
        cache_vecFriendInfo.add(new FriendInfo());
        cache_vecGroupInfo.add(new GroupInfo());
        cache_vecMSFGroupInfo.add(new GroupInfo());
    }

    public void readFrom(JceInputStream input) {
        this.reqtype = input.read(this.reqtype, 0, true);
        this.ifReflush = input.read(this.ifReflush, 1, true);
        this.uin = input.read(this.uin, 2, true);
        this.startIndex = input.read(this.startIndex, 3, true);
        this.getfriendCount = input.read(this.getfriendCount, 4, true);
        this.totoal_friend_count = input.read(this.totoal_friend_count, 5, true);
        this.friend_count = input.read(this.friend_count, 6, true);
        this.vecFriendInfo = (ArrayList) input.readV2(cache_vecFriendInfo, 7, true);
        this.groupid = input.read(this.groupid, 8, false);
        this.ifGetGroupInfo = input.read(this.ifGetGroupInfo, 9, true);
        this.groupstartIndex = input.read(this.groupstartIndex, 10, false);
        this.getgroupCount = input.read(this.getgroupCount, 11, false);
        this.totoal_group_count = input.read(this.totoal_group_count, 12, false);
        this.group_count = input.read(this.group_count, 13, false);
        this.vecGroupInfo = (ArrayList) input.readV2(cache_vecGroupInfo, 14, false);
        this.result = input.read(this.result, 15, true);
        this.errorCode = input.read(this.errorCode, 16, false);
        this.online_friend_count = input.read(this.online_friend_count, 17, false);
        this.serverTime = input.read(this.serverTime, 18, false);
        this.sqqOnLine_count = input.read(this.sqqOnLine_count, 19, false);
        this.vecMSFGroupInfo = (ArrayList) input.readV2(cache_vecMSFGroupInfo, 20, false);
        this.cRespType = input.read(this.cRespType, 21, false);
        this.cHasOtherRespFlag = input.read(this.cHasOtherRespFlag, 22, false);
        this.stSelfInfo = (FriendInfo) input.readV2(cache_stSelfInfo, 23, false);
        this.cShowPcIcon = input.read(this.cShowPcIcon, 24, false);
        this.wGetExtSnsRspCode = input.read(this.wGetExtSnsRspCode, 25, false);
        this.stSubSrvRspCode = (FriendListSubSrvRspCode) input.readV2(cache_stSubSrvRspCode, 26, false);
    }
}
