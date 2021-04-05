package com.toolq.qq.protocol.protobuf;

import com.qq.pb.*;

public final class SubMsgType0x1a {
    public static final class MsgBody extends MessageMicro<MsgBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24, 32, 40, 50, 56, 64, 72, 80}, new String[]{"bytes_file_key", "uint32_from_uin", "uint32_to_uin", "uint32_status", "uint32_ttl", "string_desc", "uint32_type", "uint32_capture_times", "uint64_from_uin", "uint64_to_uin"}, new Object[]{ByteStringMicro.EMPTY, 0, 0, 0, 0, "", 0, 0, 0L, 0L}, MsgBody.class);
        public final PBBytesField bytes_file_key = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBStringField string_desc = PBField.initString("");
        public final PBUInt32Field uint32_capture_times = PBField.initUInt32(0);
        public final PBUInt32Field uint32_from_uin = PBField.initUInt32(0);
        public final PBUInt32Field uint32_status = PBField.initUInt32(0);
        public final PBUInt32Field uint32_to_uin = PBField.initUInt32(0);
        public final PBUInt32Field uint32_ttl = PBField.initUInt32(0);
        public final PBUInt32Field uint32_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_from_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_to_uin = PBField.initUInt64(0);
    }
}
