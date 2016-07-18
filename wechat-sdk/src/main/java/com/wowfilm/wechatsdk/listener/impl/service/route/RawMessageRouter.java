package com.wowfilm.wechatsdk.listener.impl.service.route;

import java.util.Iterator;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


@Node(value = "raw", parents = {EncryptRouter.class, AesMessageRouter.class})
public final class RawMessageRouter implements Service {

    protected Logger log = Logger.getLogger(RawMessageRouter.class);

    @Param
    private String xmlContent;
    @Param
    private ThreadsMode threadsMode;

    @Override
    public String doService(Context context) throws Exception {

        Document document = DocumentHelper.parseText(xmlContent);
        Element root = document.getRootElement();
        for (Iterator<?> it = root.elementIterator(); it.hasNext(); ) {
            Element ele = (Element) it.next();
            context.addAttribute(ele.getName(), ele.getText());
        }
        log.info("=============消息解密完成=============");
        log.info(context.toString());
        log.info("==================================");
        String appId = context.getAttribute(String.class, "AppId");
        if (appId != null) { //此时为第三方服务平台服务自身的消息
            log.debug("路由到第三方平台服务自身");
            return threadsMode.routeToNext(this.getClass(), "platform", context);
        } else {
            String routeKey = context.getAttribute(String.class, "MsgType");
            log.debug(String.format("根据消息类型[%s]作路由", routeKey));
            return threadsMode.routeToNext(this.getClass(), routeKey, context);
        }
    }

}
