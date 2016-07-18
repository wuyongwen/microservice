package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import org.apache.log4j.Logger;

import static com.wowfilm.wechatsdk.listener.Service.DEFAULT_RETURN;

/**
 * @author lzxz1234
 * @version v1.0
 * @class LocationEventAdaptor
 * @description
 */
@Node(parents = EventRouter.class, value = "LOCATION")
public class LocationEventAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;
    @Param
    protected String FromUserName;
    @Param
    protected String CreateTime;
    @Param
    protected String Latitude;//地理位置纬度
    @Param
    protected String Longitude;//地理位置经度
    @Param
    protected String Precision;//地理位置精度

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的坐标信息 %s-%s(%s)", FromUserName,
                Latitude, Longitude, Precision));
        return DEFAULT_RETURN;
    }

}
