package com.wowfilm.entity.request;

/**
 * Created by wen on 2016/7/25 14:04.
 */
public class AuthUrlParam {
    private String redirectUrl;
    private String scope;
    private String state;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AuthUrlParam(String redirectUrl, String scope, String state) {
        this.redirectUrl = redirectUrl;
        this.scope = scope;
        this.state = state;
    }
    public AuthUrlParam(){
        super();
    }
}
