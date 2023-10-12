package cn.kizzzy.javafx.setting;

import cn.kizzzy.config.Ignore;
import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.JavafxView;
import cn.kizzzy.javafx.Stageable;
import cn.kizzzy.javafx.setting.parser.BooleanFieldParser;
import cn.kizzzy.javafx.setting.parser.BooleanPropertyFieldParser;
import cn.kizzzy.javafx.setting.parser.EnumFieldParser;
import cn.kizzzy.javafx.setting.parser.EnumPropertyFieldParser;
import cn.kizzzy.javafx.setting.parser.IFieldParser;
import cn.kizzzy.javafx.setting.parser.IntegerFieldParser;
import cn.kizzzy.javafx.setting.parser.IntegerPropertyFieldParser;
import cn.kizzzy.javafx.setting.parser.ListFieldParser;
import cn.kizzzy.javafx.setting.parser.StringFieldParser;
import cn.kizzzy.javafx.setting.parser.StringPropertyFieldParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

abstract class SettingDialogView extends JavafxView {
    
    @FXML
    protected VBox root;
    
    @FXML
    protected Button btn_cancel;
    
    @FXML
    protected Button btn_save;
    
    @FXML
    protected Button btn_save_exit;
}

@JavafxControlParameter(fxml = "/fxml/setting/setting_dialog_view.fxml")
public class SettingDialog extends SettingDialogView implements Initializable, Stageable<SettingDialog.Args> {
    
    public enum ResultType {
        CANCEL,
        SAVE,
        SAVE_EXIT
    }
    
    public static class Args {
        public Object target;
        public SettingConfigs configs;
        public Consumer<ResultType> callback;
    }
    
    private static final Logger logger = LoggerFactory.getLogger(SettingDialog.class);
    
    private Args args;
    private Stage stage;
    private IFieldParser[] parsers;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_cancel.setOnAction(event -> invokeResult(false, true));
        btn_save.setOnAction(event -> invokeResult(true, false));
        btn_save_exit.setOnAction(event -> invokeResult(true, true));
    }
    
    private void invokeResult(boolean save, boolean exit) {
        if (exit && stage != null) {
            stage.hide();
        }
        
        if (args != null && args.callback != null) {
            if (save) {
                if (exit) {
                    args.callback.accept(ResultType.SAVE_EXIT);
                } else {
                    args.callback.accept(ResultType.SAVE);
                }
            } else {
                args.callback.accept(ResultType.CANCEL);
            }
        }
    }
    
    public void show(Stage stage, Args args) {
        this.stage = stage;
        this.args = args;
        try {
            root.getChildren().clear();
            
            TransferArgs transferArgs = new TransferArgs();
            transferArgs.shower = this::show;
            transferArgs.configs = args.configs;
            
            parsers = new IFieldParser[]{
                new EnumFieldParser(),
                new EnumPropertyFieldParser(),
                new BooleanFieldParser(),
                new BooleanPropertyFieldParser(),
                new StringFieldParser(),
                new StringPropertyFieldParser(),
                new IntegerFieldParser(),
                new IntegerPropertyFieldParser(),
                new ListFieldParser(transferArgs)
            };
            
            show(root, args.target, args.configs);
            
            SettingHelper.adjustLabel(root);
        } catch (Exception e) {
            logger.error("show setting dialog error", e);
        }
    }
    
    private void show(Pane root, Object target, SettingConfigs configs) {
        SettingGroup group = null;
        
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Ignore ignore = field.getAnnotation(Ignore.class);
            if (ignore != null) {
                continue;
            }
            
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            
            SettingConfig config = null;
            if (configs != null) {
                config = configs.selectById(clazz, field.getName());
                if (config != null && config.ignore) {
                    return;
                }
            }
            
            if (group == null) {
                group = new SettingGroup();
                group.setLabel(clazz.getSimpleName());
                
                root.getChildren().add(group);
                
                AnchorPane.setLeftAnchor(group, 0d);
                AnchorPane.setTopAnchor(group, 0d);
                AnchorPane.setRightAnchor(group, 0d);
                AnchorPane.setBottomAnchor(group, 0d);
            }
            
            Class<?> fieldType = field.getType();
            
            boolean found = false;
            for (IFieldParser parser : parsers) {
                if (parser.accept(fieldType)) {
                    Node node = parser.createNode(fieldType, field, target);
                    if (node != null) {
                        found = true;
                        
                        SettingItem item = new SettingItem();
                        item.setLabel(SettingHelper.getFieldName(field, config));
                        item.addChild(node);
                        
                        group.addChild(item);
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
                    show(group.getRoot(), temp, configs);
                } catch (Exception e) {
                    logger.error("show object error", e);
                } finally {
                    field.setAccessible(false);
                }
            }
        }
    }
}
