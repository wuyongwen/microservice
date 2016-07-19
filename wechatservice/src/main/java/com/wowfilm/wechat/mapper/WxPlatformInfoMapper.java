package com.wowfilm.wechat.mapper;

import com.wowfilm.entity.wechat.WxPlatformInfo;
import org.apache.ibatis.annotations.*;

/**
 * Created by wen on 2016/7/18 15:55.
 */
@Mapper
public interface WxPlatformInfoMapper {
    WxPlatformInfo findByAppId(String appId);

    public void update(WxPlatformInfo wxPlatformInfo);

    public void save(WxPlatformInfo wxPlatformInfo);
}
