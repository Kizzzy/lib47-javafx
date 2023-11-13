package cn.kizzzy.javafx.setting.parser;

import javafx.scene.Node;

import java.lang.reflect.Field;

public class DefaultFieldParserFactory implements FieldParserFactory {
    
    private final FieldParser[] parsers;
    
    public DefaultFieldParserFactory() {
        parsers = new FieldParser[]{
            new EnumPropertyFieldParser(),
            new EnumFieldParser(),
            new BooleanPropertyFieldParser(),
            new BooleanFieldParser(),
            new StringPropertyFieldParser(),
            new StringFieldParser(),
            new IntegerPropertyFieldParser(),
            new IntegerFieldParser(),
            new ArrayFieldParser(),
            new ListFieldParser(),
            new MapFieldParser(),
            new ObjectPropertyFieldParser(),
            new ObjectFieldParser(),
        };
    }
    
    @Override
    public Node createNode(Class<?> fieldType, Field field, Object target) {
        for (FieldParser parser : parsers) {
            if (parser.accept(fieldType)) {
                return parser.createNode(this, fieldType, field, target);
            }
        }
        return null;
    }
}
