package com.wowfilm.wechatsdk.api.http;


import com.alibaba.fastjson.JSON;
import com.wowfilm.wechatsdk.common.Utf8ResponseHandler;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterial;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxGsonBuilder;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpMaterialUploadResult;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.*;
import java.util.Map;


public class MaterialUploadRequestExecutor implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {

    public WxMpMaterialUploadResult execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, WxMpMaterial material) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpProxy != null) {
            RequestConfig response = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(response);
        }

        if (material != null) {
            File file = material.getFile();
            if (file == null || !file.exists()) {
                throw new FileNotFoundException();
            }
            //BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                    //.addPart("media", new InputStreamBody(bufferedInputStream, material.getName()))
                    .addBinaryBody("media", file, ContentType.APPLICATION_OCTET_STREAM, file.getName())
                    .setMode(HttpMultipartMode.RFC6532);
            Map<String, String> form = material.getForm();
            if (material.getForm() != null && material.getVideoTitle() != null) {
                multipartEntityBuilder.addTextBody("description", WxGsonBuilder.create().toJson(form));
            }
            httpPost.setEntity(multipartEntityBuilder.build());
            // httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
        }

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        ErrorUtils.checkWXError(JSON.parseObject(responseContent, BasicResult.class));
        return WxMpMaterialUploadResult.fromJson(responseContent);
    }

}
