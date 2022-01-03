package cn.kizzzy.javafx;

import cn.kizzzy.javafx.setting.ISettingDialogFactory;
import cn.kizzzy.javafx.setting.SettingConfig;
import cn.kizzzy.javafx.setting.SettingConfigs;
import cn.kizzzy.javafx.setting.SettingDialogFactory;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.handler.JsonFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class MainOfSettingDialog extends Application {
    
    public static class TestConfig {
        public static class Config2 {
            public boolean child;
        }
        
        public boolean checked;
        public int count;
        public String filepath;
        public Config2 config2;
        
        public List<Config2> config2s
            = new LinkedList<>();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        SettingConfig count_config = new SettingConfig();
        count_config.holderType = TestConfig.class;
        count_config.fieldName = "count";
        count_config.alias = "数量";
        
        SettingConfig list_config = new SettingConfig();
        list_config.holderType = TestConfig.class;
        list_config.fieldName = "config2s";
        list_config.genericType = TestConfig.Config2.class;
    
        /*TestConfig testConfig = new TestConfig();
        testConfig.config2s.add(new TestConfig.Config2());
        testConfig.config2s.add(new TestConfig.Config2());
        testConfig.config2s.add(new TestConfig.Config2());*/
        
        IPackage vfs = new FilePackage("D:/Temp");
        vfs.getHandlerKvs().put(MainOfSettingDialog.TestConfig.class, new JsonFileHandler<>(MainOfSettingDialog.TestConfig.class));
        
        TestConfig testConfig = vfs.load("test.config", TestConfig.class);
        
        ISettingDialogFactory factory = new SettingDialogFactory(primaryStage);
        factory.show(testConfig, new SettingConfigs(count_config, list_config));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
