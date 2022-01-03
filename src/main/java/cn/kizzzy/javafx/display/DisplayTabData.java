package cn.kizzzy.javafx.display;

import javafx.scene.control.Tab;

public class DisplayTabData {
    
    private final Tab tab;
    
    private final DisplayViewAdapter subView;
    
    public DisplayTabData(Tab tab, DisplayViewAdapter subView) {
        this.tab = tab;
        this.subView = subView;
    }
    
    public Tab getTab() {
        return tab;
    }
    
    public DisplayViewAdapter getSubView() {
        return subView;
    }
}
