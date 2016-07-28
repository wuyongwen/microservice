package com.wowfilm.wechat.vo;
/**
 * 消息状态
 * @author wuyongwen
 * @Date 2016年4月7日下午3:46:43
 */
public enum WxNewsStatus {
	A(1,"新建"),B(2,"同步"),C(3,"正在发布"),D(4,"已发布"),E(5,"删除"),F(6,"同步失败"),G(7,"发布失败"),H(8,"删除失败"),I(9,"等待发布");
	Integer code;
	String msg;
	WxNewsStatus(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public Integer code(){
		return code;
	}
}
