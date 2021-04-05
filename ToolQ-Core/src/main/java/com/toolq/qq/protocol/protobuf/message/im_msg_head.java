package com.toolq.qq.protocol.protobuf.message;

import com.qq.pb.*;

public class im_msg_head {
    public static final class InstCtrl extends MessageMicro<InstCtrl> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26}, new String[]{"rpt_msg_send_to_inst", "rpt_msg_exclude_inst", "msg_from_inst"}, new Object[]{null, null, null}, InstCtrl.class);
        public im_msg_head.InstInfo msg_from_inst = new im_msg_head.InstInfo();
        public final PBRepeatMessageField<im_msg_head.InstInfo> rpt_msg_exclude_inst = PBField.initRepeatMessage(im_msg_head.InstInfo.class);
        public final PBRepeatMessageField<InstInfo> rpt_msg_send_to_inst = PBField.initRepeatMessage(im_msg_head.InstInfo.class);
    }

    public static final class InstInfo extends MessageMicro<InstInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 80}, new String[]{"uint32_apppid", "uint32_instid", "uint32_platform", "enum_device_type"}, new Object[]{0, 0, 0, 0}, InstInfo.class);
        public final PBEnumField enum_device_type = PBField.initEnum(0);
        public final PBUInt32Field uint32_apppid = PBField.initUInt32(0);
        public final PBUInt32Field uint32_instid = PBField.initUInt32(0);
        public final PBUInt32Field uint32_platform = PBField.initUInt32(0);
    }
}
