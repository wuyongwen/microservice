package com.wowfilm.wechat.controller;

import com.wowfilm.wechat.wxservices.WxPlatformAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * The type Wechat platform auth controller.
 * 微信授权controller
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 17:07:31
 */
@Controller
public class WechatPlatformAuthController {
    @Autowired
    private WxPlatformAuthService wxPlatformAuthService;

    /**
     * 获取扫码授权URL
     * @return
     */
    @RequestMapping("/doAuth")
    public String doAuth(){
        String url = wxPlatformAuthService.getAuthUrl();
        return "redirect:" + url;
    }

    @RequestMapping(value="callback",method = RequestMethod.GET)
    public String callBack(@RequestParam(value = "auth_code") String code,
                           @RequestParam(value = "expires_in") Integer expires,@RequestParam(required=false) String back) throws UnsupportedEncodingException {
        String[] param = URLDecoder.decode(back,"utf-8").split("@");
        int cinemaOrgId = Integer.parseInt(param[0].split("=")[1]);
        String redirectUrl = param[1].split("=")[1];
        //String msg = wxPlatformAuthService.callBack(code,expires,);
        return "redirect:";
    }
}
