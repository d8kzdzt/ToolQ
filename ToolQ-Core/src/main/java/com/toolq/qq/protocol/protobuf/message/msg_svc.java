package com.toolq.qq.protocol.protobuf.message;

import com.qq.pb.*;
import com.toolq.qq.protocol.protobuf.SubMsgType0x1a;
import com.toolq.qq.protocol.protobuf.SubMsgType0xc1;

public class msg_svc {
    public static final class PbSendMsgResp extends MessageMicro<PbSendMsgResp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 32, 42, 48, 58, 66, 72, 80}, new String[]{"result", "errmsg", "send_time", "uint32_svrbusy_wait_time", "msg_send_info", "errtype", "trans_svr_info", "receipt_resp", "text_analysis_result", "uint32_msg_info_flag"}, new Object[]{0, "", 0, 0, null, 0, null, null, 0, 0}, PbSendMsgResp.class);

        public final PBUInt32Field result = PBField.initUInt32(0);

        public final PBStringField errmsg = PBField.initString("");
        public final PBUInt32Field errtype = PBField.initUInt32(0);
        public msg_svc.MsgSendInfo msg_send_info = new msg_svc.MsgSendInfo();
        public im_receipt.ReceiptResp receipt_resp = new im_receipt.ReceiptResp();
        public final PBUInt32Field send_time = PBField.initUInt32(0);
        public final PBUInt32Field text_analysis_result = PBField.initUInt32(0);
        public msg_svc.TransSvrInfo trans_svr_info = new msg_svc.TransSvrInfo();
        public final PBUInt32Field uint32_msg_info_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_svrbusy_wait_time = PBField.initUInt32(0);
    }

    public static final class TransSvrInfo extends MessageMicro<TransSvrInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 34}, new String[]{"uint32_sub_type", "int32_ret_code", "bytes_err_msg", "bytes_trans_info"}, new Object[]{0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, TransSvrInfo.class);
        public final PBBytesField bytes_err_msg = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_trans_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBInt32Field int32_ret_code = PBField.initInt32(0);
        public final PBUInt32Field uint32_sub_type = PBField.initUInt32(0);
    }

    public static final class MsgSendInfo extends MessageMicro<MsgSendInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"receiver"}, new Object[]{0}, MsgSendInfo.class);
        public final PBUInt32Field receiver = PBField.initUInt32(0);
    }

    public static final class PbSendMsgReq extends MessageMicro<PbSendMsgReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 32, 40, 50, 58, 64, 72, 82, 90, 98, 106, 112}, new String[]{"routing_head", "content_head", "msg_body", "msg_seq", "msg_rand", "sync_cookie", "app_share", "msg_via", "data_statist", "multi_msg_assist", "input_notify_info", "msg_ctrl", "receipt_req", "multi_send_seq"}, new Object[]{null, null, null, 0, 0, ByteStringMicro.EMPTY, null, 0, 0, null, null, null, null, 0}, PbSendMsgReq.class);

        public RoutingHead routing_head = new msg_svc.RoutingHead();
        public msg_comm.ContentHead content_head = new msg_comm.ContentHead();
        public im_msg_body.MsgBody msg_body = new im_msg_body.MsgBody();
        public final PBUInt32Field msg_seq = PBField.initUInt32(0);
        public final PBUInt32Field msg_rand = PBField.initUInt32(0);
        public final PBBytesField sync_cookie = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field msg_via = PBField.initUInt32(0);

        public msg_comm.AppShareInfo app_share = new msg_comm.AppShareInfo();
        public final PBUInt32Field data_statist = PBField.initUInt32(0);
        public msg_svc.PbInputNotifyInfo input_notify_info = new msg_svc.PbInputNotifyInfo();
        public msg_ctrl.MsgCtrl msg_ctrl = new msg_ctrl.MsgCtrl();
        public msg_svc.MultiMsgAssist multi_msg_assist = new msg_svc.MultiMsgAssist();
        public final PBUInt32Field multi_send_seq = PBField.initUInt32(0);
        public im_receipt.ReceiptReq receipt_req = new im_receipt.ReceiptReq();
    }

    public static final class RoutingHead extends MessageMicro<RoutingHead> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 34, 42, 50, 58, 66, 74, 82, 90, 98, 106, 114, 122, 130, 138, 146, 154, 162, 170, 178}, new String[]{"c2c", "grp", "grp_tmp", "dis", "dis_tmp", "wpa_tmp", "secret_file", "public_plat", "trans_msg", "address_list", "rich_status_tmp", "trans_cmd", "accost_tmp", "pub_group_tmp", "trans_0x211", "business_wpa_tmp", "auth_tmp", "bsns_tmp", "qq_querybusiness_tmp", "nearby_dating_tmp", "nearby_assistant_tmp", "comm_tmp"}, new Object[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}, RoutingHead.class);

        public msg_svc.C2C c2c = new msg_svc.C2C();

        public msg_svc.Grp grp = new msg_svc.Grp();

        public msg_svc.AccostTmp accost_tmp = new msg_svc.AccostTmp();
        public msg_svc.AddressListTmp address_list = new msg_svc.AddressListTmp();
        public msg_svc.AuthTmp auth_tmp = new msg_svc.AuthTmp();
        public msg_svc.BsnsTmp bsns_tmp = new msg_svc.BsnsTmp();
        public msg_svc.BusinessWPATmp business_wpa_tmp = new msg_svc.BusinessWPATmp();
        public msg_svc.CommTmp comm_tmp = new msg_svc.CommTmp();
        public msg_svc.Dis dis = new msg_svc.Dis();
        public msg_svc.DisTmp dis_tmp = new msg_svc.DisTmp();
        public msg_svc.GrpTmp grp_tmp = new msg_svc.GrpTmp();
        public msg_svc.NearByAssistantTmp nearby_assistant_tmp = new msg_svc.NearByAssistantTmp();
        public msg_svc.NearByDatingTmp nearby_dating_tmp = new msg_svc.NearByDatingTmp();
        public msg_svc.PubGroupTmp pub_group_tmp = new msg_svc.PubGroupTmp();
        public msg_svc.PublicPlat public_plat = new msg_svc.PublicPlat();
        public msg_svc.QQQueryBusinessTmp qq_querybusiness_tmp = new msg_svc.QQQueryBusinessTmp();
        public msg_svc.RichStatusTmp rich_status_tmp = new msg_svc.RichStatusTmp();
        public msg_svc.SecretFileHead secret_file = new msg_svc.SecretFileHead();
        public msg_svc.Trans0x211 trans_0x211 = new msg_svc.Trans0x211();
        public msg_svc.TransCmd trans_cmd = new msg_svc.TransCmd();
        public msg_svc.TransMsg trans_msg = new msg_svc.TransMsg();
        public msg_svc.WPATmp wpa_tmp = new msg_svc.WPATmp();

        public void setC2CUin(long uin) {
            c2c.to_uin.set(uin);
            c2c.setHasFlag(true);
            this.setHasFlag(true);
        }

        public void setGrpUin(long uin) {
            grp.group_code.set(uin);
            grp.setHasFlag(true);
            this.setHasFlag(true);
        }
    }

    public static final class WPATmp extends MessageMicro<WPATmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, WPATmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class TransMsg extends MessageMicro<TransMsg> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"to_uin", "c2c_cmd"}, new Object[]{0L, 0}, TransMsg.class);
        public final PBUInt32Field c2c_cmd = PBField.initUInt32(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class TransCmd extends MessageMicro<TransCmd> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"to_uin", "msg_type"}, new Object[]{0L, 0}, TransCmd.class);
        public final PBUInt32Field msg_type = PBField.initUInt32(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class Trans0x211 extends MessageMicro<Trans0x211> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 34, 40, 48}, new String[]{"to_uin", "cc_cmd", "inst_ctrl", "sig", "c2c_type", "service_type"}, new Object[]{0L, 0, null, ByteStringMicro.EMPTY, 0, 0}, Trans0x211.class);
        public final PBUInt32Field c2c_type = PBField.initUInt32(0);
        public final PBUInt32Field cc_cmd = PBField.initUInt32(0);
        public im_msg_head.InstCtrl inst_ctrl = new im_msg_head.InstCtrl();
        public final PBUInt32Field service_type = PBField.initUInt32(0);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class SecretFileHead extends MessageMicro<SecretFileHead> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18}, new String[]{"secret_file_msg", "secret_file_status"}, new Object[]{null, null}, SecretFileHead.class);
        public SubMsgType0xc1.MsgBody secret_file_msg = new SubMsgType0xc1.MsgBody();
        public SubMsgType0x1a.MsgBody secret_file_status = new SubMsgType0x1a.MsgBody();
    }

    public static final class RichStatusTmp extends MessageMicro<RichStatusTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, RichStatusTmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class QQQueryBusinessTmp extends MessageMicro<QQQueryBusinessTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, QQQueryBusinessTmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class PublicPlat extends MessageMicro<PublicPlat> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, PublicPlat.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class PubGroupTmp extends MessageMicro<PubGroupTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24}, new String[]{"to_uin", "sig", "group_uin"}, new Object[]{0L, ByteStringMicro.EMPTY, 0L}, PubGroupTmp.class);
        public final PBUInt64Field group_uin = PBField.initUInt64(0);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class NearByDatingTmp extends MessageMicro<NearByDatingTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24}, new String[]{"to_uin", "sig", "reply"}, new Object[]{0L, ByteStringMicro.EMPTY, false}, NearByDatingTmp.class);
        public final PBBoolField reply = PBField.initBool(false);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class NearByAssistantTmp extends MessageMicro<NearByAssistantTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24}, new String[]{"to_uin", "sig", "reply"}, new Object[]{0L, ByteStringMicro.EMPTY, false}, NearByAssistantTmp.class);
        public final PBBoolField reply = PBField.initBool(false);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class GrpTmp extends MessageMicro<GrpTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"group_uin", "to_uin"}, new Object[]{0L, 0L}, GrpTmp.class);
        public final PBUInt64Field group_uin = PBField.initUInt64(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class Grp extends MessageMicro<Grp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"group_code"}, new Object[]{0L},Grp.class);
        public final PBUInt64Field group_code = PBField.initUInt64(0);
    }

    public static final class DisTmp extends MessageMicro<DisTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"dis_uin", "to_uin"}, new Object[]{0L, 0L}, DisTmp.class);
        public final PBUInt64Field dis_uin = PBField.initUInt64(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class Dis extends MessageMicro<Dis> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"dis_uin"}, new Object[]{0L}, Dis.class);
        public final PBUInt64Field dis_uin = PBField.initUInt64(0);
    }

    public static final class CommTmp extends MessageMicro<CommTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42}, new String[]{"to_uin", "c2c_type", "svr_type", "sig", "reserved"}, new Object[]{0L, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, CommTmp.class);
        public final PBUInt32Field c2c_type = PBField.initUInt32(0);
        public final PBBytesField reserved = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field svr_type = PBField.initUInt32(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class C2C extends MessageMicro<C2C> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"to_uin"}, new Object[]{0L}, C2C.class);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class BusinessWPATmp extends MessageMicro<BusinessWPATmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26}, new String[]{"to_uin", "sig", "sigt"}, new Object[]{0L, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, BusinessWPATmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField sigt = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class BsnsTmp extends MessageMicro<BsnsTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, BsnsTmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class AuthTmp extends MessageMicro<AuthTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"to_uin", "sig"}, new Object[]{0L, ByteStringMicro.EMPTY}, AuthTmp.class);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class AddressListTmp extends MessageMicro<AddressListTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 24, 34, 40}, new String[]{"from_phone", "to_phone", "to_uin", "sig", "from_contact_size"}, new Object[]{"", "", 0L, ByteStringMicro.EMPTY, 0}, AddressListTmp.class);
        public final PBUInt32Field from_contact_size = PBField.initUInt32(0);
        public final PBStringField from_phone = PBField.initString("");
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBStringField to_phone = PBField.initString("");
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class AccostTmp extends MessageMicro<AccostTmp> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24}, new String[]{"to_uin", "sig", "reply"}, new Object[]{0L, ByteStringMicro.EMPTY, false}, AccostTmp.class);
        public final PBBoolField reply = PBField.initBool(false);
        public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class MultiMsgAssist extends MessageMicro<MultiMsgAssist> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24, 32, 42, 48, 56, 64}, new String[]{"repeated_routing", "msg_use", "uint64_temp_id", "uint64_vedio_len", "bytes_redbag_id", "uint64_redbag_amount", "uint32_has_readbag", "uint32_has_vedio"}, new Object[]{null, 1, 0L, 0L, ByteStringMicro.EMPTY, 0L, 0, 0}, MultiMsgAssist.class);
        public final PBBytesField bytes_redbag_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBEnumField msg_use = PBField.initEnum(1);
        public final PBRepeatMessageField<msg_svc.RoutingHead> repeated_routing = PBField.initRepeatMessage(msg_svc.RoutingHead.class);
        public final PBUInt32Field uint32_has_readbag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_has_vedio = PBField.initUInt32(0);
        public final PBUInt64Field uint64_redbag_amount = PBField.initUInt64(0);
        public final PBUInt64Field uint64_temp_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_vedio_len = PBField.initUInt64(0);
    }

    public static final class PbInputNotifyInfo extends MessageMicro<PbInputNotifyInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42}, new String[]{"to_uin", "ime", "notify_flag", "bytes_pb_reserve", "ios_push_wording"}, new Object[]{0L, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, PbInputNotifyInfo.class);
        public final PBBytesField bytes_pb_reserve = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field ime = PBField.initUInt32(0);
        public final PBBytesField ios_push_wording = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field notify_flag = PBField.initUInt32(0);
        public final PBUInt64Field to_uin = PBField.initUInt64(0);
    }

    public static final class PbDeleteMsgReq extends MessageMicro<PbDeleteMsgReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10}, new String[]{"msgItems"}, new Object[]{null}, PbDeleteMsgReq.class);
        public final PBRepeatMessageField<MsgItem> msgItems = PBField.initRepeatMessage(MsgItem.class);

        public static final class MsgItem extends MessageMicro<MsgItem> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 58}, new String[]{"from_uin", "to_uin", "msg_type", "msg_seq", "msg_uid", "sig"}, new Object[]{0L, 0L, 0, 0L, 0L, ByteStringMicro.EMPTY}, MsgItem.class);
            public final PBUInt64Field from_uin = PBField.initUInt64(0);
            public final PBUInt64Field msg_seq = PBField.initUInt64(0);
            public final PBUInt32Field msg_type = PBField.initUInt32(0);
            public final PBUInt64Field msg_uid = PBField.initUInt64(0);
            public final PBBytesField sig = PBField.initBytes(ByteStringMicro.EMPTY);
            public final PBUInt64Field to_uin = PBField.initUInt64(0);
        }
    }

    /**
     * 同步消息（私聊，漫游）
     */
    public static final class PbGetMsgReq extends MessageMicro<PbGetMsgReq> {
        public PbGetMsgReq(int msgReqType, int rambleFlag, int contextFlag) {
            sync_flag.setHasFlag(true);
            latest_ramble_number.setHasFlag(true);
            other_ramble_number.setHasFlag(true);
            online_sync_flag.setHasFlag(true);
            msg_ctrl_buf.setHasFlag(true);
            this.msg_req_type.set(msgReqType);
            this.ramble_flag.set(rambleFlag);
            this.context_flag.set(contextFlag);
        }

        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 32, 40, 48, 56, 64, 72, 82, 90, 98},
                new String[]{
                        "sync_flag",
                        "sync_cookie",
                        "ramble_flag",
                        "latest_ramble_number",
                        "other_ramble_number",
                        "online_sync_flag",
                        "context_flag", // 7
                        "whisper_session_id",
                        "msg_req_type",
                        "pub_account_cookie",
                        "msg_ctrl_buf",
                        "bytes_server_buf"},
                new Object[]{0, ByteStringMicro.copyFromUtf8(""), 1, 20, 3, 1, 0, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY},
                PbGetMsgReq.class);
        /**
         * 消息同步状态码
         */
        public final PBEnumField sync_flag = PBField.initEnum(0);
        /**
         * 信息同步标识
         */
        public final PBBytesField sync_cookie = PBField.initBytes(ByteStringMicro.copyFromUtf8(""));
        /**
         * 是否同步漫游消息
         */
        public final PBUInt32Field ramble_flag = PBField.initUInt32(1);
        /**
         * 同步漫游消息数量
         *
         * 最新漫游消息 latest
         * 其它漫游消息 other
         */
        public final PBUInt32Field latest_ramble_number = PBField.initUInt32(20);
        public final PBUInt32Field other_ramble_number = PBField.initUInt32(3);

        public final PBUInt32Field online_sync_flag = PBField.initUInt32(1);
        public final PBUInt32Field context_flag = PBField.initUInt32(0);
        public final PBUInt32Field whisper_session_id = PBField.initUInt32(0);
        public final PBUInt32Field msg_req_type = PBField.initUInt32(0);
        public final PBBytesField pub_account_cookie = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField msg_ctrl_buf = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_server_buf = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class PbGetMsgResp extends MessageMicro<PbGetMsgResp> {
        public PbGetMsgResp() {}

        public PbGetMsgResp(int syncFlag) {
            sync_flag.set(syncFlag);
        }

        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 32, 42, 48, 56, 66, 72, 82},
                new String[]{"result", "err_msg", "sync_cookie", "sync_flag", "uin_pair_msgs", "bind_uin", "msg_rsp_type", "pub_account_cookie", "is_partial_sync", "msg_ctrl_buf"},
                new Object[]{0, "", ByteStringMicro.EMPTY, 0, null, 0L, 0, ByteStringMicro.EMPTY, false, ByteStringMicro.EMPTY},
                PbGetMsgResp.class);
        public final PBUInt64Field bind_uin = PBField.initUInt64(0);
        public final PBStringField err_msg = PBField.initString("");
        public final PBBoolField is_partial_sync = PBField.initBool(false);
        public final PBBytesField msg_ctrl_buf = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field msg_rsp_type = PBField.initUInt32(0);
        public final PBBytesField pub_account_cookie = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field result = PBField.initUInt32(0);
        public final PBBytesField sync_cookie = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBEnumField sync_flag = PBField.initEnum(0);
        public final PBRepeatMessageField<msg_comm.UinPairMsg> uin_pair_msgs = PBField.initRepeatMessage(msg_comm.UinPairMsg.class);
    }

    /**
     * 报告消息已读
     */
    public static final class PbMsgReadedReportReq extends MessageMicro<PbMsgReadedReportReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 34}, new String[]{"grp_read_report", "dis_read_report", "c2c_read_report", "bind_uin_read_report"}, new Object[]{null, null, null, null}, PbMsgReadedReportReq.class);
        public msg_svc.PbBindUinMsgReadedConfirmReq bind_uin_read_report = new msg_svc.PbBindUinMsgReadedConfirmReq();
        public msg_svc.PbC2CReadedReportReq c2c_read_report = new msg_svc.PbC2CReadedReportReq();
        public final PBRepeatMessageField<msg_svc.PbDiscussReadedReportReq> dis_read_report = PBField.initRepeatMessage(msg_svc.PbDiscussReadedReportReq.class);
        public final PBRepeatMessageField<msg_svc.PbGroupReadedReportReq> grp_read_report = PBField.initRepeatMessage(msg_svc.PbGroupReadedReportReq.class);
    }

    public static final class PbGroupReadedReportReq extends MessageMicro<PbGroupReadedReportReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"group_code", "last_read_seq"}, new Object[]{0L, 0L}, PbGroupReadedReportReq.class);
        public final PBUInt64Field group_code = PBField.initUInt64(0);
        public final PBUInt64Field last_read_seq = PBField.initUInt64(0);
    }

    public static final class PbC2CReadedReportReq extends MessageMicro<PbC2CReadedReportReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18}, new String[]{"sync_cookie", "pair_info"}, new Object[]{ByteStringMicro.EMPTY, null}, PbC2CReadedReportReq.class);
        public final PBRepeatMessageField<UinPairReadInfo> pair_info = PBField.initRepeatMessage(UinPairReadInfo.class);
        public final PBBytesField sync_cookie = PBField.initBytes(ByteStringMicro.EMPTY);

        public static final class UinPairReadInfo extends MessageMicro<UinPairReadInfo> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 32, 40, 48, 56, 72}, new String[]{"peer_uin", "last_read_time", "crm_sig", "peer_type", "chat_type", "cpid", "aio_type", "uint64_to_tiny_id"}, new Object[]{0L, 0, ByteStringMicro.EMPTY, 1, 0, 0L, 0, 0L}, UinPairReadInfo.class);
            public final PBUInt32Field aio_type = PBField.initUInt32(0);
            public final PBUInt32Field chat_type = PBField.initUInt32(0);
            public final PBUInt64Field cpid = PBField.initUInt64(0);
            public final PBBytesField crm_sig = PBField.initBytes(ByteStringMicro.EMPTY);
            public final PBUInt64Field last_read_time = PBField.initUInt64(0);
            public final PBEnumField peer_type = PBField.initEnum(1);
            public final PBUInt64Field peer_uin = PBField.initUInt64(0);
            public final PBUInt64Field uint64_to_tiny_id = PBField.initUInt64(0);
        }
    }

    public static final class PbDiscussReadedReportReq extends MessageMicro<PbDiscussReadedReportReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"conf_uin", "last_read_seq"}, new Object[]{0L, 0L}, PbDiscussReadedReportReq.class);
        public final PBUInt64Field conf_uin = PBField.initUInt64(0);
        public final PBUInt64Field last_read_seq = PBField.initUInt64(0);
    }

    public static final class PbBindUinMsgReadedConfirmReq extends MessageMicro<PbBindUinMsgReadedConfirmReq> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16}, new String[]{"sync_cookie", "bind_uin"}, new Object[]{ByteStringMicro.EMPTY, 0L}, PbBindUinMsgReadedConfirmReq.class);
        public final PBUInt64Field bind_uin = PBField.initUInt64(0);
        public final PBBytesField sync_cookie = PBField.initBytes(ByteStringMicro.EMPTY);
    }
}
