package com.toolq;

import com.toolq.helper.android.Android;
import com.toolq.helper.bot.*;
import com.toolq.helper.bot.packet.Packet;
import com.toolq.helper.bot.packet.Waiter;
import com.toolq.helper.bot.protocol.HttpQQ;
import com.toolq.helper.jce.JceHelper;
import com.toolq.helper.logger.TLog;
import com.toolq.helper.packet.ByteReader;
import com.toolq.helper.socket.SocketClient;
import com.toolq.helper.thread.ResultThread;
import com.toolq.helper.thread.ThreadManager;
import com.toolq.helper.vector.SafeVector;
import com.toolq.qq.dataClass.BytesKey;
import com.toolq.qq.dataClass.StringKey;
import com.toolq.qq.ecdh.ECDHCrypt;
import com.toolq.qq.protocol.AndroidQQ;
import com.toolq.qq.protocol.jce.statsvc.Register;
import com.toolq.qq.protocol.packet.WtLogin;
import com.toolq.utils.HexUtil;
import com.toolq.utils.PacketUtil;
import com.toolq.utils.TeaUtil;
import com.toolq.utils.ToolQUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.util.*;

/**
 * @author luoluo
 * @date 2020/10/1 21:26
 */
public final class ToolQ {
	/**
	 * QQ协议信息
	 */
	private final HttpQQ androidQQ = new HttpQQ();

	/**
	 * QQ协议记录器
	 */
	private final BotRecorder recorder = new BotRecorder();
	/**
	 * Socket监听器
	 */
	private BotSocketListener listener;
	/**
	 * 机器人信息
	 */
	private final QQAccount account;
	/**
	 * Socket客户端
	 */
	private final BotSocketClient socketClient = new BotSocketClient(this);
	/**
	 * 机器人状态
	 */
	private BotStatus botStatus = BotStatus.notLoggedIn;

	/**
	 * 设置机器人状态
	 *
	 * @param toolQ 对象
	 * @param status 状态
	 */
	public static void setBotStatus(ToolQ toolQ, BotStatus status) {
		toolQ.botStatus = status;
	}

	/**
	 * 事件列表（堵塞列表）
	 */
	private final SafeVector<IBotEvent> eventVector = new SafeVector<>();
	/**
	 * 线程管理器
	 */
	private final ThreadManager threadManager;
	// private final HashMap<Integer, NettyApplicationEngine> httpServers = new HashMap<>();

	public ToolQ(QQAccount account) {
		this(account, false);
	}

	public ToolQ(QQAccount account, boolean isHd) {
		this.account = account;
		this.threadManager = ThreadManager.Companion.getInstance(account.getUser());
		this.androidQQ.setHd(isHd);
	}

	/**
	 * 登录
	 */
	public LoginResult login() {
		if(botStatus != BotStatus.destroyed) {
			return threadManager.addTaskWithResult(new ResultThread<LoginResult>() {
				/**
				 * 登录成功
				 */
				final int Success = 0;
				/**
				 * 密码错误
				 */
				final int PasswordWrong = 1;
				/**
				 * 验证码
				 */
				final int Captcha = 2;
				/**
				 * 滑块Ticket错误
				 */
				final int SliderTicketWrong = 6;
				/**
				 * 协议错误
				 */
				final int ProtocolWrong = 9;
				/**
				 * 回滚
				 */
				final int RollBack = 180;
				/**
				 * 网络环境错误禁止登录
				 */
				final int BeBan = 237;
				/**
				 * 账号被回收
				 */
				final int Reclaimed = 32;
				/**
				 * 账号被冻结
				 */
				final int Freeze = 40;
				/**
				 * 短信验证
				 */
				final int SMS = 239;
				/**
				 * 短信次数过多
				 */
				final int TooManySMSError = 162;
				/**
				 * 短信错误
				 */
				final int SMSWrong = 163;
				final int SMSWrong2 = 160;
				/**
				 * 设备锁
				 */
				final int DeviceLock = 204;
				/**
				 * 其它设备从另外一个地方登录
				 */
				final int OtherLoginError = 149;

				@Override
				public LoginResult on() {
					ToolQUtil.TencentServer tencentServer = ToolQUtil.getTencentServer();
					int ssoSeq = recorder.nextSsoSeq();
					try {
						listener = (BotSocketListener) socketClient.setSocketClientListener(new BotSocketListener(ToolQ.this, recorder));
						socketClient.connect(tencentServer.getServer(), tencentServer.getPort(), 3000);
						byte[] pack = WtLogin.GetStWithPassword(account, androidQQ, ssoSeq);
						socketClient.allowReceive(threadManager, SocketClient.Common);
						System.out.println(HexUtil.bytesToHexString(pack));
						Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, pack);
					} catch (ConnectException | com.toolq.helper.socket.ConnectException e) {
						return LoginResult.NetWorkError;
					} catch (IOException e) {
						TLog.INSTANCE.warn("登录失败（登录）");
						return LoginResult.Fail;
					} catch (Exception e) {
						e.printStackTrace();
						return LoginResult.Fail;
					}
					return handlePacket(ssoSeq);
				}

				private LoginResult body(byte[] body) {
					ByteReader reader = new ByteReader(body);
					reader = reader.readReader( reader.dis(1).readShort() - 4 );
					long reader_uin = 0;
					if(reader.readShort() == 8001 && (reader.readShort() == 0x810) && reader.readShort() == 1 && (reader_uin = reader.readULong() ) == account.getUser()) {
						int loginResult = reader.dis(2).readByte() & 0xff;
						TLog.INSTANCE.info(String.format("LoginResult：%s", loginResult));
						byte[] wtloginKey = loginResult == 180 ? Android.getRandKey() : ECDHCrypt.getInstance().get_g_share_key();
						HashMap<Integer, byte[]> tlvMap = parseTlv(TeaUtil.decrypt(reader.readRestBytes(), wtloginKey), true);
						switch(loginResult) {
							case Success: {
								long creationTime = System.currentTimeMillis() / 1000;
								long expiredTime = creationTime + 2160000;
								TLog.INSTANCE.info("LoginSuccess --> parseTlvTest");
								HashMap<Integer, byte[]> t119Map = parseTlv(TeaUtil.decrypt(tlvMap.get(0x119), Android.getTgtgKey()), false);
								for(Map.Entry<Integer, byte[]> entry : t119Map.entrySet() ) {
									byte[] value = entry.getValue();
									// TLog.INSTANCE.info(ToolQ.class, "ParseTlv ==> " + Integer.toHexString(entry.getKey()) + " ==> " + HexUtil.bytesToHexString(value));
									switch(entry.getKey()) {
										case 0x103 : {
											recorder.getUserStSig().setWebSig(new BytesKey(value, creationTime, expiredTime));
											break;
										}
										case 0x10e:
											recorder.getUserStSig().setKey(new BytesKey(value, creationTime, expiredTime));
											break;
										case 0x114:
											recorder.getUserStSig().setSig(new BytesKey(value, creationTime, expiredTime));
											break;
										case 0x203 :
											recorder.setDa2(value);
											break;
										case 0x108:
											Android.setKsid(value);
											break;
										case 0x10a:
											Android.setTgt(value);
											break;
										case 0x10d:
											Android.setTgtKey(value);
											break;
										case 0x11a:
											ByteReader reader0x11a = new ByteReader(value);
											recorder.setFace(reader0x11a.readShort());
											recorder.setAge(Byte.toUnsignedInt(reader0x11a.readByte()));
											recorder.setGender(Byte.toUnsignedInt(reader0x11a.readByte()));
											recorder.setNick(reader0x11a.readString(reader0x11a.readByte()));
											break;
										case 0x120:
											recorder.setsKey(new String(value), creationTime, expiredTime);
											break;
										case 0x143:
											recorder.setD2(value, creationTime, expiredTime);
											break;
										case 0x16d:
											recorder.setpSkey(new String(value), creationTime, expiredTime);
											break;
										case 0x305:
											recorder.setD2Key(value);
											break;
										case 0x512:
											ByteReader tlv512Reader = new ByteReader(value);
											int size = tlv512Reader.readShort();
											for(int i = 0;i < size;i++){
												String domain = tlv512Reader.readStringByShort();
												String pskey = tlv512Reader.readStringByShort();
												String p4token = tlv512Reader.readStringByShort();
												HashMap<String, StringKey> vMap = new HashMap<>();
												vMap.put("pskey", new StringKey(pskey, creationTime, expiredTime));
												vMap.put("p4token", new StringKey(p4token, creationTime, expiredTime));
												// System.out.println(domain + "||" + pskey);
												recorder.getpSKeyAndPt4Token().put(domain, vMap);
											}
											break;
										case 0x118 : case 0x106: case 0x11d: case 0x11f:
										case 0x10c: case 0x550: case 0x322:
										case 0x522: case 0x163: case 0x528: case 0x16a:
										case 0x130: case 0x133: case 0x134: case 0x537:
										case 0x138:
											break;
										default:
											TLog.INSTANCE.error("Tlv can't parse" + Integer.toHexString(entry.getKey()));
									}
								}
								botStatus = BotStatus.hasLogged;
								return register(account.getUser());
							}
							case PasswordWrong: {
								return LoginResult.PasswordWrong;
							}
							case Captcha: {
								String url = new String(tlvMap.get(0x192));
								String ticket  = BotConfig.IBotFunction.ticket(url, androidQQ);
								if(ticket == null) {
									TLog.INSTANCE.error("超时进行滑块操作");
									return LoginResult.SliderTimeOut;
								}
								TLog.INSTANCE.info(String.format("Enter the slider!Ticket：%s,length：%s", ticket, ticket.length()));
								int ssoSeq = recorder.nextSsoSeq();
								byte[] pack = WtLogin.SendCaptcha(account, androidQQ, ticket, tlvMap.get(0x104), tlvMap.get(0x546), ssoSeq);
								try {
									Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, pack);
								} catch (IOException e) {
									TLog.INSTANCE.error("登录失败(滑块)", e);
								}
								return handlePacket(ssoSeq);
							}
							case BeBan: {
								return LoginResult.SliderGetTicketWrong;
							}
							case ProtocolWrong:{
								return LoginResult.ProtocolWrong;
							}
							case Reclaimed:{
								return LoginResult.Reclaimed;
							}
							case RollBack: {
								int ssoSeq = recorder.nextSsoSeq();
								byte[] pack = WtLogin.GetStWithPassword(account, androidQQ, ssoSeq);
								try {
									ToolQUtil.TencentServer tencentServer = ToolQUtil.getTencentServer();
									socketClient.connect(tencentServer.getServer(), tencentServer.getPort());
									Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, pack);
								} catch (IOException e) {
									TLog.INSTANCE.error("登录失败(回滚)", e);
									return LoginResult.Fail;
								}
								return handlePacket(ssoSeq);
							}
							case Freeze:{
								return LoginResult.Freeze;
							}
							case SMS : {
								// 注意SMS二次验证
								ByteReader sms_reader = new ByteReader(tlvMap.get(0x17e));
								ByteReader phone_reader = new ByteReader(tlvMap.get(0x178));
								byte[] dt104 = tlvMap.get(0x104);
								byte[] dt174 = tlvMap.get(0x174);
								String phone;
								try {
									phone = phone_reader.readString(phone_reader.readInt());
								} catch (Exception e) {
									phone = phone_reader.readString(phone_reader.readShort()) + " " + phone_reader.readString(phone_reader.readShort());
								}
								String smsCode = BotConfig.IBotFunction.GetSMSCode(ToolQ.this, getAccount().getUser(), phone, sms_reader.readRestString(), dt104, dt174);
								if(smsCode == null || smsCode.isEmpty()) {
									return LoginResult.VerificationCodeFormatError;
								}
								int ssoSeq = recorder.nextSsoSeq();
								try {
									Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, WtLogin.VerifySMSCode(ssoSeq, ToolQ.this, dt104, dt174, tlvMap.get(0x402), smsCode));
									return handlePacket(ssoSeq);
								} catch (IOException e) {
									return LoginResult.Fail;
								}
							}
							case SMSWrong2: case SMSWrong: {
								return LoginResult.SMSWrong;
							}
							case TooManySMSError: {
								return LoginResult.TooManySMSWrong;
							}
							case DeviceLock: {
								int ssoSeq = recorder.nextSsoSeq();
								try {
									Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, WtLogin.PassVerify(ssoSeq, ToolQ.this, tlvMap.get(0x104), tlvMap.get(0x402), tlvMap.get(0x403)));
									return handlePacket(ssoSeq);
								} catch (IOException e) {
									e.printStackTrace();
									return LoginResult.Fail;
								}
							}
							case OtherLoginError: {
								throw new RuntimeException("Other device has logged from another location");
							}
							default:
								throw new RuntimeException(String.format("Can't parse loginResult[%s]", loginResult));
						}
					} else {
						TLog.INSTANCE.error("Illegal agreement detected! \nPacket：" + HexUtil.bytesToHexString(body));
					}
					return LoginResult.Fail;
				}

				private HashMap<Integer, byte[]> parseTlv(byte[] data, boolean wtlogin) {
					// System.out.println(HexUtil.bytesToHexString(data));
					HashMap<Integer, byte[]> tlvMap = new HashMap<>();
					ByteReader reader = new ByteReader(data);
					if(wtlogin) {
						reader.readShort();
						reader.dis(1);
					}
					int tlvSize = reader.readShort();
					for(int i = 0;i < tlvSize;i++) {
						int ver = reader.readShort();
						// System.out.println(Integer.toHexString(ver));
						byte[] body = reader.readBytes(reader.readShort());
						if(ver == (0x146 | 0x508))
							System.out.println(new String(body));
						tlvMap.put(ver,  body);
					}
					return tlvMap;
				}

				private LoginResult handlePacket(final int ssoSeq) {
					Waiter waiter = listener.addWaiter(new Waiter("wtlogin.login") {
						@Override
						public boolean check(Packet packet) {
							return packet.getSsoSeq() == ssoSeq;
						}
					});
					if(waiter.wait("This is a long waiting process.", 3 * 1000)) {
						if(waiter.getPacket() != null)
							return body(waiter.getPacket().getBody());
						TLog.INSTANCE.warn("The received package is empty. Login failed!");
						return LoginResult.Fail;
					}
					return LoginResult.NetWorkError;
				}

			}, LoginResult.Fail);
		}
		return LoginResult.Fail;
	}

	/**
	 * 注册进入腾讯服务器
	 */
	private LoginResult register(long uin) {
		byte[] body = JceHelper.encodePacket(Register.make(uin, 11, androidQQ.localId(), 0), "PushService", "SvcReqRegister", "SvcReqRegister", 0);
		int seq = recorder.nextSsoSeq();
		byte[] data = PacketUtil.makePacket("StatSvc.register", body, botStatus, recorder.getD2().getKey(), recorder.getD2Key(), seq, uin, androidQQ, Android.getTgt(), Android.getKsid(), false);
		Waiter waiter = listener.addWaiter(new Waiter("StatSvc.register") {
			@Override
			public boolean check(Packet packet) {
				return packet.getSsoSeq() == seq;
			}
		});
		try {
			Objects.requireNonNull(socketClient.getClientOutPut()).send(threadManager, data);
		} catch (IOException e) {
			listener.removeWaiter(waiter.id);
			TLog.INSTANCE.error("请求上线：上线失败", e);
			return LoginResult.Fail;
		}
		if(waiter.wait("ILoveYou", 5 * 1000)) {
			int code = Register.parse(waiter.packet.getBodyWithLength());
			if(code == 0) {
				TLog.INSTANCE.info("Successfully launched and are receiving reports!");
				botStatus = BotStatus.online;
				// 创建心跳线程
				HeartBeat.Companion.addHeartBeat(ToolQ.this);
				return LoginResult.Success;
			} else {
				TLog.INSTANCE.warn("register code not is zero");
			}
		}
		return LoginResult.FailedToGoOnline;
	}

	/**
	 * 获取操作中心
	 */
	public Center getCenter() {
		return new Center(this);
	}

	public BotStatus getBotStatus() {
		return botStatus;
	}

	/**
	 * 设置支付密码
	 *
	 * @param word 密码
	 */
	public void setPayPassword(String word) {
		account.setPayWord(word);
	}

	/**
	 * 设置机器人状态码
	 *
	 * @param status 状态码
	 */
	private BotStatus setBotStatus(BotStatus status) {
		botStatus = status;
		return botStatus;
	}

	/**
	 * 删除某个消息处理器
	 *
	 * @param i 索引id
	 * @return 被删除的处理器
	 */
	public IBotEvent removeBotEvent(int i) {
		return eventVector.remove(i);
	}

	/**
	 * 添加事件处理器到末尾
	 */
	public boolean addBotEvent(IBotEvent botEvent) {
		SafeVector<IBotEvent> vector = getEventVector();
		// 某个消息处理器禁止另外一个消息处理器添加
		return vector.stream().allMatch(event -> event.handleAddEvent(botEvent)) && eventVector.add(botEvent);
	}

	/**
	 * 获取某个事件处理器
	 */
	public IBotEvent getBotEvent(int i) {
		return eventVector.get(i);
	}

	public List<IBotEvent> getEvents() {
		return new ArrayList<>(eventVector);
	}

	/**
	 * 获取事件处理器大小
	 */
	public int getEventSize() {
		return eventVector.size();
	}

	/**
	 * 获取某事件处理器的排序id
	 *
	 * @param botEvent 事件处理器
	 */
	public int getBotEventId(BotEvent botEvent) {
		return eventVector.indexOf(botEvent);
	}

	/**
	 * 下线(销毁)
	 * 不可以再次上线
	 */
	public void shut() {
		socketClient.prohibitReceive();
		switch (botStatus) {
			case online:
				// 在线状态下申请下线需要发送下线请求包
				// TODO("待完成发送下线请求包")
				break;
			case notLoggedIn:
			case destroyed:
			case hasLogged:
				TLog.INSTANCE.warn(String.format("Bot[%s]出现错误，Bot已销毁！", account.getUser()));
				break;
			case ruin:
				TLog.INSTANCE.warn(String.format("Bot[%s]失去网络连接，Bot正在尝试重新连接！", account.getUser()));
				break;
		}
		threadManager.shutdown();
	}

	/**
	 * 获取机器人的头像（登录后可获取）
	 */
	public int getFace() {
		return recorder.getFace();
	}

	/**
	 * 获取机器人的性别（登录后可获取）
	 */
	public int getGender() {
		return recorder.getGender();
	}

	/**
	 * 获取机器人名字（登录后可获取）
	 */
	public String getNick() {
		return recorder.getNick();
	}

	/**
	 * 获取机器人年龄（登录后可获取）
	 */
	public int getAge() {
		return recorder.getAge();
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取协议模拟环境
	 */
	public AndroidQQ getAndroidQQ() {
		return androidQQ;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取Socket监听器
	 */
	public BotSocketListener getListener() {
		return listener;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取机器人的信息类
	 */
	public QQAccount getAccount() {
		return account;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取协议Socket对象
	 */
	public SocketClient getSocketClient() {
		return socketClient;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取协议统计器
	 */
	public BotRecorder getRecorder() {
		return recorder;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取机器人的事件处理器列表
	 */
	public SafeVector<IBotEvent> getEventVector() {
		return eventVector;
	}

	/**
	 * 非协议开发者禁止使用
	 *
	 * 获取线程机器人的管理器
	 */
	public ThreadManager getThreadManager() {
		return threadManager;
	}
}
