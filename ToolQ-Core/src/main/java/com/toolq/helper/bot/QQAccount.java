package com.toolq.helper.bot;

import com.toolq.helper.exception.AccountException;
import com.toolq.helper.packet.ByteBuilder;
import com.toolq.utils.MD5;
import com.toolq.utils.QQUtil;

/**
 * @author luoluo
 * @date 2020/10/1 21:28
 */
public final class QQAccount {
	public QQAccount(long qq, String password) {
		if(!QQUtil.checkAccount(qq, password)) throw new AccountException();
		this.long_qq = qq;
		this.str_password = password;
		this.bytes_md5_password = MD5.toMD5Byte(password);
		this.bytes_md5_passwordWithQQ = new ByteBuilder(bytes_md5_password)
			.writeLong(qq)
			.md5();
	}

	private final long long_qq;
	private final String str_password;
	private final byte[] bytes_md5_password;
	private final byte[] bytes_md5_passwordWithQQ;
	/**
	 * 支付密码
	 */
	private String payWord;

	/**
	 * 获取QQ号
	 */
	public long getUser() {
		return long_qq;
	}

	/**
	 * 获取密码MD5
	 */
	public byte[] getMd5Password() {
		return bytes_md5_password;
	}

	/**
	 * 获取密码与QQ的MD5
	 */
	public byte[] getMd5PasswordWithQQ() {
		return bytes_md5_passwordWithQQ;
	}

	/**
	 * 需要被保护！！！
	 *
	 * 获取密码
	 */
	public String getPassword() {
		return str_password;
	}

	public String getPayWord() {
		return payWord;
	}

	public void setPayWord(String payWord) {
		this.payWord = payWord;
	}
}
