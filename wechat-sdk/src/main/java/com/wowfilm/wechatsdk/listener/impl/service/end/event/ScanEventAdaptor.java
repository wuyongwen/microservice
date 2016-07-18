package com.wowfilm.wechatsdk.listener.impl.service.end.event;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.EventRouter;
import org.apache.log4j.Logger;

/**
 * 二维码扫描事件
 *
 * @author lzxz1234
 * @version v1.0
 * @class ScanEventAdaptor
 * @description
 */
@Node(parents = EventRouter.class, value = "SCAN")
public class ScanEventAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;
    @Param
    protected String FromUserName;
    @Param
    protected String CreateTime;
    @Param
    protected String EventKey;//事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    @Param
    protected String Ticket;//二维码的ticket，可用来换取二维码图片

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的二维码事件 %s-%s", FromUserName,
                EventKey, Ticket));
        return DEFAULT_RETURN;
    }

}
