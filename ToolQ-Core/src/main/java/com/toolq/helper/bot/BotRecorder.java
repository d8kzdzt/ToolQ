package com.toolq.helper.bot;

import com.qq.jce.wup.WupHexUtil;
import com.toolq.qq.dataClass.BytesKey;
import com.toolq.qq.dataClass.Nick;
import com.toolq.qq.dataClass.StringKey;
import com.toolq.qq.dataClass.UserSt;
import com.toolq.utils.RandomUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luoluo
 * @date 2020/10/3 21:22
 */
public final class BotRecorder {
	// 资源服务器SEQ
	private final AtomicInteger highWaySeq = new AtomicInteger(new Random().nextInt(100000));
	// 信息请求SEQ
	private final AtomicInteger largeSeq = new AtomicInteger(0);
	// 普通推送SEQ
	private final AtomicInteger msfSsoSeq = new AtomicInteger(0);
	// JCE请求ID
	private final AtomicInteger requestId = new AtomicInteger(1017089978);
	// 信息推送ID
	private final AtomicInteger messageSeqId = new AtomicInteger(22911);

	/**
	 * 保存一些PSkey
	 */
	public final HashMap<String, HashMap<String, StringKey>> pSKeyAndPt4Token = new HashMap<>();

	/**
	 * 机器人登录的时间
	 */
	private final long robotLoginTime = System.currentTimeMillis();
	/**
	 * 最后一次私聊信息同步时间（本地）
	 * 同步成功会同步时间到腾讯！！！
	 */
	private long lastC2CTime = 0;

	/**
	 * 连接腾讯服务器的key
	 */
	private byte[] d2Key = new byte[16];
	private byte[] da2;
	private BytesKey d2;
	private StringKey sKey;
	private StringKey pSkey;
	private final UserSt userStSig = new UserSt(
			new BytesKey(new byte[0], 0, 0),
			new BytesKey(new byte[0], 0, 0),
			new BytesKey(new byte[0], 0, 0)
	);

	/**
	 * 上传文件的临时key
	 */
	private byte[] uKey = WupHexUtil.hexStr2Bytes("CA92740EE4F7518681766ADB0BCDF780A5973CEE213E47C96D8EEF69FF50309EB00949DB8A4F9AE6A0DDF50981DF1070F5A49FC0E544447DC66809F8F3BAB24A7160B7E2C432BFC9F11366D37C7599D4B36205C3CF368A69329766C127F480F0107B15113C93038E");
	private byte[] syncCookie = {};
	private byte[] pubAccCookie = {};
	private byte[] msgCtrlBuf = {};

	private int face = 0;
	private int gender = 0;
	private String nick = "";
	private int age = 0;

	private long last_read_time = 0;

	/**
	 * 缓存群名片队列
	 * （以防止每次艾特都去获取名字）
	 */
	private final HashMap<Long, HashMap<Long, Nick>> cacheNicks = new HashMap<>();

	public int nextHwSeq() {
		int seq = highWaySeq.incrementAndGet();
		if (seq > 1000000) {
			highWaySeq.set(new Random().nextInt(1060000));
		}
		return seq;
	}

	public int nextLargeSeq(){
		int seq = largeSeq.addAndGet(4);
		if (seq > 1000000) {
			largeSeq.set(0);
		}
		return seq;
	}

	public int nextSsoSeq() {
		int seq = msfSsoSeq.addAndGet(2);
		if (seq > 1000000) {
			msfSsoSeq.set(1000);
		}
		return seq;
	}

	public int nextRequestId() {
		int id = requestId.addAndGet(RandomUtil.randInt(2, 8));
		if (id > Integer.MAX_VALUE - 100) {
			requestId.set(1017089978);
		}
		return id;
	}



	public int nextMessageSeq() {
		int seq = messageSeqId.addAndGet(4);
		if (seq > 1000000) {
			messageSeqId.set(new Random().nextInt(22911));
		}
		return seq;
	}

	public byte[] getD2Key() {
		return Arrays.copyOf(this.d2Key, this.d2Key.length);
	}

	public void setD2Key(byte[] d2Key) {
		this.d2Key = Arrays.copyOf(d2Key, d2Key.length);
	}

	public BytesKey getD2() {
		return d2;
	}

	public void setD2(byte[] d2, long creationTime, long expiredTime) {
		this.d2 = new BytesKey(d2, creationTime, expiredTime);
	}

	public StringKey getsKey() {
		return sKey;
	}

	public void setsKey(String sKey, long creationTime, long expiredTime) {
		this.sKey = new StringKey(sKey, creationTime, expiredTime);
	}

	public StringKey getpSkey() {
		return pSkey;
	}

	public void setpSkey(String pSkey, long creationTime, long expiredTime) {
		this.pSkey = new StringKey(pSkey, creationTime, expiredTime);
	}

	public HashMap<String, HashMap<String, StringKey>> getpSKeyAndPt4Token() {
		return pSKeyAndPt4Token;
	}

	public long getLast_read_time() {
		return last_read_time;
	}

	public void setLast_read_time(long last_read_time) {
		this.last_read_time = last_read_time;
	}

	public byte[] getPubAccCookie() {
		return pubAccCookie;
	}

	public void setPubAccCookie(byte[] pubAccCookie) {
		if(pubAccCookie != null)
			this.pubAccCookie = pubAccCookie;
	}

	public byte[] getSyncCookie() {
		return syncCookie;
	}

	public void setSyncCookie(byte[] syncCookie) {
		if(syncCookie != null)
			this.syncCookie = syncCookie;
	}

	public byte[] getMsgCtrlBuf() {
		return msgCtrlBuf;
	}

	public void setMsgCtrlBuf(byte[] msgCtrlBuf) {
		if(msgCtrlBuf != null)
			this.msgCtrlBuf = msgCtrlBuf;
	}

	public long getRobotLoginTime() {
		return robotLoginTime;
	}

	public long getLastC2CTime() {
		return lastC2CTime;
	}

	public void setLastC2CTime(long lastC2CTime) {
		this.lastC2CTime = lastC2CTime;
	}

	public byte[] getuKey() {
		return uKey;
	}

	public void setuKey(byte[] uKey) {
		this.uKey = uKey;
	}

	public Nick getNick(long uin, long groupId) {
		if(cacheNicks.containsKey(uin)) {
			Map<Long, Nick> nickMap = cacheNicks.get(uin);
			if(nickMap.containsKey(groupId)) {
				return nickMap.get(groupId);
			}
		}
		return null;
	}

	public void setNick(long uin, long groupId, String nickName) {
		HashMap<Long, Nick> map;
		if(cacheNicks.containsKey(uin)) {
			map = cacheNicks.get(uin);
		} else {
			map = new HashMap<>();
		}
		Nick nick = new Nick(nickName, System.currentTimeMillis() + 1000 * 60);
		map.put(groupId, nick);
		cacheNicks.put(uin, map);
	}

	public UserSt getUserStSig() {
		return userStSig;
	}

	public byte[] getDa2() {
		return da2;
	}

	public void setDa2(byte[] da2) {
		this.da2 = da2;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int face) {
		this.face = face;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
