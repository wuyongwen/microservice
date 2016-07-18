/**
 * WeiXin
 *
 * @title BasicResult.java
 * @package com.chn.wx.vo.result
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月23日-下午6:22:24
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.wowfilm.wechatsdk.common.StringUtils;

/**
 * @author lzxz1234
 * @version v1.0
 * @class BasicResult
 * @description
 */
public class BasicResult implements Serializable {

    private static final long serialVersionUID = 3379612749505864582L;

    @JSONField(name = "errcode")
    private String errcode;
    @JSONField(name = "errmsg")
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Boolean hasError() {
        return !StringUtils.isEmpty(errcode) && !"0".equals(errcode);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
