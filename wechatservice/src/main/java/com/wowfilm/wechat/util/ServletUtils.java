package com.wowfilm.wechat.util;

import com.wowfilm.wechatsdk.common.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ServletUtils {

    private final static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     * 
     * 返回的结果的Parameter名已去除前缀.
     */
    public static Map<String, Object> buildParameters(ServletRequest request) {
        Enumeration<?> paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        String prefix = "search_";
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if ((values == null) || (values.length == 0)) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    String val = values[0];
                    if (StringUtils.isNotBlank(val)) {
                        params.put(unprefixed, val);
                    }
                }
            }
        }
        return params;
    }

    /**
    * 根据请求头解析出文件名
    * 请求头的格式：火狐和google浏览器下：form-data; name="file"; filename="snmp4j--api.zip"
    *                 IE浏览器下：form-data; name="file"; filename="E:\snmp4j--api.zip"
    * @param part 请求头
    * @return 文件名
    */
    private static String getFileNameFromPart(Part part) {
        //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
        String header = part.getHeader("content-disposition");
        /**
         * String[] tempArr1 = header.split(";");代码执行完之后，在不同的浏览器下，tempArr1数组里面的内容稍有区别
         * 火狐或者google浏览器下：tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
         * IE浏览器下：tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
         */
        String[] tempArr1 = header.split(";");
        /**
         *火狐或者google浏览器下：tempArr2={filename,"snmp4j--api.zip"}
         *IE浏览器下：tempArr2={filename,"E:\snmp4j--api.zip"}
         */
        String[] tempArr2 = tempArr1[2].split("=");
        //获取文件名，兼容各种浏览器的写法
        String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
        return fileName;
    }

    private static String staticFileUploadDir;

    /**
     * 获取文件上传根目录：优先取write_upload_file_dir参数值，如果没有定义则取webapp/upload
     * @return 返回图片访问相对路径
     */
    public static String writeUploadFile(InputStream fis, String name, long length) {
        staticFileUploadDir = getStaticFileUploadDir();

        //简便的做法用UUID作为主键，每次上传都会创建文件对象和数据记录，便于管理，但是存在相同文件重复保存情况
        String id = UUID.randomUUID().toString();

        //加上年月日分组处理，一方面便于直观看出上传文件日期信息以便批量处理，另一方面合理分组控制目录的个数和层级避免单一目录下文件过多
        Calendar cal = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        int year = cal.get(Calendar.YEAR);
        sb.append("/" + year);
        String month = "";
        int monthOfYear = cal.get(Calendar.MONTH) + 1;
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = "" + monthOfYear;
        }
        String day = "";
        int dayOfMonth = cal.get(Calendar.DATE);
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = "" + dayOfMonth;
        }
        sb.append("/" + month);
        sb.append("/" + day);
        Assert.notNull(id, "id is required to buildInstance");
        sb.append("/" + id);

        String path = "/upload" + sb + "/" + name;
        String fullPath = staticFileUploadDir + path;
        logger.debug("Saving upload file: {}", fullPath);
        try {
            FileUtils.copyInputStreamToFile(fis, new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;

    }
    
    public static String getStaticFileUploadDir(){
    	if (staticFileUploadDir == null) {
            //DynamicConfigService dynamicConfigService = SpringContextHolder.getBean(DynamicConfigService.class);
            //staticFileUploadDir = dynamicConfigService.getString("write_upload_file_dir");
            if (StringUtils.isBlank(staticFileUploadDir)) {
                staticFileUploadDir = WebAppContextInitFilter.getInitedWebContextRealPath();
            }
            if (staticFileUploadDir.endsWith(File.separator)) {
                staticFileUploadDir = staticFileUploadDir.substring(0, staticFileUploadDir.length() - 1);
            }
            logger.info("Setup file upload root dir:  {}", staticFileUploadDir);
        }
    	return staticFileUploadDir;
    }
}
