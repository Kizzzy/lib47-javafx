package cn.kizzzy.javafx.setting.parser;

import java.util.function.Function;

public class ObjectFieldParser extends ObjectFieldParserImpl<Object> {
    
    public ObjectFieldParser() {
        super(Function.identity());
    }
    
    @Override
    public boolean accept(Class<?> clazz) {
        return true;
    }
}
