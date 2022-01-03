package cn.kizzzy.javafx.display;

import cn.kizzzy.helper.StringHelper;
import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import cn.kizzzy.javafx.custom.LabeledTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
@CustomControlParamter(fxml = "/fxml/custom/display/display_text_view.fxml")
public class DisplayTextView extends DisplayViewAdapter implements ICustomControl, Initializable {
    
    @FXML
    private LabeledTextField filterText;
    
    @FXML
    private Button filterButton;
    
    @FXML
    private TextArea textArea;
    
    public DisplayTextView() {
        this.init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filterButton.setOnAction(this::doFilter);
    }
    
    public void show(Object data) {
        String text = (String) data;
        
        textArea.setText(text);
    }
    
    @FXML
    protected void doFilter(ActionEvent actionEvent) {
        String filter = filterText.getContent();
        if (StringHelper.isNotNullAndEmpty((filter))) {
            String content = textArea.getText();
            String[] all = content.split("\n");
            for (int i = 0; i < all.length; i++) {
                if (all[i].contains(filter)) {
                    textArea.setScrollTop(i * 1d * textArea.getHeight() / (textArea.getHeight() / 15));
                    break;
                }
            }
        }
    }
}
