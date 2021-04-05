package com.toolq.qq.protocol.protobuf.onlinepush;

import com.qq.pb.*;

public class OnlinePushTrans {
    public static final class ExtGroupKeyInfo extends MessageMicro<ExtGroupKeyInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"cur_max_seq", "cur_time"}, new Object[]{0, 0L}, ExtGroupKeyInfo.class);
        public final PBUInt32Field cur_max_seq = PBField.initUInt32(0);
        public final PBUInt64Field cur_time = PBField.initUInt64(0);

        @Override
        public String toString() {
            return "ExtGroupKeyInfo{" +
                    "cur_max_seq=" + cur_max_seq +
                    ", cur_time=" + cur_time +
                    '}';
        }
    }

    public static final class PbMsgInfo extends MessageMicro<PbMsgInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 56, 64, 74, 82, 88, 98, 136}, new String[]{"from_uin", "to_uin", "msg_type", "msg_subtype", "msg_seq", "msg_uid", "msg_time", "real_msg_time", "nick_name", "msg_data", "svr_ip", "ext_group_key_info", "uint32_general_flag"}, new Object[]{0L, 0L, 0, 0, 0L, 0L, 0, 0, "", ByteStringMicro.EMPTY, 0, null, 0}, PbMsgInfo.class);
        public OnlinePushTrans.ExtGroupKeyInfo ext_group_key_info = new OnlinePushTrans.ExtGroupKeyInfo();
        public final PBUInt64Field from_uin = PBField.initUInt64(0);
        public final PBBytesField msg_data = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field msg_seq = PBField.initUInt64(0);
        public final PBUInt32Field msg_subtype = PBField.initUInt32(0);
        public final PBUInt64Field msg_time = PBField.initUInt64(0);
        public final PBUInt32Field msg_type = PBField.initUInt32(0);
        public final PBUInt64Field msg_uid = PBField.initUInt64(0);
        public final PBStringField nick_name = PBField.initString("");
        public final PBUInt64Field real_msg_time = PBField.initUInt64(0);
        public final PBUInt32Field svr_ip = PBField.initUInt32(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
        public final PBUInt32Field uint32_general_flag = PBField.initUInt32(0);

        @Override
        public String toString() {
            return "PbMsgInfo{" +
                    "ext_group_key_info=" + ext_group_key_info +
                    ", from_uin=" + from_uin +
                    ", msg_data=" + msg_data +
                    ", msg_seq=" + msg_seq +
                    ", msg_subtype=" + msg_subtype +
                    ", msg_time=" + msg_time +
                    ", msg_type=" + msg_type +
                    ", msg_uid=" + msg_uid +
                    ", nick_name=" + nick_name +
                    ", real_msg_time=" + real_msg_time +
                    ", svr_ip=" + svr_ip +
                    ", to_uin=" + to_uin +
                    ", uint32_general_flag=" + uint32_general_flag +
                    '}';
        }
    }
}
