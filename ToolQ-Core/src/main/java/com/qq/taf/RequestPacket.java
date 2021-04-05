package com.qq.taf;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoluo
 */
public class RequestPacket extends JceStruct {
    public short iVersion = 3;
    public byte cPacketType = 0;
    public int iMessageType = 0;
    public int iRequestId = 0;
    public String sServantName = null;
    public String sFuncName = null;
    public byte[] sBuffer;
    public int iTimeout = 0;
    public Map<String, String> context;
    public Map<String, String> status;

    public RequestPacket() {}

    public RequestPacket(short iVersion, byte cPacketType, int iMessageType, int iRequestId, String sServantName, String sFuncName, byte[] sBuffer, int iTimeout, Map<String, String> context, Map<String, String> status) {
        this.iVersion = iVersion;
        this.cPacketType = cPacketType;
        this.iMessageType = iMessageType;
        this.iRequestId = iRequestId;
        this.sServantName = sServantName;
        this.sFuncName = sFuncName;
        this.sBuffer = sBuffer;
        this.iTimeout = iTimeout;
        this.context = context;
        this.status = status;
    }

    @Override
    public void writeTo(JceOutputStream _os) {
        _os.write(this.iVersion, 1);
        _os.write(this.cPacketType, 2);
        _os.write(this.iMessageType, 3);
        _os.write(this.iRequestId, 4);
        _os.write(this.sServantName, 5);
        _os.write(this.sFuncName, 6);
        _os.write(this.sBuffer, 7);
        _os.write(this.iTimeout, 8);
        _os.write(this.context, 9);
        _os.write(this.status, 10);
    }

    static byte[] cache_sBuffer = null;
    static Map<String, String> cache_context = null;

    public JceInputStream sBuffToJceInput(){
        return new JceInputStream(sBuffer);
    }

    @Override
    public void readFrom(JceInputStream _is) {
        try {
            this.iVersion = _is.read(this.iVersion, 1, true);
            this.cPacketType = _is.read(this.cPacketType, 2, true);
            this.iMessageType = _is.read(this.iMessageType, 3, true);
            this.iRequestId = _is.read(this.iRequestId, 4, true);
            this.sServantName = _is.readString(5, true);
            this.sFuncName = _is.readString(6, true);
            if (cache_sBuffer == null) {
                cache_sBuffer = new byte[1];
            }
            this.sBuffer = _is.read(cache_sBuffer, 7, true);
            this.iTimeout = _is.read(this.iTimeout, 8, true);
            if (cache_context == null) {
                cache_context = new HashMap();
                cache_context.put("", "");
            }
            this.context = ((Map)_is.readV2(cache_context, 9, true));
            if (cache_context == null) {
                cache_context = new HashMap();
                cache_context.put("", "");
            }
            this.status = ((Map)_is.readV2(cache_context, 10, true));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
