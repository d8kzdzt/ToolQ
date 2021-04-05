package com.toolq.qq.protocol.jce.group;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

import java.util.ArrayList;

public class GetTroopMemberList {
    public static final class Req extends Jce<Req> {
        public long GetListAppointTime;
        public long GroupCode;
        public long GroupUin;
        public long NextUin;
        public long ReqType;
        public long Version;
        public byte cRichCardNameVer;
        public long uin;

        @Override
        public void writeTo(JceOutputStream jceOutputStream) {
            jceOutputStream.write(this.uin, 0);
            jceOutputStream.write(this.GroupCode, 1);
            jceOutputStream.write(this.NextUin, 2);
            jceOutputStream.write(this.GroupUin, 3);
            jceOutputStream.write(this.Version, 4);
            jceOutputStream.write(this.ReqType, 5);
            jceOutputStream.write(this.GetListAppointTime, 6);
            jceOutputStream.write(this.cRichCardNameVer, 7);
        }
    }

    public static final class Resp extends Jce<Resp> {
        static ArrayList<TroopMemberInfo> cache_vecTroopMember = new ArrayList<>();
        public long GroupCode;
        public long GroupUin;
        public long NextGetTime;
        public long NextUin;
        public short errorCode;
        public long office_mode;
        public int result;
        public long uin;
        public ArrayList<TroopMemberInfo> vecTroopMember;

        static {
            cache_vecTroopMember.add(new TroopMemberInfo());
        }

        @Override
        public void readFrom(JceInputStream jceInputStream) {
            this.uin = jceInputStream.read(this.uin, 0, true);
            this.GroupCode = jceInputStream.read(this.GroupCode, 1, true);
            this.GroupUin = jceInputStream.read(this.GroupUin, 2, true);
            this.vecTroopMember = (ArrayList) jceInputStream.readV2(cache_vecTroopMember, 3, true);
            this.NextUin = jceInputStream.read(this.NextUin, 4, true);
            this.result = jceInputStream.read(this.result, 5, true);
            this.errorCode = jceInputStream.read(this.errorCode, 6, false);
            this.office_mode = jceInputStream.read(this.office_mode, 7, false);
            this.NextGetTime = jceInputStream.read(this.NextGetTime, 8, false);
        }
    }
}
