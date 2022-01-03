package cn.kizzzy.javafx.setting;

public interface ISettingDialogFactory {
    
    default <T> void show(T target) {
        show(target, null);
    }
    
    <T> void show(T target, SettingConfigs configs);
}
