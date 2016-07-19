package com.wowfilm.wechatsdk.api.builder;

import com.wowfilm.wechatsdk.store.WxMpConfigStorage;
import com.wowfilm.wechatsdk.api.wechatmp.*;
/**
 * 微信服务获取类
 * The type Wx service builder.
 * @author warden
 * Created on 2016-07-14 11:11:27
 */
public class WxServiceBuilder {
    private static ThreadLocal<WxMpConfigStorage> local = new ThreadLocal<>();

    private WxServiceBuilder() {
    }

    public static WxServiceBuilder build(WxMpConfigStorage config) {
        WxServiceBuilder wxServiceBuilder = new WxServiceBuilder();
        wxServiceBuilder.local.set(config);
        return wxServiceBuilder;
    }

    public WxConfigService CONFIG() {
        WxConfigService baseService = new WxConfigService();
        baseService.init(local.get());
        return baseService;
    }

    public WxGroupManager GROUP() {
        WxGroupManager groupManager = new WxGroupManager();
        groupManager.init(local.get());
        return groupManager;
    }

    public WxMenuManager MENU() {
        WxMenuManager wxMenuManager = new WxMenuManager();
        wxMenuManager.init(local.get());
        return wxMenuManager;
    }

    public WxOauth2Service OAUTH2() {
        WxOauth2Service wxOauth2Service = new WxOauth2Service();
        wxOauth2Service.init(local.get());
        return wxOauth2Service;
    }

    public WxQrCodeManager QRCODE() {
        WxQrCodeManager wxQrCodeManager = new WxQrCodeManager();
        wxQrCodeManager.init(local.get());
        return wxQrCodeManager;
    }

    public WxSendMessageManager SENDMSG() {
        WxSendMessageManager wxSendMessageManager = new WxSendMessageManager();
        wxSendMessageManager.init(local.get());
        return wxSendMessageManager;
    }

    public WxServiceMessageManager SERVICEMSG() {
        WxServiceMessageManager manager = new WxServiceMessageManager();
        manager.init(local.get());
        return manager;
    }

    public WxStatInfoManager STATINFO() {
        WxStatInfoManager manager = new WxStatInfoManager();
        manager.init(local.get());
        return manager;
    }

    public WxTemplateMsgManager TEMPLATEMSG() {
        WxTemplateMsgManager manager = new WxTemplateMsgManager();
        manager.init(local.get());
        return manager;
    }

    public WxUrlTransformer URLTRANS() {
        WxUrlTransformer manager = new WxUrlTransformer();
        manager.init(local.get());
        return manager;
    }

    public WxUserManager USER() {
        WxUserManager manager = new WxUserManager();
        manager.init(local.get());
        return manager;
    }

}
