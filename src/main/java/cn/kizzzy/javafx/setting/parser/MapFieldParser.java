package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

public class MapFieldParser extends AbstractFieldParser<Map<?, ?>, Map<?, ?>> {
    
    public MapFieldParser() {
        super(Map.class, Function.identity(), null);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        // todo
        return false;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        // todo
        return null;
    }
}
