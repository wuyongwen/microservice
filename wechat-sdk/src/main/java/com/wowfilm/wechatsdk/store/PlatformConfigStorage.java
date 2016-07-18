package com.wowfilm.wechatsdk.store;

/**
 * 平台信息持久化
 * 
 * @author wen
 *
 */
public interface PlatformConfigStorage {
	/**
	 * 更新cerifyTicket
	 * @param verify_ticketb
	 * @param createTime
	 */
	public void updateVerifyTicket(String verify_ticketb, String createTime);
	/**
	 * 获取ticket
	 * @return
	 */
	public String getTicket();
	/**
	 * 更新token
	 * @param accessToken
	 * @param expiresIn
	 */
	public void updateAccessToken(String accessToken, Integer expiresIn);
	/**
	 * 获取token
	 * @return
	 */
	public String getAccessToken();
	/**
	 * 更新预授权码
	 * @param preAuthCode
	 * @param expiresIn
	 */
	public void updatePreAuthCode(String preAuthCode, Integer expiresIn);
	/**
	 * 获取授权码
	 * @return
	 */
	public String getPreAuthCode();
	/**
	 * token是否过期
	 * @return
	 */
	public boolean isAccessTokenExpired();
	/**
	 * 预授权码是否过期
	 * @return
	 */
	public boolean isPreAuthCodeExpired();
}