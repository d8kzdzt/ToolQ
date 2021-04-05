package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.*;

import java.util.Arrays;

public class stTroopRemarkInfo extends Jce<stTroopRemarkInfo> {
    static byte[] cache_vecGroupHonor = new byte[1];
    public long GlamourLevel;
    public long MemberUin;
    public long TorchbearerFlag;
    public String bytes_job = "";
    public byte cGender;
    public byte cRichCardNameVer;
    public String sEmail = "";
    public String sMemo = "";
    public String sName = "";
    public String sPhone = "";
    public String strAutoRemark = "";
    public String strNick = "";
    public String strRank = "";
    public String strRemark = "";
    public byte[] vecGroupHonor;

    public void writeTo(JceOutputStream out) {
        out.write(this.MemberUin, 0);
        out.write(this.strNick, 1);
        out.write(this.strRemark, 2);
        if (this.sName != null) {
            out.write(this.sName, 3);
        }
        out.write(this.cGender, 4);
        if (this.sPhone != null) {
            out.write(this.sPhone, 5);
        }
        if (this.sEmail != null) {
            out.write(this.sEmail, 6);
        }
        if (this.sMemo != null) {
            out.write(this.sMemo, 7);
        }
        if (this.strAutoRemark != null) {
            out.write(this.strAutoRemark, 8);
        }
        if (this.strRank != null) {
            out.write(this.strRank, 9);
        }
        if (this.bytes_job != null) {
            out.write(this.bytes_job, 10);
        }
        out.write(this.GlamourLevel, 11);
        out.write(this.TorchbearerFlag, 12);
        out.write(this.cRichCardNameVer, 13);
        if (this.vecGroupHonor != null) {
            out.write(this.vecGroupHonor, 14);
        }
    }

    public void readFrom(JceInputStream inputStream) {
        this.MemberUin = inputStream.read(this.MemberUin, 0, true);
        this.strNick = inputStream.readString(1, true);
        this.strRemark = inputStream.readString(2, true);
        this.sName = inputStream.readString(3, false);
        this.cGender = inputStream.read(this.cGender, 4, false);
        this.sPhone = inputStream.readString(5, false);
        this.sEmail = inputStream.readString(6, false);
        this.sMemo = inputStream.readString(7, false);
        this.strAutoRemark = inputStream.readString(8, false);
        this.strRank = inputStream.readString(9, false);
        this.bytes_job = inputStream.readString(10, false);
        this.GlamourLevel = inputStream.read(this.GlamourLevel, 11, false);
        this.TorchbearerFlag = inputStream.read(this.TorchbearerFlag, 12, false);
        this.cRichCardNameVer = inputStream.read(this.cRichCardNameVer, 13, false);
        this.vecGroupHonor = inputStream.read(cache_vecGroupHonor, 14, false);
    }

    @Override
    public String toString() {
        return "stTroopRemarkInfo{" +
                "GlamourLevel=" + GlamourLevel +
                ", MemberUin=" + MemberUin +
                ", TorchbearerFlag=" + TorchbearerFlag +
                ", bytes_job='" + bytes_job + '\'' +
                ", cGender=" + cGender +
                ", cRichCardNameVer=" + cRichCardNameVer +
                ", sEmail='" + sEmail + '\'' +
                ", sMemo='" + sMemo + '\'' +
                ", sName='" + sName + '\'' +
                ", sPhone='" + sPhone + '\'' +
                ", strAutoRemark='" + strAutoRemark + '\'' +
                ", strNick='" + strNick + '\'' +
                ", strRank='" + strRank + '\'' +
                ", strRemark='" + strRemark + '\'' +
                ", vecGroupHonor=" + Arrays.toString(vecGroupHonor) +
                '}';
    }
}
