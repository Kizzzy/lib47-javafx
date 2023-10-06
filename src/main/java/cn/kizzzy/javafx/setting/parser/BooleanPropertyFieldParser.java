package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;

public class BooleanPropertyFieldParser extends AbstractFieldParser<Boolean, BooleanProperty> {
    
    public BooleanPropertyFieldParser() {
        super(boolean.class, BooleanExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return BooleanProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        CheckBox checkBox = new CheckBox();
        try {
            field.setAccessible(true);
            checkBox.selectedProperty().setValue(getValue(field, target));
            checkBox.selectedProperty().addListener((ob, oldValue, newValue) -> {
                try {
                    field.setAccessible(true);
                    setValue(field, target, newValue);
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            return checkBox;
        } catch (Exception e) {
            LogHelper.error(null, e);
            return null;
        } finally {
            field.setAccessible(false);
        }
    }
}
