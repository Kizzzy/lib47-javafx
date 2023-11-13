package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Generic;
import cn.kizzzy.javafx.setting.SettingList;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

public class ListFieldParser extends AbstractFieldParser<List<Object>, List<Object>> {
    
    public ListFieldParser() {
        super(List.class, Function.identity(), null);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == List.class;
    }
    
    @Override
    public Node createNode(final FieldParserFactory factory, final Class<?> clazz, final Field field, final Object target) {
        Generic generic = field.getAnnotation(Generic.class);
        if (generic == null) {
            return null;
        }
        
        List<Object> value = getValue(field, target);
        return new SettingList(value, generic.clazz(), factory);
    }
}
