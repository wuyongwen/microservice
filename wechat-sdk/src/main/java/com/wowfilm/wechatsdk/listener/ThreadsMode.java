package com.wowfilm.wechatsdk.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import com.wowfilm.wechatsdk.ioc.core.FactoryBean;
import org.apache.log4j.Logger;



public abstract class ThreadsMode {
    
    private Logger log = Logger.getLogger(ThreadsMode.class);
    private Map<String, FactoryBean<? extends Service>> route = new HashMap<>();
    private BeanFactory factory;

    public ThreadsMode(BeanFactory factory) {
        
        this.factory = factory;
    }
    
    public String process(Context context) throws Exception {
        
        return this.routeToNext(Service.class, "root", context);
    }
    
    public void init() {
        
        Iterator<Entry<String, FactoryBean<?>>> it = factory.iterator();
        while(it.hasNext()) {
            Entry<String, FactoryBean<?>> entry = it.next();
            Node node = entry.getValue().getType().getAnnotation(Node.class);
            if(node == null) continue;
            for(Class<? extends Service> each : node.parents()) {
                String path = each.getName() + "." + node.value();
                FactoryBean<? extends Service> newNode = (FactoryBean<? extends Service>) entry.getValue();
                FactoryBean<? extends Service> previous = route.get(path);
                if(previous == null) {
                    route.put(path, newNode);
                    log.info(String.format(">>>路径 %s 的处理类加载为 %s", path, newNode.getType()));
                } else if (previous.getType().isAssignableFrom(newNode.getType())) {
                    route.put(path, newNode);
                    log.info(String.format(">>>路径 %s 的处理类由 %s 切换为 %s", path, previous.getType(), newNode.getType()));
                }
            }
        }
    }

    public String routeToNext(Class<? extends Service> from, String identify, Context context) throws Exception {
        
        String key = from.getName() + "." + identify;
        FactoryBean<? extends Service> factoryBean = route.get(key);
        if(factoryBean == null) 
            throw new RuntimeException(String.format("类 %s 无 %s 子结点", from, identify));
        Service bean = factoryBean.get();
        context.injectField(bean);
        return bean.doService(context);
    }
    
}
