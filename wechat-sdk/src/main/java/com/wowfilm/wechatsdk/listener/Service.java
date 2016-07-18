package com.wowfilm.wechatsdk.listener;

import com.wowfilm.wechatsdk.dto.Context;

/**
 *
 */
public interface Service {

    public static final String DEFAULT_RETURN = "";
    public static final String SUCCESS_RETURN = "success";
    // 全网发布自动化测试 appId
    public static final String AUTO_TEST_APPID = "wx570bc396a51b8ff8";
    // 全网发布自动化测试公众号userName
    public static final String AUTO_TEST_USERNAME = "gh_3c884a361561";

    public String doService(Context context) throws Exception;
    
}
