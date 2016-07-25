package com.wowfilm.client.api;

import biz.entgroup.framework.entity.Userinfo;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

/**
 * 微信用户接口,用户授权登录,获取用户信息
 * Created by wen on 2016/7/23 0:37.
 */
public interface UserInfoApi {
    @RequestLine("GET /wxuser/info?userid={userid}")
    public Userinfo getUserInfo(@Param("userid") int id);
    @RequestLine("POST /wxuser/authurl?redirectUrl={redirectUrl}&scope={scope}&state={state}")
    public String getAuthUrl(AuthUrlParam param);
    @RequestLine("GET /wxuser/auth?code={code}")
    public int doAuth(@Param("code") String code);

    class AuthUrlParam{
        private String redirectUrl;
        private String scope;
        private String state;

        public AuthUrlParam(String redirectUrl, String scope, String state) {
            this.redirectUrl = redirectUrl;
            this.scope = scope;
            this.state = state;
        }
    }
}
