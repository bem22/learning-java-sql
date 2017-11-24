import cracker.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utills.DBUtils;
import utills.TableInformation;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class _controller implements Initializable{

    _model m = new _model();
    Connection c = m.connection;


    @FXML
    private Button closeWindow_button;

    @FXML
    private Button queryTable;

    @FXML
    private ComboBox<String> table_combobox;

    @FXML
    private TableView table;



    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> s = DBUtils.getTableNames(c);
        this.table_combobox.setItems(FXCollections.observableArrayList(s));

    }
    @FXML
    public void closeWindow(ActionEvent close){

        Stage stage = (Stage) closeWindow_button.getScene().getWindow();
        stage.close();
    }
    public ObservableList list =
    @FXML
    public void queryTable(ActionEvent query){
        table.getColumns().clear();
        TableInformation i = m.getData(table_combobox.getValue());
        table.getColumns().addAll(i.getColumns());
        table.setItems();


    }

}
