package com.toolq.qq.protocol.jce.kqq;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.Arrays;

public class GroupMng {
    public static final class Req extends Jce<Req> {
        public byte cCheckInGroup;
        public byte cIfGetAuthInfo;
        public byte cIsSupportAuthQuestionJoin;
        public byte cStatOption;
        public long dwDiscussUin;
        public long dwJoinVerifyType;
        public int reqtype;
        public String sGroupLocation = "";
        public String sJoinGroupAuth = "";
        public String sJoinGroupKey = "";
        public String sJoinGroupPicUrl = "";
        public String sJoinGroupVerifyToken = "";
        public long uin;
        public byte[] vecBody;
        public byte[] vecJoinGroupRichMsg;
        public int wSourceID;
        public int wSourceSubID;

        public void writeTo(JceOutputStream out) {
            out.write(this.reqtype, 0);
            out.write(this.uin, 1);
            out.write(this.vecBody, 2);
            out.write(this.cCheckInGroup, 3);
            if (this.sGroupLocation != null) {
                out.write(this.sGroupLocation, 4);
            }
            out.write(this.cStatOption, 5);
            out.write(this.wSourceID, 6);
            out.write(this.wSourceSubID, 7);
            out.write(this.cIsSupportAuthQuestionJoin, 8);
            out.write(this.cIfGetAuthInfo, 9);
            out.write(this.dwDiscussUin, 10);
            if (this.sJoinGroupKey != null) {
                out.write(this.sJoinGroupKey, 11);
            }
            if (this.sJoinGroupPicUrl != null) {
                out.write(this.sJoinGroupPicUrl, 12);
            }
            if (this.vecJoinGroupRichMsg != null) {
                out.write(this.vecJoinGroupRichMsg, 13);
            }
            if (this.sJoinGroupAuth != null) {
                out.write(this.sJoinGroupAuth, 14);
            }
            if (this.sJoinGroupVerifyToken != null) {
                out.write(this.sJoinGroupVerifyToken, 15);
            }
            out.write(this.dwJoinVerifyType, 16);
        }
    }

    public static final class Resp extends Jce<Resp> {
        static byte[] cache_vecBody = new byte[1];
        static byte[] cache_vecJoinPrompt = new byte[1];
        public String ErrorString = "";
        public byte cIsInGroup;
        public byte cIsMemInvite;
        public long dwDis2GrpLimitType;
        public short errorCode;
        public int reqtype;
        public byte result;
        public String sAuthGrpInfo = "";
        public String sGroupLocation = "";
        public String sJoinAnswer = "";
        public String sJoinQuestion = "";
        public byte[] vecBody;
        public byte[] vecJoinPrompt;

        public void readFrom(JceInputStream input) {
            this.reqtype = input.read(this.reqtype, 0, true);
            this.result = input.read(this.result, 1, true);
            this.vecBody = input.read(cache_vecBody, 2, true);
            this.ErrorString = input.readString(3, true);
            this.errorCode = input.read(this.errorCode, 4, false);
            this.cIsInGroup = input.read(this.cIsInGroup, 5, false);
            this.sGroupLocation = input.readString(6, false);
            this.cIsMemInvite = input.read(this.cIsMemInvite, 7, false);
            this.sAuthGrpInfo = input.readString(8, false);
            this.sJoinQuestion = input.readString(9, false);
            this.sJoinAnswer = input.readString(10, false);
            this.dwDis2GrpLimitType = input.read(this.dwDis2GrpLimitType, 11, false);
            this.vecJoinPrompt = input.read(cache_vecJoinPrompt, 12, false);
        }

        @Override
        public String toString() {
            return "Resp{" +
                    "ErrorString='" + ErrorString + '\'' +
                    ", cIsInGroup=" + cIsInGroup +
                    ", cIsMemInvite=" + cIsMemInvite +
                    ", dwDis2GrpLimitType=" + dwDis2GrpLimitType +
                    ", errorCode=" + errorCode +
                    ", reqtype=" + reqtype +
                    ", result=" + result +
                    ", sAuthGrpInfo='" + sAuthGrpInfo + '\'' +
                    ", sGroupLocation='" + sGroupLocation + '\'' +
                    ", sJoinAnswer='" + sJoinAnswer + '\'' +
                    ", sJoinQuestion='" + sJoinQuestion + '\'' +
                    ", vecBody=" + Arrays.toString(vecBody) +
                    ", vecJoinPrompt=" + Arrays.toString(vecJoinPrompt) +
                    '}';
        }
    }
}
