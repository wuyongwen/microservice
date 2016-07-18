package com.wowfilm.client;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import com.wowfilm.entity.wechat.Test;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Created by wen on 2016/7/15 16:17.
 */
public interface TestApi {
    @RequestLine("GET /test")
    Test test();
    @RequestLine("GET /testparam?id={id}&name={name}")
    Test testParam(@Param("id") int id,@Param("name") String name);
    @RequestLine("POST /testbean")
    @Headers("Content-Type: application/json")
    Test testBean(Test test);
}
