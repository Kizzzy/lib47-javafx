package cn.kizzzy.javafx.setting.parser;

import java.util.function.Function;

public class StringFieldParser extends StringFieldParserImpl<String> {
    
    public StringFieldParser() {
        super(Function.identity());
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz == String.class;
    }
}
