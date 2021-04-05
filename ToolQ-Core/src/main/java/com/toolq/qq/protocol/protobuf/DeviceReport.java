package com.toolq.qq.protocol.protobuf;

import com.qq.pb.ByteStringMicro;
import com.qq.pb.MessageMicro;
import com.qq.pb.PBBytesField;
import com.qq.pb.PBField;
import com.toolq.helper.android.Android;

public class DeviceReport extends MessageMicro<DeviceReport> {
    static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 34, 42, 50, 58, 66, 74},
        new String[]{"bytes_bootloader", "bytes_version", "bytes_codename", "bytes_incremental", "bytes_fingerprint", "bytes_boot_id", "bytes_android_id", "bytes_baseband", "bytes_inner_ver"},
        new Object[]{ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY},
        DeviceReport.class);

    public final PBBytesField bytes_bootloader = PBField.initBytes("unknown");

    public final PBBytesField bytes_version = PBField.initBytes("Linux version 4.19.113-perf-gb3dd08fa2aaa (builder@c5-miui-ota-bd143.bj) (clang version 8.0.12 for Android NDK) #1 SMP PREEMPT Thu Feb 4 04:37:10 CST 2021;");
    public final PBBytesField bytes_codename = PBField.initBytes("REL");
    public final PBBytesField bytes_incremental = PBField.initBytes("20.8.13");
    public final PBBytesField bytes_fingerprint = PBField.initBytes("Xiaomi/vangogh/vangogh:11/RKQ1.200826.002/21.2.4:user/release-keys");
    public final PBBytesField bytes_boot_id = PBField.initBytes("");
    public final PBBytesField bytes_android_id = PBField.initBytes(Android.androidId);
    public final PBBytesField bytes_baseband = PBField.initBytes("");
    public final PBBytesField bytes_inner_ver = PBField.initBytes("21.2.4");
}
