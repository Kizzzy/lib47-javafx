package cn.kizzzy.javafx;

import javafx.fxml.FXMLLoader;

public interface JavafxControl {
    
    default void init() {
        try {
            Class<?> clazz = this.getClass();
            JavafxControlParameter parameter;
            
            do {
                parameter = clazz.getAnnotation(JavafxControlParameter.class);
                clazz = clazz.getSuperclass();
            } while (parameter == null && clazz != null);
            
            if (parameter == null) {
                throw new IllegalArgumentException(JavafxControlParameter.class.getSimpleName() + " is not found");
            }
            
            String fxml = parameter.fxml();
            if (fxml == null || "".equals(fxml)) {
                throw new IllegalArgumentException("fxml parameter is empty");
            }
            
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
            loader.setRoot(this);
            loader.setControllerFactory(c -> this);
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
