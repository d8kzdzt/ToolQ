package com.toolq.qq.protocol.jce.friendlist.info;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;

/**
 * @author luoluo
 * @date 2020/10/31 11:30
 */
public class TroopInfoV2 extends Jce<TroopInfoV2> {
	public byte groupOption;
	public long certificationType;
	public long groupClassExt;
	public long groupCode;
	public long groupFlagExt;
	public long groupOwnerUin;
	public long groupUin;
	public int memberNum;
	public String fingerMemo = "";
	public String groupMemo = "";
	public String groupName = "";
	public int groupFace;

	@Override
	public void writeTo(JceOutputStream out) {
		out.write(this.groupUin, 0);
		out.write(this.groupCode, 1);
		out.write(this.groupName, 2);
		out.write(this.groupMemo, 3);
		out.write(this.groupOwnerUin, 4);
		out.write(this.groupClassExt, 5);
		out.write(this.groupFace, 6);
		if (this.fingerMemo != null) {
			out.write(this.fingerMemo, 7);
		}
		out.write(this.groupOption, 8);
		out.write(this.memberNum, 9);
		out.write(this.groupFlagExt, 10);
		out.write(this.certificationType, 11);
	}

	@Override
	public void readFrom(JceInputStream input) {
		this.groupUin = input.read(this.groupUin, 0, true);
		this.groupCode = input.read(this.groupCode, 1, true);
		this.groupName = input.readString(2, true);
		this.groupMemo = input.readString(3, true);
		this.groupOwnerUin = input.read(this.groupOwnerUin, 4, false);
		this.groupClassExt = input.read(this.groupClassExt, 5, false);
		this.groupFace = input.read(this.groupFace, 6, false);
		this.fingerMemo = input.readString(7, false);
		this.groupOption = input.read(this.groupOption, 8, false);
		this.memberNum = input.read(this.memberNum, 9, false);
		this.groupFlagExt = input.read(this.groupFlagExt, 10, false);
		this.certificationType = input.read(this.certificationType, 11, false);
	}
}
