package com.wowfilm.wechat.controller;

import com.wowfilm.wechat.service.WxArticleGroupService;
import com.wowfilm.wechat.service.WxArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wen on 2016/7/28 15:16.
 */
@RestController
@RequestMapping("/wxmsg/wxarticle")
public class WxArticleGroupController {
    @Autowired
    private WxArticleService wxArticleService;
    @Autowired
    private WxArticleGroupService wxArticleGroupService;

}
