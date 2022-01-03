package cn.kizzzy.javafx.setting;

import java.util.function.Function;

public class SettingConfig {
    public String fieldName;
    public String alias;
    public boolean ignore;
    public Class<?> holderType;
    public Class<?> sourceType;
    public Class<?> targetType;
    public Class<?> genericType;
    public Function<Object, Object> fromSource;
    public Function<Object, Object> toSource;
}
