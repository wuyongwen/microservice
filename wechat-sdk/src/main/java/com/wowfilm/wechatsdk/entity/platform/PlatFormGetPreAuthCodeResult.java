package com.wowfilm.wechatsdk.entity.platform;

import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.entity.BasicResult;

public class PlatFormGetPreAuthCodeResult extends BasicResult {

    private static final long serialVersionUID = 7492889147130582442L;
    
    @JSONField(name="pre_auth_code") private String preAuthCode;
    @JSONField(name="expires_in") private Integer expiresIn;
    
    public String getPreAuthCode() {
        return preAuthCode;
    }
    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }
    public Integer getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
    
}
