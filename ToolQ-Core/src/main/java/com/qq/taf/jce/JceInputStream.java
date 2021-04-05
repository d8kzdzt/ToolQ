package com.qq.taf.jce;

import com.toolq.utils.HexUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map.Entry;

public final class JceInputStream {
  private ByteBuffer bs;
  public JceInputStream() {}

  public static class HeadData {
    public byte type;
    public int tag;

    public void clear() {
      this.type = 0;
      this.tag = 0;
    }
  }

  public JceInputStream(ByteBuffer bs) {
    this.bs = bs;
  }

  public JceInputStream(byte[] bs) {
    this.bs = ByteBuffer.wrap(bs);
  }

  public JceInputStream(byte[] bs, int pos) { this.bs = ByteBuffer.wrap(bs);this.bs.position(pos); }

  public void warp(byte[] bs) {
    wrap(bs);
  }

  public void wrap(byte[] bs) {
    this.bs = ByteBuffer.wrap(bs);
  }

  public static int readHead(HeadData hd, ByteBuffer bb) {
    byte b = bb.get();
    hd.type = ((byte)(b & 0xF));
    hd.tag = ((b & 0xF0) >> 4);
    if (hd.tag == 15) {
      hd.tag = (bb.get() & 0xFF);
      return 2;
    }
    return 1;
  }

  public void readHead(HeadData hd) {
    readHead(hd, this.bs);
  }

  private int peakHead(HeadData hd){
    return readHead(hd, this.bs.duplicate());
  }

  private void skip(int len) {
    this.bs.position(this.bs.position() + len);
  }

  public boolean skipToTag(int tag) {
      HeadData hd = new HeadData();
      for (;;) {
        int len = peakHead(hd);
        if ((tag <= hd.tag) || (hd.type == 11)) {
          return tag == hd.tag;
        }
        skip(len);
        skipField(hd.type);
      }
  }

  public void skipToStructEnd() {
    HeadData hd = new HeadData();
    readHead(hd);
    skipField(hd.type);
    while(hd.type != 11) {
      readHead(hd);
      skipField(hd.type);
    }
  }

  private void skipField() {
    HeadData hd = new HeadData();
    readHead(hd);
    skipField(hd.type);
  }

  private void skipField(byte type) {
    switch (type) {
      case 0:
        skip(1);
        break;
      case 1:
        skip(2);
        break;
      case 2:
      case 4:
        skip(4);
        break;
      case 3:
      case 5:
        skip(8);
        break;
      case 6: {
        int len = this.bs.get();
        if (len < 0) {
          len += 256;
        }
        skip(len);
        break;
      }
      case 7:
        skip(this.bs.getInt());
        break;
      case 8: {
        int size = read(0, 0, true);
        for (int i = 0; i < size * 2; i++) {
          skipField();
        }
        break;
      }
      case 9: {
        int size = read(0, 0, true);
        for (int i = 0; i < size; i++) {
          skipField();
        }
        break;
      }
      case 13: {
        HeadData hd = new HeadData();
        readHead(hd);
        if (hd.type != 0) {
          throw new JceDecodeException("skipField with invalid type, type value: " + type + ", " + hd.type);
        }
        int size = read(0, 0, true);
        skip(size);
        break;
      }
      case 10:
        skipToStructEnd();
        break;
      case 11:
      case 12:
        break;
      default:
        throw new JceDecodeException("invalid type.");
    }
  }

  public boolean read(boolean b, int tag, boolean isRequire) {
    byte c = read((byte)0, tag, isRequire);
    return c != 0;
  }

  public byte read(byte c, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          c = 0;
          break;
        case 0:
          c = this.bs.get();
          break;
        default:
          throw new JceDecodeException("type mismatch.(byte");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return c;
  }

  public short read(short n, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          n = 0;
          break;
        case 0:
          n = (short)this.bs.get();
          break;
        case 1:
          n = this.bs.getShort();
          break;
        default:
          throw new JceDecodeException("type mismatch.(short");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return n;
  }

  public int read(int n, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          n = 0;
          break;
        case 0:
          n = this.bs.get();
          break;
        case 1:
          n = this.bs.getShort();
          break;
        case 2:
          n = this.bs.getInt();
          break;
        default:
          throw new JceDecodeException("type mismatch.(int");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return n;
  }

  public long read(long n, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          n = 0L;
          break;
        case 0:
          n = this.bs.get();
          break;
        case 1:
          n = this.bs.getShort();
          break;
        case 2:
          n = this.bs.getInt();
          break;
        case 3:
          n = this.bs.getLong();
          break;
        default:
          throw new JceDecodeException("type mismatch.(long");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return n;
  }

  public float read(float n, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          n = 0.0F;
          break;
        case 4:
          n = this.bs.getFloat();
          break;
        default:
          throw new JceDecodeException("type mismatch.(float");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return n;
  }

  public double read(double n, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 12:
          n = 0.0D;
          break;
        case 4:
          n = this.bs.getFloat();
          break;
        case 5:
          n = this.bs.getDouble();
          break;
        default:
          throw new JceDecodeException("type mismatch.(double");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return n;
  }

  public String readByteString(String s, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 6: {
          int len = this.bs.get();
          if (len < 0) {
            len += 256;
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          s = HexUtil.bytesToHexString(ss);
          break;
        }
        case 7: {
          int len = this.bs.getInt();
          if ((len > 104857600) || (len < 0)) {
            throw new JceDecodeException("String too long: " + len);
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          s = HexUtil.bytesToHexString(ss);
          break;
        }
        default:
          throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return s;
  }

  public String read(String s, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 6: {
          int len = this.bs.get();
          if (len < 0) {
            len += 256;
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          try {
            return new String(ss, this.sServerEncoding);
          } catch (UnsupportedEncodingException e) {
            return new String(ss);
          }}
        case 7: {
          int len2 = this.bs.getInt();
          if (len2 > 104857600 || len2 < 0 || len2 > this.bs.capacity()) {
            throw new JceDecodeException("String too long: " + len2);
          }
          byte[] ss2 = new byte[len2];
          this.bs.get(ss2);
          try {
            return new String(ss2, this.sServerEncoding);
          } catch (UnsupportedEncodingException e2) {
            return new String(ss2);
          }}
        default:
          throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return s;
  }

  public String readString(int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 6:
          int len = this.bs.get();
          if (len < 0) {
            len += 256;
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          try {
            return new String(ss, this.sServerEncoding);
          } catch (UnsupportedEncodingException e) {
            return new String(ss);
          }
        case 7:
          int len2 = this.bs.getInt();
          if (len2 > 104857600 || len2 < 0 || len2 > this.bs.capacity()) {
            throw new JceDecodeException("String too long: " + len2);
          }
          byte[] ss2 = new byte[len2];
          this.bs.get(ss2);
          try {
            return new String(ss2, this.sServerEncoding);
          } catch (UnsupportedEncodingException e2) {
            return new String(ss2);
          }
        default:
          throw new JceDecodeException("type mismatch.(string)tag="+tag);
      }
    } else if (!isRequire) {
      return null;
    } else {
      throw new JceDecodeException("require field not exist.");
    }
  }

  private String readStringC2(int tag, boolean isRequire) {
    String s = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      switch (hd.type) {
        case 6: {
          int len = this.bs.get();
          if (len < 0) {
            len += 256;
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          try {
            s = new String(ss, this.sServerEncoding);
          } catch (UnsupportedEncodingException e) {
            s = new String(ss);
          }
        }
        case 7: {
          int len = this.bs.getInt();
          if ((len > 104857600) || (len < 0)) {
            throw new JceDecodeException("String too long: " + len);
          }
          byte[] ss = new byte[len];
          this.bs.get(ss);
          try {
            s = new String(ss, this.sServerEncoding);
          } catch (UnsupportedEncodingException e) {
            s = new String(ss);
          }
        }
        default:
          throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return s;
  }

  public String[] read(String[] s, int tag, boolean isRequire) {
    return (String[])readArray(s, tag, isRequire);
  }

  public Map<String, String> readStringMap(int tag, boolean isRequire) {
    HashMap<String, String> mr = new HashMap<>();
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 8) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        for(int i = 0; i < size; i++) {
          String k = readString(0, true);
          String v = readString(1, true);
          mr.put(k, v);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return mr;
  }

  public <K, V> HashMap<K, V> readMap(Map<K, V> m, int tag, boolean isRequire) {
    return (HashMap<K, V>) readMap(new HashMap(), m, tag, isRequire);
  }

  private <K, V> Map<K, V> readMap(Map<K, V> mr, Map<K, V> m, int tag, boolean isRequire) {
    if ((m == null) || (m.isEmpty())) {
      return new HashMap<>();
    }
    Iterator<Entry<K, V>> it = m.entrySet().iterator();
    Entry<K, V> en = it.next();
    K mk = en.getKey();
    V mv = en.getValue();
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 8) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        for(int i = 0; i < size; i++) {
          K k = (K) readV2(mk, 0, true);
          V v = (V) readV2(mv, 1, true);
          mr.put(k, v);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return mr;
  }

  public List readList(int tag, boolean isRequire) {
    List lr = new ArrayList();
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        for(int i = 0; i < size; i++) {
          HeadData subH = new HeadData();
          readHead(subH);
          switch(subH.type) {
            case 0:
              skip(1);
              break;
            case 1:
              skip(2);
              break;
            case 2:
            case 4:
              skip(4);
              break;
            case 3:
            case 5:
              skip(8);
              break;
            case 6: {
              int len = this.bs.get();
              if(len < 0) {
                len += 256;
              }
              skip(len);

              break;
            }
            case 7:
              skip(this.bs.getInt());
              break;
            case 8:
            case 9:
              break;
            case 10:
              try {
                Class<?> newoneClass = Class.forName(JceStruct.class.getName());
                Constructor<?> cons = newoneClass.getConstructor();
                JceStruct struct = (JceStruct) cons.newInstance(new Object[0]);
                struct.readFrom(this);
                skipToStructEnd();
                lr.add(struct);
              } catch(Exception e) {
                e.printStackTrace();
                throw new JceDecodeException("type mismatch." + e);
              }
            case 12:
              lr.add(0);
              break;
            case 11:
            default:
              throw new JceDecodeException("type mismatch.");
          }
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public boolean[] read(boolean[] l, int tag, boolean isRequire) {
    boolean[] lr = (boolean[])null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new boolean[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public byte[] read(byte[] l, int tag, boolean isRequire) {
    byte[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 13) {
        HeadData hh = new HeadData();
        readHead(hh);
        if(hh.type != 0) {
          throw new JceDecodeException("type mismatch, tag: " + tag + ", type: " + hd.type + ", " + hh.type);
        }
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("invalid size, tag: " + tag + ", type: " + hd.type + ", " + hh.type + ", size: " + size);
        }
        lr = new byte[size];
        this.bs.get(lr);
      } else {
        if(hd.type == 9) {
          int size = read(0, 0, true);
          if(size < 0) {
            throw new JceDecodeException("size invalid: " + size);
          }
          lr = new byte[size];
          for(int i = 0; i < size; i++) {
            lr[i] = read(lr[0], 0, true);
          }
        } else {
          throw new JceDecodeException("type mismatch.");
        }
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public short[] read(short[] l, int tag, boolean isRequire) {
    short[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new short[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public int[] read(int[] l, int tag, boolean isRequire) {
    int[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new int[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public long[] read(long[] l, int tag, boolean isRequire) {
    long[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new long[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public float[] read(float[] l, int tag, boolean isRequire) {
    float[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new float[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public double[] read(double[] l, int tag, boolean isRequire) {
    double[] lr = null;
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        lr = new double[size];
        for(int i = 0; i < size; i++) {
          lr[i] = read(lr[0], 0, true);
        }
      } else {
        throw new JceDecodeException("type mismatch.");
      }
    } else if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return lr;
  }

  public <T> T[] readArray(T[] l, int tag, boolean isRequire) {
    if ((l == null) || (l.length == 0)) throw new JceDecodeException("unable to get type of key and value.");
    return readArrayImpl(l[0], tag, isRequire);
  }

  public <T> List<T> readArray(List<T> l, int tag, boolean isRequire) {
    if ((l == null) || (l.isEmpty())) {
      return new ArrayList<>();
    }
    T[] tt = readArrayImpl(l.get(0), tag, isRequire);
    if (tt == null) {
      return null;
    }
    return new ArrayList<>(Arrays.asList(tt));
  }

  private <T> T[] readArrayImpl(T mt, int tag, boolean isRequire) {
    if (skipToTag(tag)) {
      HeadData hd = new HeadData();
      readHead(hd);
      if(hd.type == 9) {
        int size = read(0, 0, true);
        if(size < 0) {
          throw new JceDecodeException("size invalid: " + size);
        }
        T[] lr = (T[]) Array.newInstance(mt.getClass(), size);
        for(int i = 0; i < size; i++) {
          T t = (T) readV2(mt, 0, true);
          lr[i] = t;
        }
        return lr;
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (isRequire) {
      throw new JceDecodeException("require field not exist.");
    }
    return null;
  }

  public JceStruct directRead(JceStruct o, int tag, boolean isRequire) {
    JceStruct ref = null;
    if(!skipToTag(tag)) {
      if (isRequire) {
        throw new JceDecodeException("require field not exist.");
      }
    } else {
      try {
        ref = o.newInit();
      } catch (Exception e) {
        throw new JceDecodeException(e.getMessage());
      }
      HeadData hd = new HeadData();
      readHead(hd);
      if (hd.type != 10) {
        throw new JceDecodeException("type mismatch.");
      }
      ref.readFrom(this);
      skipToStructEnd();
    }
    return ref;
  }

  public JceStruct read(JceStruct o, int tag, boolean isRequire) {
    JceStruct ref = null;
    if(!skipToTag(tag)) {
      if (isRequire) {
        throw new JceDecodeException("require field not exist.");
      }
    } else {
      try {
        ref = o.getClass().newInstance();
      } catch (Exception e) {
        throw new JceDecodeException(e.getMessage());
      }
      HeadData hd = new HeadData();
      readHead(hd);
      if (hd.type != 10) {
        throw new JceDecodeException("type mismatch. type is "+hd.type);
      }
      ref.readFrom(this);
      skipToStructEnd();
    }
    return ref;
  }

  public JceStruct[] read(JceStruct[] o, int tag, boolean isRequire) {
    return readArray(o, tag, isRequire);
  }

  public <T> Object readV2(T o, int tag, boolean isRequire) {
    if ((o instanceof Byte)) {
      return read((byte) 0, tag, isRequire);
    }
    if ((o instanceof Boolean)) {
      return read(false, tag, isRequire);
    }
    if ((o instanceof Short)) {
      return read((short) 0, tag, isRequire);
    }
    if ((o instanceof Integer)) {
      return read(0, tag, isRequire);
    }
    if ((o instanceof Long)) {
      return read(0L, tag, isRequire);
    }
    if ((o instanceof Float)) {
      return read(0.0F, tag, isRequire);
    }
    if ((o instanceof Double)) {
      return read(0.0D, tag, isRequire);
    }
    if ((o instanceof String)) {
      return readString(tag, isRequire);
    }
    if ((o instanceof Map)) {
      return readMap((Map)o, tag, isRequire);
    }
    if ((o instanceof List)) {
      return readArray((List)o, tag, isRequire);
    }
    if ((o instanceof JceStruct)) {
      return read((JceStruct)o, tag, isRequire);
    }
    if (o.getClass().isArray()) {
      if (((o instanceof byte[])) || ((o instanceof Byte[]))) {
        return read((byte[])null, tag, isRequire);
      }
      if ((o instanceof boolean[])) {
        return read((boolean[])null, tag, isRequire);
      }
      if ((o instanceof short[])) {
        return read((short[])null, tag, isRequire);
      }
      if ((o instanceof int[])) {
        return read((int[])null, tag, isRequire);
      }
      if ((o instanceof long[])) {
        return read((long[])null, tag, isRequire);
      }
      if ((o instanceof float[])) {
        return read((float[])null, tag, isRequire);
      }
      if ((o instanceof double[])) {
        return read((double[])null, tag, isRequire);
      }
      return readArray((Object[])o, tag, isRequire);
    }
    throw new JceDecodeException("read object error: unsupport type.");
  }

  protected String sServerEncoding = "UTF-8";
  
  public int setServerEncoding(String se) {
    this.sServerEncoding = se;
    return 0;
  }

  public ByteBuffer getBs() {
    return this.bs;
  }
}
