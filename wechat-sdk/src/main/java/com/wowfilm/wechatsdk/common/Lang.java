package com.wowfilm.wechatsdk.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Lang {

    public static Class<?> forName(String type) {
        
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("类型" + type + "未找到", e);
        }
    }
    
    public static byte[] loadFromClassPath(String path) {
        
        try {
            InputStream is = Lang.class.getResourceAsStream(path);
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            throw new RuntimeException("从 classpath 加载文件 " + path + "失败", e);
        }
    }

	public static File saveClassPathFile(String yml) throws IOException {
		URL url = Lang.class.getResource("/");
		File file = new File(url.getPath()+File.separatorChar+yml);
		//if(!file.exists()) file.createNewFile();
		return file;
	}

	public static boolean checkClassPathFile(String yml) {
		URL url = Lang.class.getResource("/");
		File file = new File(url.getPath()+File.separatorChar+yml);
		
		return file.exists();
	}
}
