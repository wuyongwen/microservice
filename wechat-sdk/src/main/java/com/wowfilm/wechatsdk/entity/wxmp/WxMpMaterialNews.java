package com.wowfilm.wechatsdk.entity.wxmp;

import com.wowfilm.wechatsdk.entity.wxmp.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxMpMaterialNews implements Serializable {

  private List<WxMpMaterialNewsArticle> articles = new ArrayList<WxMpMaterialNewsArticle>();

  public List<WxMpMaterialNewsArticle> getArticles() {
    return articles;
  }

  public void addArticle(WxMpMaterialNewsArticle article) {
    this.articles.add(article);
  }

  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public boolean isEmpty() {
    return articles == null || articles.isEmpty();
  }

  @Override
  public String toString() {
    return "WxMpMaterialNews [" + "articles=" + articles + "]";
  }
}
