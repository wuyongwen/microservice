package com.wowfilm.wechat.init;

import com.wowfilm.wechat.content.SpringContextHolder;
import com.wowfilm.wechat.service.WxPlatformInfoService;
import com.wowfilm.wechatsdk.MessageHandler;
import com.wowfilm.wechatsdk.api.paltform.PlatFormTokenAccessor;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wechat sdk beans init
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 12:08:08
 */
@Configuration
public class WxBeanInit {
    /**
     * 消息处理bean
     *
     * @return
     * @throws Exception
     */
    @Bean
    public MessageHandler getMessageHandler() throws Exception {
        return new MessageHandler();
    }

    /**
     * 平台token管理
     * @return
     */
    @Bean
    public PlatFormTokenAccessor getPlatFormTokenAccessor() {
        PlatformConfigStorage platformConfigDBStorage = SpringContextHolder.getBean(PlatformConfigStorage.class, "PlatformConfigDBStorage");
        PlatFormTokenAccessor platFormTokenAccessor = new PlatFormTokenAccessor();
        platFormTokenAccessor.setPlatformConfigStorage(platformConfigDBStorage);
        return platFormTokenAccessor;
    }
}
