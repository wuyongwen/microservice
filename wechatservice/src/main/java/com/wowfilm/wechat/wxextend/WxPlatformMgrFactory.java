package com.wowfilm.wechat.wxextend;

import com.wowfilm.wechatsdk.api.builder.WxPlatformServiceBuilder;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * The type Wx platform mgr factory.
 * 获取第三方平台服务类
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 17:02:43
 */
@Component
public class WxPlatformMgrFactory {
    @Autowired
    @Qualifier("PlatformConfigDBStorage")
    private PlatformConfigStorage configStorage;

    public PlatFormManager get() {
        return WxPlatformServiceBuilder.build(configStorage).PLATFORM();
    }
}
