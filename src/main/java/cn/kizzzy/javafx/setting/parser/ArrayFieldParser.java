package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.function.Function;

public class ArrayFieldParser extends AbstractFieldParser<Object[], Object[]> {
    
    public ArrayFieldParser() {
        super(Object[].class, Function.identity(), null);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.isArray();
    }
    
    @Override
    public Node createNode(FieldParserFactory factory, final Class<?> clazz, final Field field, final Object target) {
        // todo
        return null;
    }
}
