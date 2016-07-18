package com.wowfilm.wechatsdk.listener.impl.process;

import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;

public class SyncThreadMode extends ThreadsMode {

    private final Logger log = Logger.getLogger(SyncThreadMode.class);
    
    public SyncThreadMode(BeanFactory factory) {
        
        super(factory);
    }
    
    @Override
    public String process(Context context) {
        
        try {
            
            return super.process(context);
        } catch (Exception e) {
            log.error("任务处理出错", e);
        }
        return null;
    }

}
