package com.wowfilm.wechatsdk.exception;

/**
 * 微信错误码说明
 * http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 * @author wuyongwen
 * @Date 2016年3月3日下午3:24:58
 */
public class WechatErrorException extends RuntimeException {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 5738954907239458916L;
	private ErrorMsg error;

	public WechatErrorException(ErrorMsg error) {
		super(error.toString());
		this.error = error;
	}

	public ErrorMsg getError() {
		return error;
	}
}
