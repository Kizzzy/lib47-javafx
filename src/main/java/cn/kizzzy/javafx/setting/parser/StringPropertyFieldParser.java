package cn.kizzzy.javafx.setting.parser;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;

public class StringPropertyFieldParser extends StringFieldParserImpl<StringProperty> {
    
    public StringPropertyFieldParser() {
        super(StringExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return StringProperty.class.isAssignableFrom(clazz);
    }
}
