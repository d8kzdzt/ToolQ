package com.toolq.qq.ecdh;

import com.toolq.utils.HexUtil;
import com.toolq.utils.MD5;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public final class ECDHCrypt {
    private static ECDHCrypt Instance;

    public static ECDHCrypt getInstance() {
        if(Instance == null) Instance = new ECDHCrypt();
        return Instance;
    }

    /**
     * 是否开启ECDH兼容模式
     *
     * 主要应用于没有ECDH算法的Java环境
     */
    private static boolean unSafeTencent = true;

    public final String DEFAULT_PUB_KEY;
    public final String DEFAULT_SHARE_KEY;
    static String SvrPubKey = "04EBCA94D733E399B2DB96EACDD3F69A8BB0F74224E2B44E3357812211D2E62EFBC91BB553098E25E33A799ADC7F76FEB208DA7C6522CDB0719A305180CC54A82E";
    static final String X509Prefix = "3059301306072a8648ce3d020106082a8648ce3d030107034200";
    public static byte[] _c_pri_key = new byte[0];
    public static byte[] _c_pub_key = new byte[0];
    private static byte[] _g_share_key = new byte[0];
    private static final int ecdhSize = 67;
    private static boolean initFlg = false;
    public static PrivateKey pkcs8PrivateKey;
    private static int sKeyVersion = 1;
    private static boolean userOpenSSLLib = true;
    public static PublicKey x509PublicKey;

    public native int GenECDHKeyEx(String sPubKey, String cPubKey, String cPriKey);

    public int GenECDHKeyExV2(String sPubKey, String cPubKey, String cPriKey) {
        if (sPubKey.length() == 0) return -0x10;
        return -1;
    }

    private ECDHCrypt() {
        DEFAULT_PUB_KEY = "04803E9940F3FD8E6474C3CC6994A2F972B1DA6BFDE8DDB4E775E36AB4E439DB8EA7A0E6CAFC722089F4921DFEAEFBA0F56932F3E6AA3ECF81154FD230AF32B18F";
        DEFAULT_SHARE_KEY = "3F539B2549AB1F71421F2C3A66298D05";
        setPubKey("04803E9940F3FD8E6474C3CC6994A2F972B1DA6BFDE8DDB4E775E36AB4E439DB8EA7A0E6CAFC722089F4921DFEAEFBA0F56932F3E6AA3ECF81154FD230AF32B18F", 2);
        if(initShareKey() == 0) {
            unSafeTencent = false;
        }
    }

    public int GenereateKey() {
        int GenECDHKeyEx;
        try {
            synchronized (ECDHCrypt.class) {
                GenECDHKeyEx = GenECDHKeyEx(SvrPubKey, "", "");
            }
            return GenECDHKeyEx;
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            return -1;
        } catch (RuntimeException runtimeException) {
            return -2;
        } catch (Exception exception) {
            return -3;
        } catch (Error error) {
            return -4;
        }
    }

    public byte[] get_c_pub_key() {
        if(unSafeTencent) return HexUtil.hexStringToBytes("04803E9940F3FD8E6474C3CC6994A2F972B1DA6BFDE8DDB4E775E36AB4E439DB8EA7A0E6CAFC722089F4921DFEAEFBA0F56932F3E6AA3ECF81154FD230AF32B18F");
        return _c_pub_key;
    }

    public void set_c_pub_key(byte[] bArr) {
        if (bArr != null) {
            _c_pub_key = Arrays.copyOf(bArr, bArr.length);
        } else {
            _c_pub_key = new byte[0];
        }
    }

    public void set_c_pri_key(byte[] bArr) {
        if (bArr != null) {
            _c_pri_key = Arrays.copyOf(bArr, bArr.length);
        } else {
            _c_pri_key = new byte[0];
        }
    }

    public byte[] get_g_share_key() {
        if(unSafeTencent) return HexUtil.hexStringToBytes("3F539B2549AB1F71421F2C3A66298D05");
        return _g_share_key;
    }

    public void set_g_share_key(byte[] bArr) {
        if (bArr != null) {
            _g_share_key = Arrays.copyOf(bArr, bArr.length);
        } else {
            _g_share_key = new byte[0];
        }
    }

    public byte[] calShareKeyMd5ByPeerPublicKey(byte[] bArr) {
        if (userOpenSSLLib) {
            return calShareKeyByOpenSSL(HexUtil.bytesToHexString(_c_pri_key), HexUtil.bytesToHexString(_c_pub_key), HexUtil.bytesToHexString(bArr));
        }
        return calShareKeyByBouncycastle(bArr);
    }

    private byte[] calShareKeyByOpenSSL(String str, String str2, String str3) {
        if (GenECDHKeyEx(str3, str2, str) == 0) {
            return _g_share_key;
        }
        return null;
    }

    private PublicKey constructX509PublicKey(String str) {
        try {
            return KeyFactory.getInstance("EC", "BC").generatePublic(new X509EncodedKeySpec(HexUtil.hexStringToBytes(str)));
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] calShareKeyByBouncycastle(byte[] bArr) {
        try {
            PublicKey constructX509PublicKey = constructX509PublicKey(X509Prefix + HexUtil.bytesToHexString(bArr));
            KeyAgreement instance = KeyAgreement.getInstance("ECDH", "BC");
            instance.init(pkcs8PrivateKey);
            instance.doPhase(constructX509PublicKey, true);
            byte[] generateSecret = instance.generateSecret();
            byte[] bArr2 = new byte[16];
            System.arraycopy(generateSecret, 0, bArr2, 0, 16);
            return MD5.toMD5Byte(bArr2);
        } catch (ExceptionInInitializerError e) {
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private int initShareKeyByOpenSSL() {
        if (_c_pub_key == null || _c_pub_key.length == 0 || _c_pri_key == null || _c_pri_key.length == 0 || _g_share_key == null || _g_share_key.length == 0) {
            return -2;
        }
        return 0;
    }

    private int initShareKeyByBouncycastle() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("EC", "BC");
            instance.initialize(new ECGenParameterSpec("prime256v1"));
            KeyPair genKeyPair = instance.genKeyPair();
            PublicKey publicKey = genKeyPair.getPublic();
            byte[] encoded = publicKey.getEncoded();
            PrivateKey privateKey = genKeyPair.getPrivate();
            privateKey.getEncoded();
            PublicKey constructX509PublicKey = constructX509PublicKey(X509Prefix + HexUtil.bytesToHexString(HexUtil.hexStringToBytes(SvrPubKey)));
            KeyAgreement instance2 = KeyAgreement.getInstance("ECDH", "BC");
            instance2.init(privateKey);
            instance2.doPhase(constructX509PublicKey, true);
            byte[] generateSecret = instance2.generateSecret();
            byte[] bArr = new byte[16];
            System.arraycopy(generateSecret, 0, bArr, 0, 16);
            _g_share_key = MD5.toMD5Byte(bArr);
            _c_pub_key = new byte[65];
            System.arraycopy(encoded, 26, _c_pub_key, 0, 65);
            x509PublicKey = publicKey;
            pkcs8PrivateKey = privateKey;
            return 0;
        } catch (ExceptionInInitializerError e) {
            return -1;
        } catch (Throwable ignore) {
            return -2;
        }
    }

    public int initShareKeyByDefault() {
        _c_pub_key = HexUtil.hexStringToBytes(DEFAULT_PUB_KEY);
        _g_share_key = HexUtil.hexStringToBytes(DEFAULT_SHARE_KEY);
        return 0;
    }

    public int initShareKey() {
        if (initFlg) {
            return 0;
        }
        initFlg = true;
        if (initShareKeyByOpenSSL() == 0) {
            userOpenSSLLib = true;
            return 0;
        } else if (initShareKeyByBouncycastle() != 0) {
             return initShareKeyByDefault();
        } else {
            userOpenSSLLib = false;
            return 0;
        }
    }

    public void setPubKey(String str, int i) {
        try {
            if (str != null && !str.equals("") && i > 0) {
                SvrPubKey = str;
                sKeyVersion = i;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public int get_pub_key_ver() {
        return sKeyVersion;
    }
}

