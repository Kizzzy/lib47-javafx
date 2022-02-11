package cn.kizzzy.javafx.display;

public class DisplayViewInfo {
    
    public final DisplayViewAttribute attr;
    
    public final Class<? extends DisplayViewAdapter> clazz;
    
    public DisplayViewInfo(DisplayViewAttribute attr, Class<? extends DisplayViewAdapter> clazz) {
        this.attr = attr;
        this.clazz = clazz;
    }
}
