package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;

public class NumberFieldParser implements IFieldParser {
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("int");
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        TextField textField = new TextField();
        try {
            field.setAccessible(true);
            
            textField.textProperty().setValue(String.valueOf(field.get(target)));
            textField.textProperty().addListener((ob, ooo, nnn) -> {
                try {
                    field.setAccessible(true);
                    field.set(target, Integer.valueOf(nnn));
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
