package com.wowfilm.wechatsdk.api.http;

import com.alibaba.fastjson.JSON;
import com.wowfilm.wechatsdk.common.Utf8ResponseHandler;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterialNews;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxGsonBuilder;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxMpGsonBuilder;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaterialNewsInfoRequestExecutor implements RequestExecutor<WxMpMaterialNews, String> {

  public MaterialNewsInfoRequestExecutor() {
    super();
  }

  public WxMpMaterialNews execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String materialId) throws ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    Map<String, String> params = new HashMap<>();
    params.put("media_id", materialId);
    httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
    CloseableHttpResponse response = httpclient.execute(httpPost);
    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
    ErrorUtils.checkWXError(JSON.parseObject(responseContent, BasicResult.class));
    return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
  }

}
