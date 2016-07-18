package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpTemplateMessage;

import java.io.StringReader;


/**
 * 模板消息服务
 * The type Wx template msg manager.
 *
 * @author warden
 * @date Created on 2016-07-13 18:32:30
 */
public class WxTemplateMsgManager extends WxBaseService{
    /**
     * <pre>
     * 发送模板消息
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=模板消息接口
     * </pre>
     *
     * @param templateMessage
     * @return msgid
     */
    public String templateSend(WxMpTemplateMessage templateMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, templateMessage.toJson());

        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        final JsonObject jsonObject = tmpJsonElement.getAsJsonObject();
        return jsonObject.get("msgid").getAsString();
    }
}
