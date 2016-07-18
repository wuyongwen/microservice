package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import com.wowfilm.wechatsdk.listener.impl.service.route.PlatFormEventRouter;
import org.apache.log4j.Logger;

/**
 * 授权ticket更新
 *
 * @author wuyongwen
 * @Date 2016年5月26日下午2:44:50
 */
@Node(parents = {PlatFormEventRouter.class, EventRouter.class}, value = "component_verify_ticket")
public class ComponentVerifyTicketAdaptor implements Service {

    protected Logger log = Logger.getLogger(ComponentVerifyTicketAdaptor.class);

    @Param
    protected String AppId;
    @Param
    protected String CreateTime;
    @Param
    protected String ComponentVerifyTicket;

    @Override
    public String doService(Context context) throws Exception {

//        new PlatFormTokenAccessor().updatePlatFormVerifyTicket(ComponentVerifyTicket, CreateTime);
        log.debug(String.format("收到刷新授权Ticket请求，新Ticket: %s", ComponentVerifyTicket));
        return DEFAULT_RETURN;
    }

}
