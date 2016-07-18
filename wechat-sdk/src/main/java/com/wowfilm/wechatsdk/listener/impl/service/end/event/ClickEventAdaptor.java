package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import org.apache.log4j.Logger;


/**
 * 菜单点击事件推送
 */
@Node(parents = EventRouter.class, value = "CLICK")
public class ClickEventAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;
    @Param
    protected String FromUserName;
    @Param
    protected String CreateTime;
    @Param
    protected String EventKey; //事件KEY值，与自定义菜单接口中KEY值对应

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的点击事件 %s", FromUserName, EventKey));
        return DEFAULT_RETURN;
    }

}
