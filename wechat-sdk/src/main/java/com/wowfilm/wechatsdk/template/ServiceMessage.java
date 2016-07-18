/**
 * WeiXin
 *
 * @title ServiceMessage.java
 * @package com.chn.wx.format
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月18日-上午10:03:36
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.template;


import com.wowfilm.wechatsdk.common.StringTemplate;
import com.wowfilm.wechatsdk.entity.wxmp.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wowfilm.wechatsdk.common.StringTemplate.compileFromClassPath;

public class ServiceMessage {

    private static StringTemplate T_TEXT = compileFromClassPath("/com/chn/wx/template/tpl/service-text.json");
    private static StringTemplate T_VOICE = compileFromClassPath("/com/chn/wx/template/tpl/service-voice.json");
    private static StringTemplate T_VIDEO = compileFromClassPath("/com/chn/wx/template/tpl/service-video.json");
    private static StringTemplate T_MUSIC = compileFromClassPath("/com/chn/wx/template/tpl/service-music.json");
    private static StringTemplate T_NEWS = compileFromClassPath("/com/chn/wx/template/tpl/service-news.json");
    private static StringTemplate T_NEWS_Article = compileFromClassPath("/com/chn/wx/template/tpl/service-news-article.json");

    /**
     * @param touser  普通用户openid
     * @param content 文本消息内容
     */
    public static String wrapText(String touser, String content) {

        Map<String, Object> params = new HashMap<>();
        params.put("touser", touser);
        params.put("content", content);
        return T_TEXT.replace(params);
    }

    /**
     * @param touser   普通用户openid
     * @param media_id 发送的语音的媒体ID
     */
    public static String wrapVoice(String touser, String media_id) {

        Map<String, Object> params = new HashMap<>();
        params.put("touser", touser);
        params.put("media_id", media_id);
        return T_VOICE.replace(params);
    }

    /**
     * @param touser         普通用户openid
     * @param media_id       发送的视频的媒体ID
     * @param thumb_media_id 缩略图的媒体ID
     */
    public static String wrapVideo(String touser, String media_id, String thumb_media_id) {

        return wrapVideo(touser, thumb_media_id, thumb_media_id, "", "");
    }

    /**
     * @param touser         普通用户openid
     * @param media_id       发送的视频的媒体ID
     * @param thumb_media_id 缩略图的媒体ID
     * @param title          视频消息的标题
     * @param description    视频消息的描述
     */
    public static String wrapVideo(String touser, String media_id,
                                   String thumb_media_id, String title, String description) {

        Map<String, Object> params = new HashMap<>();
        params.put("touser", touser);
        params.put("media_id", media_id);
        params.put("thumb_media_id", thumb_media_id);
        params.put("title", title);
        params.put("description", description);
        return T_VIDEO.replace(params);
    }

    /**
     * @param touser         普通用户openid
     * @param musicurl       音乐链接
     * @param hqmusicurl     高品质音乐链接，wifi环境优先使用该链接播放音乐
     * @param thumb_media_id 缩略图的媒体ID
     */
    public static String wrapMusic(String touser, String musicurl,
                                   String hqmusicurl, String thumb_media_id) {

        return wrapMusic(touser, "", "", musicurl, hqmusicurl, thumb_media_id);
    }

    /**
     * @param touser         普通用户openid
     * @param title          音乐标题
     * @param description    音乐描述
     * @param musicurl       音乐链接
     * @param hqmusicurl     高品质音乐链接，wifi环境优先使用该链接播放音乐
     * @param thumb_media_id 缩略图的媒体ID
     */
    public static String wrapMusic(String touser, String title, String description,
                                   String musicurl, String hqmusicurl, String thumb_media_id) {

        Map<String, Object> params = new HashMap<>();
        params.put("touser", touser);
        params.put("title", title);
        params.put("description", description);
        params.put("musicurl", musicurl);
        params.put("hqmusicurl", hqmusicurl);
        params.put("thumb_media_id", thumb_media_id);
        return T_MUSIC.replace(params);
    }

    /**
     * @param touser   普通用户openid
     * @param articles
     */
    public static String wrapNews(String touser, List<Article> articles) {

        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        for (Article article : articles) {
            params.put("title", article.getTitle());
            params.put("description", article.getDescription());
            params.put("url", article.getUrl());
            params.put("picurl", article.getPicurl());
            sb.append(T_NEWS_Article.replace(params)).append(',');
            params.clear();
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);

        params.put("touser", touser);
        params.put("articles", sb.toString());
        return T_NEWS.replace(params);
    }

}
