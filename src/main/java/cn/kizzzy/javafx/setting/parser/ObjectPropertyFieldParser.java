package cn.kizzzy.javafx.setting.parser;

import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ObjectProperty;

public class ObjectPropertyFieldParser extends ObjectFieldParserImpl<ObjectProperty<?>> {
    
    public ObjectPropertyFieldParser() {
        super(ObjectExpression::getValue);
    }
    
    @Override
    public boolean accept(Class<?> clazz) {
        return clazz.isAssignableFrom(ObjectProperty.class);
    }
}
