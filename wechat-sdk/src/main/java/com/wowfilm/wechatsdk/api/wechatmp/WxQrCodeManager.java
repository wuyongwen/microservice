package com.wowfilm.wechatsdk.api.wechatmp;

import com.google.gson.JsonObject;
import com.wowfilm.wechatsdk.api.http.QrCodeRequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimplePostRequestExecutor;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpQrCodeTicket;

import java.io.File;

/**
 * Created by wen on 2016/7/13 10:37.
 * 二维码服务
 *
 * @author warden
 * @date Created on 2016-07-13 18:25:01
 */
public class WxQrCodeManager extends WxBaseService {
    /**
     * <pre>
     * 换取临时二维码ticket
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
     * </pre>
     *
     * @param scene_id       参数。
     * @param expire_seconds 过期秒数，默认60秒，最小60秒，最大1800秒
     * @return WxMpQrCodeTicket
     */
    public WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        JsonObject json = new JsonObject();
        json.addProperty("action_name", "QR_SCENE");
        if (expire_seconds != null) {
            json.addProperty("expire_seconds", expire_seconds);
        }
        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    /**
     * <pre>
     * 换取永久二维码ticket
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
     * </pre>
     *
     * @param scene_id 参数。永久二维码时最大值为100000（目前参数只支持1--100000）
     * @return WxMpQrCodeTicket
     */
    public WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        JsonObject json = new JsonObject();
        json.addProperty("action_name", "QR_LIMIT_SCENE");
        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        scene.addProperty("scene_id", scene_id);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    /**
     * <pre>
     * 换取永久字符串二维码ticket
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
     * </pre>
     *
     * @param scene_str 参数。字符串类型长度现在为1到64
     * @return WxMpQrCodeTicket
     */
    public WxMpQrCodeTicket qrCodeCreateLastTicket(String scene_str) {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
        JsonObject json = new JsonObject();
        json.addProperty("action_name", "QR_LIMIT_STR_SCENE");
        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        scene.addProperty("scene_str", scene_str);
        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = executor.execute(new SimplePostRequestExecutor(), url, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    /**
     * <pre>
     * 换取二维码图片文件，jpg格式
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
     * </pre>
     *
     * @param ticket 二维码ticket
     * @return File
     */
    public File qrCodePicture(WxMpQrCodeTicket ticket) {
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
        return executor.execute(new QrCodeRequestExecutor(), url, ticket);
    }

}
