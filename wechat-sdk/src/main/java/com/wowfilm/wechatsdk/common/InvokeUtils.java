package com.wowfilm.wechatsdk.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class InvokeUtils {

    /** 
     * 从对象取值，会先查询字段对应的 get 方法或者  is 方法, 如果不存在则直接从对象中查找 
     * field 字段并取值，查不到时递归查找父类，均不存在时抛出异常
     * @param obj 待取值所在对象
     * @param field 待取字段
     * @return obj 中 field 字段值
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final Object getFieldValue(Object obj, String field) throws Exception {
        
        return getFieldValue(obj.getClass(), obj, field);
    }
    
    private static final Object getFieldValue(Class<?> clazz, Object obj, String field) throws Exception {
        
        Method method = MethodUtils.getGetter(clazz, field);
        if(method != null) {
            return method.invoke(obj);
        } else {
            return FieldUtils.traverseField(clazz, field).get(obj);
        }
    }
    
    /** 
     * 为对象设值，会先查询字段对应的 set 方法或者  is 方法, 如果不存在则直接从对象中查找 
     * field 字段，并设值 value ,查不到时递归查找父类，均不存在时抛出异常
     * @param obj 待填值对象
     * @param field 待填字段
     * @return value 待填内容
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static final void setFieldValue(Object obj, String field, Object value) 
    throws Exception {
        
        Class<?> clazz = obj.getClass();
        setFieldValue(clazz, obj, field, value);
    }
    
    private static final void setFieldValue(Class<?> clazz, Object obj, String field, 
        Object value) throws Exception {
        
        Method method = MethodUtils.getSetter(clazz, field);
        if(method != null) {
            method.invoke(obj, Castors.cast(method.getParameterTypes()[0], value));
        } else {
            FieldUtils.traverseField(clazz, field).set(obj, value);
        }
    }
    
    /** 
     * 使用参数 param 调用 clazz 中的以 methodName 命名的静态方法 
     * @param clazz
     * @param methodName
     * @param param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final <T> T invokeStatic(Class<?> clazz, String methodName, Object... param) throws Exception {
        
        Method method = MethodUtils.getMethod(clazz, methodName);
        if(!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException("非静态方法！！");
        }
        return (T) method.invoke(null, param);
    }
    
    /**
     * 使用参数 param 调用 object 的 methodName 对应的方法 
     * @param object
     * @param methodName
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final <T> T invoke(Object object, String methodName, Object... params) throws Exception {
        
        Class<?>[] classes = new Class<?>[params.length];
        Method method = MethodUtils.getMethod(object.getClass(), methodName, classes);
        return (T) method.invoke(object, params);
    }
    
}