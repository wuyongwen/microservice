package com.wowfilm.wechat.service;

import biz.entgroup.framework.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wen on 2016/7/22 17:02.
 */
@Service
public class WxUserInfoService {
    @Autowired
    private UserinfoMapper userinfoMapper;

    public void doAuth(){
    }
}
