package cn.kizzzy.javafx.display;

import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
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
    
    @FXML
    private Button play_button;
    
    private int index;
    private int total;
    
    private Timeline timeline;
    
    private DisplayTracks tracks;
    
    private DisplayFrame[] frames;
    
    public DisplayImageView() {
        this.init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas_black.selectedProperty().addListener(this::onBlackChanged);
    }
    
    private void onBlackChanged(Observable observable, boolean oldValue, boolean newValue) {
        showFrames(frames);
    }
    
    @FXML
    private void doPrev(ActionEvent event) {
        index--;
        if (index < 0) {
            index = 0;
        }
        showImpl(index);
    }
    
    @FXML
    private void doNext(ActionEvent event) {
        index++;
        if (index >= total) {
            index = total - 1;
        }
        showImpl(index);
    }
    
    @FXML
    private void doPlay(ActionEvent event) {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
            play_button.setText("播放");
        } else {
            timeline.play();
            play_button.setText("停止");
        }
    }
    
    public void show(Object data) {
        if (timeline != null) {
            play_button.setText("播放");
            
            timeline.stop();
            timeline = null;
        }
        
        tracks = (DisplayTracks) data;
        frames = new DisplayFrame[tracks.tracks.size()];
        
        index = 0;
        total = 0;
        
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        int i = 0;
        for (DisplayTrack track : tracks.tracks) {
            if (track.frames.size() > total) {
                total = track.frames.size();
            }
            for (DisplayFrame frame : track.frames) {
                KeyFrame keyFrame = new KeyFrame(Duration.millis(frame.time), new KeyFrameHandler(i, frame));
                timeline.getKeyFrames().add(keyFrame);
            }
            i++;
        }
        
        showImpl(index);
    }
    
    private void showImpl(int index) {
        int i = 0;
        for (DisplayTrack track : tracks.tracks) {
            int target = Math.max(0, Math.min(index, track.frames.size() - 1));
            if (target <= track.frames.size() - 1) {
                frames[i] = track.frames.get(target);
            } else {
                frames[i] = null;
            }
            i++;
        }
        showFrames(frames);
    }
    
    private void showFrames(DisplayFrame[] frames) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        
        if (frames == null || frames.length <= 0) {
            context.clearRect(0, 0, 1920, 1080);
            return;
        }
        
        List<DisplayFrame> list = new LinkedList<>();
        
        float maxWidth = 400, maxHeight = 400;
        for (DisplayFrame frame : frames) {
            if (frame != null) {
                if (frame.x + frame.width > maxWidth) {
                    maxWidth = frame.x + frame.width;
                }
                if (frame.y + frame.height > maxHeight) {
                    maxHeight = frame.y + frame.height;
                }
                
                list.add(frame);
            }
        }
        canvas.setWidth(maxWidth);
        canvas.setHeight(maxHeight);
        
        context.clearRect(0, 0, maxWidth, maxHeight);
        
        if (canvas_black.isSelected()) {
            context.setFill(Color.BLACK);
            context.fillRect(0, 0, maxWidth, maxHeight);
        }
        
        list.sort(Comparator.comparingInt(x -> x.order));
        for (DisplayFrame frame : list) {
            if (frame != null) {
                showFrame(context, frame);
            }
        }
        
        context.setStroke(canvas_black.isSelected() ? Color.WHITE : Color.BLACK);
        context.strokeLine(150, 200, 250, 200);
        context.strokeLine(200, 180, 200, 220);
    }
    
    private void showFrame(GraphicsContext context, DisplayFrame frame) {
        context.save();
        Rotate rotate = new Rotate(frame.rotateZ, frame.x + frame.width / 2, frame.y + frame.height / 2);
        context.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        context.drawImage(
            SwingFXUtils.toFXImage(frame.image, null),
            0,
            0,
            frame.image.getWidth(),
            frame.image.getHeight(),
            frame.x + (frame.flipX ? frame.width : 0),
            frame.y + (frame.flipY ? frame.height : 0),
            frame.width * (frame.flipX ? -1 : 1),
            frame.height * (frame.flipY ? -1 : 1)
        );
        context.restore();
        context.fillText(frame.extra, frame.x, frame.y);
    }
    
    private class KeyFrameHandler implements EventHandler<ActionEvent> {
        private final int i;
        private final DisplayFrame frame;
        
        public KeyFrameHandler(int i, DisplayFrame frame) {
            this.i = i;
            this.frame = frame;
        }
        
        @Override
        public void handle(ActionEvent event) {
            frames[i] = frame;
            showFrames(frames);
        }
    }
}
