package com.toolq.qq.protocol.jce.friend;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceStruct;

public class GroupInfo extends JceStruct {
    public int friend_count;
    public byte groupId;
    public String groupname = "";
    public int online_friend_count;
    public byte seqid;
    public int sqqOnLine_count;

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.groupId = jceInputStream.read(this.groupId, 0, true);
        this.groupname = jceInputStream.readString(1, true);
        this.friend_count = jceInputStream.read(this.friend_count, 2, true);
        this.online_friend_count = jceInputStream.read(this.online_friend_count, 3, true);
        this.seqid = jceInputStream.read(this.seqid, 4, false);
        this.sqqOnLine_count = jceInputStream.read(this.sqqOnLine_count, 5, false);
    }
}
