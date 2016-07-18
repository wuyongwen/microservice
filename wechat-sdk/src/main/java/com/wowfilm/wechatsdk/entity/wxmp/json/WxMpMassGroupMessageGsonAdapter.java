package com.wowfilm.wechatsdk.entity.wxmp.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.wowfilm.wechatsdk.dto.WechatConsts;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMassGroupMessage;


import java.lang.reflect.Type;

public class WxMpMassGroupMessageGsonAdapter implements JsonSerializer<WxMpMassGroupMessage> {

  public JsonElement serialize(WxMpMassGroupMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    
    JsonObject filter = new JsonObject();
    if(null == message.getGroupId()) {
      filter.addProperty("is_to_all", true);
    } else {
      filter.addProperty("is_to_all", false);
      filter.addProperty("group_id", message.getGroupId());
    }
    messageJson.add("filter", filter);
    
    if (WechatConsts.MASS_MSG_NEWS.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(WechatConsts.MASS_MSG_NEWS, sub);
    }
    if (WechatConsts.MASS_MSG_TEXT.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("content", message.getContent());
      messageJson.add(WechatConsts.MASS_MSG_TEXT, sub);
    }
    if (WechatConsts.MASS_MSG_VOICE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(WechatConsts.MASS_MSG_VOICE, sub);
    }
    if (WechatConsts.MASS_MSG_IMAGE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(WechatConsts.MASS_MSG_IMAGE, sub);
    }
    if (WechatConsts.MASS_MSG_VIDEO.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(WechatConsts.MASS_MSG_VIDEO, sub);
    }
    messageJson.addProperty("msgtype", message.getMsgtype());
    return messageJson;
  }

}
