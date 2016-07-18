package com.wowfilm.wechatsdk.entity.platform;

import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.entity.BasicResult;

public class PlatFormGetAuthorizerInfoResult extends BasicResult {

    private static final long serialVersionUID = -8473132494340839109L;
    
    @JSONField(name="authorizer_info") private AuthorizerInfo authorizerInfo;
    @JSONField(name="authorization_info") private AuthorizationInfo authorizationInfo;
    @JSONField(name="qrcode_url") private String qrcodeUrl;
    public AuthorizerInfo getAuthorizerInfo() {
        return authorizerInfo;
    }
    public void setAuthorizerInfo(AuthorizerInfo authorizerInfo) {
        this.authorizerInfo = authorizerInfo;
    }
    /**
     * @return 二维码图片的URL，开发者最好自行也进行保存
     */
    public String getQrcodeUrl() {
        return qrcodeUrl;
    }
    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }
    /**
     * @return 授权信息
     */
    public AuthorizationInfo getAuthorizationInfo() {
        return authorizationInfo;
    }
    public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
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
    
    public static class AuthorizationInfo {
        
        @JSONField(name="appid") private String appId;
        @JSONField(name="func_info") private FuncInfo[] funcInfo;
        
        /**
         * @return 授权方appid
         */
        public String getAppId() {
            return appId;
        }
        public void setAppId(String appId) {
            this.appId = appId;
        }
        public FuncInfo[] getFuncInfo() {
            return funcInfo;
        }
        public void setFuncInfo(FuncInfo[] funcInfo) {
            this.funcInfo = funcInfo;
        }
        
    }
    public static class AuthorizerInfo {
        
        @JSONField(name="nick_name") private String nickName;
        @JSONField(name="head_img") private String headImg;
        @JSONField(name="service_type_info") private ServiceTypeInfo serviceTypeInfo;
        @JSONField(name="verify_type_info") private VerifyTypeInfo verifyTypeInfo;
        @JSONField(name="user_name") private String userName;
        @JSONField(name="business_info") private BusinessInfo businessInfo;
        @JSONField(name="alias") private String alias;
        @JSONField(name="open_shake") private String openShake;
        
        public String getOpenShake() {
			return openShake;
		}
		public void setOpenShake(String openShake) {
			this.openShake = openShake;
		}
		/**
         * @return 开通状况
         */
        public BusinessInfo getBusinessInfo() {
            return businessInfo;
        }
        public void setBusinessInfo(BusinessInfo businessInfo) {
            this.businessInfo = businessInfo;
        }
        /**
         * @return 授权方昵称
         */
        public String getNickName() {
            return nickName;
        }
        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        /**
         * @return 授权方头像
         */
        public String getHeadImg() {
            return headImg;
        }
        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
        public ServiceTypeInfo getServiceTypeInfo() {
            return serviceTypeInfo;
        }
        public void setServiceTypeInfo(ServiceTypeInfo serviceTypeInfo) {
            this.serviceTypeInfo = serviceTypeInfo;
        }
        public VerifyTypeInfo getVerifyTypeInfo() {
            return verifyTypeInfo;
        }
        public void setVerifyTypeInfo(VerifyTypeInfo verifyTypeInfo) {
            this.verifyTypeInfo = verifyTypeInfo;
        }
        /**
         * @return 授权方公众号的原始ID
         */
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        /**
         * @return 授权方公众号所设置的微信号，可能为空
         */
        public String getAlias() {
            return alias;
        }
        public void setAlias(String alias) {
            this.alias = alias;
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
         * 13 微信门店权限集<br>
         * 14 微信支付权限<br>
自	     * 15   定义菜单权限
         */
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
    }

    public static class ServiceTypeInfo {
        
        @JSONField(name="id") private Integer id;
        
        /**
         * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
         */
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        
    }
    
    public static class VerifyTypeInfo {
        
        @JSONField(name="id") private Integer id;
        
        /**
         * 授权方认证类型，
         * -1代表未认证，
         * 0代表微信认证，
         * 1代表新浪微博认证，
         * 2代表腾讯微博认证，
         * 3代表已资质认证通过但还未通过名称认证，
         * 4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，
         * 5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
         */
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        
    }
    
    public static class BusinessInfo {
        
        @JSONField(name="open_store") private Integer openStore;
        @JSONField(name="open_scan") private Integer openScan;
        @JSONField(name="open_pay") private Integer openPay;
        @JSONField(name="open_card") private Integer openCard;
        @JSONField(name="open_shake") private Integer openShake;
        
        /**
         * @return 是否开通微信门店功能
         */
        public Integer getOpenStore() {
            return openStore;
        }
        public void setOpenStore(Integer openStore) {
            this.openStore = openStore;
        }
        /**
         * @return 是否开通微信扫商品功能
         */
        public Integer getOpenScan() {
            return openScan;
        }
        public void setOpenScan(Integer openScan) {
            this.openScan = openScan;
        }
        /**
         * @return 是否开通微信支付功能
         */
        public Integer getOpenPay() {
            return openPay;
        }
        public void setOpenPay(Integer openPay) {
            this.openPay = openPay;
        }
        /**
         * @return 是否开通微信卡券功能
         */
        public Integer getOpenCard() {
            return openCard;
        }
        public void setOpenCard(Integer openCard) {
            this.openCard = openCard;
        }
        /**
         * @return 是否开通微信摇一摇功能
         */
        public Integer getOpenShake() {
            return openShake;
        }
        public void setOpenShake(Integer openShake) {
            this.openShake = openShake;
        }
    }
}
