package cn.kizzzy.javafx.plugin;

import cn.kizzzy.helper.LogHelper;
import javafx.stage.Stage;

public interface Plugin {
    
    Stage getStage();
    
    void setStage(Stage stage);
    
    PluginHolder getHolder();
    
    void setHolder(PluginHolder holder);
    
    default void showAndHide() {
        if (getStage() != null) {
            if (getStage().isShowing()) {
                getStage().hide();
            } else {
                getStage().show();
            }
        }
    }
    
    default void stop() {
        LogHelper.info("[ " + getClass().getSimpleName() + " ] 已关闭");
        
        if (getHolder() != null) {
            getHolder().remove(this);
        }
    }
}
