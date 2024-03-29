package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;

public interface FieldParser {
    
    boolean accept(final Class<?> clazz);
    
    default Node createNode(final FieldParserFactory factory, final Class<?> clazz, final Field field) {
        return createNode(factory, clazz, field, null);
    }
    
    Node createNode(final FieldParserFactory factory, final Class<?> clazz, final Field field, final Object target);
}
