package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.javafx.setting.SettingConfig;
import cn.kizzzy.javafx.setting.SettingList;
import cn.kizzzy.javafx.setting.TransferArgs;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.List;

public class ListFieldParser implements IFieldParser {
    
    private TransferArgs args;
    
    public ListFieldParser(TransferArgs args) {
        this.args = args;
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == List.class;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        if (args.configs == null) {
            LogHelper.error("SettingConfigs not found");
            return null;
        }
        
        SettingConfig config = args.configs.selectById(target.getClass(), field.getName());
        if (config == null) {
            LogHelper.error("SettingConfig not found");
            return null;
        }
        
        if (config.genericType == null) {
            LogHelper.error("GenericType of SettingConfig not found");
            return null;
        }
        
        try {
            field.setAccessible(true);
            List<Object> value = (List<Object>) field.get(target);
            
            return new SettingList(args, value, config.genericType);
        } catch (IllegalAccessException e) {
            LogHelper.error(null, e);
        } finally {
            field.setAccessible(false);
        }
        return null;
    }
}
