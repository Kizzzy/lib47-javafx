package cn.kizzzy.javafx.setting.parser;

import javafx.beans.property.ObjectProperty;

public class EnumPropertyFieldParser extends EnumFieldParserImpl<ObjectProperty<?>> {
    
    public EnumPropertyFieldParser() {
        super(property -> (Enum<?>) property.getValue());
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return ObjectProperty.class.isAssignableFrom(clazz);
    }
}
