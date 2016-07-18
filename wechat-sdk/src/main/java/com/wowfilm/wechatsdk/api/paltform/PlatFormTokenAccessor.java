package com.wowfilm.wechatsdk.api.paltform;

import com.wowfilm.wechatsdk.common.Refresher;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import org.apache.log4j.Logger;

/**
 * 公众号第三方公众平台的 AccessToken 管理器
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 17:36:46
 */
public class PlatFormTokenAccessor {

	private static Logger log = Logger.getLogger(PlatFormTokenAccessor.class);
	private PlatFormTokenRefresher refresher;
	private Refresher<String> preAuthRefresher;

	public PlatFormTokenRefresher getRefresher() {
		if (refresher == null) {
			refresher = new PlatFormTokenRefresher();
		}
		return refresher;
	}

	public void setRefresher(PlatFormTokenRefresher refresher) {
		this.refresher = refresher;
	}

	public  Refresher<String> getPreAuthRefresher() {
		if (preAuthRefresher == null) {
			preAuthRefresher = new PreAuthRefresher();
		}
		return preAuthRefresher;
	}
	public void setPlatformConfigStorage(PlatformConfigStorage storage){
		refresher.setStorage(storage);
		preAuthRefresher.setStorage(storage);
	}
	public void setPreAuthRefresher(Refresher<String> preAuthRefresher) {
		this.preAuthRefresher = preAuthRefresher;
	}

	public synchronized String getPreAuthCode() {
		return getPreAuthRefresher().get();
	}

	public synchronized String getAccessToken(){
		return getRefresher().get();
	}

	public void updatePlatFormVerifyTicket(String componentVerifyTicket, String createTime) {
		getRefresher().setComponentVerifyTicket(componentVerifyTicket, createTime);
	}
}
