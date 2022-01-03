package cn.kizzzy.javafx.common;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MenuNode {
    private String name;
    private MenuItem menu;
    private ObservableList<MenuItem> menus;
    private Map<String, MenuNode> nodes = new HashMap<>();
    
    public MenuNode(MenuItem menu) {
        this(menu, null);
    }
    
    public MenuNode(ObservableList<MenuItem> menus) {
        this(null, menus);
    }
    
    public MenuNode(MenuItem menu, ObservableList<MenuItem> menus) {
        this.menu = menu;
        this.menus = menus;
        if (menu != null) {
            this.name = menu.getText();
        }
    }
    
    public MenuNode retrieve(String name) {
        return nodes.get(name);
    }
    
    public void add(MenuNode node) {
        if (!nodes.containsKey(node.name)) {
            nodes.put(node.name, node);
            if (menu == null) {
                menus.add(node.menu);
            } else {
                ((Menu) menu).getItems().add(node.menu);
            }
        }
    }
}
