package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.javafx.setting.ITargetShower;
import javafx.scene.Node;

import java.lang.reflect.Field;

public class UndealFieldParser implements IFieldParser {
    
    private ITargetShower shower;
    
    public UndealFieldParser(ITargetShower shower) {
        this.shower = shower;
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return true;
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        try {
            field.setAccessible(true);
            Object temp = field.get(target);
            if (temp == null) {
                temp = clazz.newInstance();
                field.set(target, temp);
            }
            //shower.show(childHolder.getRoot(), temp, configs);
        } catch (Exception e) {
            LogHelper.error(null, e);
        } finally {
            field.setAccessible(false);
        }
        return null;
    }
}
