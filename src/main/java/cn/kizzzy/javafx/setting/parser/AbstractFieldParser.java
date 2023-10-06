package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.StringHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public abstract class AbstractFieldParser<PrimitiveType, WrapperType> implements IFieldParser {
    
    private final Class<?> primitiveType;
    
    private final Function<WrapperType, PrimitiveType> fromFunc;
    
    public AbstractFieldParser(Class<?> primitiveType, Function<WrapperType, PrimitiveType> fromFunc) {
        this.primitiveType = primitiveType;
        this.fromFunc = fromFunc;
    }
    
    protected PrimitiveType getValue(Field field, Object target) throws IllegalAccessException {
        try {
            Method getter = target.getClass().getDeclaredMethod("get" + StringHelper.firstUpper(field.getName()));
            return (PrimitiveType) getter.invoke(target);
        } catch (Exception e) {
            return fromFunc.apply((WrapperType) field.get(target));
        }
    }
    
    protected void setValue(Field field, Object target, PrimitiveType newValue) throws IllegalAccessException {
        try {
            Method setter = target.getClass().getDeclaredMethod("set" + StringHelper.firstUpper(field.getName()), primitiveType);
            setter.invoke(target, newValue);
        } catch (Exception e) {
            field.set(target, newValue);
        }
    }
}
