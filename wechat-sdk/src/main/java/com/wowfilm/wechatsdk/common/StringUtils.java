/**
 * WeiXin
 * @title StringUtils.java
 * @package com.chn.common
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月15日-下午6:55:34
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.common;

import java.io.UnsupportedEncodingException;

/**
 * @class StringUtils
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class StringUtils {

    public static boolean isEmpty(String src) {
        
        return src == null || src.length() == 0;
    }

    public static byte[] getBytesUtf8(String str) {
        
        if(str == null) return null;
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String newStringUtf8(byte[] bytes) {
        try {
            return bytes == null ? null : new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
