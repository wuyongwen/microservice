package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.*;
import com.wowfilm.wechatsdk.common.FileUtils;
import com.wowfilm.wechatsdk.dto.WechatConsts;
import com.wowfilm.wechatsdk.entity.wxmp.WxMediaUploadResult;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterial;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterialArticleUpdate;
import com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterialNews;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxGsonBuilder;
import com.wowfilm.wechatsdk.entity.wxmp.json.WxMpGsonBuilder;
import com.wowfilm.wechatsdk.entity.wxmp.result.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.wowfilm.wechatsdk.common.HttpUtils.post;

/**
 * Created by wen on 2016/7/13 10:25.
 */
public class WxMaterialManager extends WxBaseService {
    /**
     * <pre>
     * 新增临时素材
     * 上传多媒体文件
     *
     * 上传的多媒体文件有格式和大小限制，如下：
     *   图片（image）: 1M，支持JPG格式
     *   语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     *   视频（video）：10MB，支持MP4格式
     *   缩略图（thumb）：64KB，支持JPG格式
     *
     * 参数	描述
     * 	type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
     * 	media_id	媒体文件上传后，获取时的唯一标识
     * 	created_at	媒体文件上传时间戳
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     * </pre>
     *
     * @param mediaType   媒体类型, 请看{@link com.wowfilm.wechatsdk.dto.WechatConsts}
     * @param fileType    文件类型，请看{@link com.wowfilm.wechatsdk.dto.WechatConsts}
     * @param inputStream 输入流
     */
    public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws IOException {
        File file = FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType);
        return mediaUpload(mediaType, file);
    }

    /**
     * <pre>
     * 上传非图文永久素材
     *
     * 上传的多媒体文件有格式和大小限制，如下：
     *   图片（image）: 图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式
     *   语音（voice）：语音大小不超过5M，长度不超过60秒，支持mp3/wma/wav/amr格式
     *   视频（video）：在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
     *   缩略图（thumb）：文档未说明
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
     * </pre>
     *
     * @param mediaType 媒体类型, 请看{@link com.wowfilm.wechatsdk.dto.WechatConsts}
     * @param material  上传的素材, 请看{@link com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterial}
     * @return
     */
    public WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?type=" + mediaType;
        return executor.execute(new MaterialUploadRequestExecutor(), url, material);
    }

    /**
     * <pre>
     * 上传永久图文素材
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
     * </pre>
     *
     * @param news 上传的图文消息, 请看{@link com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterialNews}
     * @return
     */
    public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) {
        if (news == null || news.isEmpty()) {
            throw new IllegalArgumentException("news is empty!");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news";
        String responseContent = executor.post(url, news.toJson());
        return WxMpMaterialUploadResult.fromJson(responseContent);
    }

    /**
     * <pre>
     * 下载声音或者图片永久素材
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
     * </pre>
     *
     * @param media_id 永久素材的id
     * @return
     */
    public InputStream materialImageOrVoiceDownload(String media_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return executor.execute(new MaterialVoiceAndImageDownloadRequestExecutor(tokenAccessor.getWxMpConfigStorage().getTmpDirFile()), url, media_id);
    }

    /**
     * <pre>
     * 获取视频永久素材的信息和下载地址
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
     * </pre>
     *
     * @param media_id 永久素材的id
     * @return
     */
    public WxMpMaterialVideoInfoResult materialVideoInfo(String media_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return executor.execute(new MaterialVideoInfoRequestExecutor(), url, media_id);
    }

    /**
     * <pre>
     * 获取图文永久素材的信息
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
     * </pre>
     *
     * @param media_id 永久素材的id
     * @return
     */
    public WxMpMaterialNews materialNewsInfo(String media_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
        return executor.execute(new MaterialNewsInfoRequestExecutor(), url, media_id);
    }

    /**
     * <pre>
     * 更新图文永久素材
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/4/19a59cba020d506e767360ca1be29450.html
     * </pre>
     *
     * @param wxMpMaterialArticleUpdate 用来更新图文素材的bean, 请看
     *                                  {@link com.wowfilm.wechatsdk.entity.wxmp.WxMpMaterialArticleUpdate}
     * @return
     */
    public boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/update_news";
        String responseText = executor.post(url, wxMpMaterialArticleUpdate.toJson());
        return true;
    }

    /**
     * <pre>
     * 删除永久素材
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/5/e66f61c303db51a6c0f90f46b15af5f5.html
     * </pre>
     *
     * @param media_id 永久素材的id
     * @return
     */
    public boolean materialDelete(String media_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/del_material";
        return executor.execute(new MaterialDeleteRequestExecutor(), url, media_id);
    }

    /**
     * <pre>
     * 获取各类素材总数
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html
     * </pre>
     *
     * @return
     */
    public WxMpMaterialCountResult materialCount() {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
        String responseText = executor.get(url, null);
        return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);

    }

    /**
     * <pre>
     * 分页获取图文素材列表
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
     * </pre>
     *
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return
     */
    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
        Map<String, Object> params = new HashMap<>();
        params.put("type", WechatConsts.MATERIAL_NEWS);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = post(url, WxGsonBuilder.create().toJson(params));

        return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);

    }

    /**
     * <pre>
     * 分页获取其他媒体素材列表
     *
     * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
     * </pre>
     *
     * @param type   媒体类型, 请看{@link com.wowfilm.wechatsdk.dto.WechatConsts}
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return
     */
    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        String responseText = executor.post(url, WxGsonBuilder.create().toJson(params));

        return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
    }

    /**
     * @param mediaType
     * @param file
     * @see #mediaUpload(String, String, InputStream)
     */
    public WxMediaUploadResult mediaUpload(String mediaType, File file) {
        String url = "http://api.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
        return executor.execute(new MediaUploadRequestExecutor(), url, file);
    }

    /**
     * 上传图文消息内的图片获取URL 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
     * 图片仅支持jpg/png格式，大小必须在1MB以下。
     *
     * @return url
     */
    public String imageUpload(File file) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
        return executor.execute(new FileUploadRequestExeutor(), url, file);
    }

    /**
     * <pre>
     * 下载多媒体文件
     * 根据微信文档，视频文件下载不了，会返回null
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     * </pre>
     *
     * @return 保存到本地的临时文件
     * @params media_id
     */
    public File mediaDownload(String media_id) {
        String url = "http://api.weixin.qq.com/cgi-bin/media/get";
        return executor.execute(new MediaDownloadRequestExecutor(tokenAccessor.getWxMpConfigStorage().getTmpDirFile()), url, "media_id=" + media_id);
    }
}
