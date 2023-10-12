package cn.kizzzy.javafx.display;

import cn.kizzzy.base.AttributeWithInstance;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.tree.Leaf;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DisplayTabView extends TabPane {
    
    private static final Logger logger = LoggerFactory.getLogger(DisplayTabView.class);
    
    private RegisterOperator registerOperator;
    
    private final Map<AttributeWithInstance<RegisterAttribute, Register>, DisplayTabData> dataKvs
        = new HashMap<>();
    
    public DisplayTabView() {
        initialize();
    }
    
    private void initialize() {
        registerOperator = new RegisterOperator("cn.kizzzy.javafx.display");
        registerOperator.load();
    }
    
    public void stop() {
        for (DisplayTabData data : dataKvs.values()) {
            AnchorPane view = data.getView();
            if (view instanceof Stoppable) {
                ((Stoppable) view).stop();
            }
        }
    }
    
    public void show(DisplayLoader display, IPackage vfs, Leaf leaf) {
        for (AttributeWithInstance<RegisterAttribute, Register> awi : registerOperator) {
            if (awi.instance.acceptDisplay(display)) {
                DisplayTabData data = dataKvs.get(awi);
                if (data == null) {
                    try {
                        AnchorPane view = awi.instance.createView();
                        AnchorPane.setLeftAnchor(view, 0d);
                        AnchorPane.setTopAnchor(view, 0d);
                        AnchorPane.setRightAnchor(view, 0d);
                        AnchorPane.setBottomAnchor(view, 0d);
                        
                        DisplayTab tab = new DisplayTab(awi.attr.name());
                        tab.setContent(view);
                        tab.setClosable(true);
                        tab.setOnCloseRequest(event -> {
                            dataKvs.remove(awi);
                            if (view instanceof Stoppable) {
                                ((Stoppable) view).stop();
                            }
                        });
                        tab.setCallback(() -> dataKvs.remove(awi));
                        
                        getTabs().add(tab);
                        getSelectionModel().select(tab);
                        
                        data = new DisplayTabData(tab, view);
                        dataKvs.put(awi, data);
                    } catch (Exception e) {
                        logger.error("initial view error", e);
                    }
                }
                
                if (data != null) {
                    if (!getPin()) {
                        getSelectionModel().select(data.getTab());
                    }
                    
                    try {
                        awi.instance.show(data.getView(), display, vfs, leaf);
                    } catch (Exception e) {
                        logger.info("show {} error", awi.attr.name(), e);
                    }
                }
            }
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
