package com.wowfilm.wechatsdk.listener.impl.process;

import java.util.concurrent.Callable;

import com.wowfilm.wechatsdk.common.Exec;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import com.wowfilm.wechatsdk.listener.Service;
import com.wowfilm.wechatsdk.listener.ThreadsMode;
import org.apache.log4j.Logger;


/**
 * The type Async thread mode.
 *
 * @author warden
 * @date Created on 2016-07-12 16:05:35
 */
public class AsyncThreadMode extends ThreadsMode {

    public AsyncThreadMode(BeanFactory factory) {
        
        super(factory);
    }

    private final Logger log = Logger.getLogger(AsyncThreadMode.class);
    
    @Override
    public String process(final Context context) {
        
        Exec.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                
                try {
                    AsyncThreadMode.this.routeToNext(Service.class, "root", context);
                } catch (Exception e) {
                    log.error("任务处理出错", e);
                }
                return null;
            }
            
        });
        return null; //异步模式忽略返回
    }

}
