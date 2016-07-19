package com.wowfilm.wechatsdk.store;

/**
 * Created by wen on 2016/7/19 16:58.
 */
public class WxMpConfigStorageBuilder {
    public static WxMpInMemoryConfigStorage build(String token){
        WxMpInMemoryConfigStorage storage = new WxMpInMemoryConfigStorage(){
            @Override
            public boolean isAccessTokenExpired() {
                return false;
            }
        };
        storage.accessToken = token;
        return storage;
    }
}
