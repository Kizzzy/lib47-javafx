package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@JavafxControlParameter(fxml = "/fxml/setting/setting_group_view.fxml")
public class SettingGroup extends AnchorPane implements JavafxControl {
    
    @FXML
    protected VBox root;
    
    @FXML
    protected Label label;
    
    public SettingGroup() {
        super();
        init();
    }
    
    public VBox getRoot() {
        return root;
    }
    
    public void addChild(Node item) {
        root.getChildren().add(item);
    }
    
    public StringProperty labelProperty() {
        return label.textProperty();
    }
    
    public String getLabel() {
        return labelProperty().getValue();
    }
    
    public void setLabel(String label) {
        labelProperty().setValue(label);
    }
}
