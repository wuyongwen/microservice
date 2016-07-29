package com.wowfilm.wechatservice.test;

import com.wowfilm.entity.wechat.WxMpApp;
import com.wowfilm.wechat.Start;
import com.wowfilm.wechat.mapper.WxMpAppMapper;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wen on 2016/7/29 9:40.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Start.class)
public class MapperTest {
    private static Logger log = Logger.getLogger(MapperTest.class);
    @Autowired
    private WxMpAppMapper wxMpAppMapper;
    @Test
    public void testMpApp(){
        WxMpApp app = wxMpAppMapper.findByAppId("xb2345234123");
        log.info(app);
    }

}
