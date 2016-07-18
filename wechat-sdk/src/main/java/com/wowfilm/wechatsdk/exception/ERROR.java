package com.wowfilm.wechatsdk.exception;


import com.wowfilm.wechatsdk.entity.BasicResult;

public enum ERROR {
	SYS(1, "系统异常!"), 
	BUS(2, "业务异常!"), 
	VAL(3, "验证错误!"), 
	TICKET(999,"没有验证码,等待微信服务器推送."),
	WEERR(1000, "微信消息异常!");

	int code;
	String msg;

	ERROR(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ErrorMsg build() {
		return new ErrorMsg(code, msg);
	}

	public ErrorMsg buildErrorMsg(BasicResult res) {
		return new ErrorMsg(Integer.parseInt(res.getErrcode()), res.getErrmsg());
	}
}
