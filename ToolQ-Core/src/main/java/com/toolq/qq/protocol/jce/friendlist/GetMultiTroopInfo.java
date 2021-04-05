package com.toolq.qq.protocol.jce.friendlist;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.toolq.qq.protocol.jce.friendlist.info.TroopInfoV2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoluo
 * @date 2020/10/31 9:55
 */
public class GetMultiTroopInfo extends Jce<GetMultiTroopInfo> {
	static ArrayList<TroopInfoV2> cache_troopInfo;

	public byte richInfo = 0;
	public long uin = 0;
	public ArrayList<Long> groupCode = new ArrayList<>();

	public void setRichInfo(byte richInfo) {
		this.richInfo = richInfo;
	}

	public void setUin(long uin) {
		this.uin = uin;
	}

	public void addGroupCode(long groupId) {
		groupCode.add(groupId);
	}

	@Override
	public void writeTo(JceOutputStream out) {
		out.write(uin, 0);
		out.write(groupCode, 1);
		out.write(richInfo, 2);
	}

	public int result = -999;
	public short errorCode;
	public List<TroopInfoV2> troopInfo;

	@Override
	public void readFrom(JceInputStream input) {
		this.result = input.read(result, 1, true);
		this.errorCode = input.read(errorCode, 2, true);
		if (cache_troopInfo == null) {
			cache_troopInfo = new ArrayList<>();
			cache_troopInfo.add(new TroopInfoV2());
		}
		this.troopInfo = (ArrayList) input.readV2(cache_troopInfo, 3, true);
	}
}
