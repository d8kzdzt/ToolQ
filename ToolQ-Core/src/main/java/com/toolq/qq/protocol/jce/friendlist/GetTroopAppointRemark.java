package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.toolq.qq.protocol.jce.friendlist.info.stTroopRemarkInfo;

import java.util.ArrayList;

public class GetTroopAppointRemark {
    public static final class Req extends Jce<Req> {
        public long GroupCode;
        public long GroupUin;
        public byte cRichCardNameVer;
        public byte cRichInfo;
        public long uin;
        public ArrayList<Long> vecUinList;

        public void writeTo(JceOutputStream out) {
            out.write(this.uin, 0);
            out.write(this.GroupCode, 1);
            out.write(this.GroupUin, 3);
            out.write(this.vecUinList, 4);
            out.write(this.cRichInfo, 5);
            out.write(this.cRichCardNameVer, 6);
        }
    }

    public static final class Resp extends Jce<Resp> {
        static int cache_result;
        static ArrayList<stTroopRemarkInfo> cache_vecTroopRemark;
        public long GroupCode;
        public long GroupUin;
        public short errorCode;
        public long office_mode;
        public int result;
        public long uin;
        public ArrayList<stTroopRemarkInfo> vecTroopRemark;

        public void readFrom(JceInputStream jceInputStream) {
            this.uin = jceInputStream.read(this.uin, 0, true);
            this.GroupCode = jceInputStream.read(this.GroupCode, 1, true);
            this.GroupUin = jceInputStream.read(this.GroupUin, 2, true);
            if (cache_vecTroopRemark == null) {
                cache_vecTroopRemark = new ArrayList<>();
                cache_vecTroopRemark.add(new stTroopRemarkInfo());
            }
            this.vecTroopRemark = (ArrayList) jceInputStream.readV2(cache_vecTroopRemark, 3, true);
            this.result = jceInputStream.read(this.result, 4, true);
            this.errorCode = jceInputStream.read(this.errorCode, 5, false);
            this.office_mode = jceInputStream.read(this.office_mode, 6, false);
        }

        @Override
        public String toString() {
            return "Resp{" +
                    "GroupCode=" + GroupCode +
                    ", GroupUin=" + GroupUin +
                    ", errorCode=" + errorCode +
                    ", office_mode=" + office_mode +
                    ", result=" + result +
                    ", uin=" + uin +
                    ", vecTroopRemark=" + vecTroopRemark +
                    '}';
        }
    }
}
