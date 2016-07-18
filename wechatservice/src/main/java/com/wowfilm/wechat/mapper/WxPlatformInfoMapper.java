package com.wowfilm.wechat.mapper;

import com.wowfilm.entity.wechat.WxPlatformInfo;
import org.apache.ibatis.annotations.*;

/**
 * Created by wen on 2016/7/18 15:55.
 */
@Mapper
public interface WxPlatformInfoMapper {
    @Select("select * from WxPlatformInfo where appId = #{appId}")
    WxPlatformInfo findByAppId(String appId);

    @Insert("INSERT INTO WxPlatformInfo(AppId, ComponentVerifyTicket) VALUES(#{appId}, #{componentVerifyTicket})")
    int insert(@Param("appId") String appId, @Param("componentVerifyTicket") String componentVerifyTicket);

    @Update("update WxPlatformInfo set ComponentVerifyTicket = #{ComponentVerifyTicket},TicketCreateTime = #{TicketCreateTime}" +
            ",")
    void updateComponevtTicket();
}
