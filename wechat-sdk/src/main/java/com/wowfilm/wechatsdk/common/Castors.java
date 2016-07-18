package com.wowfilm.wechatsdk.common;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class Castors {

    private static MultiKeyMap map = new MultiKeyMap();
    
    static {
        //初始化转换器
        map.put(String.class, byte.class, new StringToByte());
        map.put(String.class, Byte.class, new StringToByte());
        map.put(String.class, int.class, new StringToInt());
        map.put(String.class, Integer.class, new StringToInt());
        map.put(String.class, long.class, new StringToLong());
        map.put(String.class, Long.class, new StringToLong());
        map.put(String.class, float.class, new StringToFloat());
        map.put(String.class, Float.class, new StringToFloat());
        map.put(String.class, double.class, new StringToDouble());
        map.put(String.class, Double.class, new StringToDouble());
        map.put(String.class, boolean.class, new StringToBoolean());
        map.put(String.class, Boolean.class, new StringToBoolean());
    }
    
    /**
     * @param fromClass
     * @param toClass
     * @param fromObj
     * @return
     */
    private static <F, T> T cast(Class<F> fromClass, Class<T> toClass, F fromObj) {
        
        if(fromClass == null || toClass == null) throw new NullPointerException("参数禁止为空");
        if(toClass.isAssignableFrom(fromClass)) return (T) fromObj;
        if(fromObj == null) return null;
        
        if(toClass.isArray()) {
            Class<?> componentType = toClass.getComponentType();
            String[] splits = fromObj.toString().split("[, ]+");
            T result = (T) Array.newInstance(componentType, splits.length);
            for(int i = 0; i < splits.length; i ++)
                Array.set(result, i, Castors.cast(componentType, splits[i]));
            return result;
        } else {
            Castor<F, T> castor = map.get(fromClass, toClass);
            if(castor == null)
                throw new IllegalArgumentException("不允许的类型转换 " + fromClass +"-->" + toClass);
            return castor.cast(fromObj);
        }
    }
    
    /**
     * 转换类型，将 fromObj 转换为 toClass 类型
     * @param toClass
     * @param fromObj
     * @return
     */
    public static <F, T> T cast(Class<T> toClass, F fromObj) {
        
        if(fromObj == null) return null;
        Class<F> fromClass = (Class<F>) fromObj.getClass();
        return cast(fromClass, toClass, fromObj);
    }
    
    private static class StringToByte implements Castor<String, Byte> {
        @Override
        public Byte cast(String from) {
            return new Byte(from);
        }
    }
    private static class StringToInt implements Castor<String, Integer> {
        @Override
        public Integer cast(String from) {
            return new Integer(from);
        }
    }
    private static class StringToLong implements Castor<String, Long> {
        @Override
        public Long cast(String from) {
            return new Long(from);
        }
    }
    private static class StringToFloat implements Castor<String, Float> {
        @Override
        public Float cast(String from) {
            return new Float(from);
        }
    }
    private static class StringToDouble implements Castor<String, Double> {
        @Override
        public Double cast(String from) {
            return new Double(from);
        }
    }
    private static class StringToBoolean implements Castor<String, Boolean> {
        @Override
        public Boolean cast(String from) {
            return Boolean.valueOf(from);
        }
    }
    
    public static interface Castor<F, T> {
        public T cast(F from);
    }
    
}
