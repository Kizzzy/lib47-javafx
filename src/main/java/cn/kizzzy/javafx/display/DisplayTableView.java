package cn.kizzzy.javafx.display;

import cn.kizzzy.data.TableFile;
import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.helper.StringHelper;
import cn.kizzzy.javafx.custom.CustomControlParamter;
import cn.kizzzy.javafx.custom.ICustomControl;
import cn.kizzzy.javafx.custom.LabeledTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
@CustomControlParamter(fxml = "/fxml/custom/display/display_table_view.fxml")
public class DisplayTableView extends DisplayViewAdapter implements ICustomControl, Initializable {
    
    @FXML
    private LabeledTextField fileNameTbf;
    
    @FXML
    private CheckBox filterToggle;
    
    @FXML
    private LabeledTextField filterColumn;
    
    @FXML
    private LabeledTextField filterString;
    
    @FXML
    private Button filterButton;
    
    @FXML
    private TableView<String[]> tbv;
    
    @FXML
    private Label info;
    
    private FilteredList<String[]> filteredList;
    
    public DisplayTableView() {
        this.init();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbv.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String[]>) c -> {
            for (String[] array : c.getList()) {
                info.setText(Arrays.toString(array));
                break;
            }
            info.setMinHeight(48);
            info.setWrapText(true);
        });
        
        filterButton.setOnAction(this::doFilter);
    }
    
    public void show(Object data) {
        TableFile<String[]> pkgTxtFile = (TableFile<String[]>) data;
        
        tbv.getColumns().clear();
        
        String[] temp = pkgTxtFile.dataList.get(0);
        for (int j = 0; j < temp.length; ++j) {
            final int k = j;
            TableColumn<String[], String> column = new TableColumn<>("" + j);
            column.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue()[k]));
            column.setComparator(new DisplayTableColumnComparator());
            column.setMinWidth(96);
            column.setMaxWidth(168);
            
            tbv.getColumns().add(column);
        }
        
        filteredList = new FilteredList<>(FXCollections.observableArrayList(pkgTxtFile.dataList));
        SortedList<String[]> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbv.comparatorProperty());
        tbv.setItems(sortedList);
    }
    
    @FXML
    private void doFilter(ActionEvent event) {
        if (!filterToggle.isSelected()) {
            filteredList.setPredicate(null);
            return;
        }
        
        if (StringHelper.isNotNullAndEmpty(filterColumn.getContent()) &&
            StringHelper.isNotNullAndEmpty(filterString.getContent())) {
            try {
                final int column = Integer.parseInt(filterColumn.getContent());
                final String key = filterString.getContent();
                filteredList.setPredicate(
                    strings -> {
                        try {
                            String value = strings[column];
                            return value != null && value.contains(key);
                        } catch (Exception e) {
                            return false;
                        }
                    });
            } catch (Exception e) {
                LogHelper.error(null, e);
            }
        }
    }
}
