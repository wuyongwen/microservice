package com.wowfilm.wechatsdk.common;

import com.wowfilm.wechatsdk.store.PlatformConfigStorage;

/**
 * 自动刷新工具类
 * @author lzxz1234
 * @version v1.0
 */
public abstract class Refresher<T> {

    protected T current;
    protected PlatformConfigStorage storage;
    
    public abstract T refresh();
    
    public abstract boolean isExpired();
    
    public T get() {
        
        if(isExpired()||current==null) 
            current = refresh();
        return current;
    }

    public void setStorage(PlatformConfigStorage storage) {
        this.storage = storage;
    }
}
