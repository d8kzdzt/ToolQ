package com.qq.taf.client;
import java.nio.ByteBuffer;

/**
 * @author 秋洛
 */
public class PowValue {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int[] f;
    public int g;
    public byte[] h;
    public int i;
    public byte[] j;
    public int k;
    public byte[] l;
    public int m;
    public byte[] n;
    public int o;
    public int p;

    public int init(byte[] bArr) {
        int i2;
        int i3;
        int i4;
        int i5;
        this.a = bArr[0];
        this.b = bArr[1];
        this.c = bArr[2];
        this.d = bArr[3];
        this.e = bArr[4];
        this.f = new int[3];
        int i6 = 5;
        int i7 = 0;
        while (i7 < 3) {
            this.f[i7] = bArr[i6];
            i7++;
            i6++;
        }
        int i8 = i6 + 1;
        int i9 = i8 + 1;
        this.g = a(bArr[i6], bArr[i8]);
        if (this.g > 0) {
            this.h = new byte[this.g];
            i2 = i9;
            int i10 = 0;
            while (i10 < this.g) {
                this.h[i10] = bArr[i2];
                i10++;
                i2++;
            }
        } else {
            i2 = i9;
        }
        int i11 = i2 + 1;
        int i12 = i11 + 1;
        this.i = a(bArr[i2], bArr[i11]);
        if (this.i > 0) {
            this.j = new byte[this.i];
            i3 = i12;
            int i13 = 0;
            while (i13 < this.i) {
                this.j[i13] = bArr[i3];
                i13++;
                i3++;
            }
        } else {
            i3 = i12;
        }
        int i14 = i3 + 1;
        int i15 = i14 + 1;
        this.k = a(bArr[i3], bArr[i14]);
        if (this.k > 0) {
            this.l = new byte[this.k];
            i4 = i15;
            int i16 = 0;
            while (i16 < this.k) {
                this.l[i16] = bArr[i4];
                i16++;
                i4++;
            }
        } else {
            i4 = i15;
        }
        if (this.e == 1) {
            int i17 = i4 + 1;
            int i18 = i17 + 1;
            this.m = a(bArr[i4], bArr[i17]);
            if (this.m > 0) {
                this.n = new byte[this.m];
                i5 = i18;
                int i19 = 0;
                while (i19 < this.m) {
                    this.n[i19] = bArr[i5];
                    i19++;
                    i5++;
                }
            } else {
                i5 = i18;
            }
            this.o = a(bArr, i5);
            int i20 = i5 + 4;
            this.p = a(bArr, i20);
        }
        return 0;
    }

    public int a(byte b2, byte b3) {
        return ByteBuffer.wrap(new byte[]{b2, b3}).getShort();
    }

    public int a(byte[] bArr, int i2) {
        return ByteBuffer.wrap(new byte[]{bArr[i2], bArr[i2 + 1], bArr[i2 + 2], bArr[i2 + 3]}).getInt();
    }

    public byte[] a() {
        ByteBuffer allocate = ByteBuffer.allocate(4096);
        allocate.put((byte) this.a);
        allocate.put((byte) this.b);
        allocate.put((byte) this.c);
        allocate.put((byte) this.d);
        allocate.put((byte) this.e);
        for (int i2 : this.f) {
            allocate.put((byte) i2);
        }
        allocate.putShort((short) this.g);
        if (this.g > 0) {
            allocate.put(this.h);
        }
        allocate.putShort((short) this.i);
        if (this.i > 0) {
            allocate.put(this.j);
        }
        allocate.putShort((short) this.k);
        if (this.k > 0) {
            allocate.put(this.l);
        }
        if (this.e == 1) {
            allocate.putShort((short) this.m);
            allocate.put(this.n);
            allocate.putInt(this.o);
            allocate.putInt(this.p);
        }
        return allocate.array();
    }
}
