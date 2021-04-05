package com.toolq.qq.protocol.jce.group;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;

import java.util.HashMap;
import java.util.Map;

public class QzoneUserInfo extends Jce<QzoneUserInfo> {
    static Map<String, String> cache_extendInfo = new HashMap<>();
    public int eStarState;
    public Map<String, String> extendInfo;

    static {
        cache_extendInfo.put("", "");
    }

    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.eStarState = jceInputStream.read(this.eStarState, 0, false);
        this.extendInfo = (Map) jceInputStream.readV2(cache_extendInfo, 1, false);
    }
}
