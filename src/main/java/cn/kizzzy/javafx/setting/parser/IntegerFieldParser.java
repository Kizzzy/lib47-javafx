package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Range;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.lang.reflect.Field;

public class IntegerFieldParser extends AbstractFieldParser<Integer, Integer> {
    
    public IntegerFieldParser() {
        super(int.class, Integer::intValue, 0);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return clazz.getName().startsWith("int");
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        int value = getValue(field, target);
        
        Range range = field.getAnnotation(Range.class);
        if (range != null) {
            HBox hBox = new HBox();
            hBox.setSpacing(8);
            
            Label label = new Label(value + "");
            label.setPrefWidth(48);
            
            Slider slider = new Slider();
            slider.setMin(range.min());
            slider.setMax(range.max());
            slider.setValue(value);
            slider.setBlockIncrement(range.step());
            slider.setShowTickLabels(true);
            slider.valueProperty().addListener((ob, oldValue, newValue) -> {
                label.setText(newValue.intValue() + "");
                
                setValue(field, target, newValue.intValue());
            });
            
            hBox.getChildren().add(slider);
            HBox.setHgrow(slider, Priority.ALWAYS);
            
            hBox.getChildren().add(label);
            
            return hBox;
        }
        
        TextField textField = new TextField();
        textField.textProperty().setValue(value + "");
        textField.textProperty().addListener((ob, oldValue, newValue) -> {
            setValue(field, target, Integer.parseInt(newValue));
        });
        return textField;
    }
}
