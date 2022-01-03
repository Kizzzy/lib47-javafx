package cn.kizzzy.javafx.common;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DragDropReorder {
    private final Node node;
    private final ChangeListener<Node> listener;
    
    public DragDropReorder(Node node, ChangeListener<Node> listener) {
        this.node = node;
        this.listener = listener;
        
        node.setOnMousePressed(this::onEntered);
        node.setOnMouseReleased(this::onExited);
    }
    
    private void onEntered(MouseEvent event) {
    
    }
    
    private void onExited(MouseEvent event) {
        Node target = event.getPickResult().getIntersectedNode();
        if (!node.equals(target)) {
            if (target.getClass().equals(node.getClass())) {
                listener.changed(null, node, target);
            }
        }
    }
}
