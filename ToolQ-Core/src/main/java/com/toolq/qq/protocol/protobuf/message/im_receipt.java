package com.toolq.qq.protocol.protobuf.message;

import com.qq.pb.*;

public final class im_receipt {
    public static final class ReceiptInfo extends MessageMicro<ReceiptInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"uint64_read_time"}, new Object[]{0L}, ReceiptInfo.class);
        public final PBUInt64Field uint64_read_time = PBField.initUInt64(0);
    }

    public static final class ReceiptResp extends MessageMicro<ReceiptResp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"command", "receipt_info"}, new Object[]{1, null}, ReceiptResp.class);
        public final PBEnumField command = PBField.initEnum(1);
        public im_receipt.ReceiptInfo receipt_info = new im_receipt.ReceiptInfo();
    }

    public static final class ReceiptReq extends MessageMicro<ReceiptReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"command", "msg_info"}, new Object[]{1, null}, ReceiptReq.class);
        public final PBEnumField command = PBField.initEnum(1);
        public im_receipt.MsgInfo msg_info = new im_receipt.MsgInfo();
    }

    public static final class MsgInfo extends MessageMicro<MsgInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32}, new String[]{"uint64_from_uin", "uint64_to_uin", "uint32_msg_seq", "uint32_msg_random"}, new Object[]{0L, 0L, 0, 0}, MsgInfo.class);
        public final PBUInt32Field uint32_msg_random = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_seq = PBField.initUInt32(0);
        public final PBUInt64Field uint64_from_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_to_uin = PBField.initUInt64(0);
    }
}
