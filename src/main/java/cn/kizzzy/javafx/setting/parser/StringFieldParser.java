package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.FileType;
import cn.kizzzy.config.Folder;
import cn.kizzzy.helper.LogHelper;
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

public class StringFieldParser implements IFieldParser {
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == String.class;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        try {
            field.setAccessible(true);
            String value = (String) field.get(target);
            
            HBox hBox = new HBox();
            hBox.setSpacing(8);
            
            TextField textField = new TextField();
            textField.textProperty().setValue(value);
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
            
            hBox.getChildren().add(textField);
            HBox.setHgrow(textField, Priority.ALWAYS);
            
            FileType fileType = field.getAnnotation(FileType.class);
            if (fileType != null) {
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
}
