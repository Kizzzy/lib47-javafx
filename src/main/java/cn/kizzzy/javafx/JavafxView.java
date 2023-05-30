package cn.kizzzy.javafx;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class JavafxView extends AnchorPane implements JavafxControl {
    
    public JavafxView() {
        init();
    }
    
    public static <T extends JavafxView> void show(Stage stage, Class<T> clazz) throws Exception {
        show(stage, clazz.newInstance());
    }
    
    public static <T extends JavafxView> void show(Stage stage, T view) {
        Scene scene = new Scene(view);
        
        stage = stage != null ? stage : new Stage();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
