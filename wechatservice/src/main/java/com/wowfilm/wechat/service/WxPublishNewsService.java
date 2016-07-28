package com.wowfilm.wechat.service;

import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.mapper.WxPublishNewsMapper;
import com.wowfilm.wechat.util.DateUtils;
import com.wowfilm.wechat.vo.WxNewsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wen on 2016/7/28 14:49.
 */
@Service
public class WxPublishNewsService {
    @Autowired
    private WxPublishNewsMapper mapper;

    @Autowired
    private WxMpAppService wxMpAppService;

    public boolean outOfLimit(String appId,Date publishTime) {
        WxMpApp wxMpApp = wxMpAppService.findByAppId(appId);
        int type = wxMpApp.getServiceTypeInfo();
        Date[] bettenDate = getBettenDate(publishTime,type);
        int count = mapper.checkCount(bettenDate,wxMpApp.getAuthorizerAppid(),new Integer[]{WxNewsStatus.C.code(),WxNewsStatus.D.code(),WxNewsStatus.I.code()});

        if(type==1){
            return count<1;
        }else if(type==2){
            return count<4;
        }
        return false;
    }
    private Date[] getBettenDate(Date publishTime, int type) {
        // 订阅号  一天内只能发送一个
        if(type==1 || type == 0){
            return DateUtils.getCurrentDayOfTime(publishTime);
        }
        // 服务号 一月之内只能发4条
        else if(type == 2){
            return DateUtils.getCurrentMonthOfTime(publishTime);
        }
        return null;
    }
}
