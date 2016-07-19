package com.wowfilm.wechatsdk.api.wechatmp;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.store.WxMpConfigStorage;
import com.wowfilm.wechatsdk.store.WxMpInMemoryConfigStorage;
import com.wowfilm.wechatsdk.api.builder.WxPlatformServiceBuilder;
import com.wowfilm.wechatsdk.api.http.RequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import com.wowfilm.wechatsdk.common.RandomUtils;
import com.wowfilm.wechatsdk.common.SHA1;
import com.wowfilm.wechatsdk.common.StringUtils;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.entity.platform.PlatFormGetAuthAccessResult;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxAccessToken;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxJsapiSignature;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;


/**
 * The type Token accessor. app token 管理
 * @author warden
 * @date Created on 2016-07-13 18:29:03
 */
public class TokenAccessor {
    private WxMpConfigStorage wxMpConfigStorage;
    protected CloseableHttpClient httpClient;

    protected HttpHost httpProxy;

    public WxMpConfigStorage getWxMpConfigStorage() {
        return wxMpConfigStorage;
    }

    public void setWxMpConfigStorage(WxMpConfigStorage wxMpConfigStorage) {
        this.wxMpConfigStorage = wxMpConfigStorage;

        String http_proxy_host = wxMpConfigStorage.getHttp_proxy_host();
        int http_proxy_port = wxMpConfigStorage.getHttp_proxy_port();
        String http_proxy_username = wxMpConfigStorage.getHttp_proxy_username();
        String http_proxy_password = wxMpConfigStorage.getHttp_proxy_password();

        final HttpClientBuilder builder = HttpClients.custom();
        if (StringUtils.isNotBlank(http_proxy_host)) {
            // 使用代理服务器
            if (StringUtils.isNotBlank(http_proxy_username)) {
                // 需要用户认证的代理服务器
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(
                        new AuthScope(http_proxy_host, http_proxy_port),
                        new UsernamePasswordCredentials(http_proxy_username, http_proxy_password));
                builder
                        .setDefaultCredentialsProvider(credsProvider);
            } else {
                // 无需用户认证的代理服务器
            }
            httpProxy = new HttpHost(http_proxy_host, http_proxy_port);
        }
        if (wxMpConfigStorage.getSSLContext() != null){
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    wxMpConfigStorage.getSSLContext(),
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            builder.setSSLSocketFactory(sslsf);
        }
        httpClient = builder.build();
    }

    /**
     * 获取access_token, 不强制刷新access_token
     *
     * @see #getAccessToken(boolean)
     */
    public String getAccessToken() {
        return getAccessToken(false);
    }

    /**
     * <pre>
     * 获取access_token，本方法线程安全
     * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
     *
     * 另：本service的所有方法都会在access_token过期是调用此方法
     *
     * 程序员在非必要情况下尽量不要主动调用此方法
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
     * </pre>
     *
     * @param forceRefresh 强制刷新
     * @return string
     */
    public String getAccessToken(boolean forceRefresh) {
        if (forceRefresh) {
            wxMpConfigStorage.expireAccessToken();
        }
        if (wxMpConfigStorage.isAccessTokenExpired()) {
            updateAccessToken(getHttpProxy(),getHttpclient());
        }
        return wxMpConfigStorage.getAccessToken();
    }

    public void updateAccessToken(HttpHost httpProxy, CloseableHttpClient httpClient){
        synchronized (WxMpInMemoryConfigStorage.class) {
            if (wxMpConfigStorage.isAccessTokenExpired()) {
                switch (wxMpConfigStorage.getType()){
                    case PLATFORAPP:
                        PlatFormManager formManager = WxPlatformServiceBuilder.build(wxMpConfigStorage.getPlatformConfigStorage()).PLATFORM();
                        PlatFormGetAuthAccessResult result = formManager.getAuthAccessToken(wxMpConfigStorage.getAppId(), wxMpConfigStorage.getRefreshToken());
                        ErrorUtils.checkWXError(result);
                        if(!result.hasError()){
                            wxMpConfigStorage.updateAccessToken(result.getAuthorizerAccessToken(), result.getExpiresIn());
                        }
                        break;
                    case SELFAPP:
                        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid="
                                + wxMpConfigStorage.getAppId() + "&secret=" + wxMpConfigStorage.getSecret();
                        RequestExecutor<String,String> getRequest = new SimpleGetRequestExecutor();
                        try {
                            String resultContent = getRequest.execute(getHttpclient(),getHttpProxy(),url,null);
                            ErrorUtils.checkWXError(JSON.parseObject(resultContent, BasicResult.class));
                            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                            wxMpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case WXPAYAPP:
                        break;
                }
            }
        }
    }
    /**
     * 获得jsapi_ticket,不强制刷新jsapi_ticket
     *
     * @return string
     * @see #getJsapiTicket(boolean)
     */
    public String getJsapiTicket() {
        return getJsapiTicket(false);
    }

    /**
     * <pre>
     * 获得jsapi_ticket
     * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     *
     * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
     * </pre>
     *
     * @param forceRefresh 强制刷新
     * @return
     */
    public String getJsapiTicket(boolean forceRefresh) {
        if(forceRefresh){
            wxMpConfigStorage.expireJsapiTicket();
        }
        if(wxMpConfigStorage.isJsapiTicketExpired()){
            WxServiceExecutor executor = new WxServiceExecutor();
            executor.setAccessor(this);
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
            String responseContent = executor.execute(new SimpleGetRequestExecutor(), url, null);
            JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
            JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
            String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
            int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
            wxMpConfigStorage.updateJsapiTicket(jsapiTicket, expiresInSeconds);
        }
        return wxMpConfigStorage.getJsapiTicket();
    }

    /**
     * <pre>
     * 创建调用jsapi时所需要的签名
     *
     * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     * </pre>
     *
     * @param url url
     * @return
     */
    public WxJsapiSignature createJsapiSignature(String url) {
        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsapiTicket(false);
        try {
            String signature = SHA1.genWithAmple(
                    "jsapi_ticket=" + jsapiTicket,
                    "noncestr=" + noncestr,
                    "timestamp=" + timestamp,
                    "url=" + url
            );
            WxJsapiSignature jsapiSignature = new WxJsapiSignature();
            jsapiSignature.setAppid(wxMpConfigStorage.getAppId());
            jsapiSignature.setTimestamp(timestamp);
            jsapiSignature.setNoncestr(noncestr);
            jsapiSignature.setUrl(url);
            jsapiSignature.setSignature(signature);
            return jsapiSignature;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void expireAccessToken() {
        wxMpConfigStorage.expireAccessToken();
    }

    public CloseableHttpClient getHttpclient() {
        return httpClient;
    }

    public HttpHost getHttpProxy() {
        return httpProxy;
    }
}
