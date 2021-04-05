package com.qq.pb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public final class CodedInputStreamMicro {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 0x04000000;
    private final byte[] buffer;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit;
    private int sizeLimit;
    private int totalBytesRetired;

    private CodedInputStreamMicro(InputStream input) {
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = DEFAULT_RECURSION_LIMIT;
        this.sizeLimit = DEFAULT_SIZE_LIMIT;
        this.buffer = new byte[BUFFER_SIZE];
        this.bufferSize = 0;
        this.bufferPos = 0;
        this.input = input;
    }

    private CodedInputStreamMicro(byte[] bArr, int i, int i2) {
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = DEFAULT_RECURSION_LIMIT;
        this.sizeLimit = DEFAULT_SIZE_LIMIT;
        this.buffer = bArr;
        this.bufferSize = i + i2;
        this.bufferPos = i;
        this.input = null;
    }

    public static int decodeZigZag32(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public static long decodeZigZag64(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public static CodedInputStreamMicro newInstance(InputStream inputStream) {
        return new CodedInputStreamMicro(inputStream);
    }

    public static CodedInputStreamMicro newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedInputStreamMicro newInstance(byte[] bArr, int i, int i2) {
        return new CodedInputStreamMicro(bArr, i, i2);
    }

    static int readRawVarint32(InputStream inputStream) {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= 32) {
                while (i < 64) {
                    int read;
                    try {
                        read = inputStream.read();
                        if (read == -1) {
                            throw InvalidProtocolBufferMicroException.truncatedMessage();
                        } else if ((read & 128) != 0) {
                            i += 7;
                        }
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                throw InvalidProtocolBufferMicroException.malformedVarint();
            }
            int read2;
            try {
                read2 = inputStream.read();
                if (read2 == -1) {
                    throw InvalidProtocolBufferMicroException.truncatedMessage();
                }
            } catch(IOException e) {
                throw new InvalidProtocolBufferMicroException(e);
            }
            i2 |= (read2 & 127) << i;
            if ((read2 & 128) == 0) {
                break;
            }
            i += 7;
        }
        return i2;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int i = this.totalBytesRetired + this.bufferSize;
        if (i > this.currentLimit) {
            this.bufferSizeAfterLimit = i - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    private boolean refillBuffer(boolean z) {
        if (this.bufferPos < this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        } else if (this.totalBytesRetired + this.bufferSize != this.currentLimit) {
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            try {
                this.bufferSize = this.input == null ? -1 : this.input.read(this.buffer);
            } catch(IOException e) {
                this.bufferSize = -1;
            }
            if (this.bufferSize == 0) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.bufferSize + "\nThe InputStream implementation is buggy.");
            } else if (this.bufferSize == -1) {
                this.bufferSize = 0;
                if (!z) {
                    return false;
                }
                throw InvalidProtocolBufferMicroException.truncatedMessage();
            } else {
                recomputeBufferSizeAfterLimit();
                int i = this.totalBytesRetired + this.bufferSize + this.bufferSizeAfterLimit;
                if (i <= this.sizeLimit && i >= 0) {
                    return true;
                }
                throw InvalidProtocolBufferMicroException.sizeLimitExceeded();
            }
        } else if (!z) {
            return false;
        } else {
            throw InvalidProtocolBufferMicroException.truncatedMessage();
        }
    }

    public void checkLastTagWas(int i) {
        if (this.lastTag != i) {
            throw InvalidProtocolBufferMicroException.invalidEndTag();
        }
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        return this.currentLimit - (this.totalBytesRetired + this.bufferPos);
    }

    public boolean isAtEnd() {
        return this.bufferPos == this.bufferSize && !refillBuffer(false);
    }

    public void popLimit(int i) {
        this.currentLimit = i;
        recomputeBufferSizeAfterLimit();
    }

    public int pushLimit(int i) {
        if (i < 0) {
            throw InvalidProtocolBufferMicroException.negativeSize();
        }
        int i2 = this.totalBytesRetired + this.bufferPos + i;
        int i3 = this.currentLimit;
        if (i2 > i3) {
            throw InvalidProtocolBufferMicroException.truncatedMessage();
        }
        this.currentLimit = i2;
        recomputeBufferSizeAfterLimit();
        return i3;
    }

    public boolean readBool() {
        return readRawVarint32() != 0;
    }

    public ByteStringMicro readBytes() {
        int readRawVarint32 = readRawVarint32();
        if (readRawVarint32 > this.bufferSize - this.bufferPos || readRawVarint32 <= 0) {
            return ByteStringMicro.copyFrom(readRawBytes(readRawVarint32));
        }
        ByteStringMicro copyFrom = ByteStringMicro.copyFrom(this.buffer, this.bufferPos, readRawVarint32);
        this.bufferPos = readRawVarint32 + this.bufferPos;
        return copyFrom;
    }

    public double readDouble() {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public int readEnum() {
        return readRawVarint32();
    }

    public int readFixed32() {
        return readRawLittleEndian32();
    }

    public long readFixed64() {
        return readRawLittleEndian64();
    }

    public float readFloat() {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public void readGroup(MessageMicro<?> messageMicro, int i) {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferMicroException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        messageMicro.mergeFrom(this);
        checkLastTagWas(WireFormatMicro.makeTag(i, 4));
        this.recursionDepth--;
    }

    public int readInt32() {
        return readRawVarint32();
    }

    public long readInt64() {
        return readRawVarint64();
    }

    public void readMessage(MessageMicro<?> messageMicro) {
        int readRawVarint32 = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferMicroException.recursionLimitExceeded();
        }
        int pushLimit = pushLimit(readRawVarint32);
        this.recursionDepth++;
        messageMicro.mergeFrom(this);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(pushLimit);
    }

    public byte readRawByte() {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(true);
        }
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return this.buffer[i];
    }

    public byte[] readRawBytes(int i) {
        if (i < 0) {
            throw InvalidProtocolBufferMicroException.negativeSize();
        } else if (this.totalBytesRetired + this.bufferPos + i > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferMicroException.truncatedMessage();
        } else if (i <= this.bufferSize - this.bufferPos) {
            byte[] bArr = new byte[i];
            System.arraycopy(this.buffer, this.bufferPos, bArr, 0, i);
            this.bufferPos += i;
            return bArr;
        } else if (i < BUFFER_SIZE) {
            byte[] bArr2 = new byte[i];
            int i2 = this.bufferSize - this.bufferPos;
            System.arraycopy(this.buffer, this.bufferPos, bArr2, 0, i2);
            this.bufferPos = this.bufferSize;
            refillBuffer(true);
            while (i - i2 > this.bufferSize) {
                System.arraycopy(this.buffer, 0, bArr2, i2, this.bufferSize);
                i2 += this.bufferSize;
                this.bufferPos = this.bufferSize;
                refillBuffer(true);
            }
            System.arraycopy(this.buffer, 0, bArr2, i2, i - i2);
            this.bufferPos = i - i2;
            return bArr2;
        } else {
            int i3 = this.bufferPos;
            int i4 = this.bufferSize;
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            this.bufferSize = 0;
            ArrayList<byte[]> arrayList = new ArrayList<>();
            int i5 = i - (i4 - i3);
            while (i5 > 0) {
                byte[] bArr3 = new byte[Math.min(i5, BUFFER_SIZE)];
                int i6 = 0;
                while (i6 < bArr3.length) {
                    int read = 0;
                    try {
                        read = this.input == null ? -1 : this.input.read(bArr3, i6, bArr3.length - i6);
                        if (read == -1) {
                            throw InvalidProtocolBufferMicroException.truncatedMessage();
                        }
                    } catch(IOException e) {
                        throw new InvalidProtocolBufferMicroException(e);
                    }
                    this.totalBytesRetired += read;
                    i6 += read;
                }
                arrayList.add(bArr3);
                i5 -= bArr3.length;
            }
            byte[] bArr4 = new byte[i];
            int i7 = i4 - i3;
            System.arraycopy(this.buffer, i3, bArr4, 0, i7);
            int i8 = i7;
            for (byte[] bytes : arrayList) {
                System.arraycopy(bytes, 0, bArr4, i8, bytes.length);
                i8 += bytes.length;
            }
            return bArr4;
        }
    }

    public int readRawLittleEndian32() {
        return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
    }

    public long readRawLittleEndian64() {
        byte readRawByte = readRawByte();
        byte readRawByte2 = readRawByte();
        return ((((long) readRawByte2) & 255) << 8) | (((long) readRawByte) & 255) | ((((long) readRawByte()) & 255) << 16) | ((((long) readRawByte()) & 255) << 24) | ((((long) readRawByte()) & 255) << 32) | ((((long) readRawByte()) & 255) << 40) | ((((long) readRawByte()) & 255) << 48) | ((((long) readRawByte()) & 255) << 56);
    }

    public int readRawVarint32() {
        byte readRawByte = readRawByte();
        if (readRawByte >= 0) {
            return readRawByte;
        }
        byte b = (byte) (readRawByte & Byte.MAX_VALUE);
        byte readRawByte2 = readRawByte();
        if (readRawByte2 >= 0) {
            return b | (readRawByte2 << 7);
        }
        byte b2 = (byte) (b | ((readRawByte2 & Byte.MAX_VALUE) << 7));
        byte readRawByte3 = readRawByte();
        if (readRawByte3 >= 0) {
            return b2 | (readRawByte3 << 14);
        }
        byte b3 = (byte) (b2 | ((readRawByte3 & Byte.MAX_VALUE) << 14));
        byte readRawByte4 = readRawByte();
        if (readRawByte4 >= 0) {
            return b3 | (readRawByte4 << 21);
        }
        byte b4 = (byte) (b3 | ((readRawByte4 & Byte.MAX_VALUE) << 21));
        byte readRawByte5 = readRawByte();
        byte b5 = (byte) (b4 | (readRawByte5 << 28));
        if (readRawByte5 >= 0) {
            return b5;
        }
        for (int i = 0; i < 5; i++) {
            if (readRawByte() >= 0) {
                return b5;
            }
        }
        throw InvalidProtocolBufferMicroException.malformedVarint();
    }

    public long readRawVarint64() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte readRawByte = readRawByte();
            j |= ((long) (readRawByte & Byte.MAX_VALUE)) << i;
            if ((readRawByte & 128) == 0) {
                return j;
            }
        }
        throw InvalidProtocolBufferMicroException.malformedVarint();
    }

    public int readSFixed32() {
        return readRawLittleEndian32();
    }

    public long readSFixed64() {
        return readRawLittleEndian64();
    }

    public int readSInt32() {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() {
        return decodeZigZag64(readRawVarint64());
    }

    public String readString() {
        int readRawVarint32 = readRawVarint32();
        if (readRawVarint32 > this.bufferSize - this.bufferPos || readRawVarint32 <= 0) {
            return new String(readRawBytes(readRawVarint32), StandardCharsets.UTF_8);
        }
        String str = new String(this.buffer, this.bufferPos, readRawVarint32, StandardCharsets.UTF_8);
        this.bufferPos = readRawVarint32 + this.bufferPos;
        return str;
    }

    public int readTag() {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (this.lastTag != 0) {
            return this.lastTag;
        }
        throw InvalidProtocolBufferMicroException.invalidTag();
    }

    public int readUInt32() {
        return readRawVarint32();
    }

    public long readUInt64() {
        return readRawVarint64();
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = 0;
    }

    public int setRecursionLimit(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + i);
        }
        int i2 = this.recursionLimit;
        this.recursionLimit = i;
        return i2;
    }

    public int setSizeLimit(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative: " + i);
        }
        int i2 = this.sizeLimit;
        this.sizeLimit = i;
        return i2;
    }

    public boolean skipField(int i) {
        switch (WireFormatMicro.getTagWireType(i)) {
            case 0:
                readInt32();
                return true;
            case 1:
                readRawLittleEndian64();
                return true;
            case 2:
                skipRawBytes(readRawVarint32());
                return true;
            case 3:
                skipMessage();
                checkLastTagWas(WireFormatMicro.makeTag(WireFormatMicro.getTagFieldNumber(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                readRawLittleEndian32();
                return true;
            default:
                throw InvalidProtocolBufferMicroException.invalidWireType();
        }
    }

    public void skipMessage() {
        int readTag;
        do {
            readTag = readTag();
            if (readTag == 0) {
                return;
            }
        } while (skipField(readTag));
    }

    public void skipRawBytes(int i) {
        if (i < 0) {
            throw InvalidProtocolBufferMicroException.negativeSize();
        } else if (this.totalBytesRetired + this.bufferPos + i > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferMicroException.truncatedMessage();
        } else if (i <= this.bufferSize - this.bufferPos) {
            this.bufferPos += i;
        } else {
            int i2 = this.bufferSize - this.bufferPos;
            this.totalBytesRetired += i2;
            this.bufferPos = 0;
            this.bufferSize = 0;
            int i3 = i2;
            while (i3 < i) {
                int skip = 0;
                try {
                    skip = this.input == null ? -1 : (int) this.input.skip((long) (i - i3));
                    if (skip <= 0) {
                        throw InvalidProtocolBufferMicroException.truncatedMessage();
                    }
                } catch(IOException e) {
                    throw new InvalidProtocolBufferMicroException(e);
                }
                i3 += skip;
                this.totalBytesRetired = skip + this.totalBytesRetired;
            }
        }
    }
}