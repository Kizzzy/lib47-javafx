package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;
import java.util.function.Function;

public class EnumFieldParser extends AbstractFieldParser<Enum<?>, Enum<?>> {
    
    public EnumFieldParser() {
        super(Enum.class, Function.identity(), null);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.isEnum();
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        Object value = getValue(field, target);
        
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(clazz.getEnumConstants());
        comboBox.valueProperty().setValue(value);
        comboBox.valueProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, (Enum<?>) newValue);
        });
        return comboBox;
    }
}
