package cn.kizzzy.javafx.display;

import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.tree.Leaf;
import javafx.scene.layout.AnchorPane;

public interface Register {
    
    boolean acceptDisplay(DisplayLoader loader);
    
    AnchorPane createView();
    
    void show(AnchorPane view, DisplayLoader loader, IPackage vfs, Leaf leaf) throws Exception;
}
