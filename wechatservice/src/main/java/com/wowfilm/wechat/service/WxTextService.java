package com.wowfilm.wechat.service;

import biz.entgroup.framework.db.MessageResponse;
import biz.entgroup.framework.entity.Wxtext;
import com.wowfilm.wechat.mapper.WxTextMapper;
import com.wowfilm.wechat.util.DateUtils;
import com.wowfilm.wechat.wxextend.WechatServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.awt.SystemColor.text;

/**
 * Created by wen on 2016/7/28 14:49.
 */
@Service
public class WxTextService {
    @Autowired
    private WxTextMapper wxTextMapper;
    @Autowired
    private WxPublishNewsService wxPublishNewsService;
    @Autowired
    private WechatServiceFactory wechatServiceFactory;
    public MessageResponse<Wxtext> send(Wxtext wxtext) {
        Date currDate = wxtext.getIstimingsend()?wxtext.getPublishtime(): DateUtils.getCurrentDate();
        if(wxPublishNewsService.outOfLimit(wxtext.getAppid(),currDate)){

        }
        return null;
    }
}
