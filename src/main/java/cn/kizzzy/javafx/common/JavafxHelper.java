package cn.kizzzy.javafx.common;

import cn.kizzzy.helper.StringHelper;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JavafxHelper {
    
    public static ResizeListener addResizeListener(Stage stage) {
        ResizeListener listener = new ResizeListener(stage);
        stage.addEventFilter(MouseEvent.MOUSE_MOVED, listener);
        stage.addEventFilter(MouseEvent.MOUSE_PRESSED, listener);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
        stage.addEventFilter(MouseEvent.MOUSE_RELEASED, listener);
        return listener;
    }
    
    public static void removeResizeListener(Stage stage, ResizeListener listener) {
        stage.removeEventFilter(MouseEvent.MOUSE_MOVED, listener);
        stage.removeEventFilter(MouseEvent.MOUSE_PRESSED, listener);
        stage.removeEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
        stage.removeEventFilter(MouseEvent.MOUSE_RELEASED, listener);
    }
    
    public static <T> void initColumn(TableView<T> tableView, ColumnParameter[] parameters) {
        for (ColumnParameter parameter : parameters) {
            TableColumn<T, ?> column = new TableColumn<>(parameter.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(parameter.getProperty()));
            column.setPrefWidth(parameter.getWidth());
            tableView.getColumns().add(column);
        }
    }
    
    public static void initContextMenu(final Node target, final Supplier<Iterable<MenuItemArg>> supplier) {
        final ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);
        target.setOnContextMenuRequested(event -> {
            menu.getItems().clear();
            initContextMenu(menu, supplier.get());
            menu.show(target.getScene().getWindow(), event.getScreenX(), event.getScreenY());
            event.consume();
        });
    }
    
    public static <T> ContextMenu initContextMenu(final Node target, final Supplier<T> supplier, final MenuItemArg[] args) {
        return initContextMenu(target, supplier, Arrays.asList(args));
    }
    
    public static <T> ContextMenu initContextMenu(final Node target, final Supplier<T> supplier, final Iterable<MenuItemArg> args) {
        final ContextMenu menu = new ContextMenu();
        target.setOnContextMenuRequested(event -> {
            T context = supplier.get();
            if (context instanceof Node) {
                menu.show((Node) context, event.getScreenX(), event.getScreenY());
            } else if (context instanceof Window) {
                menu.show((Window) context, event.getScreenX(), event.getScreenY());
            }
        });
        return initContextMenu(menu, args);
    }
    
    public static ContextMenu initContextMenu(ContextMenu contextMenu, MenuItemArg[] args) {
        return initContextMenu(contextMenu, Arrays.asList(args));
    }
    
    public static ContextMenu initContextMenu(ContextMenu contextMenu, Iterable<MenuItemArg> args) {
        Objects.requireNonNull(contextMenu);
        Objects.requireNonNull(args);
        
        int group = -1;
        MenuNode menuRoot = null;
        for (MenuItemArg arg : args) {
            if (menuRoot == null) {
                group = arg.getGroup();
                menuRoot = new MenuNode(contextMenu.getItems());
            }
            
            if (arg.getGroup() != group) {
                contextMenu.getItems().add(new SeparatorMenuItem());
                group = arg.getGroup();
            }
            initContextMenuImpl(menuRoot, arg);
        }
        
        return contextMenu;
    }
    
    private static void initContextMenuImpl(MenuNode menuRoot, MenuItemArg arg) {
        String[] paths = arg.getPath().split("/");
        MenuNode parent = menuRoot;
        for (int i = 0, n = paths.length; i < n - 1; ++i) {
            String var0 = paths[i];
            MenuNode node = parent.retrieve(var0);
            if (node == null) {
                node = new MenuNode(new Menu(var0));
                parent.add(node);
            }
            parent = node;
        }
        
        MenuItem menu = new MenuItem(paths[paths.length - 1]);
        parent.add(new MenuNode(menu));
        menu.setOnAction(arg.getCallback()::accept);
        if (StringHelper.isNotNullAndEmpty(arg.getQuick())) {
            menu.setAccelerator(KeyCombination.valueOf(arg.getQuick()));
        }
    }
    
    public static void fadeInPane(Node node, long mills, EventHandler<ActionEvent> callback) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(mills), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        if (callback != null) {
            fadeIn.setOnFinished(callback);
        }
        fadeIn.play();
    }
    
    public static void fadeOutPane(Node pane, long mills, EventHandler<ActionEvent> callback) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(mills), pane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        if (callback != null) {
            fadeOut.setOnFinished(callback);
        }
        fadeOut.play();
    }
    
    public static void addDragFile(Node node, Consumer<List<File>> callback) {
        node.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
        });
        
        node.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                callback.accept(event.getDragboard().getFiles());
            }
        });
    }
    
    public static <T> void setComboBoxEditable(ComboBox<T> comboBox, ObservableList<T> source, StringConverter<T> converter) {
        comboBox.setEditable(true);
        comboBox.setItems(new FilteredList<>(source, p -> true));
        
        comboBox.getEditor().textProperty().addListener((o, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final Object selected = comboBox.getSelectionModel().getSelectedItem();
            
            Platform.runLater(() -> {
                if (selected == null || !selected.equals(converter.fromString(editor.getText()))) {
                    ((FilteredList<T>) comboBox.getItems()).setPredicate(item -> {
                        if (converter.toString(item).contains(newValue)) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                    comboBox.show();
                }
            });
        });
    }
}
