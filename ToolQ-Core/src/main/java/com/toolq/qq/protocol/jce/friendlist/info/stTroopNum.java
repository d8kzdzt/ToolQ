package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public final class stTroopNum extends Jce<stTroopNum> {
    static byte[] cache_vecGroupRemark = new byte[1];
    public long GroupCode;
    public long GroupUin;
    public byte cFlag;
    public byte cIsConfGroup;
    public byte cIsModifyConfGroupFace;
    public byte cIsModifyConfGroupName;
    public long dwAdditionalFlag;
    public long dwAppPrivilegeFlag;
    public long dwAppealDeadline;
    public long dwCertificationType;
    public long dwCmdUinGroupMask;
    public long dwCmdUinUinFlag;
    public long dwCmduinJoinTime;
    public long dwGroupClassExt;
    public long dwGroupFlag;
    public long dwGroupFlagExt;
    public long dwGroupFlagExt3;
    public long dwGroupFlagExt4;
    public long dwGroupInfoSeq;
    public long dwGroupOwnerUin;
    public long dwGroupRankSeq;
    public long dwGroupSecType;
    public long dwGroupSecTypeInfo;
    public long dwGroupTypeFlag;
    public long dwMaxGroupMemberNum;
    public long dwMemberCardSeq;
    public long dwMemberNum;
    public long dwMemberNumSeq;
    public long dwMyShutupTimestamp;
    public long dwShutupTimestamp;
    public long dwSubscriptionUin;
    public String strGroupMemo = "";
    public String strGroupName = "";
    public long udwCmdUinFlagEx2;
    public long udwCmdUinRingtoneID;
    public long udwHLGuildAppid;
    public long udwHLGuildSubType;
    public long ulCompanyId;
    public byte[] vecGroupRemark;

    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.GroupUin, 0);
        jceOutputStream.write(this.GroupCode, 1);
        jceOutputStream.write(this.cFlag, 2);
        jceOutputStream.write(this.dwGroupInfoSeq, 3);
        if (this.strGroupName != null) {
            jceOutputStream.write(this.strGroupName, 4);
        }
        if (this.strGroupMemo != null) {
            jceOutputStream.write(this.strGroupMemo, 5);
        }
        jceOutputStream.write(this.dwGroupFlagExt, 6);
        jceOutputStream.write(this.dwGroupRankSeq, 7);
        jceOutputStream.write(this.dwCertificationType, 8);
        jceOutputStream.write(this.dwShutupTimestamp, 9);
        jceOutputStream.write(this.dwMyShutupTimestamp, 10);
        jceOutputStream.write(this.dwCmdUinUinFlag, 11);
        jceOutputStream.write(this.dwAdditionalFlag, 12);
        jceOutputStream.write(this.dwGroupTypeFlag, 13);
        jceOutputStream.write(this.dwGroupSecType, 14);
        jceOutputStream.write(this.dwGroupSecTypeInfo, 15);
        jceOutputStream.write(this.dwGroupClassExt, 16);
        jceOutputStream.write(this.dwAppPrivilegeFlag, 17);
        jceOutputStream.write(this.dwSubscriptionUin, 18);
        jceOutputStream.write(this.dwMemberNum, 19);
        jceOutputStream.write(this.dwMemberNumSeq, 20);
        jceOutputStream.write(this.dwMemberCardSeq, 21);
        jceOutputStream.write(this.dwGroupFlagExt3, 22);
        jceOutputStream.write(this.dwGroupOwnerUin, 23);
        jceOutputStream.write(this.cIsConfGroup, 24);
        jceOutputStream.write(this.cIsModifyConfGroupFace, 25);
        jceOutputStream.write(this.cIsModifyConfGroupName, 26);
        jceOutputStream.write(this.dwCmduinJoinTime, 27);
        jceOutputStream.write(this.ulCompanyId, 28);
        jceOutputStream.write(this.dwMaxGroupMemberNum, 29);
        jceOutputStream.write(this.dwCmdUinGroupMask, 30);
        jceOutputStream.write(this.udwHLGuildAppid, 31);
        jceOutputStream.write(this.udwHLGuildSubType, 32);
        jceOutputStream.write(this.udwCmdUinRingtoneID, 33);
        jceOutputStream.write(this.udwCmdUinFlagEx2, 34);
        jceOutputStream.write(this.dwGroupFlagExt4, 35);
        jceOutputStream.write(this.dwAppealDeadline, 36);
        jceOutputStream.write(this.dwGroupFlag, 37);
        if (this.vecGroupRemark != null) {
            jceOutputStream.write(this.vecGroupRemark, 38);
        }
    }

    public void readFrom(JceInputStream jceInputStream) {
        this.GroupUin = jceInputStream.read(this.GroupUin, 0, true);
        this.GroupCode = jceInputStream.read(this.GroupCode, 1, true);
        this.cFlag = jceInputStream.read(this.cFlag, 2, false);
        this.dwGroupInfoSeq = jceInputStream.read(this.dwGroupInfoSeq, 3, false);
        this.strGroupName = jceInputStream.readString(4, false);
        this.strGroupMemo = jceInputStream.readString(5, false);
        this.dwGroupFlagExt = jceInputStream.read(this.dwGroupFlagExt, 6, false);
        this.dwGroupRankSeq = jceInputStream.read(this.dwGroupRankSeq, 7, false);
        this.dwCertificationType = jceInputStream.read(this.dwCertificationType, 8, false);
        this.dwShutupTimestamp = jceInputStream.read(this.dwShutupTimestamp, 9, false);
        this.dwMyShutupTimestamp = jceInputStream.read(this.dwMyShutupTimestamp, 10, false);
        this.dwCmdUinUinFlag = jceInputStream.read(this.dwCmdUinUinFlag, 11, false);
        this.dwAdditionalFlag = jceInputStream.read(this.dwAdditionalFlag, 12, false);
        this.dwGroupTypeFlag = jceInputStream.read(this.dwGroupTypeFlag, 13, false);
        this.dwGroupSecType = jceInputStream.read(this.dwGroupSecType, 14, false);
        this.dwGroupSecTypeInfo = jceInputStream.read(this.dwGroupSecTypeInfo, 15, false);
        this.dwGroupClassExt = jceInputStream.read(this.dwGroupClassExt, 16, false);
        this.dwAppPrivilegeFlag = jceInputStream.read(this.dwAppPrivilegeFlag, 17, false);
        this.dwSubscriptionUin = jceInputStream.read(this.dwSubscriptionUin, 18, false);
        this.dwMemberNum = jceInputStream.read(this.dwMemberNum, 19, false);
        this.dwMemberNumSeq = jceInputStream.read(this.dwMemberNumSeq, 20, false);
        this.dwMemberCardSeq = jceInputStream.read(this.dwMemberCardSeq, 21, false);
        this.dwGroupFlagExt3 = jceInputStream.read(this.dwGroupFlagExt3, 22, false);
        this.dwGroupOwnerUin = jceInputStream.read(this.dwGroupOwnerUin, 23, false);
        this.cIsConfGroup = jceInputStream.read(this.cIsConfGroup, 24, false);
        this.cIsModifyConfGroupFace = jceInputStream.read(this.cIsModifyConfGroupFace, 25, false);
        this.cIsModifyConfGroupName = jceInputStream.read(this.cIsModifyConfGroupName, 26, false);
        this.dwCmduinJoinTime = jceInputStream.read(this.dwCmduinJoinTime, 27, false);
        this.ulCompanyId = jceInputStream.read(this.ulCompanyId, 28, false);
        this.dwMaxGroupMemberNum = jceInputStream.read(this.dwMaxGroupMemberNum, 29, false);
        this.dwCmdUinGroupMask = jceInputStream.read(this.dwCmdUinGroupMask, 30, false);
        this.udwHLGuildAppid = jceInputStream.read(this.udwHLGuildAppid, 31, false);
        this.udwHLGuildSubType = jceInputStream.read(this.udwHLGuildSubType, 32, false);
        this.udwCmdUinRingtoneID = jceInputStream.read(this.udwCmdUinRingtoneID, 33, false);
        this.udwCmdUinFlagEx2 = jceInputStream.read(this.udwCmdUinFlagEx2, 34, false);
        this.dwGroupFlagExt4 = jceInputStream.read(this.dwGroupFlagExt4, 35, false);
        this.dwAppealDeadline = jceInputStream.read(this.dwAppealDeadline, 36, false);
        this.dwGroupFlag = jceInputStream.read(this.dwGroupFlag, 37, false);
        this.vecGroupRemark = jceInputStream.read(cache_vecGroupRemark, 38, false);
    }
}
