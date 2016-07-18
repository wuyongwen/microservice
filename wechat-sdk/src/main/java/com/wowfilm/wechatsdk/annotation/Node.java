package com.wowfilm.wechatsdk.annotation;

import com.wowfilm.wechatsdk.listener.Service;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wen
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Node {

    String value();
    
    Class<? extends Service>[] parents();
    
}
