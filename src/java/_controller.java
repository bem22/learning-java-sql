import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utills.DBUtils;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
    private Button create_Tables;

    @FXML
    private Button drop_tables;

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

    @FXML
    public void queryTable(ActionEvent query){
        table.getColumns().clear();
        table.getColumns().addAll(m.getColumns(table_combobox.getValue()));
        table.setItems(m.getData((table_combobox.getValue())));
    }
    @FXML void dropTables(){
        String query1="DROP TABLE Crackers",
               query2 = "DROP TABLE Hats",
               query3="DROP TABLE Gifts",
               query4="DROP TABLE Jokes";
        try{
            m.connection.prepareStatement(query1).execute();
            m.connection.prepareStatement(query2).execute();
            m.connection.prepareStatement(query4).execute();
            m.connection.prepareStatement(query3).execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void createTables(){
        String query1 ="CREATE TABLE Jokes(" +
                "  jid INTEGER," +
                "  joke VARCHAR NOT NULL," +
                "  royality INTEGER NOT NULL," +
                "  CONSTRAINT Joke_Primary PRIMARY KEY (jid)," +
                "  CONSTRAINT Joke_Joke_Unique UNIQUE (joke)" +
                ")";
        String query2="CREATE TABLE Hats(" +
                "  hid INTEGER," +
                "  description VARCHAR NOT NULL," +
                "  price INTEGER NOT NULL," +
                "  CONSTRAINT Hat_Primary PRIMARY KEY (hid)" +
                ")";
        String query3="CREATE TABLE Gifts(" +
                "  gid INTEGER," +
                "  description VARCHAR NOT NULL," +
                "  price INTEGER NOT NULL," +
                "  CONSTRAINT Gi_Primary PRIMARY KEY (gid)" +
                ")";


        String query4 = "CREATE TABLE Crackers(" +
                "  cid INTEGER," +
                "  name VARCHAR NOT NULL," +
                "  jid INTEGER," +
                "  hid INTEGER," +
                "  gid INTEGER," +
                "  CONSTRAINT Cracker_Primary PRIMARY KEY (cid)," +
                "  CONSTRAINT Cracker_Name_Unique UNIQUE(name)," +
                "  CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid)," +
                "  CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid)," +
                "  CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid)" +
                ")";

        try {
            m.connection.prepareStatement(query1).execute();
            m.connection.prepareStatement(query2).execute();
            m.connection.prepareStatement(query3).execute();
            m.connection.prepareStatement(query4).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
