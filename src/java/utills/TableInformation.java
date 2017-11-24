package utills;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.util.Observable;

public class TableInformation {
    private ObservableList data;
    private ObservableList<TableColumn> columns;

    public TableInformation(ObservableList<TableColumn> columns, ObservableList<ObservableList> data){
        this.data = data;
        this.columns = columns;
    }
    public ObservableList getData() {
        return data;
    }

    public void setData(ObservableList data) {
        this.data = data;
    }

    public ObservableList <TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(ObservableList<TableColumn> columns) {
        this.columns = columns;
    }
}
