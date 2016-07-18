package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpGroup;
import com.wowfilm.wechatsdk.entity.wxmp.json.GsonHelper;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxMpGsonBuilder;

import java.io.StringReader;
import java.util.List;

/**
 * The type Wx group manager.
 * 分组服务
 * @author warden
 * @date Created on 2016-07-13 13:32:45
 */
public class WxGroupManager extends WxBaseService{
    /**
     * <pre>
     * 分组管理接口 - 创建分组
     * 最多支持创建500个分组
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
     * </pre>
     *
     * @param name
     *            分组名字（30个字符以内）
     */
    public WxMpGroup groupCreate(String name){
        String url = "https://api.weixin.qq.com/cgi-bin/groups/create";
        JsonObject json = new JsonObject();
        JsonObject groupJson = new JsonObject();
        json.add("group", groupJson);
        groupJson.addProperty("name", name);

        String responseContent = executor.execute(
                new SimplePostRequestExecutor(),
                url,
                json.toString());
        return WxMpGroup.fromJson(responseContent);
    }

    /**
     * <pre>
     * 分组管理接口 - 查询所有分组
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
     * </pre>
     *
     * @return
     */
    public List<WxMpGroup> groupGet(){
        String url = "https://api.weixin.qq.com/cgi-bin/groups/get";
        String responseContent = executor.execute(new SimpleGetRequestExecutor(), url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("groups"),
                new TypeToken<List<WxMpGroup>>() {
                }.getType());
    }

    /**
     * <pre>
     * 分组管理接口 - 查询用户所在分组
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
     * </pre>
     *
     * @param openid
     *            微信用户的openid
     */
    public long userGetGroup(String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/groups/getid";
        JsonObject o = new JsonObject();
        o.addProperty("openid", openid);
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, o.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return GsonHelper.getAsLong(tmpJsonElement.getAsJsonObject().get("groupid"));
    }

    /**
     * <pre>
     * 分组管理接口 - 修改分组名
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
     *
     * 如果id为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
     * </pre>
     *
     * @param group
     *            要更新的group，group的id,name必须设置
     */
    public void groupUpdate(WxMpGroup group){
        String url = "https://api.weixin.qq.com/cgi-bin/groups/update";
        executor.execute(new SimplePostRequestExecutor(), url, group.toJson());
    }

    /**
     * <pre>
     * 分组管理接口 - 移动用户分组
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
     *
     * 如果to_groupid为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
     * </pre>
     *
     * @param openid
     *            用户openid
     * @param to_groupid
     *            移动到的分组id
     */
    public void userUpdateGroup(String openid, long to_groupid){
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);
        json.addProperty("to_groupid", to_groupid);
        executor.execute(new SimplePostRequestExecutor(), url, json.toString());
    }
}
