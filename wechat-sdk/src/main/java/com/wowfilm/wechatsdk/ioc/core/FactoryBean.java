package com.wowfilm.wechatsdk.ioc.core;


public abstract class FactoryBean<T> {

    protected BeanFactory context;
    
    public FactoryBean(BeanFactory context) {
        
        this.context = context;
    }
    
    public abstract T get() throws Exception;
    public abstract Class<?> getType();
    
}
