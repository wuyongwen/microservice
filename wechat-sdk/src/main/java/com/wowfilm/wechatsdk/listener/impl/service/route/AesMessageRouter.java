package com.wowfilm.wechatsdk.listener.impl.service.route;


import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;

@Node(value = "aes", parents = EncryptRouter.class)
public final class AesMessageRouter implements Service {

    @Param
    private String msg_signature;//消息体签名
    @Param
    private String timestamp;
    @Param
    private String nonce;
    @Param
    private String xmlContent;

    @Param
    private ThreadsMode threadsMode;

    private WXBizMsgCrypt msgCrypt;

    @Override
    public String doService(Context context) throws Exception {

        msgCrypt = new WXBizMsgCrypt(App.Info.token, App.Info.aesKey, App.Info.id);

        //解密，并将解密后信息放入上下文
        xmlContent = msgCrypt.decryptMsg(msg_signature, timestamp, nonce, xmlContent);
        context.setAttribute("xmlContent", xmlContent);

        //调用下一环节
        String result = threadsMode.routeToNext(this.getClass(), "raw", context);

        //加密并组 XML 返回
        return msgCrypt.encryptMsg(result, timestamp, nonce);
    }

}
