package com.wowfilm.wechat.mapper;

import biz.entgroup.framework.mapper.WxpublishnewsMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by wen on 2016/7/28 14:54.
 */
public interface WxPublishNewsMapper extends WxpublishnewsMapper {
    int checkCount(@Param("start") Date start,@Param("end") Date endDate,@Param("appId") String authorizerAppid,@Param("status") int[] stautus);
}
