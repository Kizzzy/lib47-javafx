package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    private final TransferArgs args;
    
    public SettingListItem(SettingList settingList, Object obj, TransferArgs args) {
        this.settingList = settingList;
        this.obj = obj;
        this.args = args;
        
        init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeBtn.setOnAction(this::onRemove);
        
        args.shower.show(root, obj, args.configs);
    }
    
    private void onRemove(ActionEvent actionEvent) {
        settingList.onRemove(this, obj);
    }
}
