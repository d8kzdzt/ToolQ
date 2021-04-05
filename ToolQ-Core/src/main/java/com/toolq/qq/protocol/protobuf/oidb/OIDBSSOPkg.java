package com.toolq.qq.protocol.protobuf.oidb;

import com.qq.pb.*;

public class OIDBSSOPkg extends MessageMicro<OIDBSSOPkg> {
    public static final int BYTES_BODYBUFFER_FIELD_NUMBER = 4;
    public static final int STR_CLIENT_VERSION_FIELD_NUMBER = 6;
    public static final int STR_ERROR_MSG_FIELD_NUMBER = 5;
    public static final int UINT32_COMMAND_FIELD_NUMBER = 1;
    public static final int UINT32_RESULT_FIELD_NUMBER = 3;
    public static final int UINT32_SERVICE_TYPE_FIELD_NUMBER = 2;

    static final MessageMicro.FieldMap __fieldMap__ = MessageMicro.initFieldMap(new int[]{8, 16, 24, 34, 42, 50}, new String[]{"uint32_command", "uint32_service_type", "uint32_result", "bytes_bodybuffer", "str_error_msg", "str_client_version"}, new Object[]{0, 0, 0, ByteStringMicro.EMPTY, "", ""}, OIDBSSOPkg.class);
    public final PBBytesField bytes_bodybuffer = PBField.initBytes(ByteStringMicro.EMPTY);
    public final PBStringField str_client_version = PBField.initString("");
    public final PBStringField str_error_msg = PBField.initString("");
    public final PBUInt32Field uint32_command = PBField.initUInt32(0);
    public final PBUInt32Field uint32_result = PBField.initUInt32(0);
    public final PBUInt32Field uint32_service_type = PBField.initUInt32(0);
}
