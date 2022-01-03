package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

@CustomControlParamter(fxml = "/fxml/custom/setting/setting_item_view.fxml")
public class SettingItem extends AnchorPane implements ICustomControl {
    
    @FXML
    protected AnchorPane root;
    
    @FXML
    protected Label label;
    
    public SettingItem() {
        super();
        init();
    }
    
    public void addChild(Node item) {
        root.getChildren().add(item);
        
        AnchorPane.setLeftAnchor(item, 0d);
        AnchorPane.setTopAnchor(item, 0d);
        AnchorPane.setRightAnchor(item, 0d);
        AnchorPane.setBottomAnchor(item, 0d);
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
