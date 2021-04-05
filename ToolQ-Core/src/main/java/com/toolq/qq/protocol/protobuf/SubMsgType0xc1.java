package com.toolq.qq.protocol.protobuf;

import com.qq.pb.*;

public final class SubMsgType0xc1 {
    public static final class MsgBody extends MessageMicro<MsgBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24, 32, 40, 48, 56, 64, 74, 80, 88, 98}, new String[]{"bytes_file_key", "uint64_from_uin", "uint64_to_uin", "uint32_status", "uint32_ttl", "uint32_type", "uint32_encrypt_prehead_length", "uint32_encrypt_type", "bytes_encrypt_key", "uint32_read_times", "uint32_left_time", "not_online_image"}, new Object[]{ByteStringMicro.EMPTY, 0L, 0L, 0, 0, 0, 0, 0, ByteStringMicro.EMPTY, 0, 0, null}, MsgBody.class);
        public final PBBytesField bytes_encrypt_key = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_file_key = PBField.initBytes(ByteStringMicro.EMPTY);
        public SubMsgType0xc1.NotOnlineImage not_online_image = new SubMsgType0xc1.NotOnlineImage();
        public final PBUInt32Field uint32_encrypt_prehead_length = PBField.initUInt32(0);
        public final PBUInt32Field uint32_encrypt_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_left_time = PBField.initUInt32(0);
        public final PBUInt32Field uint32_read_times = PBField.initUInt32(0);
        public final PBUInt32Field uint32_status = PBField.initUInt32(0);
        public final PBUInt32Field uint32_ttl = PBField.initUInt32(0);
        public final PBUInt32Field uint32_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_from_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_to_uin = PBField.initUInt64(0);
    }

    public static final class NotOnlineImage extends MessageMicro<NotOnlineImage> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 26, 34, 40, 50, 58, 64, 72, 82, 90, 98, 104}, new String[]{"file_path", "file_len", "download_path", "old_ver_send_file", "img_type", "previews_image", "pic_md5", "pic_height", "pic_width", "res_id", "flag", "str_download_url", "original"}, new Object[]{ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, "", 0}, NotOnlineImage.class);
        public final PBBytesField download_path = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field file_len = PBField.initUInt32(0);
        public final PBBytesField file_path = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField flag = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field img_type = PBField.initUInt32(0);
        public final PBBytesField old_ver_send_file = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field original = PBField.initUInt32(0);
        public final PBUInt32Field pic_height = PBField.initUInt32(0);
        public final PBBytesField pic_md5 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field pic_width = PBField.initUInt32(0);
        public final PBBytesField previews_image = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField res_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBStringField str_download_url = PBField.initString("");
    }
}
