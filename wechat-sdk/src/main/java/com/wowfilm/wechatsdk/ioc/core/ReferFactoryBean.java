package com.wowfilm.wechatsdk.ioc.core;

public class ReferFactoryBean<T> extends FactoryBean<T> {

    private String refer;
    
    public ReferFactoryBean(BeanFactory context) {
        
        super(context);
    }

    @Override
    public T get() throws Exception {
        
        FactoryBean<T> result = context.get(refer);
        return result.get();
    }

    @Override
    public Class<?> getType() {
        
        FactoryBean<T> result = context.get(refer);
        return result.getType();
    }

    public void setRefer(String refer) {
        
        this.refer = refer;
    }
    
}
