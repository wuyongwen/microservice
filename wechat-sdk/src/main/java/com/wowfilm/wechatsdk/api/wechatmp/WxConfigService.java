package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpSemanticQuery;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpSemanticQueryResult;

import java.io.StringReader;


/**
 * The type weChat config service.
 *
 * @author warden
 * @date Created on 2016-07-13 18:29:50
 */
public class WxConfigService extends WxBaseService{

    /**
     * <pre>
     * 获取微信服务器IP地址
     * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
     * </pre>
     */
    String[] getCallbackIP(){
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
        String responseContent = executor.get(url, null);
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
        String[] ipArray = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipArray[i] = ipList.get(i).getAsString();
        }
        return ipArray;
    }
    /**
     * <pre>
     * 语义查询接口
     * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
     * </pre>
     */
    public  WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery){
        String url = "https://api.weixin.qq.com/semantic/semproxy/search";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, semanticQuery.toJson());
        return WxMpSemanticQueryResult.fromJson(responseContent);
    }
}
