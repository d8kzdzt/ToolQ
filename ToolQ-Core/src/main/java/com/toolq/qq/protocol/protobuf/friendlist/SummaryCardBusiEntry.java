package com.toolq.qq.protocol.protobuf.friendlist;

import com.qq.pb.*;

public class SummaryCardBusiEntry {
    public static final class comm extends MessageMicro<comm> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{
                        8,
                        16,
                        24,
                        32,
                        40, // 5
                        48,
                        58,
                        64,
                        // 74,
                        80,
                        90,
                        96,
                        106,
                        112,
                        // 122,
                        136,
                        // 146,
                        // 154
                },
                new String[]{"ver",
                        "seq",
                        "fromuin",
                        "touin",
                        "service", // 5
                        "session_type",
                        "session_key",
                        "client_ip",
                        // "display",
                        "result",
                        "err_msg",
                        "platform",
                        "qqver",
                        "build",
                        // "msg_login_sig",
                        "uint32_version",
                        // "msg_uin_info",
                        // "msg_rich_display"
                },
                new Object[]{0, 0, 0L, 0L, 0, 0, ByteStringMicro.EMPTY, 0, null, 0, "", 0, "", 0, null, 0, null, null},
                comm.class);
        public final PBInt32Field build = PBField.initInt32(0);
        public final PBUInt32Field client_ip = PBField.initUInt32(0);
        // public SummaryCardBusiEntry.ui display = new SummaryCardBusiEntry.ui();
        public final PBStringField err_msg = PBField.initString("");
        public final PBUInt64Field fromuin = PBField.initUInt64(0);
        // public SummaryCardBusiEntry.LoginSig msg_login_sig = new SummaryCardBusiEntry.LoginSig();
        // public SummaryCardBusiEntry.rich_ui msg_rich_display = new SummaryCardBusiEntry.rich_ui();
        // public SummaryCardBusiEntry.uin_info msg_uin_info = new SummaryCardBusiEntry.uin_info();
        public final PBUInt32Field platform = PBField.initUInt32(0);
        public final PBStringField qqver = PBField.initString("");
        public final PBInt32Field result = PBField.initInt32(0);
        public final PBUInt32Field seq = PBField.initUInt32(0);
        public final PBUInt32Field service = PBField.initUInt32(0);
        public final PBBytesField session_key = PBField.initBytes(ByteStringMicro.EMPTY);
        public final PBUInt32Field session_type = PBField.initUInt32(0);
        public final PBUInt64Field touin = PBField.initUInt64(0);
        public final PBUInt32Field uint32_version = PBField.initUInt32(0);
        public final PBUInt32Field ver = PBField.initUInt32(0);
    }

    public static final class DoFor extends MessageMicro<DoFor> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 16, 24, 32, 40, 48},
                new String[]{"ver",
                        "userUin",
                        "userUin2",
                        "userUinV2",
                        "userUin3",
                        "userUin4"},
                new Object[]{0, null, null, null, null},
                DoFor.class);

        public final PBUInt32Field ver = PBField.initUInt32(0);

        public final UserUin userUin = new UserUin(0);

        public final UserUin userUin2 = new UserUin(0);

        public final UserUinV2 userUinV2 = new UserUinV2();

        public final PBRepeatMessageField<UserUinV3> userUin3 = PBField.initRepeatMessage(UserUinV3.class);

        public final UserUin userUin4 = new UserUin(0);
    }

    public static final class UserUin extends MessageMicro<UserUin> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8},
                new String[]{"uin"},
                new Object[]{0L},
                UserUin.class);

        public UserUin(long uin) {
            this.uin.set(uin);
        }

        public final PBUInt64Field uin = PBField.initUInt64(0);
    }

    public static final class UserUinV2 extends MessageMicro<UserUinV2> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8, 16},
                new String[]{"ver", "userUin"},
                new Object[]{0L, null},
                UserUinV2.class);
        public final PBUInt64Field ver = PBField.initUInt64(0);

        public final UserUin userUin = new UserUin(0);
    }

    public static final class UserUinV3 extends MessageMicro<UserUinV3> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(
                new int[]{8},
                new String[]{"uin"},
                new Object[]{0},
                UserUinV3.class);

        public UserUinV3(int uin) {
            this.uin.set(uin);
        }

        public final PBUInt32Field uin = PBField.initUInt32(0);
    }
}
