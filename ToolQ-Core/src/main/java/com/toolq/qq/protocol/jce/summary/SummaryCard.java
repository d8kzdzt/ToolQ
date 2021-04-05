package com.toolq.qq.protocol.jce.summary;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.toolq.qq.protocol.jce.friend.VipBaseInfo;

import java.util.ArrayList;

public class SummaryCard {
    public static class Req extends Jce<Req> {
        public byte isFriend;
        public byte reqCommLabel;
        public byte reqExtendCard;
        public byte reqMedalWallInfo;
        public byte reqNearbyGodInfo;
        public int addFriendSource;
        public int comeFrom = 65535;
        public long getControl;
        public long groupCode;
        public long groupUin;
        public long tinyId;
        public long uin;
        public UserLocaleInfo localeInfo;
        public String searchName = "";
        public long likeSource;
        public long qzoneFeedTimestamp;
        public long richCardNameVer;
        public ArrayList<Integer> req0x5ebFieldId;
        public byte[] reqKandianInfo;
        public byte[] reqLastGameInfo;
        public byte[] reqStarInfo;
        public byte[] reqTemplateInfo;
        public byte[] secureSig;
        public byte[] seed;
        public ArrayList<byte[]> reqServices;

        @Override
        public void writeTo(JceOutputStream jceOutputStream) {
            jceOutputStream.write(this.uin, 0);
            jceOutputStream.write(this.comeFrom, 1);
            jceOutputStream.write(this.qzoneFeedTimestamp, 2);
            jceOutputStream.write(this.isFriend, 3);
            jceOutputStream.write(this.groupCode, 4);
            jceOutputStream.write(this.groupUin, 5);
            if (this.seed != null) {
                jceOutputStream.write(this.seed, 6);
            }
            if (this.searchName != null) {
                jceOutputStream.write(this.searchName, 7);
            }
            jceOutputStream.write(this.getControl, 8);
            jceOutputStream.write(this.addFriendSource, 9);
            if (this.secureSig != null) {
                jceOutputStream.write(this.secureSig, 10);
            }
            if (this.reqLastGameInfo != null) {
                jceOutputStream.write(this.reqLastGameInfo, 11);
            }
            if (this.reqTemplateInfo != null) {
                jceOutputStream.write(this.reqTemplateInfo, 12);
            }
            if (this.reqStarInfo != null) {
                jceOutputStream.write(this.reqStarInfo, 13);
            }
            if (this.reqServices != null) {
                jceOutputStream.write(this.reqServices, 14);
            }
            jceOutputStream.write(this.tinyId, 15);
            jceOutputStream.write(this.likeSource, 16);
            if (this.localeInfo != null) {
                jceOutputStream.write(this.localeInfo, 17);
            }
            jceOutputStream.write(this.reqMedalWallInfo, 18);
            if (this.req0x5ebFieldId != null) {
                jceOutputStream.write(this.req0x5ebFieldId, 19);
            }
            jceOutputStream.write(this.reqNearbyGodInfo, 20);
            jceOutputStream.write(this.reqCommLabel, 21);
            jceOutputStream.write(this.reqExtendCard, 22);
            if (this.reqKandianInfo != null) {
                jceOutputStream.write(this.reqKandianInfo, 23);
            }
            jceOutputStream.write(this.richCardNameVer, 24);
        }
    }

    public static class Rsp extends Jce<Rsp> {
        public byte age = 0;
        public byte sex = 0;
        public byte bValid4Vote = 0;
        public int birthday = 0;
        public int level = 0;
        public int photoCount = 0;
        public int voteCount = 0;
        public long cacheControl = 0;
        public long uin = 0;
        public long userFlag = 0;
        public int face = 0;
        public String autoRemark = "";
        public String city = "";
        public String contactName = "";
        public String country = "";
        public String discussName = "";
        public String Email = "";
        public String GroupName = "";
        public String GroupNick = "";
        public String LoginDesc = "";
        public String Mobile = "";
        public String Nick = "";
        public String Personal = "";
        public String Province = "";
        public String QzoneFeedsDesc = "";
        public String Remark = "";
        public String ShowName = "";
        public String Sign = "";
        public String SpaceName = "";
        public String Status = "";
        public long LoginDays;
        public long QQMasterLoginDays = 20;
        public long TemplateId;
        public long lFaceAddonId;
        public long lShowControl;
        public ArrayList<String> addQuestion;
        public VipBaseInfo vipInfo;
        public HeartInfo heartInfo;

        @Override
        public void readFrom(JceInputStream jceInputStream) {
            this.face = jceInputStream.read(face, 0, false);
            this.sex = jceInputStream.read(this.sex, 1, false);
            this.age = jceInputStream.read(this.age, 2, false);
            this.Nick = jceInputStream.readString(3, false);
            this.Remark = jceInputStream.readString(4, false);
            this.level = jceInputStream.read(this.level, 5, false);
            this.Province = jceInputStream.readString(6, false);
            this.city = jceInputStream.readString(7, false);
            this.Sign = jceInputStream.readString(8, false);
            this.GroupName = jceInputStream.readString(9, false);
            this.GroupNick = jceInputStream.readString(10, false);
            this.Mobile = jceInputStream.readString(11, false);
            this.contactName = jceInputStream.readString(12, false);
            this.lShowControl = jceInputStream.read(this.lShowControl, 13, false);
            this.QzoneFeedsDesc = jceInputStream.readString(14, false);
            this.voteCount = jceInputStream.read(this.voteCount, 16, false);
            this.bValid4Vote = jceInputStream.read(this.bValid4Vote, 18, false);
            this.country = jceInputStream.readString(19, false);
            this.Status = jceInputStream.readString(20, false);
            this.autoRemark = jceInputStream.readString(21, false);
            this.cacheControl = jceInputStream.read(this.cacheControl, 22, false);
            this.uin = jceInputStream.read(this.uin, 23, false);
            this.photoCount = jceInputStream.read(this.photoCount, 24, false);
            this.addQuestion = (ArrayList<String>) jceInputStream.readV2(new ArrayList(), 26, false);
            this.discussName = jceInputStream.readString(28, false);
            this.vipInfo = (VipBaseInfo) jceInputStream.readV2(new VipBaseInfo(), 29, false);
            this.ShowName = jceInputStream.readString(30, false);
            this.userFlag = jceInputStream.read(this.userFlag, 35, false);
            this.LoginDays = jceInputStream.read(this.LoginDays, 36, false);
            this.LoginDesc = jceInputStream.readString(37, false);
            this.TemplateId = jceInputStream.read(this.TemplateId, 38, false);
            this.QQMasterLoginDays = jceInputStream.read(this.QQMasterLoginDays, 39, false);
            this.lFaceAddonId = jceInputStream.read(this.lFaceAddonId, 40, false);
            this.SpaceName = jceInputStream.readString(47, false);
            this.heartInfo = (HeartInfo) jceInputStream.readV2(new HeartInfo(), 90, false);
        }
    }
}
