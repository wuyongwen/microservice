package com.wowfilm.wechat.content;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Spring上下文容器
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        Assert.notNull(applicationContext);
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, String beanId) {
        Assert.notNull(applicationContext);
        return applicationContext.getBean(requiredType, beanId);
    }


}
