package com.wowfilm.wechatsdk.api.paltform;

import com.wowfilm.wechatsdk.api.WeiXinURL;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.common.Refresher;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.entity.platform.PlatFormAccessTokenResult;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import com.wowfilm.wechatsdk.store.PlatformConfigMemStorage;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import com.wowfilm.wechatsdk.template.PlatFormMessage;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;


/**
 * The type Plat form token refresher.
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 17:37:32
 */
public class PlatFormTokenRefresher extends Refresher<String> {
	private static Logger log = Logger.getLogger(PlatFormTokenRefresher.class);
	private String platformTokenUrl = WeiXinURL.PLATFORM_GET_ACCESSTOKEN;

	
	public PlatformConfigStorage getConfigStorage() {
		if(storage == null)
			storage = new PlatformConfigMemStorage();
		return storage;
	}

	public void setComponentVerifyTicket(String componentVerifyTicket, String createTime) {
		getConfigStorage().updateVerifyTicket(componentVerifyTicket,createTime);
	}

	@Override
	public String refresh() {
		log.info("更新平台component_access_token");
		PlatFormAccessTokenResult result = null;
		if (!getConfigStorage().isAccessTokenExpired())
			return getConfigStorage().getAccessToken();
		String postJson = PlatFormMessage.wrapGetAccessToken(App.Info.id, App.Info.secret, getConfigStorage().getTicket());
		String respJson = HttpUtils.post(platformTokenUrl, postJson);
		result = JSON.parseObject(respJson, PlatFormAccessTokenResult.class);

		ErrorUtils.checkWXError(result);

		log.debug("获取的component_access_token:" + result);
		getConfigStorage().updateAccessToken(result.getComponentAccessToken(), result.getExpiresIn());
		return result.getComponentAccessToken();
	}

	@Override
	public boolean isExpired() {
		return getConfigStorage().isAccessTokenExpired();
	}

	@Override
	public String get() {
		current = getConfigStorage().getAccessToken();
		return super.get();
	}
}
