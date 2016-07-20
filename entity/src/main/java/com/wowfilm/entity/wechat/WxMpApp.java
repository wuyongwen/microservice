package com.wowfilm.entity.wechat;


import java.util.Date;

/**
 * The type Wx mp app.
 * 授权微信公众号
 *
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-20 10:32:10
 */
public class WxMpApp {
    private Long id;
    /**
     * 公众号Id
     */
    private String authorizerAppid;
    /**
     * 公众号是否失效,用户取消授权时为true:1 ralse:0
     */
    private Integer unauthorized = 0;
    /**
     * 公众号Token
     */
    private String authorizerAccessToken;

    /**
     * Token失效时间
     */
    private Date tokenExpiresOut;

    /**
     * 公众号Token刷新码
     */
    private String authorizerRefreshToken;

    /**
     * 公众号权限
     */
    private String funcInfo;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 公众号类型,授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    private Integer serviceTypeInfo;

    /**
     * 认证类型,-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，
     * + 4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    private Integer verifyTypeInfo;

    /**
     * 公众号原始ID
     */
    private String userName;
    // 保存格式 {open_store: 0, open_scan: 0, open_pay: 0, open_card: 0, open_shake: 0}
    /**
     * 功能开通权限 , 用以了解以下功能的开通状况（0代表未开通，1代表已开通）：
     * + open_store:是否开通微信门店功能;open_scan:是否开通微信扫商品功能;
     * + open_pay:是否开通微信支付功能;open_card:是否开通微信卡券功能;open_shake:是否开通微信摇一摇功能
     */
    private String businessInfo;

    private String openShake;

    /**
     * 微信号,授权方公众号所设置的微信号，可能为空
     */
    private String alias;

    /**
     * 二维码图片
     */
    private String qrcodeUrl;
    /**
     * 本地存储地址
     */
    private String localqrcodePath;
    /**
     * 菜单是否开启,0:未开启;1:开启
     */
    private Integer isMenuOpern;
    /**
     * 菜单信息,菜单信息
     */
    private String selfMenuInfo;
    /**
     * 配置菜单信息,配置菜单信息
     */
    private String confMenuInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }


    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }

    public Integer getUnauthorized() {
        return unauthorized;
    }

    public void setUnauthorized(Integer unauthorized) {
        this.unauthorized = unauthorized;
    }

    public Date getTokenExpiresOut() {
        return tokenExpiresOut;
    }

    public void setTokenExpiresOut(Date tokenExpiresOut) {
        this.tokenExpiresOut = tokenExpiresOut;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public String getFuncInfo() {
        return funcInfo;
    }

    public void setFuncInfo(String funcInfo) {
        this.funcInfo = funcInfo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public void setServiceTypeInfo(Integer serviceTypeInfo) {
        this.serviceTypeInfo = serviceTypeInfo;
    }

    public Integer getVerifyTypeInfo() {
        return verifyTypeInfo;
    }

    public void setVerifyTypeInfo(Integer verifyTypeInfo) {
        this.verifyTypeInfo = verifyTypeInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getOpenShake() {
        return openShake;
    }

    public void setOpenShake(String openShake) {
        this.openShake = openShake;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getLocalqrcodePath() {
        return localqrcodePath;
    }

    public void setLocalqrcodePath(String localqrcodePath) {
        this.localqrcodePath = localqrcodePath;
    }

    public Integer getIsMenuOpern() {
        return isMenuOpern;
    }

    public void setIsMenuOpern(Integer isMenuOpern) {
        this.isMenuOpern = isMenuOpern;
    }

    public String getSelfMenuInfo() {
        return selfMenuInfo;
    }

    public void setSelfMenuInfo(String selfMenuInfo) {
        this.selfMenuInfo = selfMenuInfo;
    }

    public String getConfMenuInfo() {
        return confMenuInfo;
    }

    public void setConfMenuInfo(String confMenuInfo) {
        this.confMenuInfo = confMenuInfo;
    }
}
