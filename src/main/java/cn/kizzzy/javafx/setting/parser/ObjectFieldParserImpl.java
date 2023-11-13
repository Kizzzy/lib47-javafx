package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Ignore;
import cn.kizzzy.config.Name;
import cn.kizzzy.javafx.setting.SettingGroup;
import cn.kizzzy.javafx.setting.SettingItem;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

public abstract class ObjectFieldParserImpl<T> extends AbstractFieldParser<Object, T> {
    
    public ObjectFieldParserImpl(Function<T, Object> fromFunc) {
        super(Object.class, fromFunc, null);
    }
    
    @Override
    public Node createNode(final FieldParserFactory factory, Class<?> clazz, Field field, Object target) {
        return createNodeImpl(factory, clazz, field == null ? target : getValue(field, target));
    }
    
    private Node createNodeImpl(final FieldParserFactory factory, Class<?> clazz, Object target) {
        SettingGroup group = new SettingGroup();
        group.setLabel(clazz.getSimpleName());
        
        AnchorPane.setLeftAnchor(group, 0d);
        AnchorPane.setTopAnchor(group, 0d);
        AnchorPane.setRightAnchor(group, 0d);
        AnchorPane.setBottomAnchor(group, 0d);
        
        for (Field field : clazz.getDeclaredFields()) {
            Ignore ignore = field.getAnnotation(Ignore.class);
            if (ignore != null) {
                continue;
            }
            
            if (Modifier.isStatic(field.getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            
            Node node = factory.createNode(field.getType(), field, target);
            if (node != null) {
                Name name = field.getAnnotation(Name.class);
                
                SettingItem item = new SettingItem();
                item.setLabel(name != null ? name.alias() : field.getName());
                item.addChild(node);
                
                group.addChild(item);
            }
        }
        
        return group;
    }
}
