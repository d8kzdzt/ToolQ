package com.qq.taf.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author 秋洛
 */
public class ClientPow {
    public byte[] getPow(byte[] bArr) {
        return getPowByJava(bArr);
    }

    public byte[] getPowByJava(byte[] bArr) {
        int a2;
        byte[] bArr2 = new byte[0];
        PowValue aVar = new PowValue();
        int a3 = aVar.init(bArr);
        if (a3 == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            aVar.p = 0;
            aVar.o = 0;
            switch (aVar.b) {
                case 1:
                    a2 = b(aVar);
                    break;
                case 2:
                    a2 = a(aVar);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + aVar.b);
            }
            if (a2 >= 0) {
                aVar.o = (int) (System.currentTimeMillis() - currentTimeMillis);
                aVar.e = 1;
                aVar.p = a2;
                bArr2 = aVar.a();
            }
        }
        return bArr2;
    }
    
    private int a(PowValue aVar) {
        byte[] bArr = new byte[32];
        byte[] bArr2 = new byte[aVar.h.length];
        System.arraycopy(aVar.h, 0, bArr2, 0, aVar.h.length);
        BigInteger bigInteger = new BigInteger(bArr2);
        while (true) {
            if (aVar.c == 1) {
                sha256(bArr2, bArr);
                if (Arrays.equals(bArr, aVar.j)) {
                    byte[] copyOf = Arrays.copyOf(bArr2, bArr2.length);
                    aVar.n = copyOf;
                    aVar.m = copyOf.length;
                    return aVar.p;
                }
                aVar.p++;
                bigInteger = bigInteger.add(BigInteger.ONE);
                byte[] byteArray = bigInteger.toByteArray();
                if (byteArray.length > bArr2.length) {
                    return -1;
                }
                System.arraycopy(byteArray, 0, bArr2, 0, byteArray.length);
            } else {
                return -1;
            }
        }
    }
    
    private void sha256(byte[] bArr, byte[] bArr2) {
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException ignored) { }
        assert instance != null;
        instance.update(bArr);
        byte[] digest = instance.digest();
        System.arraycopy(digest, 0, bArr2, 0, digest.length);
    }
    
    private int b(PowValue aVar) {
        byte[] bArr = new byte[32];
        byte[] bArr2 = new byte[aVar.h.length];
        System.arraycopy(aVar.h, 0, bArr2, 0, aVar.h.length);
        BigInteger bigInteger = new BigInteger(bArr2);
        while (aVar.c == 1) {
            sha256(bArr2, bArr);
            if (calc(bArr, aVar.d) == 0) {
                byte[] copyOf = Arrays.copyOf(bArr2, bArr2.length);
                aVar.n = copyOf;
                aVar.m = copyOf.length;
                return aVar.p;
            }
            aVar.p++;
            bigInteger = bigInteger.add(BigInteger.ONE);
            byte[] byteArray = bigInteger.toByteArray();
            if (byteArray.length > bArr2.length) {
                return -1;
            }
            System.arraycopy(byteArray, 0, bArr2, 0, byteArray.length);
        }
        return -1;
    }

    public int max_calc = 32;
    
    int calc(byte[] bArr, int i) {
        if (i > max_calc) {
            return 1;
        }
        int i2 = 255;
        int i3 = 0;
        while (i2 >= 0 && i3 < i) {
            if ((bArr[i2 / 8] & (1 << (i2 % 8))) != 0) {
                return 2;
            }
            i2--;
            i3++;
        }
        return 0;
    }
}
