package cn.kizzzy.javafx.setting;

import javafx.scene.layout.Pane;

public interface ITargetShower {
    
    <T> void show(Pane root, T target, SettingConfigs configs);
}
