package com.toolq.qq.protocol.protobuf;

import com.qq.pb.ByteStringMicro;
import com.qq.pb.MessageMicro;
import com.qq.pb.PBField;
import com.qq.pb.PBUInt64Field;
import com.toolq.utils.RandomUtil;

public class SyncCookie extends MessageMicro<SyncCookie> {
    private static final int val = RandomUtil.randInt(1458467940, 269578051);

    static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
            new int[]{10, 16, 24, 32, 40, 48, 56, 64, 74, 80, 88, 98},
            new String[]{"createTime", "sendTime", "random", "rand", "uint32_ttl", "uint32_type", "uint32_encrypt_prehead_length", "uint32_encrypt_type", "bytes_encrypt_key", "uint32_read_times", "uint32_left_time", "not_online_image"},
            new Object[]{0L, 0L, 0L, 0L, 0, 0, 0, 0, ByteStringMicro.EMPTY, 0, 0, null},
            SyncCookie.class);

    public final PBUInt64Field createTime = PBField.initUInt64(0);
    public final PBUInt64Field sendTime = PBField.initUInt64(0);
    public final PBUInt64Field random = PBField.initUInt64(0);
    public final PBUInt64Field rand = PBField.initUInt64(0);


    // 废弃
}
