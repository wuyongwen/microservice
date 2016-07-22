package com.wowfilm.wechat.controller;

import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.service.WxMpAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Index jsp controller.
 * @author warden
 * Created on 2016-07-12 15:37:17
 */
@Controller
public class IndexJspController {
    @Autowired
    private WxMpAppService service;
    @RequestMapping("/index")
    public String index(Model model){
        WxMpApp app = service.findByAppId("xb2345234123");
        model.addAttribute("val","wechat");
        model.addAttribute("app",app);
        return "index";
    }
}
