package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;

public class BooleanFieldParser extends AbstractFieldParser<Boolean, Boolean> {
    
    public BooleanFieldParser() {
        super(boolean.class, Boolean::booleanValue, false);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("boolean");
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
