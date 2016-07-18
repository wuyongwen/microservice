package com.wowfilm.wechatsdk.ioc;


import com.wowfilm.wechatsdk.annotation.Node;
import com.wowfilm.wechatsdk.common.Lang;
import com.wowfilm.wechatsdk.dto.App;
import com.wowfilm.wechatsdk.ioc.core.BeanFactory;
import com.wowfilm.wechatsdk.ioc.provider.AnnotationProvider;
import com.wowfilm.wechatsdk.ioc.provider.JsonIocProvider;

public class Ioc {

    private BeanFactory factory;
    
    public Ioc() {
        
        factory = new BeanFactory();
        factory.regist("beanFactory", factory);
        new AnnotationProvider<Node>("com.chn.wx.listener", Node.class).registTo(factory);
        new AnnotationProvider<Node>(App.getConfig("weixin.service.package"), Node.class).registTo(factory);
        new JsonIocProvider(new String(Lang.loadFromClassPath("/weixin.json"))).registTo(factory);
    }
    
}
