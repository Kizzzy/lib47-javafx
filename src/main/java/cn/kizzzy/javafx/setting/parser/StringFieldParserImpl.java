package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Password;
import cn.kizzzy.config.Path;
import cn.kizzzy.helper.FileHelper;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        
        HBox hBox = new HBox();
        hBox.getStylesheets().add(getClass().getResource("/css/button.css").toExternalForm());
        hBox.setSpacing(8.0);
        
        StackPane stackPane = new StackPane();
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
        hBox.getChildren().add(stackPane);
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        
        Path path = field.getAnnotation(Path.class);
        if (path != null) {
            Button browser = new Button("");
            browser.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            browser.getStyleClass().add("folder");
            browser.setOnAction(event -> {
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
            
            stackPane.getChildren().add(browser);
            
            StackPane.setAlignment(browser, Pos.CENTER_RIGHT);
            StackPane.setMargin(browser, new Insets(0, 8, 0, 0));
            
            Button open = new Button("打开");
            open.setMinWidth(48);
            open.setOnAction(event -> FileHelper.openFile(textField.getText()));
            
            hBox.getChildren().add(open);
        }
        
        return hBox;
    }
}
