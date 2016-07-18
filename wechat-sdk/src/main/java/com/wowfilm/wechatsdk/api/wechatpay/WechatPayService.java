package com.wowfilm.wechatsdk.api.wechatpay;

import com.thoughtworks.xstream.XStream;
import com.wowfilm.wechatsdk.api.wechatmp.WxBaseService;
import com.wowfilm.wechatsdk.common.Utf8ResponseHandler;
import com.wowfilm.wechatsdk.common.WxCryptUtil;
import com.wowfilm.wechatsdk.entity.wxpay.result.WxMpPayCallback;
import com.wowfilm.wechatsdk.entity.wxpay.result.WxMpPayResult;
import com.wowfilm.wechatsdk.entity.wxpay.result.WxMpPrepayIdResult;
import com.wowfilm.wechatsdk.entity.wxpay.xml.XStreamInitializer;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wen on 2016/7/13 10:47.
 */
public class WechatPayService extends WxBaseService{
    /**
     * 该接口提供所有微信支付订单的查询,当支付通知处理异常戒丢失的情冴,商户可以通过该接口查询订单支付状态。
     * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     *
     * @param transactionId
     * @param outTradeNo
     */
    WxMpPayResult getJSSDKPayResult(String transactionId, String outTradeNo){
        String nonce_str = System.currentTimeMillis() + "";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", tokenAccessor.getWxMpConfigStorage().getAppId());
        packageParams.put("mch_id", tokenAccessor.getWxMpConfigStorage().getPartnerId());
        if (transactionId != null && !"".equals(transactionId.trim()))
            packageParams.put("transaction_id", transactionId);
        else if (outTradeNo != null && !"".equals(outTradeNo.trim()))
            packageParams.put("out_trade_no", outTradeNo);
        else
            throw new IllegalArgumentException("Either 'transactionId' or 'outTradeNo' must be given.");
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("sign", WxCryptUtil.createSign(packageParams, tokenAccessor.getWxMpConfigStorage().getPartnerKey()));

        StringBuilder request = new StringBuilder("<xml>");
        for (Map.Entry<String, String> para : packageParams.entrySet()) {
            request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
        }
        request.append("</xml>");

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");
        if (tokenAccessor.getHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(tokenAccessor.getHttpProxy()).build();
            httpPost.setConfig(config);
        }

        StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response = tokenAccessor.getHttpclient().execute(httpPost);
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPayResult.class);
            WxMpPayResult wxMpPayResult = (WxMpPayResult) xstream.fromXML(responseContent);
            return wxMpPayResult;
        } catch (IOException e) {
            throw new RuntimeException("Failed to query order due to IO exception.", e);
        }
    }

    /**
     * 读取支付结果通知 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     *
     * @param xmlData
     * @return
     */
    WxMpPayCallback getJSSDKCallbackData(String xmlData){
        try {
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPayCallback.class);
            WxMpPayCallback wxMpCallback = (WxMpPayCallback) xstream.fromXML(xmlData);
            return wxMpCallback;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new WxMpPayCallback();
    }

    /**
     * <pre>
     * 计算Map键值对是否和签名相符,
     * 按照字段名的 ASCII 码从小到大排序(字典序)后,使用 URL 键值对的 格式(即 key1=value1&key2=value2...)拼接成字符串
     * </pre>
     *
     * @param kvm
     * @param signature
     * @return
     */
    public boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm, String signature){
        return signature.equals(WxCryptUtil.createSign(kvm, tokenAccessor.getWxMpConfigStorage().getPartnerKey()));
    }
    /**
     * 该接口调用“统一下单”接口，并拼装JSSDK发起支付请求需要的参数
     * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.
     * E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.
     * E8.AF.B7.E6.B1.82
     *
     * @param parameters
     *            the required or optional parameters
     * @return
     */
    Map<String, String> getJSSDKPayInfo(Map<String, String> parameters){
        WxMpPrepayIdResult wxMpPrepayIdResult = getPrepayId(parameters);
        String prepayId = wxMpPrepayIdResult.getPrepay_id();
        if (prepayId == null || prepayId.equals("")) {
            throw new RuntimeException(String.format("Failed to get prepay id due to error code '%s'(%s).", wxMpPrepayIdResult.getErr_code(), wxMpPrepayIdResult.getErr_code_des()));
        }

        Map<String, String> payInfo = new HashMap<String, String>();
        payInfo.put("appId", tokenAccessor.getWxMpConfigStorage().getAppId());
        // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
        payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        payInfo.put("nonceStr", System.currentTimeMillis() + "");
        payInfo.put("package", "prepay_id=" + prepayId);
        payInfo.put("signType", "MD5");

        String finalSign = WxCryptUtil.createSign(payInfo, tokenAccessor.getWxMpConfigStorage().getPartnerKey());
        payInfo.put("paySign", finalSign);
        return payInfo;
    }
    /**
     * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     *
     * @param parameters
     *            All required/optional parameters for weixin payment
     * @return
     * @throws IllegalArgumentException
     */
    WxMpPrepayIdResult getPrepayId(Map<String, String> parameters){
        String nonce_str = System.currentTimeMillis() + "";

        final SortedMap<String, String> packageParams = new TreeMap<String, String>(parameters);
        packageParams.put("appid", tokenAccessor.getWxMpConfigStorage().getAppId());
        packageParams.put("mch_id", tokenAccessor.getWxMpConfigStorage().getPartnerId());
        packageParams.put("nonce_str", nonce_str);
        checkParameters(packageParams);

        String sign = WxCryptUtil.createSign(packageParams, tokenAccessor.getWxMpConfigStorage().getPartnerKey());
        packageParams.put("sign", sign);

        StringBuilder request = new StringBuilder("<xml>");
        for (Map.Entry<String, String> para : packageParams.entrySet()) {
            request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
        }
        request.append("</xml>");

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
        if (tokenAccessor.getHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(tokenAccessor.getHttpProxy()).build();
            httpPost.setConfig(config);
        }

        StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response = tokenAccessor.getHttpclient().execute(httpPost);
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            XStream xstream = XStreamInitializer.getInstance();
            xstream.alias("xml", WxMpPrepayIdResult.class);
            WxMpPrepayIdResult wxMpPrepayIdResult = (WxMpPrepayIdResult) xstream.fromXML(responseContent);
            return wxMpPrepayIdResult;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get prepay id due to IO exception.", e);
        }
    }
    final String[] REQUIRED_ORDER_PARAMETERS = new String[] { "appid", "mch_id", "body", "out_trade_no", "total_fee", "spbill_create_ip", "notify_url",
            "trade_type", };
    private void checkParameters(Map<String, String> parameters) {
        for (String para : REQUIRED_ORDER_PARAMETERS) {
            if (!parameters.containsKey(para))
                throw new IllegalArgumentException("Reqiured argument '" + para + "' is missing.");
        }
        if ("JSAPI".equals(parameters.get("trade_type")) && !parameters.containsKey("openid"))
            throw new IllegalArgumentException("Reqiured argument 'openid' is missing when trade_type is 'JSAPI'.");
        if ("NATIVE".equals(parameters.get("trade_type")) && !parameters.containsKey("product_id"))
            throw new IllegalArgumentException("Reqiured argument 'product_id' is missing when trade_type is 'NATIVE'.");
    }
}
