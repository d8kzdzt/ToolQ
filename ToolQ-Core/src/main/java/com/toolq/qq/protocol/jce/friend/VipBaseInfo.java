package com.toolq.qq.protocol.jce.friend;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceStruct;

import java.util.HashMap;
import java.util.Map;

public class VipBaseInfo extends JceStruct {
    static final boolean assertionsDisabled;
    static Map<Integer, VipOpenInfo> cache_mOpenInfo = new HashMap();
    public int iGrayNameplateFlag;
    public int iNameplateVipType;
    public Map<Integer, VipOpenInfo> mOpenInfo;
    
    static {
        boolean z = false;
        if (!VipBaseInfo.class.desiredAssertionStatus()) {
            z = true;
        }
        assertionsDisabled = z;
        cache_mOpenInfo.put(0, new VipOpenInfo());
    }
    
    @Override
    public void readFrom(JceInputStream jceInputStream) {
        this.mOpenInfo = (Map) jceInputStream.readV2(cache_mOpenInfo, 0, true);
        this.iNameplateVipType = jceInputStream.read(this.iNameplateVipType, 1, false);
        this.iGrayNameplateFlag = jceInputStream.read(this.iGrayNameplateFlag, 2, false);
    }
    
    /**
     * mOpenInfo={
     *  1=VipOpenInfo{ QQ会员
     *      bOpen=true,
     *      iVipFlag=0,
     *      iVipLevel=7,
     *      iVipType=0,
     *      lNameplateId=0
     *  },
     *  2=VipOpenInfo{
     *      bOpen=false,
     *      iVipFlag=0,
     *      iVipLevel=0,
     *      iVipType=0,
     *      lNameplateId=0
     *  },
     *  3=VipOpenInfo{ 超级VIP
     *      bOpen=true,
     *      iVipFlag=0,
     *      iVipLevel=7,
     *      iVipType=0,
     *      lNameplateId=0
     *  },
     *  7=VipOpenInfo{ 大会员
     *      bOpen=true,
     *      iVipFlag=0,
     *      iVipLevel=4,
     *      iVipType=0,
     *      lNameplateId=0
     *  }
     * }
     */
}
