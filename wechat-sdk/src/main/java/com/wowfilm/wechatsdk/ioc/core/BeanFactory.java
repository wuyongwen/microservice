package com.wowfilm.wechatsdk.ioc.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wowfilm.wechatsdk.ioc.SingletonFactoryBean;
import org.apache.log4j.Logger;

public class BeanFactory implements Iterable<Entry<String, FactoryBean<?>>> {

    private Logger log = Logger.getLogger(BeanFactory.class);
    private Map<String, FactoryBean<?>> map = new LinkedHashMap<>();

    public <T> FactoryBean<T> get(String name) {

        return (FactoryBean<T>) map.get(name);
    }

    public Iterator<Entry<String, FactoryBean<?>>> iterator() {

        return map.entrySet().iterator();
    }

    public void regist(String name, Object obj) {

        FactoryBean<?> target = from(obj);
        FactoryBean<?> previous = this.map.put(name, target);
        if (previous != null)
            throw new RuntimeException("重复 bean 定义" + name + "-" + target.getType().getName()
                    + "-" + previous.getType().getName());
        else
            log.info(String.format(">>加载 bean 定义 %s-%s", name, target.getType().getName()));
    }

    public void replace(String name, Object obj) {

        FactoryBean<?> target = from(obj);
        FactoryBean<?> previous = this.map.put(name, target);
        if (previous != null)
            log.info(String.format(">> %s 定义从 %s 切换为 %s", name, previous.getType().getName()
                    , target.getType().getName()));
        else
            log.info(String.format(">>加载 bean 定义 %s-%s", name, target.getType().getName()));
    }

    protected <T> FactoryBean<T> from(Object obj) {

        if (obj instanceof Map)
            return fromMap((Map<?, ?>) obj);
        if (obj instanceof Class)
            return fromClass((Class<?>) obj);
        return new PrimitiveTypeFactoryBean<T>(this, (T) obj);
    }

    private <T> ProtoTypeFactoryBean<T> fromClass(Class<?> clazz) {

        ProtoTypeFactoryBean<T> result = new ProtoTypeFactoryBean<T>(this);
        result.setType(clazz);
        return result;
    }

    private <T> FactoryBean<T> fromMap(Map<?, ?> params) {

        if (params == null || params.size() == 0)
            throw new IllegalArgumentException("配置项错误");
        if (params.containsKey("refer")) {
            ReferFactoryBean<T> result = new ReferFactoryBean<T>(this);
            result.setRefer((String) params.get("refer"));
            return result;
        } else {
            boolean isSingleton = params.get("singleton") != null && ("true".equals(params.get("singleton").toString()));
            ProtoTypeFactoryBean<T> result = isSingleton ? new SingletonFactoryBean<T>(this) : new ProtoTypeFactoryBean<T>(this);
            result.setType(params.get("type"));
            result.setArgs(params.get("args"));
            result.setFields(params.get("fields"));
            result.setInit(params.get("init"));
            return result;
        }
    }

}
