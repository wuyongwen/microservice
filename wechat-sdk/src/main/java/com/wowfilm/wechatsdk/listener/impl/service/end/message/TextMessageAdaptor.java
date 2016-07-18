package com.wowfilm.wechatsdk.listener.impl.service.end.message;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.RawMessageRouter;
import org.apache.log4j.Logger;

@Node(parents = RawMessageRouter.class, value = "text")
public class TextMessageAdaptor implements Service {

    protected Logger log = Logger.getLogger(this.getClass());
    protected static final String AUTO_TEST_TEXTMSG_RETURN = "TESTCOMPONENT_MSG_TYPE_TEXT";
    protected static final String AUTO_TEST_TEXTMSG_RETURN_BACK = "TESTCOMPONENT_MSG_TYPE_TEXT_callback";
    @Param
    protected String ToUserName;   //开发者微信号
    @Param
    protected String FromUserName; //发送方帐号（一个OpenID）
    @Param
    protected String CreateTime;   //消息创建时间 （整型）
    @Param
    protected String MsgId;        //消息id，64位整型
    @Param
    protected String Content;      //消息id，64位整型

    @Override
    public String doService(Context context) throws Exception {
        log.debug(String.format("收到来自 %s 的文本信息 %s", FromUserName, Content));
        // String content = PassiveMessage.wrapText(FromUserName, ToUserName, TuringRobotApi.talking(Content, FromUserName).getTextInfo());
        // return content;
        return DEFAULT_RETURN;
    }
}
