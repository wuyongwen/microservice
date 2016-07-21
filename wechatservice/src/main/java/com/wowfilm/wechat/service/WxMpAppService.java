package com.wowfilm.wechat.service;

import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.mapper.WxMpAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wen on 2016/7/21 10:47.
 */
@Service
@Transactional
public class WxMpAppService {
    @Autowired
    private WxMpAppMapper wxMpAppMapper;
    public WxMpApp findByAppId(String appId){
        return wxMpAppMapper.findByAppId(appId);
    }

    public void update(WxMpApp app) {
        wxMpAppMapper.update(app,app.getId());
    }

    public Long save(WxMpApp app){
        return wxMpAppMapper.save(app);
    }

    public Long saveOrUpdate(WxMpApp app){
        if(app.getId()!=null && app.getId()>0){
            wxMpAppMapper.update(app,app.getId());
            return app.getId();
        }else{
            return wxMpAppMapper.save(app);
        }
    }
}
