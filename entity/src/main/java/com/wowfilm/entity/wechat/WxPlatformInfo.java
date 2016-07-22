package com.wowfilm.entity.wechat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-18 15:54:03
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WxPlatformInfo {
    private Long id;
    /**
     * 第三方开发平台appId
     */
    private String appId;
    /**
     * 微信推送的验证码
     */
    private String componentVerifyTicket;
    /**
     * 验证码推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone="GMT+8")
    private Date ticketCreateTime;
    /**
     * 平台token
     */
    private String componentAccessToken;
    /**
     * 平台token失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone="GMT+8")
    private Date tokenExpiresOut;
    /**
     * 平台预授权码
     */
    private String preAuthCode;
    /**
     * 平台预授权码失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone="GMT+8")
    private Date preAuthCodeExpiresOut;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentVerifyTicket() {
        return componentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        this.componentVerifyTicket = componentVerifyTicket;
    }

    public Date getTicketCreateTime() {
        return ticketCreateTime;
    }

    public void setTicketCreateTime(Date ticketCreateTime) {
        this.ticketCreateTime = ticketCreateTime;
    }

    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }

    public Date getTokenExpiresOut() {
        return tokenExpiresOut;
    }

    public void setTokenExpiresOut(Date tokenExpiresOut) {
        this.tokenExpiresOut = tokenExpiresOut;
    }

    public String getPreAuthCode() {
        return preAuthCode;
    }

    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }

    public Date getPreAuthCodeExpiresOut() {
        return preAuthCodeExpiresOut;
    }

    public void setPreAuthCodeExpiresOut(Date preAuthCodeExpiresOut) {
        this.preAuthCodeExpiresOut = preAuthCodeExpiresOut;
    }
}
