package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;

import java.io.StringReader;

/**
 * url转换服务
 * @author warden
 * @date Created on 2016-07-13 18:28:22
 */
public class WxUrlTransformer extends WxBaseService{

    /**
     * <pre>
     * 长链接转短链接接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
     * </pre>
     *
     * @param long_url
     * @return String
     */
    public String shortUrl(String long_url){
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl";
        JsonObject o = new JsonObject();
        o.addProperty("action", "long2short");
        o.addProperty("long_url", long_url);
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, o.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
    }
}
