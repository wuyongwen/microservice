package com.wowfilm.wechatsdk.entity.platform;

import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.entity.BasicResult;

/**
 * The type Plat form get auth access result.
 *
 * @author warden
 * @date Created on 2016-07-12 17:51:25
 */
public class PlatFormGetAuthAccessResult extends BasicResult {

    private static final long serialVersionUID = -1392505130233185759L;
    
    @JSONField(name="authorizer_access_token") private String authorizerAccessToken;
    @JSONField(name="expires_in") private Integer expiresIn;
    @JSONField(name="authorizer_refresh_token") private String authorizerRefreshToken;
    
    /**
     * @return 授权方令牌
     */
    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }
    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }
    /**
     * @return 有效期，为2小时
     */
    public Integer getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
    /**
     * @return 刷新令牌
     */
    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }
    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }
    
}
