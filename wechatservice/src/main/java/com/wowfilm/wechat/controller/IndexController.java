package com.wowfilm.wechat.controller;

import com.wowfilm.entity.wechat.Test;
import com.wowfilm.entity.wechat.WxPlatformInfo;
import com.wowfilm.wechat.service.WxPlatformInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
