package com.wowfilm.wechatsdk.entity.platform;

import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.entity.BasicResult;

public class PlatFormGetAuthInfoResult extends BasicResult {
    
    private static final long serialVersionUID = -4554229536745019368L;
    
    @JSONField(name="authorization_info") private AuthorizationInfo authorizationInfo;
    
    /**
     * @return 授权信息
     */
    public AuthorizationInfo getAuthorizationInfo() {
        return authorizationInfo;
    }
    public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }

    public static class AuthorizationInfo {
        
        @JSONField(name="authorizer_appid") private String authorizerAppId;
        @JSONField(name="authorizer_access_token") private String authorizerAccessToken;
        @JSONField(name="expires_in") private String expiresIn;
        @JSONField(name="authorizer_refresh_token") private String authorizerRefreshToken;
        @JSONField(name="func_info") private FuncInfo[] funcInfo;
        
        /**
         * @return 授权方appid
         */
        public String getAuthorizerAppId() {
            return authorizerAppId;
        }
        public void setAuthorizerAppId(String authorizerAppId) {
            this.authorizerAppId = authorizerAppId;
        }
        /**
         * @return 授权方令牌（在授权的公众号具备API权限时，才有此返回值）
         */
        public String getAuthorizerAccessToken() {
            return authorizerAccessToken;
        }
        public void setAuthorizerAccessToken(String authorizerAccessToken) {
            this.authorizerAccessToken = authorizerAccessToken;
        }
        /**
         * @return 有效期（在授权的公众号具备API权限时，才有此返回值）
         */
        public String getExpiresIn() {
            return expiresIn;
        }
        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }
        /**
         * @return 刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于公众
         * 号第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。 一
         * 旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
         */
        public String getAuthorizerRefreshToken() {
            return authorizerRefreshToken;
        }
        public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
            this.authorizerRefreshToken = authorizerRefreshToken;
        }
        /**
         * @return 公众号授权给开发者的权限集列表
         */
        public FuncInfo[] getFuncInfo() {
            return funcInfo;
        }
        public void setFuncInfo(FuncInfo[] funcInfo) {
            this.funcInfo = funcInfo;
        }
        
    }
    
    public static class FuncInfo {
        
        @JSONField(name="funcscope_category") private FuncScopCategory funcscopCategory;

        public FuncScopCategory getFuncscopCategory() {
            return funcscopCategory;
        }
        public void setFuncscopCategory(FuncScopCategory funcscopCategory) {
            this.funcscopCategory = funcscopCategory;
        }
        
    }
    
    public static class FuncScopCategory {
        
        @JSONField(name="id") private Integer id;

        /**
         * 1  消息与菜单权限集<br>
         * 2  用户管理权限集<br>
         * 3  帐号管理权限集<br>
         * 4  网页授权权限集<br>
         * 5  微信小店权限集<br>
         * 6  多客服权限集<br>
         * 7  业务通知权限集<br>
         * 8  微信卡券权限集<br>
         * 9  微信扫一扫权限集<br>
         * 10 微信连WIFI权限集<br>
         * 11 素材管理权限集<br>
         * 12 摇一摇周边权限集<br>
         * 13 微信门店权限集
         */
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
    }
}
