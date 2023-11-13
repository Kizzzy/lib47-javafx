package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import java.lang.reflect.Field;
import java.util.function.Function;

public abstract class EnumFieldParserImpl<T> extends AbstractFieldParser<Enum<?>, T> {
    
    public EnumFieldParserImpl(Function<T, Enum<?>> fromFunc) {
        super(Enum.class, fromFunc, null);
    }
    
    @Override
    public Node createNode(final FieldParserFactory factory, final Class<?> clazz, final Field field, final Object target) {
        Object value = getValue(field, target);
        
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.getItems().addAll((value != null ? value.getClass() : clazz).getEnumConstants());
        comboBox.valueProperty().setValue(value);
        comboBox.valueProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, (Enum<?>) newValue);
        });
        return comboBox;
    }
}
