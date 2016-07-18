package com.wowfilm.wechatsdk.template;

import com.wowfilm.wechatsdk.common.StringTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.wowfilm.wechatsdk.common.StringTemplate.compileFromClassPath;

public class PlatFormMessage {

    private static StringTemplate T_GET_ACCESSTOKEN = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-get-accesstoken.json");
    private static StringTemplate T_GET_PREAUTHCODE = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-get-preauthcode.json");
    private static StringTemplate T_GET_AUTHINFO = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-get-authinfo.json");
    private static StringTemplate T_REFRESH_ACCESSTOKEN = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-refresh-accesstoken.json");
    private static StringTemplate T_GET_AUTHORINFO = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-get-authorizer-info.json");
    private static StringTemplate T_GET_AUTHOROPTION = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-get-authorizer-option.json");
    private static StringTemplate T_SET_AUTHOROPTION = compileFromClassPath("/com/wowfilm/wechatsdk/template/tpl/plat-set-authorizer-option.json");

    /**
     * 用于获取第三方平台令牌（component_access_token）
     *
     * @param appId  第三方平台appid
     * @param secret 第三方平台appsecret
     * @param ticket 微信后台推送的ticket，此ticket会定时推送，具体请见本页末尾的推送说明
     * @return
     */
    public static String wrapGetAccessToken(String appId, String secret, String ticket) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("appsecret_value", secret);
        params.put("ticket_value", ticket);
        return T_GET_ACCESSTOKEN.replace(params);
    }

    /**
     * 用于获取预授权码。预授权码用于公众号授权时的第三方平台方安全验证。
     *
     * @param appId 第三方平台方appid
     * @return
     */
    public static String wrapGetPreAuthCode(String appId) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        return T_GET_PREAUTHCODE.replace(params);
    }

    /**
     * 用于使用授权码换取授权公众号的授权信息，并换取authorizer_access_token 和
     * authorizer_refresh_token。
     *
     * @param appId
     * @param authCode
     * @return
     */
    public static String wrapGetAuthInfo(String appId, String authCode) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("auth_code_value", authCode);
        return T_GET_AUTHINFO.replace(params);
    }

    /**
     * 用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌
     * （authorizer_refresh_token）获取新的令牌。
     *
     * @param appId        第三方平台appid
     * @param authAppId    授权方appid
     * @param refreshToken 授权方的刷新令牌，刷新令牌主要用于公众号第三方平台获取和刷新已
     *                     授权用户的access_token，只会在授权时刻提供，请妥善保存。 一旦丢失，只能让用户重新授
     *                     权，才能再次拿到新的刷新令牌
     * @return
     */
    public static String wrapGetAuthAccessToken(String appId, String authAppId, String refreshToken) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("auth_appid_value", authAppId);
        params.put("refresh_token_value", refreshToken);
        return T_REFRESH_ACCESSTOKEN.replace(params);
    }

    /**
     * 用于获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和
     * 二维码图片URL。
     *
     * @param appId     服务appid
     * @param authAppId 授权方appid
     * @return
     */
    public static String wrapGetAuthorizerInfo(String appId, String authAppId) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("auth_appid_value", authAppId);
        return T_GET_AUTHORINFO.replace(params);
    }

    /**
     * 用于获取授权方的公众号的选项设置信息，如：地理位置上报，语音识别开关，多客服开关。注
     * 意，获取各项选项设置信息，需要有授权方的授权，详见权限集说明。
     *
     * @param appId      第三方平台appid
     * @param authAppId  授权公众号appid
     * @param optionName 选项名称
     * @return
     */
    public static String wrapGetAuthorizerOption(String appId, String authAppId, String optionName) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("auth_appid_value", authAppId);
        params.put("option_name_value", optionName);
        return T_GET_AUTHOROPTION.replace(params);
    }

    /**
     * 用于设置授权方的公众号的选项信息，如：地理位置上报，语音识别开关，多客服开关。注意，
     * 设置各项选项设置信息，需要有授权方的授权，详见权限集说明。
     * <table>
     * <tr><th>option_name</th><th>option_value</th><th>选项值说明</th></tr>
     * <tr><td rowspan="3">location_report(地理位置上报选项)</td><td>0</td><td>无上报</td></tr>
     * <tr><td>1</td><td>进入会话时上报</td></tr>
     * <tr><td>2</td><td>每5s上报</td></tr>
     * <tr><td rowspan="2">voice_recognize（语音识别开关选项）</td><td>0</td><td>关闭语音识别</td></tr>
     * <tr><td>1</td><td>开启语音识别</td></tr>
     * <tr><td rowspan="2">customer_service（客服开关选项）</td><td>0</td><td>关闭多客服</td></tr>
     * <tr><td>1</td><td>开启多客服</td></tr>
     * </table>
     *
     * @param appId       第三方平台appid
     * @param authAppId   授权公众号appid
     * @param optionName  选项名称
     * @param optionValue 设置的选项值
     * @return
     */
    public static String wrapSetAuthorizerOption(String appId, String authAppId,
                                                 String optionName, String optionValue) {

        Map<String, Object> params = new HashMap<>();
        params.put("appid_value", appId);
        params.put("auth_appid_value", authAppId);
        params.put("option_name_value", optionName);
        params.put("option_value_value", optionValue);
        return T_SET_AUTHOROPTION.replace(params);
    }

}
