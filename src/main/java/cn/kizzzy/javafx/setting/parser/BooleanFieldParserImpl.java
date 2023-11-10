package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;
import java.util.function.Function;

public abstract class BooleanFieldParserImpl<T> extends AbstractFieldParser<Boolean, T> {
    
    public BooleanFieldParserImpl(Function<T, Boolean> fromFunc) {
        super(boolean.class, fromFunc, false);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        boolean value = getValue(field, target);
        
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(value);
        checkBox.selectedProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, newValue);
        });
        return checkBox;
    }
}
