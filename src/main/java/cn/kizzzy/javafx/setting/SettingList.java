package cn.kizzzy.javafx.setting;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@CustomControlParamter(fxml = "/fxml/custom/setting/setting_list_view.fxml")
public class SettingList extends VBox implements ICustomControl, Initializable {
    @FXML
    private VBox root;
    
    @FXML
    private Button addBtn;
    
    private final TransferArgs args;
    
    private final List<Object> list;
    
    private final Class<?> clazz;
    
    public SettingList(TransferArgs args, List<Object> list, Class<?> clazz) {
        super();
        this.args = args;
        this.list = list;
        this.clazz = clazz;
        
        init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addBtn.setOnAction(this::onAdd);
        
        for (Object obj : list) {
            addItem(obj);
        }
    }
    
    private void onAdd(ActionEvent actionEvent) {
        if (clazz != null) {
            try {
                Object obj = clazz.newInstance();
                list.add(obj);
                
                addItem(obj);
            } catch (Exception e) {
                LogHelper.error(null, e);
            }
        }
    }
    
    public void onRemove(SettingListItem item, Object obj) {
        list.remove(obj);
        
        root.getChildren().remove(item);
    }
    
    private void addItem(Object obj) {
        SettingListItem item = new SettingListItem(this, obj, args);
        root.getChildren().add(item);
    }
}
