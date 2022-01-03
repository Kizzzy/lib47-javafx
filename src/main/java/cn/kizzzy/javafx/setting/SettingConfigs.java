package cn.kizzzy.javafx.setting;

import java.util.HashMap;
import java.util.Map;

public class SettingConfigs {
    
    private SettingConfig[] configs;
    
    private Map<Class<?>, Map<String, SettingConfig>> configKvs
        = new HashMap<>();
    
    public SettingConfigs(SettingConfig... configs) {
        this.configs = configs;
        
        for (SettingConfig config : configs) {
            Map<String, SettingConfig> kvs = selectByClass(config.holderType);
            kvs.put(config.fieldName, config);
        }
    }
    
    private Map<String, SettingConfig> selectByClass(Class<?> sourceType) {
        return configKvs.computeIfAbsent(sourceType, k -> new HashMap<>());
    }
    
    public SettingConfig selectById(Class<?> clazz, String name) {
        return selectByClass(clazz).get(name);
    }
}
