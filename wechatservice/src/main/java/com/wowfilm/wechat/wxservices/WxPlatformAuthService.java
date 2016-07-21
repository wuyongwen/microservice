package com.wowfilm.wechat.wxservices;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.service.WxMpAppService;
import com.wowfilm.wechat.util.DateUtils;
import com.wowfilm.wechat.util.QrUtils;
import com.wowfilm.wechat.wxextend.WechatServiceFactory;
import com.wowfilm.wechat.wxextend.WxPlatformMgrFactory;
import com.wowfilm.wechatsdk.api.paltform.PlatFormManager;
import com.wowfilm.wechatsdk.api.wechatmp.WxMenuManager;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.entity.BasicResult;
import com.wowfilm.wechatsdk.entity.platform.PlatFormGetAuthInfoResult;
import com.wowfilm.wechatsdk.entity.platform.PlatFormGetAuthorizerInfoResult;
import com.wowfilm.wechatsdk.exception.ErrorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * The type Wx platform auth service.
 * 微信公众号授权服务
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 15:56:32
 */
@Service
public class WxPlatformAuthService {
    private static final Logger log = Logger.getLogger(WxPlatformAuthService.class);

    @Autowired
    private WxPlatformMgrFactory factory;
    @Autowired
    private WxMpAppService wxMpAppService;
    @Autowired
    private WechatServiceFactory wechatServiceFactory;
    public String getAuthUrl(Map param){
        log.info("获取授权URL!");
        String url = "";
        if(param.keySet().size()==0){
            url = factory.get().getLoginUrl();
        }else{
            url = factory.get().getLoginUrl(param);
        }
        return url;
    }
    public String callBack(String code, Integer expires) {
        log.info("授权回调:code :" + code + "   expires:" + expires);
        PlatFormManager formManager = factory.get();
        PlatFormGetAuthInfoResult authInfoResult = formManager.getAuthInfo(code);
        String authId = authInfoResult.getAuthorizationInfo().getAuthorizerAppId();
        PlatFormGetAuthorizerInfoResult authorizeInfo = formManager.getAuthorizerInfo(authId);
        WxMpApp wxMpApp = wxMpAppService.findByAppId(authId);
        if (wxMpApp == null)
            wxMpApp = new WxMpApp();
        // 保存信息
        putWxAppInfo(wxMpApp, authInfoResult, authorizeInfo);
        // 设置二维码
        getQrcodeImg(wxMpApp);
        // 设置菜单
        getMenuInfo(wxMpApp);
        wxMpAppService.save(wxMpApp);
        return wxMpApp.getNickName() + "授权成功!";
    }
    /**
     * 获取公众号菜单设置
     *
     * @param app
     * @return
     */
    public String getMenuInfo(WxMpApp app) {
        WxMenuManager service = wechatServiceFactory.getPlatformAppBuilder(app).MENU();
        String menu = service.selfMenuGet();
        JSONObject jsObj = JSON.parseObject(menu);
        Integer isMenuOpen = jsObj.getInteger("is_menu_open");
        String selfMenuInfo = jsObj.getString("selfmenu_info");
        app.setIsMenuOpern(isMenuOpen);
        app.setSelfMenuInfo(selfMenuInfo);
        return menu;
    }
    private void getQrcodeImg(WxMpApp wxMpApp) {
        String url = "http://open.weixin.qq.com/qr/code/?username=" + wxMpApp.getUserName();
        File temp = HttpUtils.downloadFile(url);
        String localPaht = QrUtils.uploadQrImg(temp, wxMpApp.getAuthorizerAppid());
        wxMpApp.setLocalqrcodePath(localPaht);
        String result = wechatServiceFactory.getSelfAppBuilder().MATERIAL().imageUpload(temp);
        ErrorUtils.checkWXError(JSON.parseObject(result, BasicResult.class));
        String qrurl = JSON.parseObject(result).getString("url");
        wxMpApp.setQrcodeUrl(qrurl);

    }
    private void putWxAppInfo(WxMpApp wxMpApp, PlatFormGetAuthInfoResult authInfoResult,
                              PlatFormGetAuthorizerInfoResult authorizeInfo) {
        wxMpApp.setAuthorizerAppid(authInfoResult.getAuthorizationInfo().getAuthorizerAppId());
        wxMpApp.setAuthorizerAccessToken(authInfoResult.getAuthorizationInfo().getAuthorizerAccessToken());
        wxMpApp.setAuthorizerRefreshToken(authInfoResult.getAuthorizationInfo().getAuthorizerRefreshToken());
        Integer expiresIn = Integer.parseInt(authInfoResult.getAuthorizationInfo().getExpiresIn());
        wxMpApp.setTokenExpiresOut(DateUtils.getAfterSecondDate(expiresIn - 600));
        wxMpApp.setFuncInfo(toFunInfo(authorizeInfo));
        wxMpApp.setNickName(authorizeInfo.getAuthorizerInfo().getNickName());
        wxMpApp.setHeadImg(authorizeInfo.getAuthorizerInfo().getHeadImg());
        wxMpApp.setServiceTypeInfo(authorizeInfo.getAuthorizerInfo().getServiceTypeInfo().getId());
        wxMpApp.setVerifyTypeInfo(authorizeInfo.getAuthorizerInfo().getVerifyTypeInfo().getId());
        wxMpApp.setUserName(authorizeInfo.getAuthorizerInfo().getUserName());
        wxMpApp.setBusinessInfo(JSON.toJSONString(authorizeInfo.getAuthorizerInfo().getBusinessInfo()));
        wxMpApp.setOpenShake(authorizeInfo.getAuthorizerInfo().getOpenShake());
        wxMpApp.setAlias(authorizeInfo.getAuthorizerInfo().getAlias());
        wxMpApp.setQrcodeUrl(authorizeInfo.getQrcodeUrl());
    }
    private String toFunInfo(PlatFormGetAuthorizerInfoResult authorizeInfo) {
        PlatFormGetAuthorizerInfoResult.FuncInfo[] funInfo = authorizeInfo.getAuthorizationInfo().getFuncInfo();
        StringBuffer sb = new StringBuffer();
        for (PlatFormGetAuthorizerInfoResult.FuncInfo f : funInfo) {
            sb.append(f.getFuncscopCategory().getId() + ",");
        }
        if (sb.toString().endsWith(",")) {
            return sb.substring(0, sb.lastIndexOf(","));
        }
        return "";
    }
}
