package com.wowfilm.wechatsdk.store;


/**
 * The type Platform config mem storage.
 *
 * @author warden
 * @date Created on 2016-07-12 19:44:07
 */
public class PlatformConfigMemStorage implements PlatformConfigStorage{
    private static PlatformConfig config = new PlatformConfig();
    @Override
    public void updateVerifyTicket(String verify_ticketb, String createTime) {
        config.setVerifyTicket(verify_ticketb);
        config.setCreateTime(Long.parseLong(createTime));
    }

    @Override
    public String getTicket() {
        return config.getVerifyTicket();
    }

    @Override
    public void updateAccessToken(String accessToken, Integer expiresIn) {
        config.setAccessToken(accessToken);
        config.setExpiresIn(System.currentTimeMillis() + (expiresIn - 200)* 1000l);
    }

    @Override
    public String getAccessToken() {
        if(isAccessTokenExpired()) return null;
        return config.getAccessToken();
    }

    @Override
    public void updatePreAuthCode(String preAuthCode, Integer expiresIn) {
        config.setPreAuthCode(preAuthCode);
        config.setPreAuthCodeExpiresIn(System.currentTimeMillis() + (expiresIn - 120)* 1000l);
    }

    @Override
    public String getPreAuthCode() {
        return config.getPreAuthCode();
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > config.getExpiresIn();
    }

    @Override
    public boolean isPreAuthCodeExpired() {
        return System.currentTimeMillis() > config.getPreAuthCodeExpiresIn();
    }
}
