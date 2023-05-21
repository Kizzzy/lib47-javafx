package cn.kizzzy.javafx;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public interface JavafxControl {
    
    default void init() {
        try {
            Class<?> clazz = this.getClass();
            JavafxControlParameter paramter;
            
            do {
                paramter = clazz.getAnnotation(JavafxControlParameter.class);
                clazz = clazz.getSuperclass();
            } while (paramter == null && clazz != null);
            
            
            if (paramter == null) {
                throw new IllegalArgumentException("can not find annotation, " + this.getClass());
            }
            
            String fxml = paramter.fxml();
            if (fxml.equals("")) {
                throw new IllegalArgumentException("fxml parameter is empty, " + this.getClass());
            }
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(fxml));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
