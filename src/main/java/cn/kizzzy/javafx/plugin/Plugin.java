package cn.kizzzy.javafx.plugin;

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
        System.out.printf("[ %s ] 已关闭%n", getClass().getSimpleName());
        
        if (getHolder() != null) {
            getHolder().remove(this);
        }
    }
}
