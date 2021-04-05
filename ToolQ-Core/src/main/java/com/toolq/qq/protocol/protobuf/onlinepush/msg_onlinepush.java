package com.toolq.qq.protocol.protobuf.onlinepush;

import com.qq.pb.*;
import com.toolq.qq.protocol.protobuf.message.msg_comm;

public final class msg_onlinepush {
    public static final class PbPushMsg extends MessageMicro<PbPushMsg> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 26, 32, 72, 80}, new String[]{"msg", "svrip", "bytes_push_token", "ping_flag", "uint32_general_flag", "uint64_bind_uin"}, new Object[]{null, 0, ByteStringMicro.EMPTY, 0, 0, 0L}, PbPushMsg.class);

        public final PBBytesField bytes_push_token = PBField.initBytes(ByteStringMicro.EMPTY);
        public msg_comm.Msg msg = new msg_comm.Msg();

        public final PBUInt32Field ping_flag = PBField.initUInt32(0);
        public final PBInt32Field svrip = PBField.initInt32(0);
        public final PBUInt32Field uint32_general_flag = PBField.initUInt32(0);
        public final PBUInt64Field uint64_bind_uin = PBField.initUInt64(0);
    }
}
