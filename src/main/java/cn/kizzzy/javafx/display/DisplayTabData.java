package cn.kizzzy.javafx.display;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class DisplayTabData {
    
    private final Tab tab;
    
    private final AnchorPane view;
    
    public DisplayTabData(Tab tab, AnchorPane view) {
        this.tab = tab;
        this.view = view;
    }
    
    public Tab getTab() {
        return tab;
    }
    
    public AnchorPane getView() {
        return view;
    }
}
