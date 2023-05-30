package cn.kizzzy.javafx.menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MenuParameter {
    
    int priority() default 0;
    
    String path();
    
    String shortcut() default "";
}
