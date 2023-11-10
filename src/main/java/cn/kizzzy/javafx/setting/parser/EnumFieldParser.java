package cn.kizzzy.javafx.setting.parser;

import java.util.function.Function;

public class EnumFieldParser extends EnumFieldParserImpl<Enum<?>> {
    
    public EnumFieldParser() {
        super(Function.identity());
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.isEnum();
    }
}
