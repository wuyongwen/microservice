package com.wowfilm.wechat.wxextend;

import com.wowfilm.entity.wechat.WxPlatformInfo;
import com.wowfilm.wechat.service.WxPlatformInfoService;
import com.wowfilm.wechat.util.DateUtils;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.dto.AppInfo;
import com.wowfilm.wechatsdk.store.PlatformConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * The type Platform config db storage.
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-19 15:05:42
 */
@Component("PlatformConfigDBStorage")
public class PlatformConfigDBStorage implements PlatformConfigStorage {
    private static final AppInfo appInfo = App.getInfo();
    @Autowired
    private WxPlatformInfoService wxPlatformInfoService;

    @Override
    public void updateVerifyTicket(String verifyTicketb, String createTime) {
        wxPlatformInfoService.updateVerifyTicket(verifyTicketb, DateUtils.parseTime(createTime), appInfo.id);
    }

    @Override
    public String getTicket() {
        return wxPlatformInfoService.getPlatformInfo(appInfo.id).getComponentVerifyTicket();
    }

    @Override
    public void updateAccessToken(String accessToken, Integer expiresIn) {
        Date date = DateUtils.getAfterSecondDate(expiresIn);
        wxPlatformInfoService.updateAccessToken(accessToken, date, appInfo.id);
    }

    @Override
    public String getAccessToken() {
        return wxPlatformInfoService.getPlatformInfo(appInfo.id).getComponentAccessToken();
    }

    @Override
    public void updatePreAuthCode(String preAuthCode, Integer expiresIn) {
        Date date = DateUtils.getAfterSecondDate(expiresIn);
        wxPlatformInfoService.updatePreAuthCode(preAuthCode, date, appInfo.id);
    }

    @Override
    public String getPreAuthCode() {
        return wxPlatformInfoService.getPlatformInfo(appInfo.id).getPreAuthCode();
    }

    @Override
    public boolean isAccessTokenExpired() {
        WxPlatformInfo info = wxPlatformInfoService.getPlatformInfo(appInfo.id);
        if(info == null || info.getTokenExpiresOut()==null) return true;
        return DateUtils.getCurrentDate().after(info.getTokenExpiresOut());
    }

    @Override
    public boolean isPreAuthCodeExpired() {
        WxPlatformInfo info = wxPlatformInfoService.getPlatformInfo(appInfo.id);
        if(info == null || info.getPreAuthCodeExpiresOut()==null) return true;
        return DateUtils.getCurrentDate().after(info.getPreAuthCodeExpiresOut());
    }
}
