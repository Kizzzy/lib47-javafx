package cn.kizzzy.javafx.setting.parser;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;

public class BooleanPropertyFieldParser extends AbstractFieldParser<Boolean, BooleanProperty> {
    
    public BooleanPropertyFieldParser() {
        super(boolean.class, BooleanExpression::getValue, false);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return BooleanProperty.class.isAssignableFrom(clazz);
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
