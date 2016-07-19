package com.wowfilm.wechat.wxservices;

import com.wowfilm.wechat.content.SpringContextHolder;
import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.api.builder.WxServiceBuilder;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import com.wowfilm.wechatsdk.common.Exec;
import com.wowfilm.wechatsdk.common.StringUtils;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.entity.platform.PlatFormGetAuthInfoResult;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpCustomMessage;
import com.wowfilm.wechatsdk.listener.impl.service.end.message.TextMessageAdaptor;
import com.wowfilm.wechatsdk.listener.impl.service.route.RawMessageRouter;
import com.wowfilm.wechatsdk.store.WxMpConfigStorageBuilder;
import com.wowfilm.wechatsdk.template.PassiveMessage;

import java.util.concurrent.Callable;


@Node(parents = RawMessageRouter.class, value = "text")
public class TextMessageService extends TextMessageAdaptor {

    @Override
    public String doService(Context context) throws Exception {
        // 全网发布测试
        if (!StringUtils.isEmpty(ToUserName) && AUTO_TEST_USERNAME.equals(ToUserName)
                && AUTO_TEST_TEXTMSG_RETURN.equals(Content)) {
            return PassiveMessage.wrapText(FromUserName, ToUserName, AUTO_TEST_TEXTMSG_RETURN_BACK);
        } else if (!StringUtils.isEmpty(ToUserName) && AUTO_TEST_USERNAME.equals(ToUserName)
                && Content.startsWith("QUERY_AUTH_CODE")) {
            final String query_auth = Content.split(":")[1];
            Exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        PlatFormManager mgr = SpringContextHolder.getBean(PlatFormManager.class, "platFormManager");
                        PlatFormGetAuthInfoResult result = mgr.getAuthInfo(query_auth);
                        String token = result.getAuthorizationInfo().getAuthorizerAccessToken();
                        WxMpCustomMessage m = WxMpCustomMessage.TEXT().content(query_auth + "_from_api").toUser(FromUserName).build();
                        WxServiceBuilder.build(WxMpConfigStorageBuilder.build(token)).SERVICEMSG().customMessageSend(m);
                    } catch (Exception e) {
                        log.error("任务处理出错", e);
                    }
                    return null;
                }
            });
            return DEFAULT_RETURN;
        }
        return super.doService(context);
    }

}
