package cn.kizzzy.javafx.setting;

import cn.kizzzy.config.Name;
import cn.kizzzy.helper.StringHelper;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SettingHelper {
    
    public static String getFieldName(Field field, SettingConfig config) {
        Name name = field.getAnnotation(Name.class);
        if (name != null) {
            return name.alias();
        }
        
        if (config != null && StringHelper.isNotNullAndEmpty(config.alias)) {
            return config.alias;
        }
        
        return field.getName();
    }
    
    public static void adjustLabel(Pane root) {
        Platform.runLater(() -> adjustLabelFromRootImpl(root));
    }
    
    private static void adjustLabelFromRootImpl(Pane root) {
        Platform.runLater(() -> {
            for (Node node : root.getChildren()) {
                if (node instanceof SettingGroup) {
                    SettingGroup group = (SettingGroup) node;
                    adjustLabelFromSettingGroup(group);
                }
            }
        });
    }
    
    private static void adjustLabelFromSettingGroup(SettingGroup group) {
        double max_label_length = 0;
        
        List<SettingItem> items = new ArrayList<>();
        for (Node child : group.root.getChildren()) {
            if (child instanceof SettingItem) {
                SettingItem item = (SettingItem) child;
                items.add(item);
                
                if (item.label.getWidth() > max_label_length) {
                    max_label_length = item.label.getWidth();
                }
                
                for (Node c2 : item.root.getChildren()) {
                    if (c2 instanceof SettingList) {
                        SettingList list = (SettingList) c2;
                        for (Node c3 : list.root.getChildren()) {
                            if (c3 instanceof SettingListItem) {
                                SettingListItem listItem = (SettingListItem) c3;
                                adjustLabelFromRootImpl(listItem.root);
                            }
                        }
                    }
                }
            }
        }
        
        if (max_label_length > 0) {
            for (SettingItem item : items) {
                item.label.setPrefWidth(max_label_length);
            }
        }
    }
}
