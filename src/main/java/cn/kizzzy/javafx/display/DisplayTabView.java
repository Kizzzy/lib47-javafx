package cn.kizzzy.javafx.display;

import cn.kizzzy.clazz.ClassFilter;
import cn.kizzzy.clazz.ClassFinderHelper;
import cn.kizzzy.helper.LogHelper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DisplayTabView extends TabPane {
    
    private final Map<DisplayType, DisplayTabData> viewKvs
        = new HashMap<>();
    
    private final Map<DisplayType, DisplayViewInfo> infoKvs
        = new HashMap<>();
    
    public DisplayTabView() {
        initialize();
    }
    
    private void initialize() {
        try {
            List<Class<?>> list = ClassFinderHelper.find(new ClassFilter() {
                @Override
                public String packageRoot() {
                    return "cn.kizzzy.javafx.display";
                }
                
                @Override
                public boolean isRecursive() {
                    return true;
                }
                
                @Override
                public boolean accept(Class<?> clazz) {
                    return clazz.isAnnotationPresent(DisplayViewAttribute.class);
                }
            });
            
            for (Class<?> clazz : list) {
                DisplayViewAttribute attr = clazz.getAnnotation(DisplayViewAttribute.class);
                infoKvs.put(attr.type(), new DisplayViewInfo(attr, (Class<? extends DisplayViewAdapter>) clazz));
            }
            
        } catch (Exception e) {
            LogHelper.error(null, e);
        }
    }
    
    public void show(DisplayAAA args) {
        DisplayTabData data = viewKvs.get(args.type);
        DisplayViewInfo viewInfo = infoKvs.get(args.type);
        if (data == null && viewInfo != null) {
            try {
                DisplayViewAdapter view = viewInfo.clazz.newInstance();
                
                AnchorPane.setLeftAnchor(view, 0d);
                AnchorPane.setTopAnchor(view, 0d);
                AnchorPane.setRightAnchor(view, 0d);
                AnchorPane.setBottomAnchor(view, 0d);
                
                DisplayTab tab = new DisplayTab(viewInfo.attr.title());
                tab.setContent(view);
                tab.setClosable(true);
                tab.setOnCloseRequest(event -> viewKvs.remove(args.type));
                tab.setCallback(() -> viewKvs.remove(args.type));
                
                getTabs().add(tab);
                getSelectionModel().select(tab);
                
                data = new DisplayTabData(tab, view);
                viewKvs.put(args.type, data);
            } catch (Exception e) {
                LogHelper.error("initial view error", e);
                data = null;
            }
        }
        
        if (data != null) {
            if (!getPin()) {
                getSelectionModel().select(data.getTab());
            }
            data.getSubView().show(args.param);
        }
    }
    
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
}
