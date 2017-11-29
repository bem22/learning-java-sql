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
    private Text connectionInfo;
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
        getReport.setDisable(true);
        connectionInfo.setText("You are connected to: " + m.url);
    }
    @FXML
    public void closeWindow(ActionEvent close){
        Stage stage = (Stage) closeWindowButton.getScene().getWindow();
        stage.close();
    }


    public void setEntriesNumber(){
        numberOfEntries.setText(String.valueOf(table.getItems().size()) + " Entries");
    }

    public static void setAdd_Closed(){
        addWindow_isOpen = false;
    }

    @FXML
    public void searchTable(){
        if(tableCombobox.getValue()!=null&&tableCombobox.getValue()!=""){
            if(tableCombobox.getValue().equals("crackers")||tableCombobox.getValue().equals("jokes")){
                getReport.setDisable(false);
            }
            else getReport.setDisable(true);
            if(!searchBox.getText().trim().equals("")){
                numberOfEntries.setDisable(false);
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
                _add_controller addController = (_add_controller) loader.getController();
                addController.setModel(m);
                addController.setContentType(tableCombobox.getValue());
                addController.showColumns();
                if(tableCombobox.getValue().equals("crackers")) {
                    addController.percentageSlider.setDisable(false);
                }
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
        String  s="";
        s += table.getSelectionModel().getSelectedItem();

        if(!table.getSelectionModel().isEmpty()) {
            return Integer.valueOf(s.substring(1, s.indexOf(' ') - 1));
        }
        return 0;

    }
    @FXML
    public void removeValue(ActionEvent e){
        m.removeId(tableCombobox.getValue(), getId());
        searchTable();
        setEntriesNumber();
    }

    @FXML
    public void getCrackerReport(){
        int cjid = m.getCrackerJID(getId());
        int chid = m.getCrackerHID(getId());
        int cgid = m.getCrackerGID(getId());
        int crackerSalePrice = m.getCrackerSalePrice(getId());
        int crackerCostPrice = m.getGiftPrice(cgid)+m.getHatPrice(chid)+m.getJokeRoyalty(cjid);
        reportText.setText("");
        reportText.setText(
                "This cracker has a sale price price of " + crackerSalePrice
                        + " and a cost price of " + crackerCostPrice
                        + ", which results in a profit of "
                        + (crackerSalePrice-crackerCostPrice) + "."
                        + " The joke is: "
                        + m.getJokeString(cjid) + "\", "
                        + " and the hat is described as: " + m.getHatDescription(chid) + ", "
                        + "and the gift is: " + m.getGiftDescription(cgid));
    }

    @FXML
    public void getJokeReport(){

        reportText.setText("This joke has a royalty of: " + m.getJokeRoyalty(getId()) + " and a payment due of: " + m.getPaymentDue(getId())
        );


    }

    @FXML
    public void getReport(ActionEvent e){
        if(getId()!=0) {
            numberOfEntries.setText(":D");
            if (tableCombobox.getValue().equals("crackers")) {
                getCrackerReport();
            } else if (tableCombobox.getValue().equals("jokes")) {
                getJokeReport();

            }
        }
        else numberOfEntries.setText("You have to select an item first");

    }


    @FXML
    public void populateG(){
        m.populateCrackers();
    }



}
