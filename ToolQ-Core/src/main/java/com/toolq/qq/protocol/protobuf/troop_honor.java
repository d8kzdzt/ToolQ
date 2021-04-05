package com.toolq.qq.protocol.protobuf;

import com.qq.pb.MessageMicro;
import com.qq.pb.PBField;
import com.qq.pb.PBRepeatField;
import com.qq.pb.PBUInt32Field;

public class troop_honor {
    public static final class GroupUserCardHonor extends MessageMicro<GroupUserCardHonor> {
        static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16}, new String[]{"id", "level"}, new Object[]{0, 0}, GroupUserCardHonor.class);
        public final PBRepeatField<Integer> id = PBField.initRepeat(PBUInt32Field.__repeatHelper__);
        public final PBUInt32Field level = PBField.initUInt32(0);
    }
}
