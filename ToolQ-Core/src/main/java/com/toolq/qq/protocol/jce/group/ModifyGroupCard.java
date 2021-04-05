package com.toolq.qq.protocol.jce.group;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public class ModifyGroupCard {
    public static final class Req extends Jce<Req> {
        public long groupCode;
        public long newSeq;
        public long zero;
        public ArrayList<UinInfo> uinInfo;

        @Override
        public void writeTo(JceOutputStream jceOutputStream) {
            jceOutputStream.write(this.zero, 0);
            jceOutputStream.write(this.groupCode, 1);
            jceOutputStream.write(this.newSeq, 2);
            jceOutputStream.write(this.uinInfo, 3);
        }
    }

    public static final class Resp extends Jce<Resp> {
        static ArrayList<Long> cache_vecUin = new ArrayList<>();
        public String ErrorString = "";
        public long groupCode;
        public long groupUin;
        public int result;
        public ArrayList<Long> uins;

        static {
            cache_vecUin.add(0L);
        }

        @Override
        public void readFrom(JceInputStream jceInputStream) {
            this.result = jceInputStream.read(this.result, 0, true);
            this.groupUin = jceInputStream.read(this.groupUin, 1, true);
            this.groupCode = jceInputStream.read(this.groupCode, 2, true);
            this.uins = (ArrayList) jceInputStream.readV2(cache_vecUin, 3, true);
            this.ErrorString = jceInputStream.readString(4, false);
        }
    }
}
