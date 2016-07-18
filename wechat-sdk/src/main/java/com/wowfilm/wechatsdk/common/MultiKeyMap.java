package com.wowfilm.wechatsdk.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 支持多键值的 Map，非线程安全
 * @author lzxz1234<lzxz1234@gmail.com>
 */
public class MultiKeyMap {

    private Map<Object, Object> root = new HashMap<Object, Object>();
    
    /**
     * 查询
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Object...  keys) {
        
        if(keys == null || keys.length == 0) throw new NullPointerException("不允许空键。");
        Map<Object, Object> map = this.loopTo(root, Arrays.copyOf(keys, keys.length - 1));
        return (T) map.get(keys[keys.length - 1]);
    }
    
    /**
     * @see {@link #put(keys...)}
     */
    public <T> T put(Object key1, T value) {
        
        return put(new Object[]{key1}, value);
    }
    
    /**
     * @see {@link #put(keys...)}
     */
    public <T> T put(Object key1, Object key2, T value) {
        
        return put(new Object[]{key1, key2}, value);
    }
    
    /**
     * @see {@link #put(keys...)}
     */
    public <T> T put(Object key1, Object key2, Object key3, T value) {
        
        return put(new Object[]{key1, key2, key3}, value);
    }
    
    /**
     * 设值，返回前一个
     * @param keys
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T put(Object[] keys, T value) {
        
        if(value == null) throw new NullPointerException("不允许空值。");
        Map<Object, Object> map = this.loopTo(root, Arrays.copyOf(keys, keys.length - 1));
        return (T) map.put(keys[keys.length - 1], value);
    }
    
    @SuppressWarnings("unchecked")
    private Map<Object, Object> loopTo(Map<Object, Object> root, Object... keys) {
        
        if(keys == null) throw new NullPointerException("不允许空键。");
        Map<Object, Object> result = root;
        for(Object key : keys) {
            if(key == null) throw new NullPointerException("不允许空键。");
            Object findObj = result.get(key);
            if(findObj == null) {
                findObj = new HashMap<Object, Object>();
                result.put(key, findObj);
            }
            if(findObj instanceof Map) 
                result = (Map<Object, Object>) findObj;
            else
                throw new IllegalArgumentException("参数错误，子路径已被占用");
        }
        return result;
    }
    
}
