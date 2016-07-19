package com.wowfilm.wechat.wxservices;

import com.wowfilm.wechat.content.SpringContextHolder;
import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.api.paltform.PlatFormTokenAccessor;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.impl.service.end.event.ComponentVerifyTicketAdaptor;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import com.wowfilm.wechatsdk.listener.impl.service.route.PlatFormEventRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 更新授权
 *
 * @author wuyongwen
 * @Date 2016年6月1日下午5:06:06
 */
@Node(parents = {PlatFormEventRouter.class, EventRouter.class}, value = "component_verify_ticket")
public class ComponentVerifyTicketService extends ComponentVerifyTicketAdaptor {
    private static final Logger logger = LoggerFactory.getLogger(ComponentVerifyTicketService.class);
    private PlatFormTokenAccessor platFormTokenAccessor = SpringContextHolder.getBean(PlatFormTokenAccessor.class,
            "platFormTokenAccessor");

    @Override
    public String doService(Context context) throws Exception {
        logger.debug(String.format("收到刷新授权Ticket请求，新Ticket: %s", ComponentVerifyTicket));
        platFormTokenAccessor.updatePlatFormVerifyTicket(ComponentVerifyTicket, CreateTime);
        return DEFAULT_RETURN;
    }
}
