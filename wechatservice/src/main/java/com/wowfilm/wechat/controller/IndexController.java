package com.wowfilm.wechat.controller;

import com.wowfilm.entity.wechat.Test;
import com.wowfilm.entity.wechat.WxPlatformInfo;
import com.wowfilm.wechat.service.WxPlatformInfoService;
import com.wowfilm.wechat.util.WebAppContextInitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.wowfilm.wechat.util.WebAppContextInitFilter.getInitedWebContextFullUrl;
import static org.apache.coyote.http11.Constants.a;

/**
 * Created by wen on 2016/7/11.
 *
 * @author warden  Created on 2016-07-12 15:35:53
 */
@RestController
public class IndexController {

    @Autowired
    WxPlatformInfoService wxPlatformInfoService;

    @RequestMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public WxPlatformInfo test() {

        WxPlatformInfo wxPlatformInfo = wxPlatformInfoService.getPlatformInfo("1");
//        wxPlatformInfo.setAppId("2");
//        wxPlatformInfo.setComponentAccessToken("logging.level.root");
//        wxPlatformInfo.setPreAuthCode("server.port ");
//        wxPlatformInfoService.save(wxPlatformInfo);
        return wxPlatformInfo;
    }

    @RequestMapping("/testparam")
    @ResponseStatus(HttpStatus.OK)
    public Test testParam(@RequestParam String name, @RequestParam int id) {
        Test test = new Test();
        test.setId(id);
        test.setName(name);
        return test;
    }
    @RequestMapping(value = "/testbean",method = RequestMethod.POST)
    public Test testBean(@RequestBody Test test){
        System.out.println(test);
        test.setId(test.getId()+1);
        return test;
    }
    @RequestMapping("/testredirect")
    public ModelAndView testRedirect(RedirectAttributes attributes){
        ModelAndView view = new ModelAndView();
        Test t = new Test();
        t.setId(122);
        t.setName("中华");
        attributes.addAttribute("test",t);
        view.setViewName("redirect:/toRedirect");
        return view;
    }

    @RequestMapping("/toRedirect")
    public String toRedirect(String test,Model model){
        model.addAttribute("test",test);
        String url = WebAppContextInitFilter.getInitedWebContextFullUrl();
        model.addAttribute("url",url);
        return "/authinfo";
    }
}
