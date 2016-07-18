package com.wowfilm.wechatsdk.entity.platform;

import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.entity.BasicResult;

public class PlatFormGetAuthorizerOptionResult extends BasicResult {

    private static final long serialVersionUID = -4376993323688333514L;
    
    @JSONField(name="authorizer_appid") private String authorizerAppid;
    @JSONField(name="option_name") private String optionName;
    @JSONField(name="option_value") private String optionValue;
    
    /**
     * @return 授权公众号appid
     */
    public String getAuthorizerAppid() {
        return authorizerAppid;
    }
    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }
    /**
     * @return 选项名称
     */
    public String getOptionName() {
        return optionName;
    }
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
    /**
     * @return 选项值
     */
    public String getOptionValue() {
        return optionValue;
    }
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
    
}
