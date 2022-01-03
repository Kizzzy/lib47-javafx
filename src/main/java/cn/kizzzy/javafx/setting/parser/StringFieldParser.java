package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.lang.reflect.Field;

public class StringFieldParser implements IFieldParser {
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == String.class;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        TextField textField = new TextField();
        try {
            field.setAccessible(true);
            
            Object temp = field.get(target);
            
            textField.textProperty().setValue(temp == null ? "" : (String) temp);
            textField.textProperty().addListener((ob, ooo, nnn) -> {
                try {
                    field.setAccessible(true);
                    field.set(target, nnn);
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
}
