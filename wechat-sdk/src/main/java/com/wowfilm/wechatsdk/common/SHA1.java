/**
 * WeiXin
 * @title SHA1.java
 * @package com.chn.wx.util
 * @author lzxz1234<lzxz1234@gmail.com>
 * @date 2014年12月17日-下午4:47:55
 * @version V1.0
 * All Right Reserved
 */
package com.wowfilm.wechatsdk.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @class SHA1
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class SHA1 {

    public static String getSHA1(String token, String timestamp, String nonce) {
        
        try {
            String[] array = new String[]{token, timestamp, nonce};
            Arrays.sort(array);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) 
                sb.append(array[i]);
            
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
    
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) hexstr.append(0);
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            throw new RuntimeException("未知错误！", e);
        }
    }
    /**
     * 串接arr参数，生成sha1 digest
     *
     * @param arr
     * @return
     */
    public static String gen(String... arr) throws NoSuchAlgorithmException {
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String a : arr) {
            sb.append(a);
        }
        return DigestUtils.sha1Hex(sb.toString());
    }
    /**
     * 用&串接arr参数，生成sha1 digest
     *
     * @param arr
     * @return
     */
    public static String genWithAmple(String... arr) throws NoSuchAlgorithmException {
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String a = arr[i];
            sb.append(a);
            if (i != arr.length - 1) {
                sb.append('&');
            }
        }
        return DigestUtils.sha1Hex(sb.toString());
    }
    
}
