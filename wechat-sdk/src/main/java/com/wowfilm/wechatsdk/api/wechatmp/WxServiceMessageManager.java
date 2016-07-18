package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpCustomMessage;

/**
 * 客服消息接口
 * @author warden
 * @date Created on 2016-07-13 19:53:08
 */
public class WxServiceMessageManager extends WxBaseService {
    /**
     * <pre>
     * 发送客服消息
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
     * </pre>
     * @param message
     */
    public void customMessageSend(WxMpCustomMessage message) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
        executor.execute(new SimplePostRequestExecutor(), url, message.toJson());
    }
}
