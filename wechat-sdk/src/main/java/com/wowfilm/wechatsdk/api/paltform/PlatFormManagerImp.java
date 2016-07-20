package com.wowfilm.wechatsdk.api.paltform;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.wowfilm.wechatsdk.api.WeiXinURL;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.common.StringTemplate;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.entity.platform.*;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import com.wowfilm.wechatsdk.template.PlatFormMessage;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.wowfilm.wechatsdk.common.StringTemplate.compile;

/**
 * 公众号第三方平台可用主动调用
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 17:37:19
 */
public class PlatFormManagerImp implements PlatFormManager {

	private static Logger log = Logger.getLogger(PlatFormManager.class);

	private static StringTemplate getLoginUrl = compile(WeiXinURL.PLATFORM_LOGINURL);
	private static StringTemplate getAuthInfoUrl = compile(WeiXinURL.PLATFORM_GET_AUTHINFO);
	private static StringTemplate getAuthAccessTokenUrl = compile(WeiXinURL.PLATFORM_GET_AUTHACCESSTOKEN);
	private static StringTemplate getAuthorizerInfoUrl = compile(WeiXinURL.PLATFORM_GET_AUTHORIZER_INFO);
	private static StringTemplate getAuthorizerOptionUrl = compile(WeiXinURL.PLATFORM_GET_AUTHORIZER_OPTION);
	private static StringTemplate setAuthorizerOptionUrl = compile(WeiXinURL.PLATFORM_SET_AUTHORIZER_OPTION);

	private PlatFormTokenAccessor platFormTokenAccessor;
	private PlatformConfigStorage configStorage;

	public PlatformConfigStorage getConfigStorage() {
		return configStorage;
	}

	public void setConfigStorage(PlatformConfigStorage configStorage) {
		this.configStorage = configStorage;
	}

	public PlatFormTokenAccessor getPlatFormTokenAccessor() {
		if(platFormTokenAccessor==null){
			platFormTokenAccessor = new PlatFormTokenAccessor();
			platFormTokenAccessor.setPlatformConfigStorage(getConfigStorage());
		}
		return platFormTokenAccessor;
	}

	public void setPlatFormTokenAccessor(PlatFormTokenAccessor platFormTokenAccessor) {
		this.platFormTokenAccessor = platFormTokenAccessor;
	}
	@Override
	public String getLoginUrl(Map requestParam) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("component_appid", App.Info.id);
		params.put("pre_auth_code", this.getPreAuthCode());
		String jsonParam = JSON.toJSONString(requestParam);
		String url = App.Info.loginedUrl;
		try {
			url +="?backParam="+ URLEncoder.encode(jsonParam,"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("参数转换错误!",e);
		}
		params.put("loogined_redirect_url", url);
		return getLoginUrl.replace(params);
	}
	@Override
	public String getLoginUrl() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("component_appid", App.Info.id);
		params.put("pre_auth_code", this.getPreAuthCode());
		params.put("loogined_redirect_url", App.Info.loginedUrl);
		return getLoginUrl.replace(params);
	}
	
	@Override
	public String getPreAuthCode() {
		return getPlatFormTokenAccessor().getPreAuthCode();
	}

	@Override
	public PlatFormGetAuthInfoResult getAuthInfo(String authCode) {

		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = getAuthInfoUrl.replace(params);
		String postContent = PlatFormMessage.wrapGetAuthInfo(App.Info.id, authCode);
		String respJson = HttpUtils.post(urlLocation, postContent);
		return JSON.parseObject(respJson, PlatFormGetAuthInfoResult.class);
	}

	@Override
	public PlatFormGetAuthAccessResult getAuthAccessToken(String authAppId, String refreshToken) {
		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = getAuthAccessTokenUrl.replace(params);
		String postContent = PlatFormMessage.wrapGetAuthAccessToken(App.Info.id, authAppId, refreshToken);
		String respJson = HttpUtils.post(urlLocation, postContent);
		return JSON.parseObject(respJson, PlatFormGetAuthAccessResult.class);
	}

	@Override
	public PlatFormGetAuthorizerInfoResult getAuthorizerInfo(String authAppId) {

		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = getAuthorizerInfoUrl.replace(params);
		String postContent = PlatFormMessage.wrapGetAuthorizerInfo(App.Info.id, authAppId);
		String respJson = HttpUtils.post(urlLocation, postContent);
		return JSON.parseObject(respJson, PlatFormGetAuthorizerInfoResult.class);
	}

	@Override
	public PlatFormGetAuthorizerOptionResult getAuthorizerOption(String authAppId, String optionName) {

		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = getAuthorizerOptionUrl.replace(params);
		String postContent = PlatFormMessage.wrapGetAuthorizerOption(App.Info.id, authAppId, optionName);
		String respJson = HttpUtils.post(urlLocation, postContent);
		return JSON.parseObject(respJson, PlatFormGetAuthorizerOptionResult.class);
	}

	@Override
	public PlatFormSetAuthorizerOptionResult setAuthorizerOption(String authAppId, String optionName,
																 String optionValue) {

		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = setAuthorizerOptionUrl.replace(params);
		String postContent = PlatFormMessage.wrapSetAuthorizerOption(App.Info.id, authAppId, optionName, optionValue);
		String respJson = HttpUtils.post(urlLocation, postContent);
		return JSON.parseObject(respJson, PlatFormSetAuthorizerOptionResult.class);
	}

}
