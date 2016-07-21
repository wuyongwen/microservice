package com.wowfilm.wechat.mapper;

import com.wowfilm.entity.wechat.WxMpApp;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by wen on 2016/7/20 10:41.
 */
@Mapper
public interface WxMpAppMapper {
    public WxMpApp findByAppId(String appId);
    public Long save(WxMpApp app);
    public void delete(Long id);
    public void update(WxMpApp app,Long id);
}
