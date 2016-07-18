package com.wowfilm.wechatsdk.exception;

public class ErrorMsg {
	public int errorcode;
	public String errormsg;
	public String content;
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ErrorMsg(int errorcode, String errormsg) {
		super();
		this.errorcode = errorcode;
		this.errormsg = errormsg;
	}
	@Override
	public String toString() {
		return "错误码:"+errorcode +", 消息说明:" + errormsg;
	}
	
}
