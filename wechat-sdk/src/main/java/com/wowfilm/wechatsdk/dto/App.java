package com.wowfilm.wechatsdk.dto;


import com.wowfilm.wechatsdk.common.Cfg;

public class App {

    private static Cfg cfg = Cfg.getClassPathCfg("/weixin.properties");

    public static String getConfig(String key) {

        return cfg.get(key);
    }

    public static AppInfo Info = new AppInfo();

    static {
        Info.id = cfg.get("weixin.app.id");
        Info.name = cfg.get("weixin.app.name");
        Info.secret = cfg.get("weixin.app.secret");
        Info.aesKey = cfg.get("weixin.app.aeskey");
        Info.token = cfg.get("weixin.app.token");
        Info.loginedUrl = cfg.get("weixin.thirdparty.logined.redirect");

        Info.defaultTicket = cfg.get("weixin.default.componentverifyticket");
    }

    public static AppInfo getInfo() {
        return Info;
    }
}
