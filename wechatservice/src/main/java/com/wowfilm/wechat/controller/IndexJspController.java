package com.wowfilm.wechat.controller;

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
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("val","wechat");
        return "index";
    }
}
