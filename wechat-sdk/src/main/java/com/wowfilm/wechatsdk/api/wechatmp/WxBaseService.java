package com.wowfilm.wechatsdk.api.wechatmp;


import com.wowfilm.wechatsdk.store.WxMpConfigStorage;


/**
 * The type Wx base service.
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-14 11:54:27
 */
public class WxBaseService{
    protected TokenAccessor tokenAccessor;
    protected WxServiceExecutor executor;

    protected WxBaseService() {
    }
    public void init(WxMpConfigStorage config){
        tokenAccessor = new TokenAccessor();
        executor = new WxServiceExecutor();
        tokenAccessor.setWxMpConfigStorage(config);
        executor.setAccessor(tokenAccessor);
    }
}
