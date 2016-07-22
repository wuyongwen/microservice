package com.wowfilm.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.wowfilm.wechat.util.ServletUtils;
import com.wowfilm.wechat.util.WebAppContextInitFilter;
import com.wowfilm.wechat.wxservices.WxPlatformAuthService;
import com.wowfilm.wechatsdk.common.HttpUtils;
import com.wowfilm.wechatsdk.common.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;


/**
 * The type Wechat platform auth controller.
 * 微信授权controller
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 17:07:31
 */
@Controller
public class WechatPlatformAuthController {
    private static Logger logger = Logger.getLogger(WechatPlatformAuthController.class);
    @Autowired
    private WxPlatformAuthService wxPlatformAuthService;

    /**
     * 获取扫码授权URL
     * @return
     */
    @RequestMapping("/platform/doAuth")
    @ResponseBody
    public String doAuth(HttpServletRequest request){
        String url = wxPlatformAuthService.getAuthUrl(HttpUtils.decodeParams(request));
        return url;
    }

    /**
     * 扫码授权回调路径
     * 获取公众号信息,防止重复提交,重定向到info页面
     * @param code       the code
     * @param expires    the expires
     * @param backParam  the back param
     * @param attributes the attributes
     * @return the string
     */
    @RequestMapping(value="/platform/auth/callback",method = RequestMethod.GET)
    public String callBack(@RequestParam(value = "auth_code") String code,
                           @RequestParam(value = "expires_in") Integer expires, @RequestParam(required=false) String backParam, RedirectAttributes attributes){
        if(StringUtils.isNotBlank(backParam)){
            try {
                String param = URLDecoder.decode(backParam,"utf-8");
                Map mapParam = JSON.parseObject(param,Map.class);
                //TODO 微信授权完成后的处理业务
            } catch (UnsupportedEncodingException e) {
                logger.error("解析参数错误!",e);
            }
        }

        String msg = wxPlatformAuthService.callBack(code,expires);
        attributes.addAttribute("msg",msg);
        return "redirect:/auth/info";
    }

    /**
     * 授权成功回调到微信服务内部url,业务系统展示微信服务内的页面
     * @param msg
     * @return
     */
    @RequestMapping("/platform//auth/info")
    public ModelAndView authInfo(String msg){
        ModelAndView view = new ModelAndView();
        view.addObject("msg",msg);
        view.setViewName("authinfo");
        return view;
    }
}
