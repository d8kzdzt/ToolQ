package com.toolq.qq.protocol.protobuf;

import com.qq.pb.*;

public final class group_member_info {
    public static final class ReqBody extends MessageMicro<ReqBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40}, new String[]{"uint64_group_code", "uint64_uin", "bool_new_client", "uint32_client_type", "uint32_rich_card_name_ver"}, new Object[]{0L, 0L, false, 0, 0}, ReqBody.class);
        public final PBBoolField bool_new_client = PBField.initBool(false);
        public final PBUInt32Field uint32_client_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_rich_card_name_ver = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_code = PBField.initUInt64(0);
        public final PBUInt64Field uint64_uin = PBField.initUInt64(0);
    }

    public static final class RspBody extends MessageMicro<RspBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 32, 40}, new String[]{"uint64_group_code", "uint32_self_role", "msg_meminfo", "bool_self_location_shared", "uint32_group_type"}, new Object[]{0L, 0, null, false, 0}, RspBody.class);
        public final PBBoolField bool_self_location_shared = PBField.initBool(false);
        public group_member_info.MemberInfo msg_meminfo = new group_member_info.MemberInfo();
        public final PBUInt32Field uint32_group_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_self_role = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_code = PBField.initUInt64(0);
    }

    public static final class MemberInfo extends MessageMicro<MemberInfo> {
        public static final int CONCERN_TYPE_CONCERN = 1;
        public static final int CONCERN_TYPE_GENERAL = 0;
        public static final int CONCERN_TYPE_HATE = 2;

        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 32, 42, 48, 56, 66, 72, 82, 90, 96, 106, 112, 120, 130, 138, 146, 154, 160, 168, 176, 184, 192, 200, 208, 216, 224, 232, 240, 250, 256, 266, 274, 282, 290, 296, 306, 312, 322, 330, 336}, new String[]{"uint64_uin", "uint32_result", "str_errmsg", "bool_is_friend", "str_remark", "bool_is_concerned", "uint32_credit", "str_card", "uint32_sex", "str_location", "str_nick", "uint32_age", "str_lev", "uint64_join", "uint64_last_speak", "rpt_msg_custom_enties", "rpt_msg_gbar_concerned", "str_gbar_title", "str_gbar_url", "uint32_gbar_cnt", "bool_is_allow_mod_card", "bool_is_vip", "bool_is_year_vip", "bool_is_super_vip", "bool_is_super_qq", "uint32_vip_lev", "uint32_role", "bool_location_shared", "uint64_distance", "uint32_concern_type", "bytes_special_title", "uint32_special_title_expire_time", "msg_flower_entry", "msg_team_entry", "bytes_phone_num", "bytes_job", "medal_id", "qqstory_infocard", "uint32_level", "msg_game_info", "bytes_group_honor", "uint32_group_honor_bit"}, new Object[]{0L, 0, ByteStringMicro.EMPTY, false, ByteStringMicro.EMPTY, false, 0, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, 0L, 0L, null, null, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, false, false, false, false, false, 0, 0, false, 0L, 0, ByteStringMicro.EMPTY, 0, null, null, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, null, 0, null, ByteStringMicro.EMPTY, 0}, MemberInfo.class);
        public final PBBoolField bool_is_allow_mod_card = PBField.initBool(false);
        public final PBBoolField bool_is_concerned = PBField.initBool(false);
        public final PBBoolField bool_is_friend = PBField.initBool(false);
        public final PBBoolField bool_is_super_qq = PBField.initBool(false);
        public final PBBoolField bool_is_super_vip = PBField.initBool(false);
        public final PBBoolField bool_is_vip = PBField.initBool(false);
        public final PBBoolField bool_is_year_vip = PBField.initBool(false);
        public final PBBoolField bool_location_shared = PBField.initBool(false);
        public final PBBytesField bytes_group_honor = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_job = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_phone_num = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_special_title = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field medal_id = PBField.initUInt32(0);
        public group_member_info.FlowersEntry msg_flower_entry = new group_member_info.FlowersEntry();
        public group_member_info.MemberGameInfo msg_game_info = new group_member_info.MemberGameInfo();
        public group_member_info.TeamEntry msg_team_entry = new group_member_info.TeamEntry();
        public group_member_info.RspGroupCardGetStory qqstory_infocard = new group_member_info.RspGroupCardGetStory();
        public final PBRepeatMessageField<group_member_info.CustomEntry> rpt_msg_custom_enties = PBField.initRepeatMessage(group_member_info.CustomEntry.class);
        public final PBRepeatMessageField<group_member_info.GBarInfo> rpt_msg_gbar_concerned = PBField.initRepeatMessage(group_member_info.GBarInfo.class);
        public final PBBytesField str_card = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_errmsg = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_gbar_title = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_gbar_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_lev = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_location = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_nick = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_remark = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_age = PBField.initUInt32(0);
        public final PBUInt32Field uint32_concern_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_credit = PBField.initUInt32(0);
        public final PBUInt32Field uint32_gbar_cnt = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_honor_bit = PBField.initUInt32(0);
        public final PBUInt32Field uint32_level = PBField.initUInt32(0);
        public final PBUInt32Field uint32_result = PBField.initUInt32(0);
        public final PBUInt32Field uint32_role = PBField.initUInt32(0);
        public final PBUInt32Field uint32_sex = PBField.initUInt32(0);
        public final PBUInt32Field uint32_special_title_expire_time = PBField.initUInt32(0);
        public final PBUInt32Field uint32_vip_lev = PBField.initUInt32(0);
        public final PBUInt64Field uint64_distance = PBField.initUInt64(0);
        public final PBUInt64Field uint64_join = PBField.initUInt64(0);
        public final PBUInt64Field uint64_last_speak = PBField.initUInt64(0);
        public final PBUInt64Field uint64_uin = PBField.initUInt64(0);
    }

    public static final class GBarInfo extends MessageMicro<GBarInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 34}, new String[]{"uint32_gbar_id", "uint32_uin_lev", "str_head_portrait", "bytes_gbar_name"}, new Object[]{0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, GBarInfo.class);
        public final PBBytesField bytes_gbar_name = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_head_portrait = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_gbar_id = PBField.initUInt32(0);
        public final PBUInt32Field uint32_uin_lev = PBField.initUInt32(0);
    }

    public static final class CustomEntry extends MessageMicro<CustomEntry> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 24, 34, 40}, new String[]{"str_name", "str_value", "bool_clicked", "str_url", "uint64_report_id"}, new Object[]{ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, false, ByteStringMicro.EMPTY, 0L}, CustomEntry.class);
        public final PBBoolField bool_clicked = PBField.initBool(false);
        public final PBBytesField str_name = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_value = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field uint64_report_id = PBField.initUInt64(0);
    }

    public static final class RspGroupCardGetStory extends MessageMicro<RspGroupCardGetStory> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 26}, new String[]{"result", "flag", "video_info"}, new Object[]{null, 0, null}, RspGroupCardGetStory.class);
        public final PBUInt32Field flag = PBField.initUInt32(0);
        public group_member_info.ErrorInfo result = new group_member_info.ErrorInfo();
        public final PBRepeatMessageField<group_member_info.InfoCardVideoInfo> video_info = PBField.initRepeatMessage(group_member_info.InfoCardVideoInfo.class);
    }

    public static final class InfoCardVideoInfo extends MessageMicro<InfoCardVideoInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26}, new String[]{"cover", "vid", "feed_id"}, new Object[]{ByteStringMicro.EMPTY, "", ""}, InfoCardVideoInfo.class);
        public final PBBytesField cover = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBStringField feed_id = PBField.initString("");
        public final PBStringField vid = PBField.initString("");
    }

    public static final class ErrorInfo extends MessageMicro<ErrorInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"error_code", "error_desc"}, new Object[]{0, ByteStringMicro.EMPTY}, ErrorInfo.class);
        public final PBUInt32Field error_code = PBField.initUInt32(0);
        public final PBBytesField error_desc = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class TeamEntry extends MessageMicro<TeamEntry> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"rpt_uint64_depid", "rpt_uint64_self_depid"}, new Object[]{0L, 0L}, TeamEntry.class);
        public final PBRepeatField<Long> rpt_uint64_depid = PBField.initRepeat(PBUInt64Field.__repeatHelper__);
        public final PBRepeatField<Long> rpt_uint64_self_depid = PBField.initRepeat(PBUInt64Field.__repeatHelper__);
    }

    public static final class MemberGameInfo extends MessageMicro<MemberGameInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26, 34, 42, 50, 58}, new String[]{"str_game_name", "str_level_name", "str_level_icon", "str_game_font_color", "str_game_background_color", "str_game_url", "str_desc_info"}, new Object[]{"", "", "", "", "", "", ""}, MemberGameInfo.class);
        public final PBRepeatField<String> str_desc_info = PBField.initRepeat(PBStringField.__repeatHelper__);
        public final PBStringField str_game_background_color = PBField.initString("");
        public final PBStringField str_game_font_color = PBField.initString("");
        public final PBStringField str_game_name = PBField.initString("");
        public final PBStringField str_game_url = PBField.initString("");
        public final PBStringField str_level_icon = PBField.initString("");
        public final PBStringField str_level_name = PBField.initString("");
    }

    public static final class FlowersEntry extends MessageMicro<FlowersEntry> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"uint64_flower_count"}, new Object[]{0L}, FlowersEntry.class);
        public final PBUInt64Field uint64_flower_count = PBField.initUInt64(0);
    }
}
