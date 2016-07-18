package com.wowfilm.wechatsdk.entity.wxmp.json;

import com.google.gson.*;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpMaterialCountResult;

import java.lang.reflect.Type;


public class WxMpMaterialCountResultAdapter implements JsonDeserializer<WxMpMaterialCountResult> {

  public WxMpMaterialCountResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxMpMaterialCountResult countResult = new WxMpMaterialCountResult();
    JsonObject materialCountResultJsonObject = json.getAsJsonObject();

    if (materialCountResultJsonObject.get("voice_count") != null && !materialCountResultJsonObject.get("voice_count").isJsonNull()) {
      countResult.setVoiceCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("voice_count")));
    }
    if (materialCountResultJsonObject.get("video_count") != null && !materialCountResultJsonObject.get("video_count").isJsonNull()) {
      countResult.setVideoCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("video_count")));
    }
    if (materialCountResultJsonObject.get("image_count") != null && !materialCountResultJsonObject.get("image_count").isJsonNull()) {
      countResult.setImageCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("image_count")));
    }
    if (materialCountResultJsonObject.get("news_count") != null && !materialCountResultJsonObject.get("news_count").isJsonNull()) {
      countResult.setNewsCount(GsonHelper.getAsInteger(materialCountResultJsonObject.get("news_count")));
    }
    return countResult;
  }

}
