package cn.kizzzy.javafx.custom;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

abstract class LabeledSliderView extends AnchorPane implements ICustomControl {
    
    @FXML
    protected Label name_lbl;
    
    @FXML
    protected Slider slider;
    
    @FXML
    protected Label value_lbl;
    
    public LabeledSliderView() {
        init();
    }
    
    public StringProperty nameProperty() {
        return name_lbl.textProperty();
    }
    
    public void setName(String name) {
        nameProperty().set(name);
    }
    
    public String getName() {
        return nameProperty().get();
    }
    
    public DoubleProperty valueProperty() {
        return slider.valueProperty();
    }
    
    public void setValue(double value) {
        valueProperty().set(value);
    }
    
    public double getValue() {
        return valueProperty().get();
    }
    
    public DoubleProperty minProperty() {
        return slider.minProperty();
    }
    
    public void setMin(double min) {
        minProperty().set(min);
    }
    
    public double getMin() {
        return minProperty().get();
    }
    
    public DoubleProperty maxProperty() {
        return slider.maxProperty();
    }
    
    public void setMax(double max) {
        maxProperty().set(max);
    }
    
    public double getMax() {
        return maxProperty().get();
    }
}

@CustomControlParamter(fxml = "/fxml/labeled_slider.fxml")
public class LabeledSlider extends LabeledSliderView implements Initializable {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.valueProperty().addListener(this::OnValueChanged);
    }
    
    private void OnValueChanged(Observable observable, Number oldValue, Number newValue) {
        int nThread = (int) Math.round((Double) newValue);
        value_lbl.setText("" + nThread);
    }
}

