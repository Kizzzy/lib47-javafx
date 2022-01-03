package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;

public interface IFieldParser {
    
    boolean accept(final Class<?> clazz);
    
    default Node createNode(final Class<?> clazz, final Field field) {
        return createNode(clazz, field, null);
    }
    
    Node createNode(final Class<?> clazz, final Field field, final Object target);
}
