package com.wowfilm.wechatsdk.api.http;

import com.alibaba.fastjson.JSON;
import com.wowfilm.wechatsdk.common.Utf8ResponseHandler;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;

/**
 * Created by wen on 2016/7/13 20:23.
 */
public class FileUploadRequestExeutor implements RequestExecutor<String, File> {
    @Override
    public String execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, File file)
            throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("media", file, ContentType.APPLICATION_OCTET_STREAM, file.getName())
                    .setMode(HttpMultipartMode.RFC6532)
                    .build();
            httpPost.setEntity(entity);
            //httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
        }
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            java.lang.String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            ErrorUtils.checkWXError(JSON.parseObject(responseContent, BasicResult.class));
            //HashMap<String,String> map = WxGsonBuilder.create().fromJson(responseContent, HashMap.class);
            return responseContent;
        }
    }
}
