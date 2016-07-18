/**
 * WeiXin
 *
 * @title LocationMessageAdaptor.java
 * @package com.chn.wx.listener
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月17日-下午6:12:58
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.listener.impl.service.end.message;

import com.wowfilm.wechatsdk.listener.impl.service.route.RawMessageRouter;
import org.apache.log4j.Logger;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;

@Node(parents = RawMessageRouter.class, value = "location")
public class LocationMessageAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());

    @Param
    protected String ToUserName;   //开发者微信号
    @Param
    protected String FromUserName; //发送方帐号（一个OpenID）
    @Param
    protected String CreateTime;   //消息创建时间 （整型）
    @Param
    protected String MsgId;        //消息id，64位整型
    @Param
    protected String Location_X;   //地理位置维度
    @Param
    protected String Location_Y;   //地理位置经度
    @Param
    protected String Scale;        //地图缩放大小
    @Param
    protected String Label;        //地理位置信息

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的位置信息 %s-%s", FromUserName,
                Location_X, Location_Y));
        //return PassiveMessage.wrapText(FromUserName, ToUserName, Label);
        return DEFAULT_RETURN;
    }


}
