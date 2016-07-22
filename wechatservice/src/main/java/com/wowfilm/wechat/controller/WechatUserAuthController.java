package com.wowfilm.wechat.controller;

import biz.entgroup.framework.entity.Userinfo;
import com.wowfilm.wechat.service.WxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Wechat user auth controller.
 * 微信用户授权登录接口, 拉取用户信息
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-22 16:31:18
 */
@RestController
public class WechatUserAuthController {
    @Autowired
    private WxUserInfoService wxUserInfoService;

    /**
     * Get auth url.
     *
     * @param redirectUrl the redirect url
     * @param stats       the stats
     * @param scope       the scope
     * @return the auth url
     */
    @RequestMapping("/wxuser/authurl")
    public String getAuthUrl(@RequestParam(required = false) String redirectUrl, String stats, String scope){
        return wxUserInfoService.getOauthUrl(redirectUrl,stats,scope);
    }

    /**
     * Auth user .
     * @param code the code
     * @return user id
     */
    @RequestMapping("/wxuser/auth")
    public Integer authUser(String code){
        return wxUserInfoService.doAuth(code);
    }


    /**
     * Get user info userinfo.
     * @param userId the user id
     * @return the userinfo
     */
    @RequestMapping("/wxuser/info")
    public Userinfo getUserInfo(Integer userId){
        return wxUserInfoService.getUserInfoById(userId);
    }
}
