package cn.kizzzy.javafx.display;

import javafx.scene.layout.AnchorPane;

public abstract class DisplayViewAdapter extends AnchorPane {
    
    public abstract void show(Object data);
}
