package com.wowfilm.wechat.service;

import com.wowfilm.entity.wechat.WxPlatformInfo;
import com.wowfilm.wechat.mapper.WxPlatformInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wen on 2016/7/18 16:47.
 */

@Service
@Transactional
public class WxPlatformInfoService {
    @Autowired
    private WxPlatformInfoMapper wxPlatformInfoMapper;
    public WxPlatformInfo getPlatformInfo(String appId){
        return wxPlatformInfoMapper.findByAppId(appId);
    }

}
