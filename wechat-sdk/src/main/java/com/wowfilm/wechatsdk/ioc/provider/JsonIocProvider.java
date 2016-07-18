package com.wowfilm.wechatsdk.ioc.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.wowfilm.wechatsdk.common.Json;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import org.apache.log4j.Logger;



public class JsonIocProvider implements IocProvider {

    private Logger log = Logger.getLogger(JsonIocProvider.class);
    private Map<String, Object> ioc;
    
    public JsonIocProvider(String src) {
        
        try {
            ioc = Json.parse(new StringReader(src));
        } catch (IOException e) {
            log.error("Json 串加载失败", e);
        }
    }
    
    public JsonIocProvider(byte[] src) {
        
        try {
            ioc = Json.parse(new InputStreamReader(new ByteArrayInputStream(src), "UTF-8"));
        } catch (IOException e) {
            log.error("Json 串加载失败", e);
        }
    }
    
    @Override
    public void registTo(BeanFactory factory) {
        
        Iterator<Entry<String, Object>> it = ioc.entrySet().iterator();
        while(it.hasNext()) {
            Entry<String, Object> entry = it.next();
            factory.regist(entry.getKey(), entry.getValue());
        }
    }
}
