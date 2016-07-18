package com.wowfilm.wechatsdk.ioc.provider;


import com.wowfilm.wechatsdk.ioc.core.BeanFactory;

/**
 * The interface Ioc provider.
 */
public interface IocProvider {

    /**
     * Regist to.
     *
     * @param factory the factory
     */
    public void registTo(BeanFactory factory);

}
