package cn.kizzzy.javafx.common;

public class ColumnParameter {
    private String property;
    private String name;
    private int width;
    private boolean editable;
    private boolean sortable;

    public ColumnParameter(String property, String name, int width, boolean editable, boolean sortable) {
        this.property = property;
        this.name = name;
        this.width = width;
        this.editable = editable;
        this.sortable = sortable;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
}