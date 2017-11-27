import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DBUtils;
import java.net.URL;
import java.sql.Connection;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;

public class _controller implements Initializable{

    _model m = new _model();
    Connection c = m.connection;


    @FXML
    private Button closeWindowButton;

    @FXML
    private Text numberOfEntries;

    @FXML
    private Button queryTable;

    @FXML
    private TextField searchBox;


    @FXML
    private Button populate;

    @FXML
    private Button addValue;

    @FXML
    private Button removeValue;

    @FXML
    private Button getReport;

    @FXML
    private ComboBox<String> tableCombobox;

    @FXML
    private TableView table;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> s = DBUtils.getTableNames(c);
        this.tableCombobox.setItems(FXCollections.observableArrayList(s));
    }
    @FXML
    public void closeWindow(ActionEvent close){
        Stage stage = (Stage) closeWindowButton.getScene().getWindow();
        stage.close();
    }


    public void setEntriesNumber(){
        numberOfEntries.setText(String.valueOf(m.count(tableCombobox.getValue()) + " Entries"));
    }

    @FXML
    public void searchTable(){
        if(tableCombobox.getValue()!=null&&tableCombobox.getValue()!=""){
            if(!searchBox.getText().trim().equals("")){
                table.getColumns().clear();
                table.getColumns().addAll(m.getColumns(tableCombobox.getValue()));
                table.setItems(m.getData(tableCombobox.getValue(), searchBox.getText()));
                setEntriesNumber();
            }
            else {
                table.getColumns().clear();
                table.getColumns().addAll(m.getColumns(tableCombobox.getValue()));
                table.setItems(m.getData((tableCombobox.getValue())));
                setEntriesNumber();
            }
        }
    }

    @FXML
    public void addValue(){

    }

    public int getId(){
        String s = ""+table.getSelectionModel().getSelectedItem();
        return Integer.valueOf(s.substring(1,s.indexOf(' ')-1));

    }
    @FXML
    public void removeValue(ActionEvent e){
        m.removeId(tableCombobox.getValue(), getId());
        searchTable();
        setEntriesNumber();


    }

    @FXML
    public void getReport(ActionEvent e){

    }



    @FXML
    public void populateG(){
        m.populateCrackers();
    }



}
