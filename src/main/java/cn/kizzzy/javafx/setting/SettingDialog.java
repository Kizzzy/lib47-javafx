package cn.kizzzy.javafx.setting;

import cn.kizzzy.javafx.JavafxControlParameter;
import cn.kizzzy.javafx.JavafxView;
import cn.kizzzy.javafx.Stageable;
import cn.kizzzy.javafx.setting.parser.DefaultFieldParserFactory;
import cn.kizzzy.javafx.setting.parser.FieldParserFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        public Consumer<ResultType> callback;
    }
    
    private static final Logger logger = LoggerFactory.getLogger(SettingDialog.class);
    
    private Args args;
    private Stage stage;
    
    private FieldParserFactory factory;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_cancel.setOnAction(event -> invokeResult(false, true));
        btn_save.setOnAction(event -> invokeResult(true, false));
        btn_save_exit.setOnAction(event -> invokeResult(true, true));
        
        factory = new DefaultFieldParserFactory();
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
            
            Node child = factory.createNode(args.target.getClass(), null, args.target);
            root.getChildren().add(child);
            
            SettingHelper.adjustLabel(root);
        } catch (Exception e) {
            logger.error("show setting dialog error", e);
        }
    }
}
