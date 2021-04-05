package com.toolq.qq.protocol.device;

import com.toolq.ToolQ;
import com.toolq.helper.android.Android;
import com.toolq.helper.bot.packet.Packet;
import com.toolq.helper.bot.packet.Waiter;
import com.toolq.helper.logger.TLog;
import com.toolq.helper.packet.ByteReader;
import com.toolq.qq.ecdh.ECDHCrypt;
import com.toolq.qq.protocol.packet.WtLogin;
import com.toolq.utils.TeaUtil;

import java.util.HashMap;

/**
 * 设备锁操作相关接口
 */
public abstract class IDeviceLock {
    protected Exception exception;

    /**
     * 提交短信验证码
     *
     * @param bot 机器人QQ
     * @param phone 手机号
     * @param msg 提示
     * @return 返回短信验证码
     */
    public abstract String GetSMSCode(ToolQ toolQ, long bot, String phone, String msg, byte[] dt104, byte[] dt174);

    /**
     * 向腾讯服务器提交获取验证码请求
     */
    protected boolean submitAVerificationCodeRequest(ToolQ toolQ, byte[] dt104, byte[] dt174) {
        final int seq = toolQ.getRecorder().nextSsoSeq();
        byte[] data = WtLogin.GetSMSCode(seq, toolQ, dt104, dt174);
        Waiter waiter = toolQ.getListener().addWaiter(new Waiter("wtlogin.login") {
            @Override
            public boolean check(Packet packet) {
                return packet.getSsoSeq() == seq;
            }
        });
        try {
            toolQ.getSocketClient().getClientOutPut().send(toolQ.getThreadManager(), data);
            if(waiter.wait("Keep holy relics in Jiangshan, I will come back in generations!", 5 * 1000) ) {
                Packet packet = waiter.getPacket();
                ByteReader reader = packet.toByteReader();
                reader = reader.readReader( reader.dis(1).readShort() - 4 );
                if(reader.readShort() == 8001 && (reader.readShort() == 0x810) && reader.readShort() == 1 && reader.readULong() == toolQ.getAccount().getUser()) {
                    int loginResult = reader.dis(2).readByte() & 0xff;
                    byte[] wtloginKey = loginResult == 180 ? Android.getRandKey() : ECDHCrypt.getInstance().get_g_share_key();
                    byte[] decrypt = TeaUtil.decrypt(reader.readRestBytes(), wtloginKey);
                    HashMap<Integer, byte[]> tlvMap = parseTlv(decrypt);
                    switch (loginResult) {
                        case 161 :
                        case 160 :
                        case 162 : {
                            return true;
                        }
                        case 163 : {
                            return false;
                        }
                        default:
                            throw new RuntimeException(String.format("Can't parse loginResult[%s] by submitAVerificationCodeRequest", loginResult));
                    }
                }
            }
        } catch (Exception e) {
            this.exception = e;
            TLog.INSTANCE.warn("submitAVerificationCodeRequest", e);
        }
        return false;
    }

    private HashMap<Integer, byte[]> parseTlv(byte[] data) {
        HashMap<Integer, byte[]> tlvMap = new HashMap<>();
        ByteReader reader = new ByteReader(data);
        reader.readShort();
        reader.dis(1);
        int tlvSize = reader.readShort();
        for(int i = 0;i < tlvSize;i++) {
            int ver = reader.readShort();
            byte[] body = reader.readBytes(reader.readShort());
            // TLog.INSTANCE.info(IDeviceLock.class, "ParseTlv-SMS ==> " + Integer.toHexString(ver) + "||" + new String(body));
            tlvMap.put(ver,  body);
        }
        return tlvMap;
    }
}