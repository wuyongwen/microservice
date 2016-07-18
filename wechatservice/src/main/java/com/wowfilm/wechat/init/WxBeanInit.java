package com.wowfilm.wechat.init;

import com.wowfilm.wechatsdk.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wechat sdk beans init
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 12:08:08
 */
@Configuration
public class WxBeanInit {
    @Bean
    public MessageHandler getMessageHandler() throws Exception {
        return new MessageHandler();
    }
}
