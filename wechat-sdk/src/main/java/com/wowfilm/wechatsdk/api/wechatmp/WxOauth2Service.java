package com.wowfilm.wechatsdk.api.wechatmp;

import com.wowfilm.wechatsdk.api.http.RequestExecutor;
import com.wowfilm.wechatsdk.api.http.SimpleGetRequestExecutor;
import com.wowfilm.wechatsdk.common.URIUtil;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpOAuth2AccessToken;
import com.wowfilm.wechatsdk.entity.wxmp.result.WxMpUser;
import com.wowfilm.wechatsdk.exception.WechatErrorException;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;


/**
 * The type Wx oauth 2 service.
 * 授权认证服务
 * @author warden
 * @date Created on 2016-07-13 19:51:01
 */
public class WxOauth2Service extends WxBaseService {
    /**
     * <pre>
     * 构造oauth2授权的url连接
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param scope
     * @param state
     * @return url
     */
    public String oauth2buildAuthorizationUrl(String scope, String state) {
        return this.oauth2buildAuthorizationUrl(tokenAccessor.getWxMpConfigStorage().getOauth2redirectUri(), scope, state);
    }

    /**
     * <pre>
     * 构造oauth2授权的url连接
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scope
     * @param state
     * @return url
     */
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
        url += "appid=" + tokenAccessor.getWxMpConfigStorage().getAppId();
        url += "&redirect_uri=" + URIUtil.encodeURIComponent(redirectURI);
        url += "&response_type=code";
        url += "&scope=" + scope;
        if (state != null) {
            url += "&state=" + state;
        }
        url += "#wechat_redirect";
        return url;
    }

    /**
     * <pre>
     * 用code换取oauth2的access token
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param code
     * @return
     */
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        url += "appid=" + tokenAccessor.getWxMpConfigStorage().getAppId();
        url += "&secret=" + tokenAccessor.getWxMpConfigStorage().getSecret();
        url += "&code=" + code;
        url += "&grant_type=authorization_code";

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(tokenAccessor.getHttpclient(), tokenAccessor.getHttpProxy(), url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <pre>
     * 刷新oauth2的access token
     * </pre>
     *
     * @param refreshToken
     * @return WxMpOAuth2AccessToken
     */
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
        url += "appid=" + tokenAccessor.getWxMpConfigStorage().getAppId();
        url += "&grant_type=refresh_token";
        url += "&refresh_token=" + refreshToken;

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(tokenAccessor.getHttpclient(), tokenAccessor.getHttpProxy(), url, null);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <pre>
     * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
     * </pre>
     *
     * @param oAuth2AccessToken
     * @param lang
     */
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) {
        String url = "https://api.weixin.qq.com/sns/userinfo?";
        url += "access_token=" + oAuth2AccessToken.getAccessToken();
        url += "&openid=" + oAuth2AccessToken.getOpenId();
        if (lang == null) {
            url += "&lang=zh_CN";
        } else {
            url += "&lang=" + lang;
        }

        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            String responseText = executor.execute(tokenAccessor.getHttpclient(), tokenAccessor.getHttpProxy(), url, null);
            return WxMpUser.fromJson(responseText);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <pre>
     * 验证oauth2的access token是否有效
     * </pre>
     *
     * @param oAuth2AccessToken
     * @return
     */
    public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken){
        String url = "https://api.weixin.qq.com/sns/auth?";
        url += "access_token=" + oAuth2AccessToken.getAccessToken();
        url += "&openid=" + oAuth2AccessToken.getOpenId();
        try {
            RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
            executor.execute(tokenAccessor.getHttpclient(), tokenAccessor.getHttpProxy(), url, null);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WechatErrorException e) {
            return false;
        }
        return true;
    }
}
