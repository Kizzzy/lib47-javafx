package cn.kizzzy.javafx.setting.parser;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;

public class EnumPropertyFieldParser extends AbstractFieldParser<Enum<?>, ObjectProperty<?>> {
    
    public EnumPropertyFieldParser() {
        super(Enum.class, property -> (Enum<?>) property.getValue(), null);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return ObjectProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        Object value = getValue(field, target);
        
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(clazz.getEnumConstants());
        comboBox.valueProperty().setValue(value);
        comboBox.valueProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, (Enum<?>) newValue);
        });
        return comboBox;
    }
}
