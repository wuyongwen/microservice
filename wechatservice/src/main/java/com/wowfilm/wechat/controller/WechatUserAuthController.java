package com.wowfilm.wechat.controller;

import com.wowfilm.wechat.service.WxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/wxuser/auth")
    public Integer authUser(String code){
        return 0;
    }
}
