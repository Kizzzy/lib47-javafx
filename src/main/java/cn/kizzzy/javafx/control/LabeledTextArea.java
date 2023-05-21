package cn.kizzzy.javafx.control;

import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

abstract class LabeledTextAreaView extends AnchorPane implements JavafxControl {
    
    public LabeledTextAreaView() {
        init();
    }
    
    @FXML
    protected Label title;
    
    public StringProperty titleProperty() {
        return title.textProperty();
    }
    
    public void setTitle(String title) {
        titleProperty().set(title);
    }
    
    public String getTitle() {
        return titleProperty().get();
    }
    
    // ----------------------------------------
    
    public ObjectProperty<TextAlignment> titleTextAlignmentProperty() {
        return title.textAlignmentProperty();
    }
    
    public final void setTextAlignment(TextAlignment value) {
        titleTextAlignmentProperty().setValue(value);
    }
    
    public final TextAlignment getTextAlignment() {
        return titleTextAlignmentProperty().get();
    }
    
    // ----------------------------------------
    
    public DoubleProperty titleMinWidthProperty() {
        return title.minWidthProperty();
    }
    
    public void setTitleMinWidth(double minWidth) {
        titleMinWidthProperty().set(minWidth);
    }
    
    public double getTitleMinWidth() {
        return titleMinWidthProperty().get();
    }
    
    // ----------------------------------------
    
    @FXML
    protected TextArea content;
    
    public StringProperty contentProperty() {
        return content.textProperty();
    }
    
    public void setContent(String content) {
        contentProperty().set(content);
    }
    
    public String getContent() {
        return contentProperty().get();
    }
}

@JavafxControlParameter(fxml = "/fxml/control/labeled_textarea.fxml")
public class LabeledTextArea extends LabeledTextAreaView {
    
}
