package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.*;
import com.qq.taf.jce.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GetUserAddFriendSetting {
    public static final class Req extends Jce<Req> {
        public byte[] name;
        public byte[] name1;
        public long queryUin;
        public int sourceID = 3999;
        public int sourceSubID = 0;
        public long uin;
        public long version;

        public void writeTo(JceOutputStream out) {
            out.write(this.uin, 0);
            out.write(this.queryUin, 1);
            out.write(this.sourceID, 2);
            out.write(this.sourceSubID, 3);
            if (this.name != null) {
                out.write(this.name, 4);
            }
            out.write(this.version, 5);
            if (this.name1 != null) {
                out.write(this.name1, 6);
            }
        }
    }

    public static final class Resp extends Jce<Resp> {
        static byte[] cache_name;
        static byte[] cache_name1;
        static ArrayList<String> cache_vecStrUserQuestion;
        public boolean contact_bothway_friend;
        public short errorCode;
        public byte[] name;
        public byte[] name1;
        public long queryUin;
        public int queryUinSetting;
        public int result;
        public long uin;
        public ArrayList<String> vecStrUserQuestion;

        public void readFrom(JceInputStream input) {
            this.uin = input.read(this.uin, 0, true);
            this.queryUin = input.read(this.queryUin, 1, true);
            this.queryUinSetting = input.read(this.queryUinSetting, 2, true);
            if (cache_vecStrUserQuestion == null) {
                cache_vecStrUserQuestion = new ArrayList<>();
                cache_vecStrUserQuestion.add("");
            }
            this.vecStrUserQuestion = (ArrayList) input.readV2(cache_vecStrUserQuestion, 3, false);
            this.result = input.read(this.result, 4, true);
            this.errorCode = input.read(this.errorCode, 5, false);
            if (cache_name == null) {
                cache_name = new byte[1];
            }
            this.name = input.read(cache_name, 6, false);
            this.contact_bothway_friend = input.read(this.contact_bothway_friend, 7, false);
            if (cache_name1 == null) {
                cache_name1 = new byte[1];
            }
            this.name1 = input.read(cache_name1, 8, false);
        }
    }
}
