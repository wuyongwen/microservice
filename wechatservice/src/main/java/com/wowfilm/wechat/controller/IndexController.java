package com.wowfilm.wechat.controller;

import com.wowfilm.entity.wechat.Test;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wen on 2016/7/11.
 *
 * @author warden  Created on 2016-07-12 15:35:53
 */
@RestController
public class IndexController {
    @RequestMapping("/test")
    public Test test() {
        Test test = new Test();
        test.setId(1);
        test.setName("name");
        return test;
    }

    @RequestMapping("/testparam")
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
