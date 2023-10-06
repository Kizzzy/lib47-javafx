package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.lang.reflect.Field;

public class StringPropertyFieldParser extends AbstractFieldParser<String, StringProperty> {
    
    public StringPropertyFieldParser() {
        super(String.class, StringExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return StringProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        TextField textField = new TextField();
        try {
            field.setAccessible(true);
            
            textField.textProperty().setValue(getValue(field, target));
            textField.textProperty().addListener((ob, oldValue, newValue) -> {
                try {
                    field.setAccessible(true);
                    setValue(field, target, newValue);
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            
            textField.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
            textField.setOnDragDropped(event -> {
                if (event.getDragboard().hasFiles()) {
                    for (File file : event.getDragboard().getFiles()) {
                        if (file.isFile()) {
                            textField.setText(file.getAbsolutePath());
                        }
                    }
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
    
    @Override
    protected String getValue(Field field, Object target) throws IllegalAccessException {
        String val = super.getValue(field, target);
        return val == null ? "" : val;
    }
}
