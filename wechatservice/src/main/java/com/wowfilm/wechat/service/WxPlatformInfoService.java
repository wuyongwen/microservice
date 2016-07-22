package com.wowfilm.wechat.service;

import com.wowfilm.entity.wechat.WxPlatformInfo;
import com.wowfilm.wechat.mapper.WxPlatformInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wen on 2016/7/18 16:47.
 */

@Service
@Transactional
public class WxPlatformInfoService {
    @Autowired
    private WxPlatformInfoMapper wxPlatformInfoMapper;
    public WxPlatformInfo getPlatformInfo(String appId){
        return wxPlatformInfoMapper.findByAppId(appId);
    }
    public void save(WxPlatformInfo wxPlatformInfo){
        wxPlatformInfoMapper.save(wxPlatformInfo);
    }

    public void updateVerifyTicket(String verifyTicketb, Date createTime, String id) {
        WxPlatformInfo wxPlatformInfo = getPlatformInfo(id);
        if(wxPlatformInfo!=null){
            wxPlatformInfo.setComponentVerifyTicket(verifyTicketb);
            wxPlatformInfo.setTicketCreateTime(createTime);
            wxPlatformInfoMapper.update(wxPlatformInfo);
        }
    }

    public void updateAccessToken(String accessToken, Date date, String id) {
        WxPlatformInfo wxPlatformInfo = getPlatformInfo(id);
        if(wxPlatformInfo!=null){
            wxPlatformInfo.setComponentAccessToken(accessToken);
            wxPlatformInfo.setTokenExpiresOut(date);
            wxPlatformInfoMapper.update(wxPlatformInfo);
        }
    }

    public void updatePreAuthCode(String preAuthCode, Date date, String id) {
        WxPlatformInfo wxPlatformInfo = getPlatformInfo(id);
        if(wxPlatformInfo!=null){
            wxPlatformInfo.setPreAuthCode(preAuthCode);
            wxPlatformInfo.setPreAuthCodeExpiresOut(date);
            wxPlatformInfoMapper.update(wxPlatformInfo);
        }
    }
}
