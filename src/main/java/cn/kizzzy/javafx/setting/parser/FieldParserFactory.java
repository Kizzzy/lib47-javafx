package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;

public interface FieldParserFactory {
    
    Node createNode(final Class<?> clazz, final Field field, final Object target);
}
