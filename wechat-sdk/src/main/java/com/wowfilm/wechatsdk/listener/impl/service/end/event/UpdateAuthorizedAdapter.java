package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import com.wowfilm.wechatsdk.listener.impl.service.route.PlatFormEventRouter;
import org.apache.log4j.Logger;

/**
 * 更新授权
 *
 * @author wuyongwen
 * @Date 2016年5月26日下午3:12:04
 */
@Node(parents = {PlatFormEventRouter.class, EventRouter.class}, value = "updateauthorized")
public class UpdateAuthorizedAdapter implements Service {
    protected Logger log = Logger.getLogger(UpdateAuthorizedAdapter.class);
    @Param
    protected String AppId;
    @Param
    protected String CreateTime;
    @Param
    protected String AuthorizerAppid;
    @Param
    protected String AuthorizationCode;
    @Param
    protected String AuthorizationCodeExpiredTime;

    @Override
    public String doService(Context context) throws Exception {
        log.debug(String.format("收到更新授权事件，公众号ID： %s", AuthorizerAppid));
        return DEFAULT_RETURN;
    }

}
