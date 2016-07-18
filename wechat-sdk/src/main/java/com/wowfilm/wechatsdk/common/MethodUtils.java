package com.wowfilm.wechatsdk.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodUtils {

    /**
     * 递归查找构造函数
     * @param target
     * @param args
     * @return
     * @throws  
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructor(Class<?> target, Class<?>... args) throws NoSuchMethodException {
        
        Class<?>[] tmp = Arrays.copyOf(args, args.length);
        for(int i = 0; i < args.length; i ++) {
            while(true) {
                try {
                    return (Constructor<T>) target.getConstructor(tmp);
                } catch (NoSuchMethodException e) {
                    if(!tmp[i].equals(Object.class))
                        tmp[i] = tmp[i].getSuperclass();
                    else 
                        break;
                }
            }
            Class<?>[] interfaces = args[i].getInterfaces();
            for(int j = 0; j < interfaces.length; j ++) {
                tmp[i] = interfaces[j];
                try {
                    return (Constructor<T>) target.getConstructor(tmp);
                } catch (NoSuchMethodException e) {
                    
                }
            }
            tmp[i] = args[i];
        }
        return (Constructor<T>) target.getConstructor();
    }
    
    /**
     * 方法重载时可能返回任意 name 命名的方法对象
     * @param clazz
     * @param name
     * @return
     */
    public static Method getMethod(Class<?> clazz, String name) {
        
        List<Method> list = getMethods(clazz, name);
        if(list != null) 
            if(list.size() > 0) return list.get(0);
        return null;
    }

    /**
     * 按入参取唯一方法对象
     * @param clazz
     * @param name
     * @param params
     * @return
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... params) {
        
        List<Method> list = getMethods(clazz, name);
        if(list != null) {
            for(Method method : list)
                if(Arrays.equals(method.getParameterTypes(), params))
                    return method;
        }
        return null;
    }
    
    /**
     * 查询类中声明或者父类声明的方法，Accessible 已设置为True
     * @param clazz
     * @param name
     * @return
     */
    public static List<Method> getMethods(final Class<?> clazz, final String name) {
        
        return CacheUtils.getValue(MethodUtils.class, clazz.toString(), new CacheUtils.Provider<String, Map<String, List<Method>>>() {
            @Override
            public Map<String, List<Method>> get(String key) {
                
                if(clazz == null || name == null) throw new IllegalArgumentException("参数不能为空。");
                Map<String, List<Method>> map = new HashMap<String, List<Method>>();
                Method[] methods = clazz.getMethods();
                for(Method method : methods) {
                    method.setAccessible(true);
                    if(!map.containsKey(method.getName())) 
                        map.put(method.getName(), new ArrayList<Method>());
                    map.get(method.getName()).add(method);
                }
                return map;
            }
            @Override
            public int maxSize() {
                
                return 100;
            }
        }).get(name);
    }
    
    /**
     * 查询 set 方法，先查询 set 开头方法，如果不存在，查询 is 开头方法，否则返回 null
     * @param clazz
     * @param name
     * @return
     * @throws Exception
     */
    public static Method getSetter(Class<?> clazz, String name) {
        
        if(clazz == null || name == null) throw new IllegalArgumentException("参数不能为空。");
        String setName = "set" + name.substring(0, 1).toUpperCase() + 
                (name.length() > 1 ? name.substring(1, name.length()) : "");
        Method result = getMethod(clazz, setName);
        return result == null ? getIs(clazz, name) : result;
    }
    
    /**
     * 查询 get 方法，先查询 set 开头方法，如果不存在，查询 is 开头方法，否则返回 null
     * @param clazz
     * @param name
     * @return
     * @throws Exception
     */
    public static Method getGetter(Class<?> clazz, String name) {
        
        if(clazz == null || name == null) throw new IllegalArgumentException("参数不能为空。");
        String getName = "get" + name.substring(0, 1).toUpperCase() + 
                (name.length() > 1 ? name.substring(1, name.length()) : "");
        Method result = getMethod(clazz, getName);
        return result == null ? getIs(clazz, name) : result;
    }
    
    /**
     * 查询  is 方法
     * @param clazz
     * @param name
     * @return
     * @throws Exception
     */
    public static Method getIs(Class<?> clazz, String name) {
        
        if(clazz == null || name == null) throw new IllegalArgumentException("参数不能为空。");
        String isName = "is" + name.substring(0, 1).toUpperCase() + 
                (name.length() > 1 ? name.substring(1, name.length()) : "");
        return getMethod(clazz, isName);
    }
}
