package cn.kizzzy.javafx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class StageHelper {
    
    private static class Holder<View> {
        public Class<View> viewClass;
        public Supplier<View> viewSupplier;
        public Stage self;
        public View view;
    }
    
    private Map<Class<?>, Holder<?>> holderKvs
        = new HashMap<>();
    
    public <View> void addFactory(Supplier<View> viewSupplier, Class<View> viewClass) {
        Holder<View> holder = new Holder<>();
        holder.viewSupplier = viewSupplier;
        holderKvs.put(viewClass, holder);
    }
    
    public <View extends Parent & Stageable<Args>, Args> void show(Stage owner, Args args, Class<View> viewClass) {
        Holder<View> holder = (Holder<View>) holderKvs.get(viewClass);
        if (holder == null) {
            return;
        }
        
        if (args == null) {
            return;
        }
        
        Stage stage = holder.self;
        View view = holder.view;
        if (stage == null) {
            stage = new Stage();
            //stage.setTitle(config.getFile().getAbsolutePath());
            if (owner != null) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(owner);
            }
            
            view = holder.viewSupplier.get();
            stage.setScene(new Scene(view));
            
            holder.self = stage;
            holder.view = view;
        }
        
        view.show(stage, args);
        stage.show();
    }
}
