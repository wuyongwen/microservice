package com.wowfilm.wechatsdk.dto;

/**
 * Created by wen on 2016/7/14 10:25.
 */
public class AppInfo {
    public String id;
    public String name;
    public String secret;
    public String aesKey;
    public String token;
    public String loginedUrl;

    public String defaultTicket;

    public AppInfo() {
    }

    public AppInfo(String id, String name, String secret, String aesKey, String token) {
        this.id = id;
        this.name = name;
        this.secret = secret;
        this.aesKey = aesKey;
        this.token = token;
    }

    public AppInfo(String id, String name, String secret, String aesKey, String token, String loginedUrl, String defaultTicket) {
        this.id = id;
        this.name = name;
        this.secret = secret;
        this.aesKey = aesKey;
        this.token = token;
        this.loginedUrl = loginedUrl;
        this.defaultTicket = defaultTicket;
    }
}
