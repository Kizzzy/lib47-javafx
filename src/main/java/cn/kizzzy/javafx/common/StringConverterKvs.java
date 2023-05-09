package cn.kizzzy.javafx.common;

import javafx.util.StringConverter;

import java.util.Map;

public class StringConverterKvs<T> extends StringConverter<T> {
    
    private final Map<String, T> kvs;
    
    public StringConverterKvs(Map<String, T> kvs) {
        this.kvs = kvs;
    }
    
    @Override
    public String toString(T object) {
        for (Map.Entry<String, T> entry : kvs.entrySet()) {
            if (entry.getValue().equals(object)) {
                return entry.getKey();
            }
        }
        return "not found";
    }
    
    @Override
    public T fromString(String string) {
        return kvs.get(string);
    }
}
