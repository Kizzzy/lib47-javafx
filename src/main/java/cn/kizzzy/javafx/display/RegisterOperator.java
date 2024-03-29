package cn.kizzzy.javafx.display;

import cn.kizzzy.base.AttributeWithInstance;
import cn.kizzzy.clazz.ClassFilter;
import cn.kizzzy.clazz.ClassFinderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RegisterOperator implements Iterable<AttributeWithInstance<RegisterAttribute, Register>> {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterOperator.class);
    
    private final String namespace;
    
    private final List<AttributeWithInstance<RegisterAttribute, Register>> registers
        = new LinkedList<>();
    
    public RegisterOperator(String namespace) {
        this.namespace = namespace;
    }
    
    public void load() {
        try {
            List<Class<?>> list = ClassFinderHelper.find(new ClassFilter() {
                @Override
                public String packageRoot() {
                    return namespace;
                }
                
                @Override
                public boolean isRecursive() {
                    return true;
                }
                
                @Override
                public boolean accept(Class<?> clazz) {
                    return clazz.isAnnotationPresent(RegisterAttribute.class);
                }
            });
            
            for (Class<?> clazz : list) {
                RegisterAttribute attr = clazz.getAnnotation(RegisterAttribute.class);
                registers.add(new AttributeWithInstance<>(attr, (Register) clazz.newInstance()));
            }
            
        } catch (Exception e) {
            logger.error("register error", e);
        }
    }
    
    @Override
    public Iterator<AttributeWithInstance<RegisterAttribute, Register>> iterator() {
        return registers.iterator();
    }
}
