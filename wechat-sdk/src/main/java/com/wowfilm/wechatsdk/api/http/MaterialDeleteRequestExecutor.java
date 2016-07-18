package com.wowfilm.wechatsdk.api.http;

import com.alibaba.fastjson.JSON;
import com.wowfilm.wechatsdk.common.Utf8ResponseHandler;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxGsonBuilder;
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

public class MaterialDeleteRequestExecutor implements RequestExecutor<Boolean, String> {


  public MaterialDeleteRequestExecutor() {
    super();
  }

  public Boolean execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String materialId) throws ClientProtocolException, IOException {
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
    BasicResult result = JSON.parseObject(responseContent,BasicResult.class);
    ErrorUtils.checkWXError(result);
    /*WxError error = WxError.fromJson(responseContent);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    } else {
    }*/
    return true;
  }

}
