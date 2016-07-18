package com.wowfilm.wechatsdk;


import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.common.Lang;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.dto.Context;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import com.wowfilm.wechatsdk.ioc.core.FactoryBean;
import com.wowfilm.wechatsdk.ioc.provider.AnnotationProvider;
import com.wowfilm.wechatsdk.ioc.provider.JsonIocProvider;
import com.wowfilm.wechatsdk.listener.ThreadsMode;

/**
 * The type Message handler.
 *
 * @author warden
 * @date Created on 2016-07-12 20:15:04
 */
public class MessageHandler {

    private ThreadsMode proxy;
    private BeanFactory factory;
    
    public MessageHandler() throws Exception {
        
        factory = new BeanFactory();
        factory.regist("beanFactory", factory);
        new AnnotationProvider<Node>("com.wowfilm.wechatsdk.listener", Node.class).registTo(factory);
        new AnnotationProvider<Node>(App.getConfig("weixin.service.package"), Node.class).registTo(factory);
        new JsonIocProvider(new String(Lang.loadFromClassPath("/weixin.json"))).registTo(factory);
        FactoryBean<ThreadsMode> factoryBean = factory.get("root");
        proxy = factoryBean.get();
    }

    public String process(Context context) throws Exception {
        context.setAttribute("threadsMode", proxy);
        return proxy.process(context);
    }

}
