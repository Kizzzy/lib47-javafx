package cn.kizzzy.javafx.display;

import cn.kizzzy.clazz.ClassFilter;
import cn.kizzzy.clazz.ClassFinderHelper;
import cn.kizzzy.helper.FileHelper;
import cn.kizzzy.helper.LogHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DisplayOperator<T> {
    
    private final String namespace;
    
    private final DisplayTabView tabView;
    
    private final Class<T> contextClass;
    
    private T context;
    
    private Map<String, List<DisplayInfo<T>>> displayKvs
        = new HashMap<>();
    
    public DisplayOperator(String namespace, DisplayTabView tabView, Class<T> contextClass) {
        this.namespace = namespace;
        this.tabView = tabView;
        this.contextClass = contextClass;
    }
    
    public void setContext(T context) {
        this.context = context;
    }
    
    public void load() {
        try {
            List<Class<?>> list1 = ClassFinderHelper.find(new ClassFilter() {
                @Override
                public String packageRoot() {
                    return "cn.kizzzy.javafx.display";
                }
                
                @Override
                public boolean isRecursive() {
                    return true;
                }
                
                @Override
                public boolean accept(Class<?> clazz) {
                    return clazz.isAnnotationPresent(DisplayAttribute.class);
                }
            });
            
            List<Class<?>> list2 = ClassFinderHelper.find(new ClassFilter() {
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
            
            initialAttribute(list1, list2);
            
        } catch (Exception e) {
            LogHelper.error(null, e);
        }
    }
    
    private void initialAttribute(List<Class<?>>... lists) {
        for (List<Class<?>> list : lists) {
            for (Class<?> clazz : list) {
                DisplayAttribute flag = clazz.getAnnotation(DisplayAttribute.class);
                for (String suffix : flag.suffix()) {
                    List<DisplayInfo<T>> temp = displayKvs.computeIfAbsent(
                        suffix, k -> new LinkedList<>()
                    );
                    temp.add(new DisplayInfo<>(flag, (Class<? extends Display<T>>) clazz));
                }
            }
        }
        
        displayKvs.values().forEach(temp -> temp.sort((x, y) -> y.attr.priority() - x.attr.priority()));
    }
    
    public void display(String path) {
        String ext = FileHelper.getExtension(path);
        List<DisplayInfo<T>> temp = displayKvs.get(ext);
        if (temp != null) {
            for (DisplayInfo<T> info : temp) {
                try {
                    Display<T> display = info.clazz.getDeclaredConstructor(contextClass, String.class)
                        .newInstance(context, path);
                    DisplayAAA args = display.load();
                    if (args != null && args.param != null) {
                        tabView.show(args);
                        return;
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    LogHelper.error("display error: ", e);
                }
            }
        }
    }
    
    public void stop() {
        tabView.stop();
    }
}
