package com.wowfilm.wechatsdk.entity.wxmp.custombuilder;

import com.wowfilm.wechatsdk.dto.WechatConsts;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpCustomMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpCustomMessage m = WxMpCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

  private List<WxMpCustomMessage.WxArticle> articles = new ArrayList<WxMpCustomMessage.WxArticle>();
  
  public NewsBuilder() {
    this.msgType = WechatConsts.CUSTOM_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxMpCustomMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  public WxMpCustomMessage build() {
    WxMpCustomMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
