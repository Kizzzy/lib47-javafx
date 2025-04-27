package cn.kizzzy.javafx.common;

import cn.kizzzy.helper.StringHelper;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JavafxChooser {
    
    private Supplier<String> getter;
    private Consumer<String> setter;
    
    private String title;
    private Window window;
    private FileChooser.ExtensionFilter[] filters;
    
    private JavafxChooser() {
    
    }
    
    public static JavafxChooser newBuilder() {
        return new JavafxChooser();
    }
    
    public JavafxChooser setSetter(Consumer<String> setter) {
        this.setter = setter;
        return this;
    }
    
    public JavafxChooser setGetter(Supplier<String> getter) {
        this.getter = getter;
        return this;
    }
    
    public JavafxChooser setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public JavafxChooser setWindow(Window window) {
        this.window = window;
        return this;
    }
    
    public JavafxChooser setFilters(FileChooser.ExtensionFilter... filters) {
        this.filters = filters;
        return this;
    }
    
    private File getValidFolder(String path) {
        if (StringHelper.isNullOrEmpty(path)) {
            return null;
        }
        
        File file = new File(path);
        if (file.isFile()) {
            file = file.getParentFile();
        }
        
        do {
            if (file.exists()) {
                return file;
            }
            file = file.getParentFile();
        } while (file != null);
        
        return null;
    }
    
    public void chooseFolder(Consumer<File> callback) {
        DirectoryChooser chooser = new DirectoryChooser();
        
        if (StringHelper.isNotNullAndEmpty(title)) {
            chooser.setTitle(title);
        }
        
        if (getter != null) {
            File last = getValidFolder(getter.get());
            if (last != null) {
                chooser.setInitialDirectory(last);
            }
        }
        
        File file = chooser.showDialog(window);
        if (file != null && setter != null) {
            this.setter.accept(file.getAbsolutePath());
            
            if (callback != null) {
                callback.accept(file);
            }
        }
    }
    
    public void chooseFile(Consumer<File> callback) {
        FileChooser chooser = new FileChooser();
        
        if (StringHelper.isNotNullAndEmpty(title)) {
            chooser.setTitle(title);
        }
        
        if (getter != null) {
            File last = getValidFolder(getter.get());
            if (last != null) {
                chooser.setInitialDirectory(last);
            }
        }
        
        if (filters != null) {
            chooser.getExtensionFilters().addAll(filters);
        }
        
        File file = chooser.showOpenDialog(window);
        if (file != null && setter != null) {
            this.setter.accept(file.getAbsolutePath());
            
            if (callback != null) {
                callback.accept(file);
            }
        }
    }
    
    public void saveFile(Consumer<File> callback) {
        FileChooser chooser = new FileChooser();
        
        if (StringHelper.isNotNullAndEmpty(title)) {
            chooser.setTitle(title);
        }
        
        if (getter != null) {
            File last = getValidFolder(getter.get());
            if (last != null) {
                chooser.setInitialDirectory(last);
            }
        }
        
        if (filters != null) {
            chooser.getExtensionFilters().addAll(filters);
        }
        
        File file = chooser.showSaveDialog(window);
        if (file != null && setter != null) {
            this.setter.accept(file.getAbsolutePath());
            
            if (callback != null) {
                callback.accept(file);
            }
        }
    }
    
    public void chooseFiles(Consumer<List<File>> callback) {
        FileChooser chooser = new FileChooser();
        
        if (StringHelper.isNotNullAndEmpty(title)) {
            chooser.setTitle(title);
        }
        
        if (getter != null) {
            File last = getValidFolder(getter.get());
            if (last != null) {
                chooser.setInitialDirectory(last);
            }
        }
        
        if (filters != null) {
            chooser.getExtensionFilters().addAll(filters);
        }
        
        List<File> files = chooser.showOpenMultipleDialog(window);
        if (files != null && !files.isEmpty() && setter != null) {
            this.setter.accept(files.get(0).getParent());
            
            if (callback != null) {
                callback.accept(files);
            }
        }
    }
}
