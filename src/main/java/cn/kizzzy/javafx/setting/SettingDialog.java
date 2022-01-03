package cn.kizzzy.javafx.setting;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import cn.kizzzy.javafx.setting.parser.BooleanFieldParser;
import cn.kizzzy.javafx.setting.parser.EnumFieldParser;
import cn.kizzzy.javafx.setting.parser.IFieldParser;
import cn.kizzzy.javafx.setting.parser.ListFieldParser;
import cn.kizzzy.javafx.setting.parser.NumberFieldParser;
import cn.kizzzy.javafx.setting.parser.StringFieldParser;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;

@CustomControlParamter(fxml = "/fxml/custom/setting/setting_dialog_view.fxml")
public class SettingDialog extends AnchorPane implements ICustomControl {
    
    @FXML
    protected VBox root;
    
    @FXML
    protected Button btn_cancel;
    
    @FXML
    protected Button btn_save;
    
    @FXML
    protected Button btn_save_exit;
    
    private IFieldParser[] parsers;
    
    public SettingDialog() {
        super();
        init();
    }
    
    public <T> void show(T target, SettingConfigs configs) {
        try {
            root.getChildren().clear();
            
            TransferArgs args = new TransferArgs();
            args.shower = this::show;
            args.configs = configs;
            
            parsers = new IFieldParser[]{
                new EnumFieldParser(),
                new BooleanFieldParser(),
                new StringFieldParser(),
                new NumberFieldParser(),
                new ListFieldParser(args)
            };
            
            show(root, target, configs);
        } catch (Exception e) {
            LogHelper.error(null, e);
        }
    }
    
    private <T> void show(Pane root, T target, SettingConfigs configs) {
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
