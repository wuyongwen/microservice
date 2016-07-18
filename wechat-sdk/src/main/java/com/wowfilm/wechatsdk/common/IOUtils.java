package com.wowfilm.wechatsdk.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * IO 操作工具类
 * @version v1.0
 */
public class IOUtils {

    private static Logger log = Logger.getLogger(IOUtils.class);
    
    /**
     * 调用无参的 close() 方法且不会抛出异常
     * @param x
     */
    public static final void closeQuietly(Object x) {
        
        try {
            if(x == null) return;
            InvokeUtils.invoke(x, "close");
        } catch (Exception e) {
            log.warn("[close]方法调用失败", e);
        }
    }

    /**
     * 将输入流读取成字节数组后返回 
     * @param input 输入流
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    
    /**
     * 将输入流中的全部内容读取后写到输出流
     * @param input 输入流
     * @param output 输出流
     * @return 总拷贝长度
     * @throws IOException
     */
    public static int copy(InputStream input, OutputStream output) 
            throws IOException {
        
        byte[] buffer = new byte[1024];
        long count = 0L;
        int n = 0;
        while(-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return (int)count;
    }

    /**
     * 读取输入流中的内容并按指定编码方式组成字符串后返回
     * @param is 输入流
     * @param characterEncoding 输入流的编码格式
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String toString(InputStream is, String characterEncoding) 
            throws UnsupportedEncodingException, IOException {
        
        return new String(toByteArray(is), characterEncoding);
    }
    
    /**
     * 读取输入流中的内容并组成字符串后返回
     * @param reader
     * @return
     * @throws IOException
     */
    public static String toString(Reader reader) throws IOException {
        
        if (!(reader instanceof BufferedReader))
            reader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        
        char[] data = new char[64];
        int len;
        while (true) {
            if ((len = reader.read(data)) == -1)
                break;
            sb.append(data, 0, len);
        }
        return sb.toString();
    }
    
}
