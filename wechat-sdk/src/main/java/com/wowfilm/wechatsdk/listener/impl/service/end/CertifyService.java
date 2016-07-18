package com.wowfilm.wechatsdk.listener.impl.service.end;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.annotation.Param;
import com.wowfilm.wechatsdk.common.SHA1;
import com.wowfilm.wechatsdk.common.StringUtils;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.impl.service.route.MethodRouter;
import org.apache.log4j.Logger;

/**
 * @class CertifyService
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
@Node(value="GET", parents=MethodRouter.class)
public class CertifyService implements Service {

    private static Logger log = Logger.getLogger(CertifyService.class);
    
    @Param
    protected String signature;
    @Param
    protected String timestamp;
    @Param
    protected String nonce;
    @Param
    protected String echostr;

    /* 
     * 加密/校验流程如下：
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */
    @Override
    public String doService(Context context) throws Exception {
        
        if(StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) ||
                StringUtils.isEmpty(nonce) || StringUtils.isEmpty(echostr))
            return "微信接口，禁止访问！";
        String calcSignature = SHA1.getSHA1(App.Info.token, timestamp, nonce);
        log.info("对比签名：["+calcSignature+"]["+signature+"]");
        return calcSignature.equals(signature) ? echostr : null;
    }

}
