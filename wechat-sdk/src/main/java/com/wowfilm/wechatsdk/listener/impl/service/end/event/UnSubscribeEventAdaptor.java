package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import org.apache.log4j.Logger;

/**
 * 取消关注公众号
 */
@Node(parents = EventRouter.class, value = "unsubscribe")
public class UnSubscribeEventAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;
    @Param
    protected String FromUserName;
    @Param
    protected String CreateTime;

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到 %s 的退订事件", FromUserName));
        return DEFAULT_RETURN;
    }

}
