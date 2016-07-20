package com.wowfilm.wechatsdk.api.paltform;

import com.wowfilm.wechatsdk.entity.platform.*;

import java.util.Map;

/**
 * 微信平台接口
 * @author wuyongwen
 * @Date 2016年3月22日上午10:35:19
 */
public interface PlatFormManager {
	/**
	 * 获取授权登录URL+回调地址
	 * @return
     */
	public String getLoginUrl();

	/**
	 * 获取授权登录URl
	 * @param requestParam
	 * @return
     */
	public String getLoginUrl(Map requestParam);
	/**
     * 该API用于获取预授权码。预授权码用于公众号授权时的第三方平台方安全验证。
     * pre_auth_code
     * @return 
     */
	public String getPreAuthCode();
	/**
     * 该API用于使用授权码换取授权公众号的授权信息，并换取authorizer_access_token和
     * authorizer_refresh_token。 授权码的获取，需要在用户在第三方平台授权页中完成授
     * 权流程后，在回调URI中通过URL参数提供给第三方平台方。
     * @param authCode
     * @return 
     */
	public PlatFormGetAuthInfoResult getAuthInfo(String authCode);
	/**
     * TODO 这个应该是需要持久化的
     * 该API用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌
     * （authorizer_refresh_token）获取新的令牌。
     * @param authAppId 授权方appid
     * @param refreshToken 授权方的刷新令牌，刷新令牌主要用于公众号第三方平台获取和刷新已
     * 授权用户的refreshToken，只会在授权时刻提供，请妥善保存。 一旦丢失，只能让用户重新授
     * 权，才能再次拿到新的刷新令牌
     * @return
     */
	public PlatFormGetAuthAccessResult getAuthAccessToken(String authAppId, String refreshToken);
	/**
     * 该API用于获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
     * @param authAppId 授权方appid
     * @return
     */
	public PlatFormGetAuthorizerInfoResult getAuthorizerInfo(String authAppId);
	/**
     * 该API用于获取授权方的公众号的选项设置信息，如：地理位置上报，语音识别开关，多客服开关。<br>
     * 注意，获取各项选项设置信息，需要有授权方的授权，详见权限集说明。
     * @param authAppId 授权公众号appid
     * @param optionName 选项名称
     * @return
     */
	public PlatFormGetAuthorizerOptionResult getAuthorizerOption(String authAppId, String optionName);
	/**
     * 该API用于设置授权方的公众号的选项信息，如：地理位置上报，语音识别开关，多客服开关。<br>
     * 注意，设置各项选项设置信息，需要有授权方的授权，详见权限集说明。
     * @param authAppId 授权公众号appid
     * @param optionName 选项名称
     * @param optionValue 设置的选项值
     * @return
     */
	public PlatFormSetAuthorizerOptionResult setAuthorizerOption(String authAppId, String optionName,
																 String optionValue);

}
