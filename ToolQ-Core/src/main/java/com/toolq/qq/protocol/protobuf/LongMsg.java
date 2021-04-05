package com.toolq.qq.protocol.protobuf;

import com.qq.pb.*;
import com.toolq.qq.protocol.protobuf.highway.CSDataHighwayHead;
import com.toolq.qq.protocol.protobuf.message.msg_comm;

public final class LongMsg {
    public static final class MsgResId extends MessageMicro<MsgResId> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{10, 18, 58},
                new String[]{"msg_basehead", "msg_seghead", "resId"},
                new Object[]{null, null, null},
                MsgResId.class);
        public CSDataHighwayHead.DataHighwayHead msg_basehead = new CSDataHighwayHead.DataHighwayHead();
        public CSDataHighwayHead.SegHead msg_seghead = new CSDataHighwayHead.SegHead();
        public final ResId resId = new ResId();
    }

    public static final class ResId extends MessageMicro<ResId> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{10},
                new String[]{"id"},
                new Object[]{ByteStringMicro.EMPTY},
                ResId.class);
        public final PBBytesField id = PBField.initBytes("");
    }

    public static final class MsgSpace extends MessageMicro<MsgSpace> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"msg"}, new Object[]{null}, MsgSpace.class);
        public final msg_comm.Msg msg = new msg_comm.Msg();
    }

    public static final class RspBody extends MessageMicro<RspBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 34}, new String[]{"uint32_subcmd", "rpt_msg_up_rsp", "rpt_msg_down_rsp", "rpt_msg_del_rsp"}, new Object[]{0, null, null, null}, RspBody.class);
        public final PBRepeatMessageField<LongMsg.MsgDeleteRsp> rpt_msg_del_rsp = PBField.initRepeatMessage(LongMsg.MsgDeleteRsp.class);
        public final PBRepeatMessageField<LongMsg.MsgDownRsp> rpt_msg_down_rsp = PBField.initRepeatMessage(LongMsg.MsgDownRsp.class);
        public final PBRepeatMessageField<LongMsg.MsgUpRsp> rpt_msg_up_rsp = PBField.initRepeatMessage(LongMsg.MsgUpRsp.class);
        public final PBUInt32Field uint32_subcmd = PBField.initUInt32(0);
    }

    public static final class ReqBody extends MessageMicro<ReqBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42, 50, 80, 88}, new String[]{"uint32_subcmd", "uint32_term_type", "uint32_platform_type", "rpt_msg_up_req", "rpt_msg_down_req", "rpt_msg_del_req", "uint32_agent_type", "uint32_busi_type"}, new Object[]{0, 0, 0, null, null, null, 0, 0}, ReqBody.class);
        public final PBRepeatMessageField<MsgDeleteReq> rpt_msg_del_req = PBField.initRepeatMessage(LongMsg.MsgDeleteReq.class);
        public final PBRepeatMessageField<LongMsg.MsgDownReq> rpt_msg_down_req = PBField.initRepeatMessage(LongMsg.MsgDownReq.class);
        public final PBRepeatMessageField<LongMsg.MsgUpReq> rpt_msg_up_req = PBField.initRepeatMessage(LongMsg.MsgUpReq.class);
        public final PBUInt32Field uint32_agent_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_busi_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_platform_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_subcmd = PBField.initUInt32(0);
        public final PBUInt32Field uint32_term_type = PBField.initUInt32(0);
    }

    public static final class MsgUpRsp extends MessageMicro<MsgUpRsp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26}, new String[]{"uint32_result", "uint32_msg_id", "bytes_msg_resid"}, new Object[]{0, 0, ByteStringMicro.EMPTY}, MsgUpRsp.class);
        public final PBBytesField bytes_msg_resid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_msg_id = PBField.initUInt32(0);
        public final PBUInt32Field uint32_result = PBField.initUInt32(0);
    }

    public static final class MsgDownRsp extends MessageMicro<MsgDownRsp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26}, new String[]{"uint32_result", "bytes_msg_resid", "bytes_msg_content"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, MsgDownRsp.class);
        public final PBBytesField bytes_msg_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_msg_resid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_result = PBField.initUInt32(0);
    }

    public static final class MsgDeleteRsp extends MessageMicro<MsgDeleteRsp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"uint32_result", "bytes_msg_resid"}, new Object[]{0, ByteStringMicro.EMPTY}, MsgDeleteRsp.class);
        public final PBBytesField bytes_msg_resid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_result = PBField.initUInt32(0);
    }

    public static final class MsgDeleteReq extends MessageMicro<MsgDeleteReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16}, new String[]{"bytes_msg_resid", "uint32_msg_type"}, new Object[]{ByteStringMicro.EMPTY, 0}, MsgDeleteReq.class);
        public final PBBytesField bytes_msg_resid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
    }

    public static final class MsgDownReq extends MessageMicro<MsgDownReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 32}, new String[]{"uint32_src_uin", "bytes_msg_resid", "uint32_msg_type", "uint32_need_cache"}, new Object[]{0, ByteStringMicro.EMPTY, 0, 0}, MsgDownReq.class);
        public final PBBytesField bytes_msg_resid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_need_cache = PBField.initUInt32(0);
        public final PBUInt32Field uint32_src_uin = PBField.initUInt32(0);
    }

    public static final class MsgUpReq extends MessageMicro<MsgUpReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 40, 50, 56}, new String[]{"uint32_msg_type", "uint64_dst_uin", "uint32_msg_id", "bytes_msg_content", "uint32_store_type", "bytes_msg_ukey", "uint32_need_cache"}, new Object[]{0, 0L, 0, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, 0}, MsgUpReq.class);
        public final PBBytesField bytes_msg_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_msg_ukey = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_msg_id = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_need_cache = PBField.initUInt32(0);
        public final PBUInt32Field uint32_store_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_dst_uin = PBField.initUInt64(0);
    }
}
