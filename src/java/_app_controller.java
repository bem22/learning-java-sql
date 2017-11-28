import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class _app_controller implements Initializable{

    _app_model m = new _app_model();
    Connection c = m.connection;
    public  static boolean addWindow_isOpen = false;


    @FXML
    private Button closeWindowButton;

    @FXML
    private Text numberOfEntries;

    @FXML
    private Text reportText;
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

    public static void setAdd_Closed(){
        addWindow_isOpen = false;
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
        if (!addWindow_isOpen) {
            try {
                Stage addPopUP = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = (Pane) loader.load(getClass().getResource("addPopUp.fxml").openStream());
                Scene scene = new Scene(root);
                addPopUP.setScene(scene);
                addPopUP.initModality(Modality.APPLICATION_MODAL);
                addPopUP.initStyle(StageStyle.UNDECORATED);
                addPopUP.show();
                addPopUP.setAlwaysOnTop(true);
                addWindow_isOpen = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        int cjid = m.getCrackerJID(getId());
        int chid = m.getCrackerHID(getId());
        int cgid = m.getCrackerGID(getId());

        reportText.setText(
                "This cracker has:" + '\n' +
                        "Joke = " + m.getJokeString(cjid) + '\n' +
                        "Hat = " + m.getHatDescription(chid) + '\n' +
                        "Gift = " + m.getGiftDescription(cgid) + '\n' +
                        "and a price of... " + (m.getGiftPrice(cgid)+m.getHatPrice(chid)+m.getJokeRoyalty(cjid))
        );
    }


    @FXML
    public void populateG(){
        m.populateCrackers();
    }



}
