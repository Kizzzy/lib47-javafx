package cn.kizzzy.javafx.common;

import javafx.event.ActionEvent;

import java.util.function.Consumer;

public class MenuItemArg implements Comparable<MenuItemArg> {
    private final int group;
    private final String path;
    private final String quick;
    private final Consumer<ActionEvent> callback;
    
    public MenuItemArg(int group, String path, Consumer<ActionEvent> callback) {
        this(group, path, null, callback);
    }
    
    public MenuItemArg(int group, String path, String quick, Consumer<ActionEvent> callback) {
        this.group = group;
        this.path = path;
        this.quick = quick;
        this.callback = callback;
    }
    
    public int getGroup() {
        return group;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getQuick() {
        return quick;
    }
    
    public Consumer<ActionEvent> getCallback() {
        return callback;
    }
    
    @Override
    public int compareTo(MenuItemArg o) {
        if (group != o.group) {
            return group - o.group;
        }
        return 0;
    }
}
