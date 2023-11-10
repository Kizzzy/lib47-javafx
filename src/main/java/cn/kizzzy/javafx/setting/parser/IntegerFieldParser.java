package cn.kizzzy.javafx.setting.parser;

public class IntegerFieldParser extends IntegerFieldParserImpl<Integer> {
    
    public IntegerFieldParser() {
        super(Integer::intValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("int");
    }
}
