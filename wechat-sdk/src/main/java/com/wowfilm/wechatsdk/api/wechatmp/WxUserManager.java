package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonObject;
import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUser;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUserList;

/**
 * 用户管理接口
 * @author warden
 * @date Created on 2016-07-13 18:24:32
 */
public class WxUserManager extends WxBaseService {
    /**
     * <pre>
     * 设置用户备注名接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=设置用户备注名接口
     * </pre>
     *
     * @param openid 用户openid
     * @param remark 备注名
     */
    public void userUpdateRemark(String openid, String remark) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);
        json.addProperty("remark", remark);
        executor.execute(new SimplePostRequestExecutor(), url, json.toString());
    }

    /**
     * <pre>
     * 获取用户基本信息
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
     * </pre>
     *
     * @param openid 用户openid
     * @param lang   语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
     * @return WxMpUser
     */
    public WxMpUser userInfo(String openid, String lang) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = executor.execute(new SimpleGetRequestExecutor(), url, "openid=" + openid + "&lang=" + lang);
        return WxMpUser.fromJson(responseContent);
    }

    /**
     * <pre>
     * 获取关注者列表
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
     * </pre>
     *
     * @param next_openid 可选，第一个拉取的OPENID，null为从头开始拉取
     * @return WxMpUserList
     */
    public WxMpUserList userList(String next_openid) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get";
        String responseContent = executor.execute(new SimpleGetRequestExecutor(), url, next_openid == null ? null : "next_openid=" + next_openid);
        return WxMpUserList.fromJson(responseContent);
    }
}
