package com.wowfilm.wechat.service;

import com.wowfilm.wechat.mapper.WxArticleGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wen on 2016/7/28 14:49.
 */
@Service
public class WxArticleGroupService {
    @Autowired
    private WxArticleGroupMapper wxArticleGroupMapper;
}
