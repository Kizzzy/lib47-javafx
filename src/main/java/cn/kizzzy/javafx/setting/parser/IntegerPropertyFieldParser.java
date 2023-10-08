package cn.kizzzy.javafx.setting.parser;

import cn.kizzzy.config.Range;
import cn.kizzzy.helper.LogHelper;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.lang.reflect.Field;

public class IntegerPropertyFieldParser extends AbstractFieldParser<Integer, IntegerProperty> {
    
    public IntegerPropertyFieldParser() {
        super(int.class, IntegerExpression::getValue);
    }
    
    @Override
    public boolean accept(final Class<?> clazz) {
        return IntegerProperty.class.isAssignableFrom(clazz);
    }
    
    @Override
    public Node createNode(final Class<?> clazz, final Field field, final Object target) {
        try {
            field.setAccessible(true);
            Object value = getValue(field, target);
            
            Range range = field.getAnnotation(Range.class);
            if (range != null) {
                HBox hBox = new HBox();
                hBox.setSpacing(8);
                
                Label label = new Label(value + "");
                label.setPrefWidth(48);
                
                Slider slider = new Slider();
                slider.setMin(range.min());
                slider.setMax(range.max());
                slider.setValue((int) value);
                slider.setBlockIncrement(range.step());
                slider.setShowTickLabels(true);
                slider.valueProperty().addListener((ob, ooo, nnn) -> {
                    try {
                        label.setText(nnn.intValue() + "");
                        
                        field.setAccessible(true);
                        field.set(target, nnn.intValue());
                    } catch (IllegalAccessException e) {
                        LogHelper.error(null, e);
                    } finally {
                        field.setAccessible(false);
                    }
                });
                
                hBox.getChildren().add(slider);
                HBox.setHgrow(slider, Priority.ALWAYS);
                
                hBox.getChildren().add(label);
                
                return hBox;
            }
            
            TextField textField = new TextField();
            textField.textProperty().setValue(value + "");
            textField.textProperty().addListener((ob, oldValue, newValue) -> {
                try {
                    field.setAccessible(true);
                    setValue(field, target, Integer.parseInt(newValue));
                } catch (IllegalAccessException e) {
                    LogHelper.error(null, e);
                } finally {
                    field.setAccessible(false);
                }
            });
            return textField;
        } catch (IllegalAccessException e) {
            LogHelper.error(null, e);
            return null;
        } finally {
            field.setAccessible(false);
        }
    }
}
