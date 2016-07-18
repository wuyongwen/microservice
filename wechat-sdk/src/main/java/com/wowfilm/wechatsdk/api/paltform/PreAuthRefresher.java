package com.wowfilm.wechatsdk.api.paltform;

import java.util.HashMap;
import java.util.Map;

import com.wowfilm.wechatsdk.api.WeiXinURL;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.common.Refresher;
import com.wowfilm.wechatsdk.common.StringTemplate;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.entity.platform.PlatFormGetPreAuthCodeResult;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import com.wowfilm.wechatsdk.store.PlatformConfigMemStorage;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import com.wowfilm.wechatsdk.template.PlatFormMessage;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import static com.wowfilm.wechatsdk.common.StringTemplate.compile;

/**
 * The type Pre auth refresher.
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 17:37:38
 */
public class PreAuthRefresher extends Refresher<String> {
	private static Logger log = Logger.getLogger(PreAuthRefresher.class);
	private StringTemplate getPreAuthCodeUrl = compile(WeiXinURL.PLATFORM_GET_PRE_AUTHCODE);

	protected PlatFormTokenAccessor platFormTokenAccessor;
	public  PlatformConfigStorage getConfigStorage() {
		if (storage == null)
			storage = new PlatformConfigMemStorage();
		return storage;
	}

	public void setGetPreAuthCodeUrl(StringTemplate getPreAuthCodeUrl) {
		this.getPreAuthCodeUrl = getPreAuthCodeUrl;
	}

	public PlatFormTokenAccessor getPlatFormTokenAccessor() {
		if(platFormTokenAccessor == null)
			platFormTokenAccessor = new PlatFormTokenAccessor();
		return platFormTokenAccessor;
	}

	public void setPlatFormTokenAccessor(PlatFormTokenAccessor platFormTokenAccessor) {
		this.platFormTokenAccessor = platFormTokenAccessor;
	}

	@Override
	public String refresh() {
		log.info("更新 PreAuthCode");
		Map<String, Object> params = new HashMap<>();
		params.put("component_access_token", getPlatFormTokenAccessor().getAccessToken());
		String urlLocation = getPreAuthCodeUrl.replace(params);

		PlatFormGetPreAuthCodeResult result = null;
		try {
			String respJson = HttpUtils.post(urlLocation, PlatFormMessage.wrapGetPreAuthCode(App.Info.id));
			result = JSON.parseObject(respJson, PlatFormGetPreAuthCodeResult.class);
			ErrorUtils.checkWXError(result);
		} catch (Exception e) {
			log.error("请求 PreAuthCode 失败，继续采用之前 AuthCode！", e);
			return current;
		}
		log.info("更新 PreAuthCode：" + result.toJsonString());
		getConfigStorage().updatePreAuthCode(result.getPreAuthCode(), result.getExpiresIn());
		return result.getPreAuthCode();
	}

	@Override
	public boolean isExpired() {
		return getConfigStorage().isPreAuthCodeExpired();
	}

}