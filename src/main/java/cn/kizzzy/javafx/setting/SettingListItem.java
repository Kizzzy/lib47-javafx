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

@JavafxControlParameter(fxml = "/fxml/control/setting/setting_list_item_view.fxml")
public class SettingListItem extends AnchorPane implements JavafxControl, Initializable {
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private Button removeBtn;
    
    private final SettingList settingList;
    
    private final Object obj;
    
    private final TransferArgs args;
    
    public SettingListItem(SettingList settingList, Object obj, TransferArgs args) {
        super();
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
