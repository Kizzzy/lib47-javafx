package cn.kizzzy.javafx.setting.parser;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;

public class BooleanPropertyFieldParser extends BooleanFieldParserImpl<BooleanProperty> {
    
    public BooleanPropertyFieldParser() {
        super(BooleanExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return BooleanProperty.class.isAssignableFrom(clazz);
    }
}
