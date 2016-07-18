package com.wowfilm.wechatsdk.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @class Param
 * @author lzxz1234
 * @description 
 * @version v1.0
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    
  public String value() default "";

  public boolean required() default true;

  public String defaultValue() default "";
  
}
