package com.wowfilm.wechat.wxextend;

import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import com.wowfilm.wechatsdk.store.WechatAppType;
import com.wowfilm.wechatsdk.store.WxMpConfigStorage;

import javax.net.ssl.SSLContext;
import java.io.File;

/**
 * Created by wen on 2016/7/19 17:12.
 */
public class WxMpConfigDBStorage implements WxMpConfigStorage{
    private String appId;
    private String secret;
    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public WechatAppType getType() {
        return WechatAppType.WXPAYAPP;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return false;
    }

    @Override
    public void expireAccessToken() {

    }

    @Override
    public void updateAccessToken(String accessToken, long expiresIn) {

    }

    @Override
    public String getJsapiTicket() {
        return null;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return false;
    }

    @Override
    public void expireJsapiTicket() {

    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

    }

    @Override
    public String getAppId() {
        return null;
    }

    @Override
    public String getSecret() {
        return null;
    }

    @Override
    public String getPartnerId() {
        return null;
    }

    @Override
    public String getPartnerKey() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getAesKey() {
        return null;
    }

    @Override
    public long getExpiresTime() {
        return 0;
    }

    @Override
    public String getOauth2redirectUri() {
        return null;
    }

    @Override
    public String getHttp_proxy_host() {
        return null;
    }

    @Override
    public int getHttp_proxy_port() {
        return 0;
    }

    @Override
    public String getHttp_proxy_username() {
        return null;
    }

    @Override
    public String getHttp_proxy_password() {
        return null;
    }

    @Override
    public File getTmpDirFile() {
        return null;
    }

    @Override
    public SSLContext getSSLContext() {
        return null;
    }

    @Override
    public PlatformConfigStorage getPlatformConfigStorage() {
        return null;
    }
}
