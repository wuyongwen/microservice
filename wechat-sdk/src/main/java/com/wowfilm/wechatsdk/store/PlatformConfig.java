package com.wowfilm.wechatsdk.store;

/**
 * 微信开发平台验证参数
 * 
 * @author wuyongwen
 * @Date 2016年3月22日下午3:10:42
 */
public class PlatformConfig {
	private String verifyTicket;
	private long createTime;
	private String accessToken;
	private long expiresIn;
	private String preAuthCode;
	private long preAuthCodeExpiresIn;

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getPreAuthCode() {
		return preAuthCode;
	}

	public void setPreAuthCode(String preAuthCode) {
		this.preAuthCode = preAuthCode;
	}

	public long getPreAuthCodeExpiresIn() {
		return preAuthCodeExpiresIn;
	}

	public void setPreAuthCodeExpiresIn(long preAuthCodeExpiresIn) {
		this.preAuthCodeExpiresIn = preAuthCodeExpiresIn;
	}

	public String getVerifyTicket() {
		return verifyTicket;
	}

	public void setVerifyTicket(String verifyTicket) {
		this.verifyTicket = verifyTicket;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
