package cn.kizzzy.javafx.display;

import cn.kizzzy.clazz.ClassFilter;
import cn.kizzzy.clazz.ClassFinderHelper;
import cn.kizzzy.helper.FileHelper;
import cn.kizzzy.helper.LogHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DisplayOperator<T> {
    
    private final String namespace;
    
    private final DisplayTabView tabView;
    
    private final Class<T> contextClass;
    
    private T context;
    
    private Map<String, Class<? extends Display<T>>> displayKvs
        = new HashMap<>();
    
    public DisplayOperator(String namespace, DisplayTabView tabView, Class<T> contextClass) {
        this.namespace = namespace;
        this.tabView = tabView;
        this.contextClass = contextClass;
    }
    
    public void setContext(T context) {
        this.context = context;
    }
    
    public boolean load() {
        try {
            List<Class<?>> list = ClassFinderHelper.find(new ClassFilter() {
                @Override
                public String packageRoot() {
                    return namespace;
                }
                
                @Override
                public boolean isRecursive() {
                    return false;
                }
                
                @Override
                public boolean accept(Class<?> clazz) {
                    return clazz.isAnnotationPresent(DisplayAttribute.class);
                }
            });
            
            for (Class<?> clazz : list) {
                DisplayAttribute flag = clazz.getAnnotation(DisplayAttribute.class);
                for (String suffix : flag.suffix()) {
                    displayKvs.put(suffix, (Class<? extends Display<T>>) clazz);
                }
            }
            
            return true;
        } catch (Exception e) {
            LogHelper.error(null, e);
            return false;
        }
    }
    
    public void display(String path) {
        String ext = FileHelper.getExtension(path);
        Class<? extends Display<T>> clazz = displayKvs.get(ext);
        if (clazz != null) {
            try {
                Display<T> display = clazz.getDeclaredConstructor(contextClass, String.class)
                    .newInstance(context, path);
                DisplayAAA aaa = display.load();
                tabView.show(aaa.type, aaa.param);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LogHelper.error("display error: ", e);
            }
        }
    }
}
