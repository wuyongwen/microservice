package com.wowfilm.wechatsdk.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * @class Cfg
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class Cfg {

    private Properties prop;
    
    /**
     * @description 每次调用都会引起文件加载，文件编码格式应为 UTF-8
     * @param path classpath，如果是classes目录下的config.properties那么传参应为 '/config.properties'
     * @return 
     */
    public static Cfg getClassPathCfg(String path) {
        
        try {
            return new Cfg(Cfg.class.getResourceAsStream(path));
        } catch (Exception e) {
            throw new IllegalArgumentException("Config file[classpath: " + path + "] can't be loaded!", e);
        } 
    }
    
    public static Cfg getFileSystemCfg(String path) {
        
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return new Cfg(is);
        } catch (Exception e) {
            throw new IllegalArgumentException("Config file[classpath: " + path + "] can't be loaded!", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    private Cfg(InputStream is) throws Exception {
        
        try {
            Properties props = new Properties();
            props.load(new InputStreamReader(is, "UTF-8"));
            this.prop = props;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    public String get(String key) {
        
        return prop.getProperty(key);
    }
    
    public String get(String key, String defaultValue) {
        
        return prop.getProperty(key, defaultValue);
    }
    
}
