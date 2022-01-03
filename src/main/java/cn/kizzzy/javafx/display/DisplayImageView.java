package cn.kizzzy.javafx.display;

import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
@CustomControlParamter(fxml = "/fxml/custom/display/display_image_view.fxml")
public class DisplayImageView extends DisplayViewAdapter implements ICustomControl, Initializable {
    
    @FXML
    private CheckBox canvas_black;
    
    @FXML
    private CheckBox image_filter;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Canvas canvas;
    
    private List<DisplayParam> params;
    
    public DisplayImageView() {
        this.init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas_black.selectedProperty().addListener((observable, oldValue, newValue) -> showImpl());
    }
    
    public void show(Object data) {
        params = (List<DisplayParam>) data;
        showImpl();
    }
    
    private void showImpl() {
        if (params == null) {
            
            return;
        }
        
        float maxWidth = 400, maxHeight = 400;
        
        for (DisplayParam param : params) {
            if (param.getX() + param.getWidth() > maxWidth) {
                maxWidth = param.getX() + param.getWidth();
            }
            if (param.getY() + param.getHeight() > maxHeight) {
                maxHeight = param.getY() + param.getHeight();
            }
        }
        
        canvas.setWidth(maxWidth);
        canvas.setHeight(maxHeight);
        
        GraphicsContext context = canvas.getGraphicsContext2D();
        
        context.clearRect(0, 0, maxWidth, maxHeight);
        
        if (canvas_black.isSelected()) {
            context.setFill(Color.BLACK);
            context.fillRect(0, 0, maxWidth, maxHeight);
        }
        
        for (DisplayParam param : params) {
            context.save();
            Rotate rotate = new Rotate(param.getRotateZ(), param.getX() + param.getWidth() / 2, param.getY() + param.getHeight() / 2);
            context.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
            context.drawImage(
                SwingFXUtils.toFXImage(param.getImage(), null),
                0,
                0,
                param.getImage().getWidth(),
                param.getImage().getHeight(),
                param.getX() + (param.isFlipX() ? param.getWidth() : 0),
                param.getY() + (param.isFlipY() ? param.getHeight() : 0),
                param.getWidth() * (param.isFlipX() ? -1 : 1),
                param.getHeight() * (param.isFlipY() ? -1 : 1)
            );
            context.restore();
        }
        
        context.setStroke(canvas_black.isSelected() ? Color.WHITE : Color.BLACK);
        context.strokeLine(150, 200, 250, 200);
        context.strokeLine(200, 180, 200, 220);
    }
}
