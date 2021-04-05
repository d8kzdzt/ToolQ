package com.toolq.qq.protocol.protobuf.message;

import com.qq.pb.*;

public class obj_msg {
    public static final class ObjMsg extends MessageMicro<ObjMsg> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 42, 50, 58, 64}, new String[]{"uint32_msg_type", "bytes_title", "rpt_bytes_abstact", "bytes_title_ext", "rpt_msg_pic", "msg_content_info", "uint32_report_id_show"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, null, null, 0}, ObjMsg.class);
        public final PBBytesField bytes_title = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_title_ext = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBRepeatMessageField<obj_msg.MsgContentInfo> msg_content_info = PBField.initRepeatMessage(obj_msg.MsgContentInfo.class);
        public final PBRepeatField<ByteStringMicro> rpt_bytes_abstact = PBField.initRepeat(PBBytesField.__repeatHelper__);
        public final PBRepeatMessageField<obj_msg.MsgPic> rpt_msg_pic = PBField.initRepeatMessage(obj_msg.MsgPic.class);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_report_id_show = PBField.initUInt32(0);
    }

    public static final class MsgContentInfo extends MessageMicro<MsgContentInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18}, new String[]{"bytes_Content_info_id", "msg_file"}, new Object[]{ByteStringMicro.EMPTY, null}, MsgContentInfo.class);
        public final PBBytesField bytes_Content_info_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public MsgFile msg_file = new MsgFile();
    }

    public static final class MsgFile extends MessageMicro<MsgFile> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 18, 24, 34, 40, 50, 58, 66},
                new String[]{"uint32_bus_id",
                        "bytes_file_path",
                        "uint64_file_size",
                        "str_file_name",
                        "int64_dead_time",
                        "bytes_file_sha1",
                        "bytes_ext",
                        "bytes_file_md5"},
                new Object[]{0, ByteStringMicro.EMPTY, 0L, "", 0L, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY},
                MsgFile.class);
        public final PBBytesField bytes_ext = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_file_md5 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_file_path = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_file_sha1 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBInt64Field int64_dead_time = PBField.initInt64(0);
        public final PBStringField str_file_name = PBField.initString("");
        public final PBUInt32Field uint32_bus_id = PBField.initUInt32(0);
        public final PBUInt64Field uint64_file_size = PBField.initUInt64(0);
    }

    public static final class MsgPic extends MessageMicro<MsgPic> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 24}, new String[]{"bytes_small_pic_url", "bytes_original_pic_url", "uint32_local_pic_id"}, new Object[]{ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0}, MsgPic.class);
        public final PBBytesField bytes_original_pic_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_small_pic_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_local_pic_id = PBField.initUInt32(0);
    }
}
