package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.javafx.setting.ITargetShower;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.function.Function;

public class UndealFieldParser extends AbstractFieldParser<Object, Object> {
    
    private final ITargetShower shower;
    
    public UndealFieldParser(ITargetShower shower) {
        super(Object.class, Function.identity(), null);
        this.shower = shower;
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return true;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        Object value = getValue(field, target);
        
        // todo
        return null;
    }
}
