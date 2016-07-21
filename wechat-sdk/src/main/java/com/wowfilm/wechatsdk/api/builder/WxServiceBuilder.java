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

    /**
     * 微信配置服务接口
     * @return
     */
    public WxConfigService CONFIG() {
        WxConfigService baseService = new WxConfigService();
        baseService.init(local.get());
        return baseService;
    }

    /**
     * 微信分组接口
     * @return
     */
    public WxGroupManager GROUP() {
        WxGroupManager groupManager = new WxGroupManager();
        groupManager.init(local.get());
        return groupManager;
    }

    /**
     * 微信菜单接口
     * @return
     */
    public WxMenuManager MENU() {
        WxMenuManager wxMenuManager = new WxMenuManager();
        wxMenuManager.init(local.get());
        return wxMenuManager;
    }

    /**
     * 微信oauth2授权接口
     * @return
     */
    public WxOauth2Service OAUTH2() {
        WxOauth2Service wxOauth2Service = new WxOauth2Service();
        wxOauth2Service.init(local.get());
        return wxOauth2Service;
    }

    /**
     * 微信二维码接口
     * @return
     */
    public WxQrCodeManager QRCODE() {
        WxQrCodeManager wxQrCodeManager = new WxQrCodeManager();
        wxQrCodeManager.init(local.get());
        return wxQrCodeManager;
    }

    /**
     * 微信群发消息接口
     * @return
     */
    public WxSendMessageManager SENDMSG() {
        WxSendMessageManager wxSendMessageManager = new WxSendMessageManager();
        wxSendMessageManager.init(local.get());
        return wxSendMessageManager;
    }

    /**
     * 微信客服接口
     * @return
     */
    public WxServiceMessageManager SERVICEMSG() {
        WxServiceMessageManager manager = new WxServiceMessageManager();
        manager.init(local.get());
        return manager;
    }

    /**
     * 微信统计接口
     * @return
     */
    public WxStatInfoManager STATINFO() {
        WxStatInfoManager manager = new WxStatInfoManager();
        manager.init(local.get());
        return manager;
    }

    /**
     * 微信模板消息接口
     * @return
     */
    public WxTemplateMsgManager TEMPLATEMSG() {
        WxTemplateMsgManager manager = new WxTemplateMsgManager();
        manager.init(local.get());
        return manager;
    }

    /**
     * 微信URL服务即可
     * @return
     */
    public WxUrlTransformer URLTRANS() {
        WxUrlTransformer manager = new WxUrlTransformer();
        manager.init(local.get());
        return manager;
    }

    /**
     * 微信用户接口
     * @return
     */
    public WxUserManager USER() {
        WxUserManager manager = new WxUserManager();
        manager.init(local.get());
        return manager;
    }

    /**
     * 微信素材接口
     * @return
     */
    public WxMaterialManager MATERIAL(){
        WxMaterialManager materialManager = new WxMaterialManager();
        materialManager.init(local.get());
        return  materialManager;
    }
}
