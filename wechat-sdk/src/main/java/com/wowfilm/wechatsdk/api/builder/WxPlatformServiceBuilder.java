package com.wowfilm.wechatsdk.api.builder;

import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManagerImp;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;

/**
 *  平台服务调用生成
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 17:43:38
 */
public class WxPlatformServiceBuilder {
    private static ThreadLocal<PlatformConfigStorage> local = new ThreadLocal<>();

    private WxPlatformServiceBuilder() {
    }
    public static WxPlatformServiceBuilder build(PlatformConfigStorage config) {
        WxPlatformServiceBuilder builder = new WxPlatformServiceBuilder();
        builder.local.set(config);
        return builder;
    }

    public PlatFormManager PLATFORM(){
        PlatFormManagerImp formManager = new PlatFormManagerImp();
        formManager.setConfigStorage(this.local.get());
        return formManager;
    }
}
