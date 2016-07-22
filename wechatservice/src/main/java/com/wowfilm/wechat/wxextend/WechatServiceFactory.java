package com.wowfilm.wechat.wxextend;

import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.service.WxMpAppService;
import com.wowfilm.wechat.util.XmlUtil;
import com.wowfilm.wechatsdk.api.builder.WxServiceBuilder;
import com.wowfilm.wechatsdk.store.WxMpConfigStorage;
import com.wowfilm.wechatsdk.store.WxMpInMemoryConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wen on 2016/7/21 14:28.
 */
@Component
public class WechatServiceFactory {
    private static Logger log = LoggerFactory.getLogger(WechatServiceFactory.class);
    @Autowired
    private WxMpAppService wxMpAppService;
    private WxMpInMemoryConfigStorage defaultAppConfig;
    /**
     * 获取授权公众号
     * @param appId
     * @return
     */
    public WxServiceBuilder getPlatformAppBuilder(String appId){
        WxMpApp app = wxMpAppService.findByAppId(appId);
        if(app == null){
            throw new RuntimeException("没有查到公众号!");
        }
        WxMpConfigDBStorage storage = new WxMpConfigDBStorage();
        storage.setApp(app);
        return WxServiceBuilder.build(storage);
    }
    public WxServiceBuilder getPlatformAppBuilder(WxMpApp app){
        WxMpConfigDBStorage storage = new WxMpConfigDBStorage();
        storage.setApp(app);
        return WxServiceBuilder.build(storage);
    }
    /**
     * 获取默认公众号
     * @return
     */
    public WxServiceBuilder getSelfAppBuilder(){
        return WxServiceBuilder.build(configure());
    }

    /**
     * 加载默认公众号配置
     * @return
     */
    public WxMpConfigStorage configure() {
        if(defaultAppConfig == null){
            synchronized (this){
                if(defaultAppConfig == null){
                    InputStream in = null;
                    try {
                        in = this.getClass().getClassLoader().getResourceAsStream("wowfilmAppConfig.xml");
                        defaultAppConfig = XmlUtil.fromXml(WxMpInMemoryConfigStorage.class, in);
                        log.info("当前使用的公众号为:appId:{},appsecret{}" , defaultAppConfig.getAppId(),defaultAppConfig.getSecret() );
                    } catch (Exception e) {
                        log.error("解析wowfilmAppConfig出错!无法获取默认的服务号配置!",e);
                        throw new RuntimeException("获取默认服务号出错.",e);
                    } finally {
                        if(in != null){
                            try {
                                in.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }
        return defaultAppConfig;
    }
}
