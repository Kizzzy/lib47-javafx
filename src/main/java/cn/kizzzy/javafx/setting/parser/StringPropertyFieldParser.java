package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.FileType;
import cn.kizzzy.config.Folder;
import cn.kizzzy.helper.LogHelper;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

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
        try {
            field.setAccessible(true);
            String value = getValue(field, target);
            
            HBox hBox = new HBox();
            hBox.setSpacing(8);
            
            TextField textField = new TextField();
            textField.textProperty().setValue(value);
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
            
            hBox.getChildren().add(textField);
            HBox.setHgrow(textField, Priority.ALWAYS);
            
            FileType file_ = field.getAnnotation(FileType.class);
            if (file_ != null) {
                Button button = new Button("浏览");
                button.setOnAction(event -> {
                    FileChooser chooser = new FileChooser();
                    File file = chooser.showOpenDialog(null);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                });
                
                hBox.getChildren().add(button);
            }
            
            Folder folder = field.getAnnotation(Folder.class);
            if (folder != null) {
                Button button = new Button("浏览");
                button.setOnAction(event -> {
                    DirectoryChooser chooser = new DirectoryChooser();
                    File file = chooser.showDialog(null);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                });
                
                hBox.getChildren().add(button);
            }
            
            return hBox;
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
