package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.lang.reflect.Field;

public class BooleanFieldParser implements IFieldParser {
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("boolean");
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        CheckBox checkBox = new CheckBox();
        try {
            field.setAccessible(true);
            checkBox.selectedProperty().setValue((boolean) field.get(target));
            checkBox.selectedProperty().addListener((ob, ooo, nnn) -> {
                try {
                    field.setAccessible(true);
                    field.set(target, nnn);
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            return checkBox;
        } catch (IllegalAccessException e) {
            LogHelper.error(null, e);
            return null;
        } finally {
            field.setAccessible(false);
        }
    }
}
