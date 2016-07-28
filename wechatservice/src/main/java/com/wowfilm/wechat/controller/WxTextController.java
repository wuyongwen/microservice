package com.wowfilm.wechat.controller;

import biz.entgroup.framework.db.MessageResponse;
import biz.entgroup.framework.entity.Wxtext;
import com.wowfilm.wechat.service.WxTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wen on 2016/7/28 15:14.
 */
@RestController
@RequestMapping("/wxmsg/wxtext")
public class WxTextController {
    @Autowired
    private WxTextService wxTextService;

    @RequestMapping("send")
    public MessageResponse<Wxtext> send(@RequestBody Wxtext wxtext){
       return wxTextService.send(wxtext);
    }
}
