package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.ReflectHelper;
import cn.kizzzy.helper.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public abstract class AbstractFieldParser<PrimitiveType, WrapperType> implements FieldParser {
    
    protected static final Logger logger = LoggerFactory.getLogger(AbstractFieldParser.class);
    
    private final Class<?> primitiveType;
    
    private final Function<WrapperType, PrimitiveType> fromFunc;
    
    private final PrimitiveType defaultValue;
    
    public AbstractFieldParser(Class<?> primitiveType, Function<WrapperType, PrimitiveType> fromFunc, PrimitiveType defaultValue) {
        this.primitiveType = primitiveType;
        this.fromFunc = fromFunc;
        this.defaultValue = defaultValue;
    }
    
    protected PrimitiveType getValue(Field field, Object target) {
        try {
            String methodName = "get" + StringHelper.firstUpper(field.getName());
            Method getter = target.getClass().getDeclaredMethod(methodName);
            return (PrimitiveType) getter.invoke(target);
        } catch (Exception e1) {
            try {
                String methodName = "is" + StringHelper.firstUpper(field.getName());
                Method getter = target.getClass().getDeclaredMethod(methodName);
                return (PrimitiveType) getter.invoke(target);
            } catch (Exception e2) {
                try {
                    return fromFunc.apply((WrapperType) ReflectHelper.getValue(field, target));
                } catch (Exception e3) {
                    logger.error("getValue error", e3);
                    return defaultValue;
                }
            }
        }
    }
    
    protected void setValue(Field field, Object target, PrimitiveType newValue) {
        try {
            String methodName = "set" + StringHelper.firstUpper(field.getName());
            Method setter = target.getClass().getDeclaredMethod(methodName, primitiveType);
            setter.invoke(target, newValue);
        } catch (Exception e) {
            try {
                ReflectHelper.setValue(field, target, newValue);
            } catch (Exception e2) {
                logger.error("setValue error", e2);
            }
        }
    }
}
