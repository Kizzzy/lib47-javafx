package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.JavafxView;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

abstract class SettingGroupView extends JavafxView {
    
    @FXML
    protected VBox root;
    
    public VBox getRoot() {
        return root;
    }
    
    // ----------------------------------------
    
    @FXML
    protected Label label;
    
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

@JavafxControlParameter(fxml = "/fxml/setting/setting_group_view.fxml")
public class SettingGroup extends SettingGroupView {
    
    public void addChild(Node item) {
        root.getChildren().add(item);
    }
}
