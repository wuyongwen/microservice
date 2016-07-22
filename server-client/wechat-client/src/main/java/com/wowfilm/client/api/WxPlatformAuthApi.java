package com.wowfilm.client.api;

import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

/**
 * Created by wen on 2016/7/23 0:38.
 */
public interface WxPlatformAuthApi {
    @RequestLine("POST /platform/doAuth")
    public String authUrl(@QueryMap Map<String, Object> queryMap);
}
