package com.qq.pb;

import com.toolq.utils.HexUtil;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class MessageMicro<T extends MessageMicro<T>> extends PBPrimitiveField<T> {
    private FieldMap _fields = null;

    private FieldMap getFieldMap() {
        if (this._fields == null) {
            try {
                // 缓存反射变量
                Field declaredField = getClass().getDeclaredField("__fieldMap__");
                declaredField.setAccessible(true);
                this._fields = (FieldMap) declaredField.get(this);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this._fields;
    }

    public static FieldMap initFieldMap(int[] tags, String[] fields, Object[] defaultValue, Class<?> cls) {
        return new FieldMap(tags, fields, defaultValue, cls);
    }

    public static <T extends MessageMicro<T>> T mergeFrom(T t, byte[] bArr) {
        return t.mergeFrom(bArr);
    }

    public static byte[] toByteArray(MessageMicro<?> messageMicro) {
        return messageMicro.toByteArray();
    }

    public void clear(Object obj) {
        try {
            getFieldMap().clear(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        setHasFlag(false);
    }

    public int computeSize(int i) {
        if (has()) {
            return CodedOutputStreamMicro.computeMessageSize(i, this);
        }
        return 0;
    }

    protected int computeSizeDirectly(int i, T t) {
        return CodedOutputStreamMicro.computeMessageSize(i, t);
    }

    protected void copyFrom(PBField<T> field) {
        try {
            getFieldMap().copyFields(this, (MessageMicro) field);
            setHasFlag(((MessageMicro) field).has());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public T get() {
        return (T) this;
    }

    public final int getCachedSize() {
        return getSerializedSize();
    }

    public final int getSerializedSize() {
        int i = -1;
        try {
            i = getFieldMap().getSerializedSize(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return i;
    }

    public final T mergeFrom(CodedInputStreamMicro codedInputStreamMicro) {
        FieldMap fieldMap = getFieldMap();
        setHasFlag(true);
        while (true) {
            int readTag = codedInputStreamMicro.readTag();
            try {
                if (!fieldMap.readFieldFrom(codedInputStreamMicro, readTag, this) &&
                        (readTag == 0 || !parseUnknownField(codedInputStreamMicro, readTag))) {
                    return (T) this;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                break;
            }
        }
        return (T) this;
    }

    public final T parse(byte[] bArr) {
        return mergeFrom(bArr, 0, bArr.length);
    }

    public final T parse(ByteStringMicro b) {
        byte[] bArr = b.toByteArray();
        return mergeFrom(bArr, 0, bArr.length);
    }

    public final T mergeFrom(byte[] bArr) {
        return mergeFrom(bArr, 0, bArr.length);
    }

    public final T mergeFrom(byte[] bArr, int i, int i2) {
        try {
            CodedInputStreamMicro newInstance = CodedInputStreamMicro.newInstance(bArr, i, i2);
            mergeFrom(newInstance);
            newInstance.checkLastTagWas(0);
            return (T) this;
        } catch (InvalidProtocolBufferMicroException e) {
            throw e;
        }
    }

    protected boolean parseUnknownField(CodedInputStreamMicro codedInputStreamMicro, int i) {
        return codedInputStreamMicro.skipField(i);
    }

    public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
        codedInputStreamMicro.readMessage(this);
    }

    protected T readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
        try {
            T t = (T) getClass().newInstance();
            codedInputStreamMicro.readMessage(t);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(T t) {
        set(t, true);
    }

    public void set(T t, boolean z) {
        copyFrom(t);
        setHasFlag(z);
    }

    public final void toByteArray(byte[] bArr, int i, int i2) {
        CodedOutputStreamMicro newInstance = CodedOutputStreamMicro.newInstance(bArr, i, i2);
        writeTo(newInstance);
        newInstance.checkNoSpaceLeft();
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[getSerializedSize()];
        toByteArray(bArr, 0, bArr.length);
        return bArr;
    }

    public final void writeTo(CodedOutputStreamMicro codedOutputStreamMicro) {
        try {
            getFieldMap().writeTo(codedOutputStreamMicro, this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
        if (has()) {
            codedOutputStreamMicro.writeMessage(i, this);
        }
    }

    protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, T t) {
        codedOutputStreamMicro.writeMessage(i, t);
    }

    public static final class FieldMap {
        private final Object[] defaultValues;
        private final Field[] fields;
        private final int[] tags;

        public FieldMap(int[] tags, String[] fields, Object[] defaultValue, Class<?> cls) {
            this.tags = tags;
            this.defaultValues = defaultValue;
            this.fields = new Field[tags.length];
            for (int i = 0; i < tags.length; i++) {
                try {
                    // 使用 try 跳过因为错误构建不存在的field
                    this.fields[i] = cls.getField(fields[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public <T extends MessageMicro<T>> void  clear(MessageMicro<T> messageMicro) {
            int i = 0;
            while (true) {
                if (i < this.tags.length) {
                    this.fields[i].setAccessible(true);
                    try {
                        ( (PBField<?>) this.fields[i].get(messageMicro) ).clear(this.defaultValues[i]);
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    i++;
                } else {
                    return;
                }
            }
        }

        public <U> void copyFields(U u, MessageMicro uu) {
            int i = 0;
            while (true) {
                if (i < this.tags.length) {
                    Field field = this.fields[i];
                    try {
                        field.setAccessible(true);
                        ((PBField<U>) field.get(u)).copyFrom((PBField<U>) field.get(uu));
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    i++;
                } else {
                    return;
                }
            }
        }

        private static <T> void copyFrom(PBField<T> field, PBField<T> formField) {
            field.copyFrom(formField);
        }

        public <U extends MessageMicro<U>> void copyFields(U u, U u2) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.tags.length) {
                    Field field = this.fields[i2];
                    try {
                        field.setAccessible(true);
                        ((PBField<?>) field.get(u)).copyFrom((PBField) field.get(u2));
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }

        public Field get(int i) {
            int binarySearch = Arrays.binarySearch(this.tags, i);
            if (binarySearch < 0) {
                return null;
            }
            return this.fields[binarySearch];
        }

        public int getSerializedSize(MessageMicro<?> messageMicro) {
            int i = 0;
            for (int j = 0; j < this.tags.length; j++) {
                this.fields[j].setAccessible(true);
                try {
                    i += ((PBField<?>) this.fields[j].get(messageMicro))
                            .computeSize(
                                    WireFormatMicro.getTagFieldNumber(this.tags[j])
                            );
                } catch(IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }

        public boolean readFieldFrom(CodedInputStreamMicro codedInputStreamMicro, int i, MessageMicro<?> messageMicro) {
            int binarySearch = Arrays.binarySearch(this.tags, i);
            if (binarySearch < 0) {
                return false;
            }
            this.fields[binarySearch].setAccessible(true);
            try {
                ((PBField<?>) this.fields[binarySearch].get(messageMicro)).readFrom(codedInputStreamMicro);
            } catch(IllegalAccessException e) {
                e.printStackTrace();
            }
            return true;
        }

        public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, MessageMicro<?> messageMicro) {
            int i = 0;
            while (true) {
                if (i < this.tags.length) {
                    try {
                        this.fields[i].setAccessible(true);
                        ((PBField<?>) this.fields[i].get(messageMicro))
                                .writeTo(codedOutputStreamMicro, WireFormatMicro.getTagFieldNumber(this.tags[i]));
                    } catch(IllegalAccessException ignore) { }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public String toHex() {
        return HexUtil.bytesToHexString(toByteArray());
    }


}
