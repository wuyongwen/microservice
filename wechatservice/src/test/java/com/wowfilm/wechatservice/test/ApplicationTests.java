package com.wowfilm.wechatservice.test;

import com.alibaba.fastjson.JSON;
import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.Start;
import com.wowfilm.wechat.service.WxMpAppService;
import com.wowfilm.wechat.util.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;

/**
 * Created by wen on 2016/7/22 11:21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Start.class)
public class ApplicationTests {
    private static Logger logger = Logger.getLogger(ApplicationTests.class);
    @Autowired
    private WxMpAppService service;
    @Test
    public void testAdd(){
        WxMpApp app = new WxMpApp();
        app.setAuthorizerAppid("xb2345234123");
        app.setAuthorizerAccessToken("fsdaaaaaaaaaa");
        app.setTokenExpiresOut(DateUtils.getAfterSecondDate(1000));
        service.saveOrUpdate(app);
    }
    @Test
    public void testGet(){
        WxMpApp app = service.findByAppId("xb2345234123");
        logger.info(JSON.toJSONString(app));
    }
}
