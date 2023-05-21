package cn.kizzzy.javafx.control;

import cn.kizzzy.helper.StringHelper;
import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

abstract class LabeledChooserView extends AnchorPane implements JavafxControl {
    
    public LabeledChooserView() {
        init();
    }
    
    @FXML
    protected LabeledTextField ltf;
    
    public StringProperty titleProperty() {
        return ltf.titleProperty();
    }
    
    public void setTitle(String title) {
        titleProperty().set(title);
    }
    
    public String getTitle() {
        return titleProperty().get();
    }
    
    // ----------------------------------------
    
    public StringProperty contentProperty() {
        return ltf.contentProperty();
    }
    
    public void setContent(String content) {
        contentProperty().set(content);
    }
    
    public String getContent() {
        return contentProperty().get();
    }
    
    // ----------------------------------------
    
    @FXML
    protected Button button;
    
    protected BooleanProperty showFolder;
    
    public BooleanProperty showFolderProperty() {
        if (showFolder == null) {
            showFolder = new SimpleBooleanProperty();
        }
        return showFolder;
    }
    
    public boolean isShowFolder() {
        return showFolderProperty().get();
    }
    
    public void setShowFolder(boolean showFolder) {
        showFolderProperty().set(showFolder);
    }
}

@JavafxControlParameter(fxml = "/fxml/control/labeled_chooser.fxml")
public class LabeledChooser extends LabeledChooserView implements Initializable {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setOnAction(this::show);
    }
    
    public File show(ActionEvent event) {
        Window window = getScene().getWindow();
        File file = isShowFolder() ?
            showOpenChooser(window, getContent()) :
            showSaveChooser(window, getContent());
        if (file != null) {
            setContent(file.getAbsolutePath());
        }
        return file;
    }
    
    private File showOpenChooser(Window window, String initFolder) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择文件");
        
        if (StringHelper.isNotNullAndEmpty(initFolder)) {
            File lastFolder = new File(initFolder);
            if (lastFolder.exists()) {
                chooser.setInitialDirectory(lastFolder);
            }
        }
        return chooser.showOpenDialog(window);
    }
    
    private File showSaveChooser(Window window, String initFolder) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择目录");
        
        if (StringHelper.isNotNullAndEmpty(initFolder)) {
            File lastFolder = new File(initFolder);
            if (lastFolder.exists()) {
                chooser.setInitialDirectory(lastFolder);
            }
        }
        return chooser.showDialog(window);
    }
}
