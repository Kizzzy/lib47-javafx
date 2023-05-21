package cn.kizzzy.javafx.setting;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.javafx.JavafxControl;
import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.Stageable;
import cn.kizzzy.javafx.setting.parser.BooleanFieldParser;
import cn.kizzzy.javafx.setting.parser.EnumFieldParser;
import cn.kizzzy.javafx.setting.parser.IFieldParser;
import cn.kizzzy.javafx.setting.parser.ListFieldParser;
import cn.kizzzy.javafx.setting.parser.NumberFieldParser;
import cn.kizzzy.javafx.setting.parser.StringFieldParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

abstract class SettingDialogView extends AnchorPane implements JavafxControl {
    
    @FXML
    protected VBox root;
    
    @FXML
    protected Button btn_cancel;
    
    @FXML
    protected Button btn_save;
    
    @FXML
    protected Button btn_save_exit;
    
    public SettingDialogView() {
        init();
    }
}

@JavafxControlParameter(fxml = "/fxml/setting/setting_dialog_view.fxml")
public class SettingDialog extends SettingDialogView implements Initializable, Stageable<SettingDialog.Args> {
    
    public static class Args {
        public Object target;
        public SettingConfigs configs;
    }
    
    private Stage stage;
    
    private IFieldParser[] parsers;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_cancel.setOnAction(this::OnExit);
        btn_save.setOnAction(this::OnExit);
        btn_save_exit.setOnAction(this::OnExit);
    }
    
    private void OnExit(ActionEvent event) {
        if (stage != null) {
            stage.hide();
        }
    }
    
    public void show(Stage stage, Args args) {
        this.stage = stage;
        try {
            root.getChildren().clear();
            
            TransferArgs transferArgs = new TransferArgs();
            transferArgs.shower = this::show;
            transferArgs.configs = args.configs;
            
            parsers = new IFieldParser[]{
                new EnumFieldParser(),
                new BooleanFieldParser(),
                new StringFieldParser(),
                new NumberFieldParser(),
                new ListFieldParser(transferArgs)
            };
            
            show(root, args.target, args.configs);
        } catch (Exception e) {
            LogHelper.error(null, e);
        }
    }
    
    private void show(Pane root, Object target, SettingConfigs configs) {
        SettingGroup childHolder = null;
        
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            SettingConfig config = null;
            if (configs != null) {
                config = configs.selectById(target.getClass(), field.getName());
                if (config != null && config.ignore) {
                    return;
                }
            }
            
            if (childHolder == null) {
                childHolder = new SettingGroup();
                childHolder.setLabel(target.getClass().getSimpleName());
                
                root.getChildren().add(childHolder);
                
                AnchorPane.setLeftAnchor(childHolder, 0d);
                AnchorPane.setTopAnchor(childHolder, 0d);
                AnchorPane.setRightAnchor(childHolder, 0d);
                AnchorPane.setBottomAnchor(childHolder, 0d);
            }
            
            Class<?> fieldType = field.getType();
            
            boolean found = false;
            for (IFieldParser parser : parsers) {
                if (parser.accept(fieldType)) {
                    Node node = parser.createNode(fieldType, field, target);
                    if (node != null) {
                        found = true;
                        
                        SettingItem item = new SettingItem();
                        item.setLabel(config == null || config.alias == null ? field.getName() : config.alias);
                        item.addChild(node);
                        
                        childHolder.addChild(item);
                        break;
                    }
                }
            }
            if (!found) {
                try {
                    field.setAccessible(true);
                    Object temp = field.get(target);
                    if (temp == null) {
                        temp = fieldType.newInstance();
                        field.set(target, temp);
                    }
                    show(childHolder.getRoot(), temp, configs);
                } catch (Exception e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            }
        }
    }
}
