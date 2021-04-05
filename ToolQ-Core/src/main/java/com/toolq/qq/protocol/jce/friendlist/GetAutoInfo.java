package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

public class GetAutoInfo {
    public static final class Req extends Jce<Req> {
        public byte cType = 1;
        public long dwFriendUin;
        public int sourceID = 3999;
        public int sourceSubID = 0;
        public long uin;

        public void writeTo(JceOutputStream out) {
            out.write(this.uin, 0);
            out.write(this.dwFriendUin, 1);
            out.write(this.cType, 2);
            out.write(this.sourceID, 3);
            out.write(this.sourceSubID, 4);
        }
    }

    public static final class Resp extends Jce<Resp> {
        public byte cGroupID;
        public short errorCode;
        public int result;
        public String strGroupName = "";
        public String strRemark = "";

        public void readFrom(JceInputStream input) {
            this.result = input.read(this.result, 0, true);
            this.errorCode = input.read(this.errorCode, 1, true);
            this.strRemark = input.readString(2, true);
            this.cGroupID = input.read(this.cGroupID, 3, true);
            this.strGroupName = input.readString(4, true);
        }
    }
}
