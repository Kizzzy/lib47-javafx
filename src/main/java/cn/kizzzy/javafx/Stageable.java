package cn.kizzzy.javafx;

import javafx.stage.Stage;

public interface Stageable<Args> {
    
    void show(Stage stage, Args args);
}
