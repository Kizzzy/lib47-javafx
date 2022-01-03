package cn.kizzzy.javafx.display;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.shape.Circle;

public class DisplayTab extends Tab {
    
    public DisplayTab() {
        this(null);
    }
    
    public DisplayTab(String text) {
        this(text, null);
    }
    
    public DisplayTab(String text, Node content) {
        super(text, content);
        final Circle circle = new Circle(0, 0, 4);
        setGraphic(circle);
        circle.setOnMouseClicked(event -> {
            if (isClosable()) {
                circle.setRadius(2);
                setClosable(false);
                Runnable runnable = getCallback();
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }
    
    private ObjectProperty<Runnable> callback;
    
    public Runnable getCallback() {
        return callbackProperty().get();
    }
    
    public ObjectProperty<Runnable> callbackProperty() {
        if (callback == null) {
            callback = new ObjectPropertyBase<Runnable>() {
                @Override
                public Object getBean() {
                    return DisplayTab.this;
                }
                
                @Override
                public String getName() {
                    return "callback";
                }
            };
        }
        return callback;
    }
    
    public void setCallback(Runnable callback) {
        this.callbackProperty().set(callback);
    }
}
