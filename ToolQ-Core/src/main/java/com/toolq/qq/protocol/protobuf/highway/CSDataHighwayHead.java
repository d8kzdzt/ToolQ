package com.toolq.qq.protocol.protobuf.highway;

import com.qq.pb.*;

public class CSDataHighwayHead {
    public static final class SegHead extends MessageMicro<SegHead> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 50, 56, 66, 74, 80, 88, 96}, new String[]{"uint32_serviceid", "uint64_filesize", "uint64_dataoffset",
                "uint32_datalength", // 4
                "uint32_rtcode",
                "bytes_serviceticket",
                "uint32_flag",  // 7
                "bytes_md5",
                "bytes_file_md5",
                "uint32_cache_addr", "uint32_query_times", "uint32_update_cacheip"}, new Object[]{0, 0L, 0L, 0, 0, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, 0}, SegHead.class);
        public final PBBytesField bytes_file_md5 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_md5 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_serviceticket = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_cache_addr = PBField.initUInt32(0);
        public final PBUInt32Field uint32_datalength = PBField.initUInt32(0);
        public final PBUInt32Field uint32_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_query_times = PBField.initUInt32(0);
        public final PBUInt32Field uint32_rtcode = PBField.initUInt32(0);
        public final PBUInt32Field uint32_serviceid = PBField.initUInt32(0);
        public final PBUInt32Field uint32_update_cacheip = PBField.initUInt32(0);
        public final PBUInt64Field uint64_dataoffset = PBField.initUInt64(0);
        public final PBUInt64Field uint64_filesize = PBField.initUInt64(0);
    }

    public static final class DataHighwayHead extends MessageMicro<DataHighwayHead> {
        public DataHighwayHead() {
            uint32_retry_times.set(0);
        }

        static final FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 18, 26, 32, 40, 48, 56, 64, 74, 80, 88},
                new String[]{"uint32_version", "bytes_uin", "bytes_command", "uint32_seq", "uint32_retry_times", "uint64_appid", "uint32_dataflag", "uint32_command_id", "bytes_build_ver", "locale_id", "env_id"},
                new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, 0, 0, 0, ByteStringMicro.EMPTY, 0, 0},
                DataHighwayHead.class);

        public final PBUInt32Field uint32_version = PBField.initUInt32(0);
        public final PBBytesField bytes_uin = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_command = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_seq = PBField.initUInt32(0);
        public final PBUInt32Field uint32_retry_times = PBField.initUInt32(0);
        public final PBUInt64Field uint64_appid = PBField.initUInt64(0);
        public final PBUInt32Field uint32_dataflag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_command_id = PBField.initUInt32(0);
        public final PBBytesField bytes_build_ver = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field locale_id = PBField.initUInt32(0);

        public final PBUInt32Field env_id = PBField.initUInt32(0);
    }

    public static class ReqDataHighwayHead extends MessageMicro<ReqDataHighwayHead> {
        static final FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 32, 42}, new String[]{"msg_basehead", "msg_seghead", "bytes_req_extendinfo", "uint64_timestamp", "msg_login_sig_head"}, new Object[]{null, null, ByteStringMicro.EMPTY, 0L, null}, ReqDataHighwayHead.class);

        public final PBBytesField bytes_req_extendinfo = PBField.initBytes(ByteStringMicro.EMPTY);
        public DataHighwayHead msg_basehead = new DataHighwayHead();
        public CSDataHighwayHead.SegHead msg_seghead = new CSDataHighwayHead.SegHead();
        public CSDataHighwayHead.LoginSigHead msg_login_sig_head = new CSDataHighwayHead.LoginSigHead();
        public final PBUInt64Field uint64_timestamp = PBField.initUInt64(0);

    }

    public static final class LoginSigHead extends MessageMicro<LoginSigHead> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"uint32_loginsig_type", "bytes_loginsig"}, new Object[]{0, ByteStringMicro.EMPTY}, LoginSigHead.class);
        public final PBBytesField bytes_loginsig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_loginsig_type = PBField.initUInt32(0);
    }
}
