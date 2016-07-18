/**
 * WeiXin
 * @title Assert.java
 * @package com.chn.common
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月16日-下午1:55:03
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.common;

/**
 * @class Assert
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class Assert {

    public static void notNull(Object target, String format, Object... args) {
        
        if(target == null)
            throw new RuntimeException(String.format(format, args));
    }
    
}
