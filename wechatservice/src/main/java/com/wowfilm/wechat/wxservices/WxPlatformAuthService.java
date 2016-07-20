package com.wowfilm.wechat.wxservices;


import com.wowfilm.wechat.wxextend.WxPlatformMgrFactory;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Wx platform auth service.
 * 微信公众号授权服务
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 15:56:32
 */
public class WxPlatformAuthService {
    private static final Logger log = Logger.getLogger(WxPlatformAuthService.class);

    @Autowired
    private WxPlatformMgrFactory factory;
    public String getAuthUrl(){
        log.info("获取授权登录URL!");
        String url = factory.get().getLoginUrl();
        return url;
    }
}
