package com.wowfilm.wechatsdk.ioc.core;


public class PrimitiveTypeFactoryBean<T> extends FactoryBean<T> {

    private final Class<T> type;
    private final T src;
    
    public PrimitiveTypeFactoryBean(BeanFactory context, Class<T> type, T src) {
        
        super(context);
        this.type = type;
        this.src = src;
    }
    
    @SuppressWarnings("unchecked")
    public PrimitiveTypeFactoryBean(BeanFactory context, T src) {
        
        this(context, (Class<T>)src.getClass(), src);
    }
    
    @Override
    public T get() throws Exception {
        
        return src;
    }

    @Override
    public Class<T> getType() {
        
        return type;
    }

}
