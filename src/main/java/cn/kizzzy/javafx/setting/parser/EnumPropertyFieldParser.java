package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.ClassHelper;
import cn.kizzzy.helper.LogHelper;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;

public class EnumPropertyFieldParser extends AbstractFieldParser<Enum<?>, ObjectProperty<?>> {
    
    public EnumPropertyFieldParser() {
        super(Enum.class, property -> (Enum<?>) property.getValue());
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return ObjectProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        try {
            field.setAccessible(true);
            Object value = getValue(field, target);
            
            Class<?> enumType = (Class<?>) ClassHelper.getGenericClass(clazz);
            
            ComboBox<Object> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(enumType.getEnumConstants());
            comboBox.valueProperty().setValue(value);
            comboBox.valueProperty().addListener((ob, oldValue, newValue) -> {
                try {
                    field.setAccessible(true);
                    setValue(field, target, (Enum<?>) newValue);
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            return comboBox;
        } catch (IllegalAccessException e) {
            LogHelper.error(null, e);
            return null;
        } finally {
            field.setAccessible(false);
        }
    }
}
