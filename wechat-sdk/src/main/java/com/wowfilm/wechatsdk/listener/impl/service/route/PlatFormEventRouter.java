package com.wowfilm.wechatsdk.listener.impl.service.route;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;


@Node(value = "platform", parents = {RawMessageRouter.class})
public final class PlatFormEventRouter implements Service {

    protected Logger log = Logger.getLogger(PlatFormEventRouter.class);

    @Param
    private ThreadsMode threadsMode;
    @Param
    private String InfoType;

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("第三方平台服务根据类型 [%s] 作路由", InfoType));
        return threadsMode.routeToNext(this.getClass(), InfoType, context);
    }

}
