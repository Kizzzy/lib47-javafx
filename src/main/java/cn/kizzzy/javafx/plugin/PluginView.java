package cn.kizzzy.javafx.plugin;

import cn.kizzzy.helper.StringHelper;
import cn.kizzzy.javafx.JavafxView;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.InputStream;

public abstract class PluginView extends JavafxView implements Plugin {
    
    protected Stage stage;
    
    protected PluginHolder holder;
    
    @Override
    public Stage getStage() {
        return stage;
    }
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public PluginHolder getHolder() {
        return holder;
    }
    
    @Override
    public void setHolder(PluginHolder holder) {
        this.holder = holder;
    }
    
    private static class CloseHandler implements EventHandler<WindowEvent> {
        
        private final Plugin plugin;
        
        public CloseHandler(Plugin plugin) {
            this.plugin = plugin;
        }
        
        @Override
        public void handle(WindowEvent event) {
            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                plugin.stop();
            }
        }
    }
    
    public static <T extends PluginView> T show(PluginHolder holder, Stage stage, Class<T> clazz) {
        try {
            PluginParameter parameter = clazz.getAnnotation(PluginParameter.class);
            if (parameter == null) {
                throw new IllegalArgumentException(PluginParameter.class.getSimpleName() + " is not found");
            }
            
            stage = stage != null ? stage : new Stage();
            
            T view = clazz.newInstance();
            view.setStage(stage);
            view.setHolder(holder);
            
            Scene scene = new Scene(view);
            if (parameter.transparent()) {
                scene.setFill(Color.TRANSPARENT);
                scene.getRoot().setStyle("-fx-background-color: transparent;");
            }
            
            stage.setTitle(parameter.title());
            stage.setResizable(parameter.resize());
            stage.setAlwaysOnTop(parameter.top());
            stage.setScene(scene);
            stage.sizeToScene();
            
            stage.setOnCloseRequest(new CloseHandler(view));
            
            if (parameter.transparent()) {
                stage.initStyle(StageStyle.TRANSPARENT);
            }
            
            if (StringHelper.isNotNullAndEmpty(parameter.icon())) {
                try (InputStream is = PluginView.class.getResourceAsStream(parameter.icon())) {
                    stage.getIcons().add(new Image(is));
                }
            }
            
            if (parameter.show()) {
                stage.show();
            }
            
            return view;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
