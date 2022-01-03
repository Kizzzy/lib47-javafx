package cn.kizzzy.javafx.common;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeListener implements EventHandler<MouseEvent> {
    private final Stage stage;
    
    private boolean cursorChanged;
    private Cursor cursor;
    
    private double diffX;
    private double diffY;
    
    public ResizeListener(Stage stage) {
        this.stage = stage;
    }
    
    @Override
    public void handle(MouseEvent event) {
        if (MouseEvent.MOUSE_MOVED.equals(event.getEventType())) {
            onMove(event);
        } else if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
            onPress(event);
        } else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
            onDrag(event);
        }
    }
    
    private void onMove(MouseEvent event) {
        if (check(event)) {
            if (!Cursor.SE_RESIZE.equals(stage.getScene().getCursor())) {
                cursorChanged = true;
                cursor = stage.getScene().getCursor();
                stage.getScene().setCursor(Cursor.SE_RESIZE);
            }
        } else if (cursorChanged) {
            cursorChanged = false;
            stage.getScene().setCursor(cursor);
        }
    }
    
    private void onPress(MouseEvent event) {
        if (check(event)) {
            diffX = stage.getWidth() - event.getX();
            diffY = stage.getHeight() - event.getY();
        }
    }
    
    private void onDrag(MouseEvent event) {
        if (diffX <= 4 && diffY <= 4) {
            stage.setWidth(event.getX() + diffX);
            stage.setHeight(event.getY() + diffY);
        }
    }
    
    private boolean check(MouseEvent event) {
        return stage.getWidth() >= event.getX() &&
            stage.getWidth() - event.getX() <= 4 &&
            stage.getHeight() >= event.getY() &&
            stage.getHeight() - event.getY() <= 4;
    }
}
