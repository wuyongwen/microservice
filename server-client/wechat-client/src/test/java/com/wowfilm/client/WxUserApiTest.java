package com.wowfilm.client;

import com.wowfilm.client.api.UserInfoApi;
import com.wowfilm.entity.request.AuthUrlParam;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


/**
 * Created by wen on 2016/7/23 14:25.
 */
public class WxUserApiTest {
    private static final String root = "http://localhost:8081/wechat";
    private static Logger log = Logger.getLogger(WxUserApiTest.class);
    private UserInfoApi api;
    @Before
    public void beefore(){
        api = ApiFactory.connect(root).jsonApi(UserInfoApi.class);
    }
    @Test
    public void testAuth(){
        Map url = api.getAuthUrl(new AuthUrlParam("localhost","state","basee"));
        log.info(url.get("url"));
    }
    @Test
    public void testAuthCode(){
        int i = api.doAuth("123");
        log.info(i);
    }
}
