package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Password;
import cn.kizzzy.config.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.function.Function;

public abstract class StringFieldParserImpl<T> extends AbstractFieldParser<String, T> {
    
    public StringFieldParserImpl(Function<T, String> fromFunc) {
        super(String.class, fromFunc, "");
    }
    
    @Override
    public Node createNode(final FieldParserFactory factory, final Class<?> clazz, final Field field, final Object target) {
        String value = getValue(field, target);
        
        StackPane stackPane = new StackPane();
        stackPane.getStylesheets().add(getClass().getResource("/css/button.css").toExternalForm());
        AnchorPane.setTopAnchor(stackPane, 0.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setLeftAnchor(stackPane, 0.0);
        
        Password password = field.getAnnotation(Password.class);
        
        TextInputControl textField = password == null ? new TextField() : new PasswordField();
        textField.textProperty().setValue(value);
        textField.textProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, newValue);
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
        
        stackPane.getChildren().add(textField);
        
        Path path = field.getAnnotation(Path.class);
        if (path != null) {
            Button button = new Button("浏览");
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setOnAction(event -> {
                if (path.file()) {
                    FileChooser chooser = new FileChooser();
                    File file = path.save() ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                } else {
                    DirectoryChooser chooser = new DirectoryChooser();
                    File file = chooser.showDialog(null);
                    if (file != null) {
                        textField.setText(file.getAbsolutePath());
                    }
                }
            });
            
            stackPane.getChildren().add(button);
            
            StackPane.setAlignment(button, Pos.CENTER_RIGHT);
            StackPane.setMargin(button, new Insets(0, 8, 0, 0));
        }
        
        return stackPane;
    }
}
