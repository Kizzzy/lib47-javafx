package cn.kizzzy.javafx.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;

public class DisplayTabView extends TabPane {
    
    private final Map<Integer, DisplayTabData> viewKvs
        = new HashMap<>();
    
    private BooleanProperty _pinProperty;
    
    public BooleanProperty pinProperty() {
        if (_pinProperty == null) {
            _pinProperty = new SimpleBooleanProperty();
        }
        return _pinProperty;
    }
    
    public boolean getPin() {
        return pinProperty().getValue();
    }
    
    public void setPin(boolean pin) {
        pinProperty().set(pin);
    }
    
    public void show(int type, Object param) {
        DisplayTabData data = viewKvs.get(type);
        if (data == null) {
            DisplayViewAdapter view = null;
            
            String title = "未知";
            switch (type) {
                case DisplayType.SHOW_TEXT:
                    view = new DisplayTextView();
                    title = "文本";
                    break;
                case DisplayType.SHOW_IMAGE:
                    view = new DisplayImageView();
                    title = "图片";
                    break;
                case DisplayType.SHOW_TABLE:
                    view = new DisplayTableView();
                    title = "表格";
                    break;
            }
            
            if (view != null) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().add(view);
                
                AnchorPane.setLeftAnchor(view, 0d);
                AnchorPane.setTopAnchor(view, 0d);
                AnchorPane.setRightAnchor(view, 0d);
                AnchorPane.setBottomAnchor(view, 0d);
                
                DisplayTab tab = new DisplayTab(title);
                tab.setContent(anchorPane);
                tab.setClosable(true);
                tab.setOnCloseRequest(event -> viewKvs.remove(type));
                tab.setCallback(() -> viewKvs.remove(type));
                
                getTabs().add(tab);
                getSelectionModel().select(tab);
                
                data = new DisplayTabData(tab, view);
                viewKvs.put(type, data);
            }
        }
        
        if (data != null) {
            if (!getPin()) {
                getSelectionModel().select(data.getTab());
            }
            data.getSubView().show(param);
        }
    }
}
