package com.wowfilm.wechat.mapper;

import biz.entgroup.framework.mapper.WxpublishnewsMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by wen on 2016/7/28 14:54.
 */
public interface WxPublishNewsMapper extends WxpublishnewsMapper {
    @Select("select count(1) from WxPublishNews")
    int checkCount(Date[] bettenDate, String authorizerAppid, Integer[] integers);
}
