package com.wowfilm.wechatsdk.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持FIFO限定大小的内存级缓冲池
 * @class CacheUtils
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
public class CacheUtils {

    private static ConcurrentHashMap<Object, Map<Object, ExpireObj>> map = new ConcurrentHashMap<>();
    
    /**
     * 根据 Provider 的类名区别缓存位置
     * @see {@link #getValue(Object, Object, Provider)}
     * @param key
     * @param provider
     * @return 
     */
    public static <K, V> V getValue(K key, Provider<K, V> provider) {
        
        return getValue(provider.getClass().getName(), key, provider);
    }
    
    /**
     * 从命名为 cacheName 的缓存中查找 key 对应的值，记录缺失时调用 {@link Provider#get(key)}
     * 查询对应值添加加缓存并返回
     * @param cacheName
     * @param key
     * @param provider
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> V getValue(Object cacheName, K key, Provider<K, V> provider) {
        
        Map<Object, ExpireObj> cacheMap = getCache(cacheName, provider.maxSize());
        ExpireObj result = null;
        //FIFOLinkedHashMap 非线程安全，double check 限制单线程访问
        if((result = cacheMap.get(key)) == null || result.isExpired()) {
            synchronized(cacheMap) {
                if((result = cacheMap.get(key)) == null) {
                    result = new ExpireObj(provider.expireIn(), provider.get(key));
                    cacheMap.put(key, result);
                }
            }
        }
        return (V)result.obj;
    }
    
    private static Map<Object, ExpireObj> getCache(Object cacheName, int cacheSize) {
        
        Map<Object, ExpireObj> result = map.get(cacheName);
        if(result == null) {
            map.putIfAbsent(cacheName, new FIFOLinkedHashMap<Object, ExpireObj>(cacheSize));
            result = map.get(cacheName);
        }
        return result;
    }
    
    public static abstract class Provider<K, V> {
        
        /**
         * 查询 key 对应的结果，用于缓存未命中时的回调
         * @see {@link CacheUtils#getValue(cacheName, key, provider)}
         * @return
         */
        public abstract V get(K key);
        
        /**
         * 需要被缓存的最大对象数
         * @return 
         */
        public abstract int maxSize();
        
        /**
         * 最长有效时间，单位毫秒，默认一天
         * @return 
         */
        public long expireIn() {
            
            return 24 * 3600 * 1000;
        }
    }
    
    private static class ExpireObj {
        final long expireTime;
        final Object obj;
        public ExpireObj(long maxAvaliable, Object obj) {
            this.expireTime = System.currentTimeMillis() + maxAvaliable;
            this.obj = obj;
        }
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
    
    /**
     * 先进先出的限制大小的HashMap，非线程安全
     * @author lzxz1234<lzxz1234@gmail.com>
     */
    private static class FIFOLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        
        private static final long serialVersionUID = -2528998181145341846L;
        
        public final int MAX_ENTRIES;
        
        public FIFOLinkedHashMap(int maxEntries) {
            this.MAX_ENTRIES = maxEntries;
        }
        
        protected boolean removeEldestEntry(Map.Entry<K,  V> eldest) {
            return size() > MAX_ENTRIES;
        }
        
    }

}
