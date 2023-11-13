package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.setting.parser.FieldParserFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

abstract class SettingListItemView extends AnchorPane implements JavafxControl {
    
    @FXML
    protected AnchorPane root;
    
    @FXML
    protected Button removeBtn;
}

@JavafxControlParameter(fxml = "/fxml/setting/setting_list_item_view.fxml")
public class SettingListItem extends SettingListItemView implements Initializable {
    
    private final SettingList settingList;
    private final Object obj;
    private final FieldParserFactory factory;
    
    public SettingListItem(SettingList settingList, Object obj, FieldParserFactory factory) {
        this.settingList = settingList;
        this.obj = obj;
        this.factory = factory;
        
        init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeBtn.setOnAction(this::onRemove);
        
        Node child = factory.createNode(obj.getClass(), null, obj);
        if (child != null) {
            root.getChildren().add(child);
        }
    }
    
    private void onRemove(ActionEvent actionEvent) {
        settingList.onRemove(this, obj);
    }
}
