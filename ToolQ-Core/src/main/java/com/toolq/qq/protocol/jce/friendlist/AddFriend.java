package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.*;

import java.util.Arrays;

public class AddFriend {
    public static final class Req extends Jce<Req> {
        public long addUin;
        public int addUinSetting;
        public byte autoSend = 1;
        public boolean contactBothWayFriend;
        public byte[] friend_src_desc;
        public String msg = "";
        public byte msgLen;
        public byte myAllowFlag;
        public byte myFriendGroupId;
        public byte[] name;
        public byte[] name1;
        public byte[] remark;
        public byte showMyCard;
        public byte[] sig;
        public int sourceID = 3999;
        public int sourceSubID = 0;
        public byte srcFlag;
        public byte[] src_description;
        public byte[] token;
        public long uin;
        public byte[] verify;

        public void writeTo(JceOutputStream out) {
            out.write(this.uin, 0);
            out.write(this.addUin, 1);
            out.write(this.addUinSetting, 2);
            out.write(this.myAllowFlag, 3);
            out.write(this.myFriendGroupId, 4);
            out.write(this.msgLen, 5);
            if (this.msg != null) {
                out.write(this.msg, 6);
            }
            out.write(this.srcFlag, 7);
            out.write(this.autoSend, 8);
            if (this.sig != null) {
                out.write(this.sig, 9);
            }
            out.write(this.sourceID, 10);
            out.write(this.sourceSubID, 11);
            if (this.name != null) {
                out.write(this.name, 12);
            }
            if (this.src_description != null) {
                out.write(this.src_description, 13);
            }
            if (this.friend_src_desc != null) {
                out.write(this.friend_src_desc, 14);
            }
            out.write(this.contactBothWayFriend, 15);
            if (this.remark != null) {
                out.write(this.remark, 16);
            }
            if (this.name1 != null) {
                out.write(this.name1, 17);
            }
            out.write(this.showMyCard, 18);
            if (this.token != null) {
                out.write(this.token, 19);
            }
            if (this.verify != null) {
                out.write(this.verify, 20);
            }
            out.writeByte((byte)1, 22);
        }
    }

    public static final class Resp extends Jce<Resp> {
        static byte[] cache_name = new byte[1];
        static byte[] cache_name1 = new byte[1];
        static byte[] cache_sig = new byte[1];
        static byte[] cache_verify = new byte[1];
        public String ErrorString = "";
        public long addUin;
        public int addUinSetting;
        public short errorCode;
        public byte myAllowFlag;
        public byte myFriendGroupId;
        public byte[] name;
        public byte[] name1;
        public int result;
        public byte[] sig;
        public long uin;
        public byte[] verify;

        public void readFrom(JceInputStream input) {
            this.uin = input.read(this.uin, 0, true);
            this.addUin = input.read(this.addUin, 1, true);
            this.addUinSetting = input.read(this.addUinSetting, 2, true);
            this.myAllowFlag = input.read(this.myAllowFlag, 3, true);
            this.myFriendGroupId = input.read(this.myFriendGroupId, 4, true);
            this.result = input.read(this.result, 6, true);
            this.errorCode = input.read(this.errorCode, 7, false);
            this.ErrorString = input.readString(8, false);
            this.sig = input.read(cache_sig, 9, false);
            this.name = input.read(cache_name, 10, false);
            this.name1 = input.read(cache_name1, 11, false);
            this.verify = input.read(cache_verify, 12, false);
        }

        @Override
        public String toString() {
            return "Resp{" +
                    "ErrorString='" + ErrorString + '\'' +
                    ", addUin=" + addUin +
                    ", addUinSetting=" + addUinSetting +
                    ", errorCode=" + errorCode +
                    ", myAllowFlag=" + myAllowFlag +
                    ", myFriendGroupId=" + myFriendGroupId +
                    ", name=" + Arrays.toString(name) +
                    ", name1=" + Arrays.toString(name1) +
                    ", result=" + result +
                    ", sig=" + Arrays.toString(sig) +
                    ", uin=" + uin +
                    ", verify=" + Arrays.toString(verify) +
                    '}';
        }
    }
}
