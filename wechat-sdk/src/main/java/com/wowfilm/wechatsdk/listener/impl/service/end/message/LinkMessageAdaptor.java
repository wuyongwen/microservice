package com.wowfilm.wechatsdk.listener.impl.service.end.message;

import com.wowfilm.wechatsdk.listener.impl.service.route.RawMessageRouter;
import org.apache.log4j.Logger;
import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;


@Node(parents=RawMessageRouter.class, value="link")
public class LinkMessageAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());
    
    @Param protected String ToUserName;   //开发者微信号
    @Param protected String FromUserName; //发送方帐号（一个OpenID）
    @Param protected String CreateTime;   //消息创建时间 （整型）
    @Param protected String MsgId;        //消息id，64位整型
    @Param protected String Title;        //消息标题
    @Param protected String Description;  //消息描述
    @Param protected String Url;          //消息链接
    
    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的链接信息 %s", FromUserName, Title));
        //return PassiveMessage.wrapText(FromUserName,ToUserName, Url);
        return DEFAULT_RETURN;
    }

}
