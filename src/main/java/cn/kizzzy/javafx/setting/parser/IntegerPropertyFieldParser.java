package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;

public class IntegerPropertyFieldParser extends AbstractFieldParser<Integer, IntegerProperty> {
    
    public IntegerPropertyFieldParser() {
        super(int.class, IntegerExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return IntegerProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        TextField textField = new TextField();
        try {
            field.setAccessible(true);
            
            textField.textProperty().setValue(String.valueOf(getValue(field, target)));
            textField.textProperty().addListener((ob, oldValue, newValue) -> {
                try {
                    field.setAccessible(true);
                    setValue(field, target, Integer.parseInt(newValue));
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            return textField;
        } catch (IllegalAccessException e) {
            LogHelper.error(null, e);
            return null;
        } finally {
            field.setAccessible(false);
        }
    }
}
