
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class _add_controller implements Initializable{
    public void initialize(URL location, ResourceBundle resources) {

        percentageSlider.setDisable(true);



    }

    private _app_model m;
    private  String contentType = "";

    @FXML
    Button closeAddButton;

    @FXML
    GridPane columnContainer;

    @FXML
    Slider percentageSlider;


    @FXML
    Text percentageText;

    @FXML
    public void closeAddPane(ActionEvent e){
        Stage stage = (Stage) closeAddButton.getScene().getWindow();
        _app_controller.setAdd_Closed();
        stage.close();
    }

    @FXML
    public void changePercentageText(){
        percentageText.setText(String.valueOf((int)percentageSlider.getValue()));
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setModel(_app_model m){
        this.m = m;
    }



    @FXML
    public void addItem(){
        String [] a = new String[columnContainer.getChildren().size()-1];
        for(int i=1; i<columnContainer.getChildren().size();i++){
            TextField tf = (TextField) columnContainer.getChildren().get(i);
            a[i-1] = tf.getText();
        }
        switch (contentType){
            case "jokes":
                m.addJoke(Integer.valueOf(a[0]),a[1],Integer.valueOf(a[2]));
                break;
            case "hats":
                m.addHat(Integer.valueOf(a[0]),a[1], Integer.valueOf(a[2]));
                break;
            case "gifts":
                m.addGift(Integer.valueOf(a[0]),a[1], Integer.valueOf(a[2]));
                break;
            case "crackers":
                m.addCracker(Integer.valueOf(a[0]), a[1], Integer.valueOf(a[2]),Integer.valueOf(a[3]), Integer.valueOf(a[4]), Integer.valueOf(a[5]), (int)percentageSlider.getValue());
        }

    }



    public void showColumns(){
        ObservableList<TableColumn> tc = m.getColumns(contentType);
        columnContainer.add(new Text("Fill this form and click \"Add\" "),0,0);
        int columnCount = m.getColumns(contentType).size();
        if (contentType.equals("crackers")); columnCount--;
        for(int i=1; i<=columnCount; i++){

            TextField text = new TextField();
            text.setPromptText(tc.get(i-1).getText());
            columnContainer.add(text, 0, i);
        }
    }
}
