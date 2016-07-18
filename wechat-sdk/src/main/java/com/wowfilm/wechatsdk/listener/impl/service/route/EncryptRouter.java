package com.wowfilm.wechatsdk.listener.impl.service.route;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;

@Node(value = "POST", parents = MethodRouter.class)
public final class EncryptRouter implements Service {

    private Logger log = Logger.getLogger(EncryptRouter.class);

    @Param(value = "encrypt_type", defaultValue = "raw")
    private String encrypt_type; //加密类型
    @Param
    private ThreadsMode threadsMode;

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("根据加密类型[%s]做路由。", encrypt_type));
        return threadsMode.routeToNext(this.getClass(), this.encrypt_type, context);
    }

}
