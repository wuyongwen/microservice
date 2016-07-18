package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMassGroupMessage;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMassNews;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMassOpenIdsMessage;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMassVideo;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpMassSendResult;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpMassUploadResult;

/**
 * 群发消息服务
 * @author warden
 * @date Created on 2016-07-13 20:31:09
 */
public class WxSendMessageManager extends WxBaseService {
    /**
     * <pre>
     * 上传群发用的图文消息，上传后才能群发图文消息
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
     * </pre>
     *
     * @param news
     * @see #massGroupMessageSend(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassGroupMessage)
     * @see #massOpenIdsMessageSend(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassOpenIdsMessage)
     */
    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    /**
     * <pre>
     * 上传群发用的视频，上传后才能群发视频消息
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
     * </pre>
     *
     * @return
     * @see #massGroupMessageSend(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassGroupMessage)
     * @see #massOpenIdsMessageSend(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassOpenIdsMessage)
     */
    public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) {
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, video.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    /**
     * <pre>
     * 分组群发消息
     * 如果发送图文消息，必须先使用 {@link #massNewsUpload(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassNews)} 获得media_id，然后再发送
     * 如果发送视频消息，必须先使用 {@link #massVideoUpload(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassVideo)} 获得media_id，然后再发送
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
     * </pre>
     *
     * @param message
     * @return
     */
    public WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    /**
     * <pre>
     * 按openId列表群发消息
     * 如果发送图文消息，必须先使用 {@link #massNewsUpload(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassNews)} 获得media_id，然后再发送
     * 如果发送视频消息，必须先使用 {@link #massVideoUpload(com.wowfilm.wechatsdk.entity.wxmp.WxMpMassVideo)} 获得media_id，然后再发送
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
     * </pre>
     *
     * @param message
     * @return
     */
    public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }
}
