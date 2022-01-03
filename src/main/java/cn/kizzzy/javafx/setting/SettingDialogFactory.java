package cn.kizzzy.javafx.setting;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingDialogFactory implements ISettingDialogFactory {
    
    private final Stage owner;
    
    private Stage self;
    private SettingDialog dialog;
    
    public SettingDialogFactory(Stage owner) {
        this.owner = owner;
    }
    
    public <T> void show(T target, SettingConfigs configs) {
        if (target == null) {
            throw new NullPointerException();
        }
        
        if (self == null) {
            self = new Stage();
            //self.setTitle(config.getFile().getAbsolutePath());
            if (owner != null) {
                self.initModality(Modality.WINDOW_MODAL);
                self.initOwner(owner);
            }
            
            dialog = new SettingDialog();
            self.setScene(new Scene(dialog));
        }
        dialog.show(target, configs);
        self.show();
    }
}
