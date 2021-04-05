package com.toolq.qq.protocol.jce.favorite;

import com.qq.taf.Jce;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

public class Favorite {
    
    public final static class Req extends Jce<Req> {
        public int cOpType = 0;
        public int emSource = 65535;
        public int iCount = 1;
        public long lMID;
        public ReqHead stHeader;
        
        @Override
        public void writeTo(JceOutputStream jceOutputStream) {
            jceOutputStream.write(this.stHeader, 0);
            jceOutputStream.write(this.lMID, 1);
            jceOutputStream.write(this.cOpType, 2);
            jceOutputStream.write(this.emSource, 3);
            jceOutputStream.write(this.iCount, 4);
        }
    }
    
    
    public final static class Resp extends Jce<Resp> {
        static final RespHead cache_stHeader = new RespHead();
        static byte[] cache_vNotice;
        public byte cOpType;
        public long lMID;
        public RespHead stHeader;
        public byte[] vNotice;
        
        @Override
        public void readFrom(JceInputStream jceInputStream) {
            this.stHeader = (RespHead) jceInputStream.read(cache_stHeader, 0, true);
            this.lMID = jceInputStream.read(this.lMID, 1, true);
            this.cOpType = jceInputStream.read(this.cOpType, 2, true);
            if (cache_vNotice == null) {
                cache_vNotice = new byte[1];
            }
            this.vNotice = jceInputStream.read(cache_vNotice, 3, false);
        }
    }
    
}
