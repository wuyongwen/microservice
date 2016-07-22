package com.wowfilm.wechat.mapper;

import biz.entgroup.framework.entity.Userinfo;
import biz.entgroup.framework.mapper.UserinfoMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wen on 2016/7/22 17:27.
 */
@Mapper
public interface WxUserMapper extends UserinfoMapper{
    @Select("select * from UserInfo where openid = #{openid}")
    public Userinfo findByOpenId(String openId);
}
