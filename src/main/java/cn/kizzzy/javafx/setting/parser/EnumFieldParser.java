package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;

public class EnumFieldParser implements IFieldParser {
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.isEnum();
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(clazz.getEnumConstants());
        
        try {
            field.setAccessible(true);
            comboBox.valueProperty().setValue(field.get(target));
            comboBox.valueProperty().addListener((ob, ooo, nnn) -> {
                try {
                    field.setAccessible(true);
                    field.set(target, nnn);
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
