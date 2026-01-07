package cn.kizzzy.javafx.display;

import cn.kizzzy.base.AttributeWithClass;
import cn.kizzzy.clazz.ClassFilter;
import cn.kizzzy.clazz.ClassFinderHelper;
import cn.kizzzy.helper.ByteHelper;
import cn.kizzzy.helper.FileHelper;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.tree.Leaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DisplayOperator {
    
    private static final Logger logger = LoggerFactory.getLogger(DisplayOperator.class);
    
    private final String namespace;
    
    private final DisplayTabView tabView;
    
    private final Map<String, List<AttributeWithClass<DisplayLoaderAttribute, DisplayLoader>>> displayKvs
        = new HashMap<>();
    
    private final List<AttributeWithClass<DisplayLoaderAttribute, DisplayLoader>> magics
        = new ArrayList<>();
    
    public DisplayOperator(String namespace, DisplayTabView tabView) {
        this.namespace = namespace;
        this.tabView = tabView;
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
                    return clazz.isAnnotationPresent(DisplayLoaderAttribute.class);
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
                    return clazz.isAnnotationPresent(DisplayLoaderAttribute.class);
                }
            });
            
            initialAttribute(list1, list2);
            
        } catch (Exception e) {
            logger.error("list display error", e);
        }
    }
    
    private void initialAttribute(List<Class<?>>... lists) {
        for (List<Class<?>> list : lists) {
            for (Class<?> clazz : list) {
                DisplayLoaderAttribute attr = clazz.getAnnotation(DisplayLoaderAttribute.class);
                if (attr.suffix() != null) {
                    for (String suffix : attr.suffix()) {
                        List<AttributeWithClass<DisplayLoaderAttribute, DisplayLoader>> temp =
                            displayKvs.computeIfAbsent(suffix.toLowerCase(), k -> new LinkedList<>());
                        temp.add(new AttributeWithClass<>(attr, (Class<? extends DisplayLoader>) clazz));
                    }
                }
                if (attr.magic() != null && attr.magic().length > 0) {
                    magics.add(new AttributeWithClass<>(attr, (Class<? extends DisplayLoader>) clazz));
                }
            }
        }
        
        displayKvs.values().forEach(temp -> temp.sort((x, y) -> y.attr.priority() - x.attr.priority()));
    }
    
    public void display(IPackage vfs, Leaf leaf) {
        String ext = FileHelper.getExtension(leaf.path).toLowerCase();
        List<AttributeWithClass<DisplayLoaderAttribute, DisplayLoader>> temp = displayKvs.get(ext);
        if (temp != null) {
            for (AttributeWithClass<DisplayLoaderAttribute, DisplayLoader> info : temp) {
                try {
                    DisplayLoader display = info.clazz.newInstance();
                    tabView.show(display, vfs, leaf);
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("display error: ", e);
                }
            }
        }
        
        for (AttributeWithClass<DisplayLoaderAttribute, DisplayLoader> info : magics) {
            for (Magic magic : info.attr.magic()) {
                try (IFullyReader reader = vfs.getInputStreamGetter(leaf.path).getInput()) {
                    if (ByteHelper.equals(magic.getMagic(), reader.readBytes(magic.getLength()))) {
                        DisplayLoader display = info.clazz.newInstance();
                        tabView.show(display, vfs, leaf);
                    }
                } catch (Exception e) {
                    logger.error("display error: ", e);
                }
            }
        }
    }
    
    public void stop() {
        tabView.stop();
    }
}
