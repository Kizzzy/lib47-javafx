package cn.kizzzy.javafx.setting.parser;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.IntegerProperty;

public class IntegerPropertyFieldParser extends IntegerFieldParserImpl<IntegerProperty> {
    
    public IntegerPropertyFieldParser() {
        super(IntegerExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return IntegerProperty.class.isAssignableFrom(clazz);
    }
}
