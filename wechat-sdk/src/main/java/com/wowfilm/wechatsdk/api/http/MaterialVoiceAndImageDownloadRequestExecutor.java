package com.wowfilm.wechatsdk.api.http;

import com.wowfilm.wechatsdk.common.IOUtils;
import com.wowfilm.wechatsdk.common.InputStreamResponseHandler;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Material voice and image download request executor.
 *
 * @author warden
 * @date Created on 2016-07-13 15:43:47
 */
public class MaterialVoiceAndImageDownloadRequestExecutor implements RequestExecutor<InputStream, String> {

    private File tmpDirFile;

    public MaterialVoiceAndImageDownloadRequestExecutor() {
        super();
    }

    public MaterialVoiceAndImageDownloadRequestExecutor(File tmpDirFile) {
        super();
        this.tmpDirFile = tmpDirFile;
    }

    public InputStream execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String materialId) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpProxy != null) {
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            httpPost.setConfig(config);
        }

        Map<String, String> params = new HashMap<>();
        params.put("media_id", materialId);
        httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        // 下载媒体文件出错
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);
        byte[] responseContent = IOUtils.toByteArray(inputStream);
        String responseContentString = new String(responseContent, "UTF-8");
        if (responseContentString.length() < 100) {
            try {
                BasicResult wxError = WxGsonBuilder.create().fromJson(responseContentString, BasicResult.class);
                ErrorUtils.checkWXError(wxError);
            } catch (com.google.gson.JsonSyntaxException ex) {
                return new ByteArrayInputStream(responseContent);
            }
        }
        return new ByteArrayInputStream(responseContent);
    }

}
