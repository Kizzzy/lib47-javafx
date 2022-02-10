package cn.kizzzy.javafx.display;

public abstract class Display<T> {
    
    protected final T context;
    
    protected final String path;
    
    public Display(T context, String path) {
        this.context = context;
        this.path = path;
    }
    
    public abstract DisplayAAA load();
}

