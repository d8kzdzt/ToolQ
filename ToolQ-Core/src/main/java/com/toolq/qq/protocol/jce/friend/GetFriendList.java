package com.toolq.qq.protocol.jce.friend;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

import java.util.ArrayList;

public class GetFriendList {
    public static class Req extends JceStruct {
        static int cache_eAppType = 0;
        static int cache_reqtype = 0;
        static ArrayList<Long> cache_uinList = new ArrayList<Long>();
        static byte[] cache_vec0xd50Req = new byte[1];
        static byte[] cache_vec0xd6bReq = new byte[1];
        static ArrayList<Long> cache_vecSnsTypelist = new ArrayList<Long>();
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
        
        @Override
        public void writeTo(JceOutputStream jceOutputStream) {
            jceOutputStream.write(this.reqtype, 0);
            jceOutputStream.write(this.ifReflush, 1);
            jceOutputStream.write(this.uin, 2);
            jceOutputStream.write(this.startIndex, 3);
            jceOutputStream.write(this.getfriendCount, 4);
            jceOutputStream.write(this.groupid, 5);
            jceOutputStream.write(this.ifGetGroupInfo, 6);
            jceOutputStream.write(this.groupstartIndex, 7);
            jceOutputStream.write(this.getgroupCount, 8);
            jceOutputStream.write(this.ifGetMSFGroup, 9);
            jceOutputStream.write(this.ifShowTermType, 10);
            jceOutputStream.write(this.version, 11);
            if (this.uinList != null) {
                jceOutputStream.write(this.uinList, 12);
            }
            jceOutputStream.write(this.eAppType, 13);
            jceOutputStream.write(this.ifGetDOVId, 14);
            jceOutputStream.write(this.ifGetBothFlag, 15);
            if (this.vec0xd50Req != null) {
                jceOutputStream.write(this.vec0xd50Req, 16);
            }
            if (this.vec0xd6bReq != null) {
                jceOutputStream.write(this.vec0xd6bReq, 17);
            }
            if (this.vecSnsTypelist != null) {
                jceOutputStream.write(this.vecSnsTypelist, 18);
            }
        }

        static {
            cache_uinList.add(0L);
            cache_vec0xd50Req[0] = 0;
            cache_vec0xd6bReq[0] = 0;
            cache_vecSnsTypelist.add(0L);
        }   
    }
    
    public static class Resp extends JceStruct {
        static int cache_reqtype = 0;
        static int cache_result = 0;
        static FriendInfo cache_stSelfInfo = new FriendInfo();
        static FriendListSubSrvRspCode cache_stSubSrvRspCode = new FriendListSubSrvRspCode();
        static ArrayList<FriendInfo> cache_vecFriendInfo = new ArrayList<FriendInfo>();
        static ArrayList<GroupInfo> cache_vecGroupInfo = new ArrayList<GroupInfo>();
        static ArrayList<GroupInfo> cache_vecMSFGroupInfo = new ArrayList<GroupInfo>();
        public byte cHasOtherRespFlag;
        public byte cRespType;
        public byte cShowPcIcon;
        public short errorCode;
        public short friend_count;
        // 当前获取的好友数量
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
        // 实际上所有好友数量
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

        @Override
        public void readFrom(JceInputStream jceInputStream) {
            this.reqtype = jceInputStream.read(this.reqtype, 0, true);
            this.ifReflush = jceInputStream.read(this.ifReflush, 1, true);
            this.uin = jceInputStream.read(this.uin, 2, true);
            this.startIndex = jceInputStream.read(this.startIndex, 3, true);
            this.getfriendCount = jceInputStream.read(this.getfriendCount, 4, true);
            this.totoal_friend_count = jceInputStream.read(this.totoal_friend_count, 5, true);
            this.friend_count = jceInputStream.read(this.friend_count, 6, true);
            this.vecFriendInfo = (ArrayList) jceInputStream.readV2(cache_vecFriendInfo, 7, true);
            this.groupid = jceInputStream.read(this.groupid, 8, false);
            this.ifGetGroupInfo = jceInputStream.read(this.ifGetGroupInfo, 9, true);
            this.groupstartIndex = jceInputStream.read(this.groupstartIndex, 10, false);
            this.getgroupCount = jceInputStream.read(this.getgroupCount, 11, false);
            this.totoal_group_count = jceInputStream.read(this.totoal_group_count, 12, false);
            this.group_count = jceInputStream.read(this.group_count, 13, false);
            this.vecGroupInfo = (ArrayList) jceInputStream.readV2(cache_vecGroupInfo, 14, false);
            this.result = jceInputStream.read(this.result, 15, true);
            this.errorCode = jceInputStream.read(this.errorCode, 16, false);
            this.online_friend_count = jceInputStream.read(this.online_friend_count, 17, false);
            this.serverTime = jceInputStream.read(this.serverTime, 18, false);
            this.sqqOnLine_count = jceInputStream.read(this.sqqOnLine_count, 19, false);
            this.vecMSFGroupInfo = (ArrayList) jceInputStream.readV2(cache_vecMSFGroupInfo, 20, false);
            this.cRespType = jceInputStream.read(this.cRespType, 21, false);
            this.cHasOtherRespFlag = jceInputStream.read(this.cHasOtherRespFlag, 22, false);
            this.stSelfInfo = (FriendInfo) jceInputStream.read(cache_stSelfInfo, 23, false);
            this.cShowPcIcon = jceInputStream.read(this.cShowPcIcon, 24, false);
            this.wGetExtSnsRspCode = jceInputStream.read(this.wGetExtSnsRspCode, 25, false);
            this.stSubSrvRspCode = (FriendListSubSrvRspCode) jceInputStream.read(cache_stSubSrvRspCode, 26, false);
        }
    }
}
