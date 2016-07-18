package com.wowfilm.wechatsdk.listener.impl.service.route;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;

@Node(value = "root", parents = Service.class)
public final class MethodRouter implements Service {

    private Logger log = Logger.getLogger(MethodRouter.class);

    @Param("method")
    private String method;
    @Param
    private ThreadsMode threadsMode;

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("根据请求方法[%s]做路由。", method));
        return threadsMode.routeToNext(this.getClass(), this.method, context);
    }

}
