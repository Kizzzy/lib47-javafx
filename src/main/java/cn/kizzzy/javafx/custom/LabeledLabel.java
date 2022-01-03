package cn.kizzzy.javafx.custom;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

@CustomControlParamter(fxml = "/fxml/lebeled_label.fxml")
public class LabeledLabel extends AnchorPane implements ICustomControl {
    
    public LabeledLabel() {
        super();
        init();
    }
    
    @FXML
    private Label title;
    
    public StringProperty titleProperty() {
        return title.textProperty();
    }
    
    public void setTitle(String title) {
        titleProperty().set(title);
    }
    
    public String getTitle() {
        return titleProperty().get();
    }
    
    public ObjectProperty<TextAlignment> titleTextAlignmentProperty() {
        return title.textAlignmentProperty();
    }
    
    public final void setTextAlignment(TextAlignment value) {
        titleTextAlignmentProperty().setValue(value);
    }
    
    public final TextAlignment getTextAlignment() {
        return titleTextAlignmentProperty().get();
    }
    
    @FXML
    private Label content;
    
    public StringProperty contentProperty() {
        return content.textProperty();
    }
    
    public void setContent(String title) {
        contentProperty().set(title);
    }
    
    public String getContent() {
        return contentProperty().get();
    }
    
    public DoubleProperty titleMinWidthProperty() {
        return title.minWidthProperty();
    }
    
    public void setTitleMinWidth(double minWidth) {
        titleMinWidthProperty().set(minWidth);
    }
    
    public double getTitleMinWidth() {
        return titleMinWidthProperty().get();
    }
}
