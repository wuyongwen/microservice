package com.wowfilm.wechat.service;

import com.wowfilm.wechat.mapper.WxArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wen on 2016/7/28 14:55.
 */
@Service
public class WxArticleService {
    @Autowired
    private WxArticleMapper wxArticleMapper ;
}
