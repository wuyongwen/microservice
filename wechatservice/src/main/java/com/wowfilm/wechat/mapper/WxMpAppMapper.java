package com.wowfilm.wechat.mapper;

import com.wowfilm.entity.wechat.WxMpApp;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by wen on 2016/7/20 10:41.
 */
@Mapper

public interface WxMpAppMapper {
    public WxMpApp findByAppId(String appId);
    public Long insert(WxMpApp app);
    public void deleteByPrimaryKey(Long id);
    public void deleteByAppId(String appId);
    public void updateByPrimaryKey(WxMpApp app,Long id);
}
