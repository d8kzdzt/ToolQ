package com.toolq.qq.protocol.protobuf.message;

import com.qq.pb.*;

public final class msg_ctrl {
    public static final class MsgCtrl extends MessageMicro<MsgCtrl> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"msg_flag", "resv_resv_info"}, new Object[]{0, null}, MsgCtrl.class);
        public final PBUInt32Field msg_flag = PBField.initUInt32(0);
        public msg_ctrl.ResvResvInfo resv_resv_info = new msg_ctrl.ResvResvInfo();
    }

    public static final class ResvResvInfo extends MessageMicro<ResvResvInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 32, 40, 48, 56, 64}, new String[]{"uint32_flag", "bytes_reserv1", "uint64_reserv2", "uint64_reserv3", "uint32_create_time", "uint32_pic_height", "uint32_pic_width", "uint32_resv_flag"}, new Object[]{0, ByteStringMicro.EMPTY, 0L, 0L, 0, 0, 0, 0}, ResvResvInfo.class);
        public final PBBytesField bytes_reserv1 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_create_time = PBField.initUInt32(0);
        public final PBUInt32Field uint32_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_pic_height = PBField.initUInt32(0);
        public final PBUInt32Field uint32_pic_width = PBField.initUInt32(0);
        public final PBUInt32Field uint32_resv_flag = PBField.initUInt32(0);
        public final PBUInt64Field uint64_reserv2 = PBField.initUInt64(0);
        public final PBUInt64Field uint64_reserv3 = PBField.initUInt64(0);
    }
}
