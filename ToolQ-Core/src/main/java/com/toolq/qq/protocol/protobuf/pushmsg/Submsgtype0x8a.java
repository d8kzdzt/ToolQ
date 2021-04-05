package com.toolq.qq.protocol.protobuf.pushmsg;

import com.qq.pb.*;
import com.toolq.qq.protocol.protobuf.tips.TroopTips0x857;

public final class Submsgtype0x8a {
    public static final class ReqBody extends MessageMicro<ReqBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24, 32, 42}, new String[]{"msg_info", "uint32_app_id", "uint32_inst_id", "uint32_long_message_flag", "bytes_reserved"}, new Object[]{null, 0, 0, 0, ByteStringMicro.EMPTY}, ReqBody.class);
        public final PBBytesField bytes_reserved = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBRepeatMessageField<Submsgtype0x8a.MsgInfo> msg_info = PBField.initRepeatMessage(Submsgtype0x8a.MsgInfo.class);
        public final PBUInt32Field uint32_app_id = PBField.initUInt32(0);
        public final PBUInt32Field uint32_inst_id = PBField.initUInt32(0);
        public final PBUInt32Field uint32_long_message_flag = PBField.initUInt32(0);
    }

    public static final class MsgInfo extends MessageMicro<MsgInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 56, 64, 72, 96, 106}, new String[]{"uint64_from_uin", "uint64_to_uin", "uint32_msg_seq", "uint64_msg_uid", "uint64_msg_time", "uint32_msg_random", "uint32_pkg_num", "uint32_pkg_index", "uint32_div_seq", "uint32_flag", "msg_wording_info"}, new Object[]{0L, 0L, 0, 0L, 0L, 0, 0, 0, 0, 0, null}, MsgInfo.class);
        public Submsgtype0x8a.WithDrawWordingInfo msg_wording_info = new Submsgtype0x8a.WithDrawWordingInfo();
        public final PBUInt32Field uint32_div_seq = PBField.initUInt32(0);
        public final PBUInt32Field uint32_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_random = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_seq = PBField.initUInt32(0);
        public final PBUInt32Field uint32_pkg_index = PBField.initUInt32(0);
        public final PBUInt32Field uint32_pkg_num = PBField.initUInt32(0);
        public final PBUInt64Field uint64_from_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_msg_time = PBField.initUInt64(0);
        public final PBUInt64Field uint64_msg_uid = PBField.initUInt64(0);
        public final PBUInt64Field uint64_to_uin = PBField.initUInt64(0);
    }

    public static final class WithDrawWordingInfo extends MessageMicro<TroopTips0x857.MessageRecallReminder.WithDrawWordingInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"int32_item_id", "string_item_name"}, new Object[]{0, ""}, WithDrawWordingInfo.class);
        public final PBInt32Field int32_item_id = PBField.initInt32(0);
        public final PBStringField string_item_name = PBField.initString("");
    }
}
