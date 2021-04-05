package com.toolq.qq.protocol.protobuf.message;

import com.google.gson.JsonObject;
import com.qq.pb.*;
import com.toolq.helper.packet.ByteBuilder;
import com.toolq.helper.packet.ByteReader;
import com.toolq.utils.JsonUtil;
import com.toolq.utils.ZipUtil;

import java.util.Objects;

public final class hummer_commelem {
    /**
     * 抖一抖，搓一搓
     */
    public static final class MsgElemInfo_servtype2 extends MessageMicro<MsgElemInfo_servtype2> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 18, 24, 32, 42, 50, 56, 64, 72, 80},
                new String[]{"uint32_poke_type",
                        "bytes_poke_summary",
                        "uint32_double_hit",
                        "uint32_vaspoke_id",
                        "bytes_vaspoke_name", // 5
                        "bytes_vaspoke_minver", // 6
                        "uint32_poke_strength",
                        "uint32_msg_type",
                        "uint32_face_bubble_count",
                        "uint32_poke_flag"},
                new Object[]{0, ByteStringMicro.EMPTY, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, 0, 0},
                MsgElemInfo_servtype2.class);
        public final PBBytesField bytes_poke_summary = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_vaspoke_minver = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_vaspoke_name = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_double_hit = PBField.initUInt32(0);
        public final PBUInt32Field uint32_face_bubble_count = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_poke_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_poke_strength = PBField.initUInt32(0);
        public final PBUInt32Field uint32_poke_type = PBField.initUInt32(0);
        public final PBInt64Field uint32_vaspoke_id = PBField.initInt64(0);
    }

    public static final class MsgElemInfo_servtype3 extends MessageMicro<MsgElemInfo_servtype3> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18}, new String[]{"flash_troop_pic", "flash_c2c_pic"}, new Object[]{null, null}, MsgElemInfo_servtype3.class);
        public im_msg_body.NotOnlineImage flash_c2c_pic = new im_msg_body.NotOnlineImage();
        public im_msg_body.CustomFace flash_troop_pic = new im_msg_body.CustomFace();
    }

    public static final class MsgElemInfo_servtype20 extends MessageMicro<MsgElemInfo_servtype20> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10}, new String[]{"bytes_data"}, new Object[]{ByteStringMicro.EMPTY}, MsgElemInfo_servtype20.class);
        public final PBBytesField bytes_data = PBField.initBytes(ByteStringMicro.EMPTY);

        public void setData(byte[] json) {
            byte[] out = ZipUtil.compress(json);
            ByteBuilder builder = new ByteBuilder(out);
            builder.reWriteByte(1);
            this.bytes_data.set(builder.toByteArray());
        }

        public byte[] getData() {
            byte[] out;
            ByteReader reader = new ByteReader(bytes_data.get().toByteArray());
            switch (reader.readByte()) {
                case 0 : {
                    out = reader.readRestBytes();
                    break;
                }
                case 1: {
                    out = ZipUtil.uncompress(reader.readRestBytes());
                    break;
                }
                default:{
                    out = bytes_data.get().toByteArray();
                    break;
                }
            }
            return out;
        }
    }

    /**
     * 新版QQ表情
     */
    public static final class MsgElemInfo_servtype33 extends MessageMicro<MsgElemInfo_servtype33> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 34}, new String[]{"uint32_index", "bytes_text", "bytes_compat", "bytes_buf"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, MsgElemInfo_servtype33.class);
        public final PBBytesField bytes_buf = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_compat = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_text = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_index = PBField.initUInt32(0);
    }

    /**
     * QQ闪字
     */
    public static final class MsgElemInfo_servtype14 extends MessageMicro<MsgElemInfo_servtype14> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"uint32_id", "reserve_Info"}, new Object[]{0, ByteStringMicro.EMPTY}, MsgElemInfo_servtype14.class);
        public final PBBytesField reserve_Info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_id = PBField.initUInt32(0);

        public String getText(){
            if(reserve_Info.has()) {
                ByteReader reader = new ByteReader(reserve_Info.get().toByteArray());
                byte type = reader.readByte();
                byte[] source = null;
                switch (type) {
                    case 1:
                        source = ZipUtil.uncompress(reader.readRestBytes());
                        break;
                    case 0:
                        source = reader.readRestBytes();
                        break;
                }
                String json = new String(Objects.requireNonNull(source));
                JsonObject main = JsonUtil.parseJsonObject(json);
                return main.get("prompt").getAsString();
            }
            return "";
        }

        public void setText(String text){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("a", "com.tencent.randomwords");
            jsonObject.addProperty("desc", "随机字");
            jsonObject.addProperty("resid", uint32_id.get());
            jsonObject.addProperty("m", "main");
            jsonObject.addProperty("v", "1.0.0.16");
            jsonObject.addProperty("prompt", text);
            ByteBuilder byteBuilder = new ByteBuilder();
            byteBuilder.writeByte(1);
            byteBuilder.writeBytes(ZipUtil.compress(jsonObject.toString().getBytes()));
            reserve_Info.set(byteBuilder.toByteArray());
        }
    }

}
