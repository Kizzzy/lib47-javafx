package cn.kizzzy.javafx.display;

public class DisplayInfo<T> {
    
    public final DisplayAttribute attr;
    
    public final Class<? extends Display<T>> clazz;
    
    public DisplayInfo(DisplayAttribute attr, Class<? extends Display<T>> clazz) {
        this.attr = attr;
        this.clazz = clazz;
    }
}
