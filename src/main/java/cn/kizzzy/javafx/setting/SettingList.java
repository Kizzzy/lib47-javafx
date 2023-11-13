package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.setting.parser.FieldParserFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

abstract class SettingListView extends VBox implements JavafxControl {
    
    @FXML
    protected VBox root;
    
    @FXML
    protected Button addBtn;
}

@JavafxControlParameter(fxml = "/fxml/setting/setting_list_view.fxml")
public class SettingList extends SettingListView implements Initializable {
    
    private static final Logger logger = LoggerFactory.getLogger(SettingList.class);
    
    private final List<Object> list;
    private final Class<?> clazz;
    private final FieldParserFactory factory;
    
    public SettingList(List<Object> list, Class<?> clazz, FieldParserFactory factory) {
        this.list = list;
        this.clazz = clazz;
        this.factory = factory;
        
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
                logger.error("new object error", e);
            }
        }
    }
    
    public void onRemove(SettingListItem item, Object obj) {
        list.remove(obj);
        
        root.getChildren().remove(item);
    }
    
    private void addItem(Object obj) {
        SettingListItem item = new SettingListItem(this, obj, factory);
        root.getChildren().add(item);
    }
}
