package com.toolq.qq.protocol.protobuf.tips;

import com.qq.pb.*;
import com.toolq.qq.protocol.protobuf.pushmsg.apollo_game_status;
import com.toolq.qq.protocol.protobuf.pushmsg.apollo_push_msgInfo;

public final class TroopTips0x857 {
    public static final class NotifyMsgBody extends MessageMicro<NotifyMsgBody> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 16, 24, 32, 42, 50, 58, 66, 74, 82, 90, 98, 104, 114, 122, 130, 138, 146, 162, 168, 178, 186, 194, 210, 218, 226, 234, 242, 250, 258, 266, 274, 282, 290, 296, 306},
                new String[]{"opt_enum_type",
                        "opt_uint64_msg_time",
                        "opt_uint64_msg_expires",
                        "opt_uint64_group_code",
                        "opt_msg_graytips",
                        "opt_msg_messagebox",
                        "opt_msg_floatedtips",
                        "opt_msg_toptips",
                        "opt_msg_redtips",
                        "opt_msg_group_notify", // 10
                        "opt_msg_recall",
                        "opt_msg_theme_notify",
                        "uint32_service_type",
                        "opt_msg_objmsg_update",
                        "opt_msg_werewolf_push",
                        "opt_stcm_game_state",
                        "apllo_msg_push",
                        "opt_msg_goldtips",
                        "opt_msg_miniapp_notify",
                        "opt_uint64_sender_uin",
                        "opt_msg_luckybag_notify", "opt_msg_troopformtips_push", "opt_msg_media_push", "opt_general_gray_tip", "opt_msg_video_push", "opt_lbs_share_change_plus_info", "opt_msg_sing_push", "opt_msg_group_info_change", "opt_group_announce_tbc_info", "opt_qq_vedio_game_push_info", "opt_qq_group_digest_msg", "opt_study_room_member_msg", "opt_qq_live_notify", "opt_group_async_notidy", "opt_uint64_group_cur_msg_seq", "opt_group_digest_msg_summary"},
                new Object[]{1, 0L, 0L, 0L, null, null, null, null, null, null, null, null, 0, null, null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0L, null},
                NotifyMsgBody.class);

        public final PBEnumField opt_enum_type = PBField.initEnum(1);

        public final PBUInt64Field opt_uint64_group_code = PBField.initUInt64(0);

        /**
         * 撤回监测
         */
        public TroopTips0x857.MessageRecallReminder opt_msg_recall = new TroopTips0x857.MessageRecallReminder();

        /**
         * 群昵称变更
         */
        public TroopTips0x857.AIOGrayTipsInfo opt_msg_graytips = new TroopTips0x857.AIOGrayTipsInfo();


        public TroopTips0x857.GeneralGrayTipInfo opt_general_gray_tip = new TroopTips0x857.GeneralGrayTipInfo();
        public TroopTips0x857.GroupAnnounceTBCInfo opt_group_announce_tbc_info = new TroopTips0x857.GroupAnnounceTBCInfo();
        public TroopTips0x857.GroupAsyncNotify opt_group_async_notidy = new TroopTips0x857.GroupAsyncNotify();
        public TroopTips0x857.QQGroupDigestMsgSummary opt_group_digest_msg_summary = new TroopTips0x857.QQGroupDigestMsgSummary();
        public TroopTips0x857.LbsShareChangePushInfo opt_lbs_share_change_plus_info = new TroopTips0x857.LbsShareChangePushInfo();
        public TroopTips0x857.FloatedTipsInfo opt_msg_floatedtips = new TroopTips0x857.FloatedTipsInfo();
        public TroopTips0x857.GoldMsgTipsElem opt_msg_goldtips = new TroopTips0x857.GoldMsgTipsElem();
        public TroopTips0x857.GroupInfoChange opt_msg_group_info_change = new TroopTips0x857.GroupInfoChange();
        public TroopTips0x857.GroupNotifyInfo opt_msg_group_notify = new TroopTips0x857.GroupNotifyInfo();
        public TroopTips0x857.LuckyBagNotify opt_msg_luckybag_notify = new TroopTips0x857.LuckyBagNotify();
        public TroopTips0x857.MediaChangePushInfo opt_msg_media_push = new TroopTips0x857.MediaChangePushInfo();
        public TroopTips0x857.MessageBoxInfo opt_msg_messagebox = new TroopTips0x857.MessageBoxInfo();
        public TroopTips0x857.MiniAppNotify opt_msg_miniapp_notify = new TroopTips0x857.MiniAppNotify();
        public TroopTips0x857.NotifyObjmsgUpdate opt_msg_objmsg_update = new TroopTips0x857.NotifyObjmsgUpdate();
        public TroopTips0x857.RedGrayTipsInfo opt_msg_redtips = new TroopTips0x857.RedGrayTipsInfo();
        public TroopTips0x857.SingChangePushInfo opt_msg_sing_push = new TroopTips0x857.SingChangePushInfo();
        public TroopTips0x857.ThemeStateNotify opt_msg_theme_notify = new TroopTips0x857.ThemeStateNotify();
        public TroopTips0x857.AIOTopTipsInfo opt_msg_toptips = new TroopTips0x857.AIOTopTipsInfo();
        public TroopTips0x857.TroopFormGrayTipsInfo opt_msg_troopformtips_push = new TroopTips0x857.TroopFormGrayTipsInfo();
        public TroopTips0x857.VideoChangePushInfo opt_msg_video_push = new TroopTips0x857.VideoChangePushInfo();
        public TroopTips0x857.WereWolfPush opt_msg_werewolf_push = new TroopTips0x857.WereWolfPush();
        public TroopTips0x857.QQGroupDigestMsg opt_qq_group_digest_msg = new TroopTips0x857.QQGroupDigestMsg();
        public TroopTips0x857.QQVaLiveNotifyMsg opt_qq_live_notify = new TroopTips0x857.QQVaLiveNotifyMsg();
        public TroopTips0x857.QQVedioGamePushInfo opt_qq_vedio_game_push_info = new TroopTips0x857.QQVedioGamePushInfo();
        public apollo_game_status.STCMGameMessage opt_stcm_game_state = new apollo_game_status.STCMGameMessage();
        public TroopTips0x857.StudyRoomMemberChangePush opt_study_room_member_msg = new TroopTips0x857.StudyRoomMemberChangePush();
        public final PBUInt64Field opt_uint64_group_cur_msg_seq = PBField.initUInt64(0);
        public final PBUInt64Field opt_uint64_msg_expires = PBField.initUInt64(0);
        public final PBUInt64Field opt_uint64_msg_time = PBField.initUInt64(0);
        public final PBUInt64Field opt_uint64_sender_uin = PBField.initUInt64(0);
        public final PBUInt32Field uint32_service_type = PBField.initUInt32(0);
        public apollo_push_msgInfo.STPushMsgElem apllo_msg_push = new apollo_push_msgInfo.STPushMsgElem();

    }

    public static final class StudyRoomMemberChangePush extends MessageMicro<StudyRoomMemberChangePush> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8}, new String[]{"member_count"}, new Object[]{0}, StudyRoomMemberChangePush.class);
        public final PBUInt32Field member_count = PBField.initUInt32(0);
    }

    public static final class QQVedioGamePushInfo extends MessageMicro<QQVedioGamePushInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42}, new String[]{"uint32_msg_type", "uint64_group_code", "uint64_oper_uin", "bytes_version_ctrl", "bytes_ext_info"}, new Object[]{0, 0L, 0L, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, QQVedioGamePushInfo.class);
        public final PBBytesField bytes_ext_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_version_ctrl = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_code = PBField.initUInt64(0);
        public final PBUInt64Field uint64_oper_uin = PBField.initUInt64(0);
    }

    public static final class QQVaLiveNotifyMsg extends MessageMicro<QQVaLiveNotifyMsg> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 26, 34, 42}, new String[]{"bytes_uid", "notify_type", "bytes_ext1", "bytes_ext2", "bytes_ext3"}, new Object[]{ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, QQVaLiveNotifyMsg.class);
        public final PBBytesField bytes_ext1 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_ext2 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_ext3 = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_uid = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBInt32Field notify_type = PBField.initInt32(0);
    }

    public static final class QQGroupDigestMsg extends MessageMicro<QQGroupDigestMsg> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 56, 64, 74, 82, 88}, new String[]{"group_code", "msg_seq", "msg_random", "op_type", "msg_sender", "digest_oper", "op_time", "lastest_msg_seq", "oper_nick", "sender_nick", "ext_info"}, new Object[]{0L, 0, 0, 0, 0L, 0L, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0}, QQGroupDigestMsg.class);
        public final PBUInt64Field digest_oper = PBField.initUInt64(0);
        public final PBInt32Field ext_info = PBField.initInt32(0);
        public final PBUInt64Field group_code = PBField.initUInt64(0);
        public final PBUInt32Field lastest_msg_seq = PBField.initUInt32(0);
        public final PBUInt32Field msg_random = PBField.initUInt32(0);
        public final PBUInt64Field msg_sender = PBField.initUInt64(0);
        public final PBUInt32Field msg_seq = PBField.initUInt32(0);
        public final PBUInt32Field op_time = PBField.initUInt32(0);
        public final PBInt32Field op_type = PBField.initInt32(0);
        public final PBBytesField oper_nick = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField sender_nick = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class WereWolfPush extends MessageMicro<WereWolfPush> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 42, 48, 56, 66, 72, 80, 88, 96, 104, 112, 120, 130, 136}, new String[]{"uint32_push_type", "uint64_game_room", "enum_game_state", "uint32_game_round", "rpt_roles", "uint64_speaker", "uint64_judge_uin", "bytes_judge_words", "enum_operation", "uint64_src_user", "uint64_dst_user", "rpt_dead_users", "uint32_game_result", "uint32_timeout_sec", "uint32_kill_confirmed", "bytes_judge_nickname", "rpt_voted_tie_users"}, new Object[]{0, 0L, 0, 0, null, 0L, 0L, ByteStringMicro.EMPTY, 0, 0L, 0L, 0L, 0, 0, 0, ByteStringMicro.EMPTY, 0L}, WereWolfPush.class);
        public final PBBytesField bytes_judge_nickname = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_judge_words = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field enum_game_state = PBField.initUInt32(0);
        public final PBUInt32Field enum_operation = PBField.initUInt32(0);
        public final PBRepeatField<Long> rpt_dead_users = PBField.initRepeat(PBUInt64Field.__repeatHelper__);
        public final PBRepeatMessageField<Role> rpt_roles = PBField.initRepeatMessage(Role.class);
        public final PBRepeatField<Long> rpt_voted_tie_users = PBField.initRepeat(PBUInt64Field.__repeatHelper__);
        public final PBUInt32Field uint32_game_result = PBField.initUInt32(0);
        public final PBUInt32Field uint32_game_round = PBField.initUInt32(0);
        public final PBUInt32Field uint32_kill_confirmed = PBField.initUInt32(0);
        public final PBUInt32Field uint32_push_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_timeout_sec = PBField.initUInt32(0);
        public final PBUInt64Field uint64_dst_user = PBField.initUInt64(0);
        public final PBUInt64Field uint64_game_room = PBField.initUInt64(0);
        public final PBUInt64Field uint64_judge_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_speaker = PBField.initUInt64(0);
        public final PBUInt64Field uint64_src_user = PBField.initUInt64(0);

        public static final class Role extends MessageMicro<Role> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96, 104, 112, 120, 128, 138, 144, 152, 160}, new String[]{"uint64_uin", "enum_type", "enum_state", "uint32_can_speak", "uint32_can_listen", "uint32_position", "uint32_can_vote", "uint32_can_voted", "uint32_already_checked", "uint32_already_saved", "uint32_already_poisoned", "uint32_player_state", "enum_dead_op", "enum_operation", "uint64_dst_user", "uint32_operation_round", "msg_game_record", "uint32_is_werewolf", "uint64_defended_user", "uint32_is_sheriff"}, new Object[]{0L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0L, 0, null, 0, 0L, 0}, Role.class);
            public final PBUInt32Field enum_dead_op = PBField.initUInt32(0);
            public final PBUInt32Field enum_operation = PBField.initUInt32(0);
            public final PBUInt32Field enum_state = PBField.initUInt32(0);
            public final PBUInt32Field enum_type = PBField.initUInt32(0);
            public TroopTips0x857.WereWolfPush.GameRecord msg_game_record = new TroopTips0x857.WereWolfPush.GameRecord();
            public final PBUInt32Field uint32_already_checked = PBField.initUInt32(0);
            public final PBUInt32Field uint32_already_poisoned = PBField.initUInt32(0);
            public final PBUInt32Field uint32_already_saved = PBField.initUInt32(0);
            public final PBUInt32Field uint32_can_listen = PBField.initUInt32(0);
            public final PBUInt32Field uint32_can_speak = PBField.initUInt32(0);
            public final PBUInt32Field uint32_can_vote = PBField.initUInt32(0);
            public final PBUInt32Field uint32_can_voted = PBField.initUInt32(0);
            public final PBUInt32Field uint32_is_sheriff = PBField.initUInt32(0);
            public final PBUInt32Field uint32_is_werewolf = PBField.initUInt32(0);
            public final PBUInt32Field uint32_operation_round = PBField.initUInt32(0);
            public final PBUInt32Field uint32_player_state = PBField.initUInt32(0);
            public final PBUInt32Field uint32_position = PBField.initUInt32(0);
            public final PBUInt64Field uint64_defended_user = PBField.initUInt64(0);
            public final PBUInt64Field uint64_dst_user = PBField.initUInt64(0);
            public final PBUInt64Field uint64_uin = PBField.initUInt64(0);
        }

        public static final class GameRecord extends MessageMicro<GameRecord> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32}, new String[]{"uint32_total", "uint32_win", "uint32_lose", "uint32_draw"}, new Object[]{0, 0, 0, 0}, GameRecord.class);
            public final PBUInt32Field uint32_draw = PBField.initUInt32(0);
            public final PBUInt32Field uint32_lose = PBField.initUInt32(0);
            public final PBUInt32Field uint32_total = PBField.initUInt32(0);
            public final PBUInt32Field uint32_win = PBField.initUInt32(0);
        }
    }

    public static final class VideoChangePushInfo extends MessageMicro<VideoChangePushInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 42, 48, 56, 802}, new String[]{"uint64_seq", "uint32_action_type", "uint64_group_id", "uint64_oper_uin", "bytes_gray_tips", "uint32_join_nums", "uint32_join_state", "bytes_ext_info"}, new Object[]{0L, 0, 0L, 0L, ByteStringMicro.EMPTY, 0, 0, ByteStringMicro.EMPTY}, VideoChangePushInfo.class);
        public final PBBytesField bytes_ext_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_gray_tips = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_action_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_join_nums = PBField.initUInt32(0);
        public final PBUInt32Field uint32_join_state = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_oper_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_seq = PBField.initUInt64(0);
    }

    public static final class TroopFormGrayTipsInfo extends MessageMicro<TroopFormGrayTipsInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 26, 34, 42}, new String[]{"uint64_writer_uin", "uint64_creator_uin", "bytes_rich_content", "bytes_opt_bytes_url", "bytes_creator_nick"}, new Object[]{0L, 0L, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, TroopFormGrayTipsInfo.class);
        public final PBBytesField bytes_creator_nick = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_opt_bytes_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_rich_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field uint64_creator_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_writer_uin = PBField.initUInt64(0);
    }

    public static final class AIOTopTipsInfo extends MessageMicro<AIOTopTipsInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24, 34, 42, 50, 58, 66}, new String[]{"opt_bytes_content", "opt_uint32_icon", "opt_enum_action", "opt_bytes_url", "opt_bytes_data", "opt_bytes_data_i", "opt_bytes_data_a", "opt_bytes_data_p"}, new Object[]{ByteStringMicro.EMPTY, 0, 1, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, AIOTopTipsInfo.class);
        public final PBBytesField opt_bytes_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_data = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_data_a = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_data_i = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_data_p = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_url = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBEnumField opt_enum_action = PBField.initEnum(1);
        public final PBUInt32Field opt_uint32_icon = PBField.initUInt32(0);
    }

    public static final class ThemeStateNotify extends MessageMicro<ThemeStateNotify> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 32, 40}, new String[]{"uint32_state", "bytes_feeds_id", "bytes_theme_name", "uint64_action_uin", "uint64_create_uin"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0L, 0L}, ThemeStateNotify.class);
        public final PBBytesField bytes_feeds_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_theme_name = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_state = PBField.initUInt32(0);
        public final PBUInt64Field uint64_action_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_create_uin = PBField.initUInt64(0);
    }

    public static final class SingChangePushInfo extends MessageMicro<SingChangePushInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 42, 48}, new String[]{"uint64_seq", "uint32_action_type", "uint64_group_id", "uint64_oper_uin", "bytes_gray_tips", "uint32_join_nums"}, new Object[]{0L, 0, 0L, 0L, ByteStringMicro.EMPTY, 0}, SingChangePushInfo.class);
        public final PBBytesField bytes_gray_tips = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_action_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_join_nums = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_oper_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_seq = PBField.initUInt64(0);
    }

    public static final class RedGrayTipsInfo extends MessageMicro<RedGrayTipsInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42, 50, 56, 64, 72, 82, 88, 96, 104, 112, 122, 130, 136, 146, 154, 160, 170}, new String[]{"opt_uint32_show_lastest", "uint64_sender_uin", "uint64_receiver_uin", "bytes_sender_rich_content", "bytes_receiver_rich_content", "bytes_authkey", "sint32_msgtype", "uint32_lucky_flag", "uint32_hide_flag", "bytes_pc_body", "uint32_icon", "uint64_lucky_uin", "uint32_time", "uint32_random", "bytes_broadcast_rich_content", "bytes_idiom", "uint32_idiom_seq", "bytes_idiom_alpha", "bytes_jumpurl", "uint32_subchannel", "bytes_poem_rule"}, new Object[]{0, 0L, 0L, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, 0, ByteStringMicro.EMPTY, 0, 0L, 0, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY}, RedGrayTipsInfo.class);
        public final PBBytesField bytes_authkey = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_broadcast_rich_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_idiom = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_idiom_alpha = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_jumpurl = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_pc_body = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_poem_rule = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_receiver_rich_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_sender_rich_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field opt_uint32_show_lastest = PBField.initUInt32(0);
        public final PBSInt32Field sint32_msgtype = PBField.initSInt32(0);
        public final PBUInt32Field uint32_hide_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_icon = PBField.initUInt32(0);
        public final PBUInt32Field uint32_idiom_seq = PBField.initUInt32(0);
        public final PBUInt32Field uint32_lucky_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_random = PBField.initUInt32(0);
        public final PBUInt32Field uint32_subchannel = PBField.initUInt32(0);
        public final PBUInt32Field uint32_time = PBField.initUInt32(0);
        public final PBUInt64Field uint64_lucky_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_receiver_uin = PBField.initUInt64(0);
        public final PBUInt64Field uint64_sender_uin = PBField.initUInt64(0);
    }

    public static final class MessageRecallReminder extends MessageMicro<MessageRecallReminder> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 34, 42, 48, 56, 64, 74}, new String[]{"uint64_uin", "bytes_nickname", "uint32_recalled_msg_list", "str_reminder_content", "bytes_userdef", "uint32_group_type", "uint32_op_type", "uint64_admin_uin", "msg_wording_info"}, new Object[]{0L, ByteStringMicro.EMPTY, null, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0, 0, 0L, null}, MessageRecallReminder.class);
        public final PBBytesField bytes_nickname = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_userdef = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField str_reminder_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_group_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_op_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_admin_uin = PBField.initUInt64(0);

        /**
         * 撤回操作人
         */
        public final PBUInt64Field uint64_uin = PBField.initUInt64(0);

        public final PBRepeatMessageField<MessageMeta> uint32_recalled_msg_list = PBField.initRepeatMessage(MessageMeta.class);

        public WithDrawWordingInfo msg_wording_info = new WithDrawWordingInfo();

        public static final class WithDrawWordingInfo extends MessageMicro<WithDrawWordingInfo> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"int32_item_id", "string_item_name"}, new Object[]{0, ""}, WithDrawWordingInfo.class);
            public final PBInt32Field int32_item_id = PBField.initInt32(0);
            public final PBStringField string_item_name = PBField.initString("");
        }

        public static final class MessageMeta extends MessageMicro<MessageMeta> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 56}, new String[]{"uint32_seq", "uint32_time", "uint32_msg_random", "uint32_msg_type", "uint32_msg_flag", "uint64_author_uin", "uint32_is_anony_msg"}, new Object[]{0, 0L, 0, 0, 0, 0L, 0}, MessageMeta.class);
            public final PBUInt32Field uint32_is_anony_msg = PBField.initUInt32(0);
            public final PBUInt32Field uint32_msg_flag = PBField.initUInt32(0);
            public final PBUInt32Field uint32_msg_random = PBField.initUInt32(0);
            public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
            public final PBUInt32Field uint32_seq = PBField.initUInt32(0);
            public final PBUInt64Field uint32_time = PBField.initUInt64(0);
            public final PBUInt64Field uint64_author_uin = PBField.initUInt64(0);
        }
    }

    public static final class NotifyObjmsgUpdate extends MessageMicro<NotifyObjmsgUpdate> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 26}, new String[]{"bytes_objmsg_id", "uint32_update_type", "bytes_ext_msg"}, new Object[]{ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY}, NotifyObjmsgUpdate.class);
        public final PBBytesField bytes_ext_msg = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_objmsg_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_update_type = PBField.initUInt32(0);
    }

    public static final class MiniAppNotify extends MessageMicro<MiniAppNotify> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10}, new String[]{"bytes_msg"}, new Object[]{ByteStringMicro.EMPTY}, MiniAppNotify.class);
        public final PBBytesField bytes_msg = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class MessageBoxInfo extends MessageMicro<MessageBoxInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18, 26}, new String[]{"opt_bytes_content", "opt_bytes_title", "opt_bytes_button"}, new Object[]{ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, MessageBoxInfo.class);
        public final PBBytesField opt_bytes_button = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_title = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class MediaChangePushInfo extends MessageMicro<MediaChangePushInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 32, 40, 50, 56, 64, 74, 80, 88, 792, 802}, new String[]{"uint32_msg_type", "bytes_msg_info", "bytes_version_ctrl", "uint64_group_id", "uint64_oper_uin", "bytes_gray_tips", "uint64_msg_seq", "uint32_join_nums", "msg_per_setting", "uint32_play_mode", "is_join_when_start", "uint32_media_type", "bytes_ext_info"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0L, 0L, ByteStringMicro.EMPTY, 0L, 0, null, 0, false, 0, ByteStringMicro.EMPTY}, MediaChangePushInfo.class);
        public final PBBytesField bytes_ext_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_gray_tips = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_msg_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_version_ctrl = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBoolField is_join_when_start = PBField.initBool(false);
        public PersonalSetting msg_per_setting = new PersonalSetting();
        public final PBUInt32Field uint32_join_nums = PBField.initUInt32(0);
        public final PBUInt32Field uint32_media_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_play_mode = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_msg_seq = PBField.initUInt64(0);
        public final PBUInt64Field uint64_oper_uin = PBField.initUInt64(0);

        public static final class PersonalSetting extends MessageMicro<PersonalSetting> {
            static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24}, new String[]{"uint32_theme_id", "uint32_player_id", "uint32_font_id"}, new Object[]{0, 0, 0}, PersonalSetting.class);
            public final PBUInt32Field uint32_font_id = PBField.initUInt32(0);
            public final PBUInt32Field uint32_player_id = PBField.initUInt32(0);
            public final PBUInt32Field uint32_theme_id = PBField.initUInt32(0);
        }
    }

    public static final class LuckyBagNotify extends MessageMicro<LuckyBagNotify> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10}, new String[]{"bytes_msg_tips"}, new Object[]{ByteStringMicro.EMPTY}, LuckyBagNotify.class);
        public final PBBytesField bytes_msg_tips = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class GroupNotifyInfo extends MessageMicro<GroupNotifyInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18}, new String[]{"opt_uint32_auto_pull_flag", "opt_bytes_feeds_id"}, new Object[]{0, ByteStringMicro.EMPTY}, GroupNotifyInfo.class);
        public final PBBytesField opt_bytes_feeds_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field opt_uint32_auto_pull_flag = PBField.initUInt32(0);
    }

    public static final class GroupInfoChange extends MessageMicro<GroupInfoChange> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 56, 64, 72}, new String[]{"uint32_group_honor_switch", "uint32_group_member_level_switch", "uint32_group_flagext4", "uint32_appeal_deadline", "uint32_group_flag", "uint32_group_flagext3", "uint32_group_class_ext", "uint32_group_info_ext_seq"}, new Object[]{0, 0, 0, 0, 0, 0, 0, 0}, GroupInfoChange.class);
        public final PBUInt32Field uint32_appeal_deadline = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_class_ext = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_flagext3 = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_flagext4 = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_honor_switch = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_info_ext_seq = PBField.initUInt32(0);
        public final PBUInt32Field uint32_group_member_level_switch = PBField.initUInt32(0);
    }

    public static final class AIOGrayTipsInfo extends MessageMicro<AIOGrayTipsInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 34, 40, 48, 56}, new String[]{"opt_uint32_show_lastest", "opt_bytes_content", "opt_uint32_remind", "opt_bytes_brief", "uint64_receiver_uin", "uint32_reliao_admin_opt", "uint32_robot_group_opt"}, new Object[]{0, ByteStringMicro.EMPTY, 0, ByteStringMicro.EMPTY, 0L, 0, 0}, AIOGrayTipsInfo.class);
        public final PBBytesField opt_bytes_brief = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField opt_bytes_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field opt_uint32_remind = PBField.initUInt32(0);
        public final PBUInt32Field opt_uint32_show_lastest = PBField.initUInt32(0);
        public final PBUInt32Field uint32_reliao_admin_opt = PBField.initUInt32(0);
        public final PBUInt32Field uint32_robot_group_opt = PBField.initUInt32(0);
        public final PBUInt64Field uint64_receiver_uin = PBField.initUInt64(0);
    }

    public static final class GoldMsgTipsElem extends MessageMicro<GoldMsgTipsElem> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 24, 32, 40, 48, 56, 64, 72}, new String[]{"type", "billno", "result", "amount", "total", "interval", "finish", "uin", "action"}, new Object[]{0, "", 0, 0, 0, 0, 0, 0L, 0}, GoldMsgTipsElem.class);
        public final PBUInt32Field action = PBField.initUInt32(0);
        public final PBUInt32Field amount = PBField.initUInt32(0);
        public final PBStringField billno = PBField.initString("");
        public final PBUInt32Field finish = PBField.initUInt32(0);
        public final PBUInt32Field interval = PBField.initUInt32(0);
        public final PBUInt32Field result = PBField.initUInt32(0);
        public final PBUInt32Field total = PBField.initUInt32(0);
        public final PBUInt32Field type = PBField.initUInt32(0);
        public final PBRepeatField<Long> uin = PBField.initRepeat(PBUInt64Field.__repeatHelper__);

    }

    public static final class FloatedTipsInfo extends MessageMicro<FloatedTipsInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10}, new String[]{"opt_bytes_content"}, new Object[]{ByteStringMicro.EMPTY}, FloatedTipsInfo.class);
        public final PBBytesField opt_bytes_content = PBField.initBytes(ByteStringMicro.EMPTY);
    }

    public static final class LbsShareChangePushInfo extends MessageMicro<LbsShareChangePushInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 18, 26, 32, 40, 50, 56, 64, 792, 802}, new String[]{"uint32_msg_type", "bytes_msg_info", "bytes_version_ctrl", "uint64_group_id", "uint64_oper_uin", "bytes_gray_tips", "uint64_msg_seq", "uint32_join_nums", "uint32_push_type", "bytes_ext_info"}, new Object[]{0, ByteStringMicro.EMPTY, ByteStringMicro.EMPTY, 0L, 0L, ByteStringMicro.EMPTY, 0L, 0, 0, ByteStringMicro.EMPTY}, LbsShareChangePushInfo.class);
        public final PBBytesField bytes_ext_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_gray_tips = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_msg_info = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_version_ctrl = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field uint32_join_nums = PBField.initUInt32(0);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_push_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_group_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_msg_seq = PBField.initUInt64(0);
        public final PBUInt64Field uint64_oper_uin = PBField.initUInt64(0);
    }

    public static final class QQGroupDigestMsgSummary extends MessageMicro<QQGroupDigestMsgSummary> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 40, 50}, new String[]{"digest_oper", "op_type", "op_time", "digest_nick", "succ_cnt", "summary_info"}, new Object[]{0L, 0, 0, ByteStringMicro.EMPTY, 0, null}, QQGroupDigestMsgSummary.class);
        public final PBBytesField digest_nick = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field digest_oper = PBField.initUInt64(0);
        public final PBUInt32Field op_time = PBField.initUInt32(0);
        public final PBInt32Field op_type = PBField.initInt32(0);
        public final PBInt32Field succ_cnt = PBField.initInt32(0);
        public final PBRepeatMessageField<TroopTips0x857.QQGroupDigestSummaryInfo> summary_info = PBField.initRepeatMessage(TroopTips0x857.QQGroupDigestSummaryInfo.class);
    }

    public static final class QQGroupDigestSummaryInfo extends MessageMicro<QQGroupDigestSummaryInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24}, new String[]{"msg_seq", "msg_random", "error_code"}, new Object[]{0, 0, 0}, QQGroupDigestSummaryInfo.class);
        public final PBUInt32Field error_code = PBField.initUInt32(0);
        public final PBUInt32Field msg_random = PBField.initUInt32(0);
        public final PBUInt32Field msg_seq = PBField.initUInt32(0);
    }

    public static final class GroupAsyncNotify extends MessageMicro<GroupAsyncNotify> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"uint32_msg_type", "uint64_msg_seq"}, new Object[]{0, 0L}, GroupAsyncNotify.class);
        public final PBUInt32Field uint32_msg_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_msg_seq = PBField.initUInt64(0);
    }

    public static final class GroupAnnounceTBCInfo extends MessageMicro<GroupAnnounceTBCInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 16, 24}, new String[]{"feeds_id", "group_id", "action"}, new Object[]{ByteStringMicro.EMPTY, 0L, 0}, GroupAnnounceTBCInfo.class);
        public final PBUInt32Field action = PBField.initUInt32(0);
        public final PBBytesField feeds_id = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt64Field group_id = PBField.initUInt64(0);
    }

    public static final class GeneralGrayTipInfo extends MessageMicro<GeneralGrayTipInfo> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 32, 40, 48, 58, 66, 80, 802}, new String[]{"uint64_busi_type", "uint64_busi_id", "uint32_ctrl_flag", "uint32_c2c_type", "uint32_service_type", "uint64_templ_id", "rpt_msg_templ_param", "bytes_content", "uint64_tips_seq_id", "bytes_pb_reserv"}, new Object[]{0L, 0L, 0, 0, 0, 0L, null, ByteStringMicro.EMPTY, 0L, ByteStringMicro.EMPTY}, GeneralGrayTipInfo.class);
        public final PBBytesField bytes_content = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_pb_reserv = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBRepeatMessageField<TroopTips0x857.TemplParam> rpt_msg_templ_param = PBField.initRepeatMessage(TroopTips0x857.TemplParam.class);
        public final PBUInt32Field uint32_c2c_type = PBField.initUInt32(0);
        public final PBUInt32Field uint32_ctrl_flag = PBField.initUInt32(0);
        public final PBUInt32Field uint32_service_type = PBField.initUInt32(0);
        public final PBUInt64Field uint64_busi_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_busi_type = PBField.initUInt64(0);
        public final PBUInt64Field uint64_templ_id = PBField.initUInt64(0);
        public final PBUInt64Field uint64_tips_seq_id = PBField.initUInt64(0);
    }

    public static final class TemplParam extends MessageMicro<TemplParam> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{10, 18}, new String[]{"bytes_name", "bytes_value"}, new Object[]{ByteStringMicro.EMPTY, ByteStringMicro.EMPTY}, TemplParam.class);
        public final PBBytesField bytes_name = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBBytesField bytes_value = PBField.initBytes(ByteStringMicro.EMPTY);
    }
}

