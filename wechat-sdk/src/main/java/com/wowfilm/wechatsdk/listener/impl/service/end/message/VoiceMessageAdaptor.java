package com.wowfilm.wechatsdk.listener.impl.service.end.message;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.RawMessageRouter;
import org.apache.log4j.Logger;


/**
 * The type Voice message adaptor.
 * @author warden
 * Created on 2016-07-12 15:34:56
 */
@Node(parents = RawMessageRouter.class, value = "voice")
public class VoiceMessageAdaptor implements Service {

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
    protected String MediaId;      //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    @Param
    protected String Format;       //语音格式，如amr，speex等
    @Param
    protected String Recognition;  //语音识别结果，UTF8编码

    @Override
    public String doService(Context context) throws Exception {

        log.debug(String.format("收到来自 %s 的音频信息 %s", FromUserName, MediaId));
        /*String content = TuringRobotApi.talking(Recognition, FromUserName).getTextInfo();
        return PassiveMessage.wrapText(FromUserName,ToUserName, content);*/
        return DEFAULT_RETURN;
    }

}
