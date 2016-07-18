package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxMpGsonBuilder;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUserCumulate;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUserSummary;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.wowfilm.wechatsdk.common.HttpUtils.post;

/**
 * Created by wen on 2016/7/13 10:46.
 */
public class WxStatInfoManager extends WxBaseService {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * <pre>
     * 获取用户增减数据
     * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
     * </pre>
     *
     * @param beginDate 最大时间跨度7天
     * @param endDate   endDate不能早于begingDate
     * @return
     */
    List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) {
        String url = "https://api.weixin.qq.com/datacube/getusersummary";
        JsonObject param = new JsonObject();
        param.addProperty("begin_date", SIMPLE_DATE_FORMAT.format(beginDate));
        param.addProperty("end_date", SIMPLE_DATE_FORMAT.format(endDate));
        String responseContent = executor.post(url, param.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
                new TypeToken<List<WxMpUserSummary>>() {
                }.getType());
    }

    /**
     * <pre>
     * 获取累计用户数据
     * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
     * </pre>
     *
     * @param beginDate 最大时间跨度7天
     * @param endDate   endDate不能早于begingDate
     * @return
     */
    List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) {
        String url = "https://api.weixin.qq.com/datacube/getusercumulate";
        JsonObject param = new JsonObject();
        param.addProperty("begin_date", SIMPLE_DATE_FORMAT.format(beginDate));
        param.addProperty("end_date", SIMPLE_DATE_FORMAT.format(endDate));
        String responseContent = executor.post(url, param.toString());
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
                new TypeToken<List<WxMpUserCumulate>>() {
                }.getType());
    }
}
