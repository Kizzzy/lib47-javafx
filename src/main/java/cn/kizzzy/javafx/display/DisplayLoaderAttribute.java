package cn.kizzzy.javafx.display;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayLoaderAttribute {
    
    String[] suffix() default {};
    
    Magic[] magic() default {};
    
    int priority() default 0;
}
