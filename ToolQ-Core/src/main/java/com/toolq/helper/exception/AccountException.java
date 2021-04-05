package com.toolq.helper.exception;

import com.toolq.resource.TString;

/**
 * @author luoluo
 * @date 2020/10/1 21:46
 */
public final class AccountException extends ToolException {
	public AccountException() {
		super(TString.bot_account_error.get());
	}
}
