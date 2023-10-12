package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.javafx.setting.SettingConfig;
import cn.kizzzy.javafx.setting.SettingList;
import cn.kizzzy.javafx.setting.TransferArgs;
import javafx.scene.Node;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

public class ListFieldParser extends AbstractFieldParser<List<Object>, List<Object>> {
    
    private final TransferArgs args;
    
    public ListFieldParser(TransferArgs args) {
        super(List.class, Function.identity(), null);
        this.args = args;
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == List.class;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        if (args.configs == null) {
            logger.error("SettingConfigs not found");
            return null;
        }
        
        SettingConfig config = args.configs.selectById(target.getClass(), field.getName());
        if (config == null) {
            logger.error("SettingConfig not found");
            return null;
        }
        
        if (config.genericType == null) {
            logger.error("GenericType of SettingConfig not found");
            return null;
        }
        
        List<Object> value = (List<Object>) getValue(field, target);
        return new SettingList(args, value, config.genericType);
    }
}
