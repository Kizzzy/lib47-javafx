package cn.kizzzy.javafx.setting.parser;

public class BooleanFieldParser extends BooleanFieldParserImpl<Boolean> {
    
    public BooleanFieldParser() {
        super(Boolean::booleanValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("boolean");
    }
}
