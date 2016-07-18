package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import org.apache.log4j.Logger;
/**
 * 点击链接菜单
 */
@Node(parents = EventRouter.class, value = "VIEW")
public class ViewEventAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;
    @Param
    protected String FromUserName;
    @Param
    protected String CreateTime;
    @Param
    protected String EventKey;//事件KEY值，设置的跳转URL

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到 %s 的点击事件，跳转到 %s", FromUserName, EventKey));
        return DEFAULT_RETURN;
    }

}
