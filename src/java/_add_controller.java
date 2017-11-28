import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class _add_controller implements Initializable{
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    Button closeAddButton;

    @FXML
    public void closeAddPane(ActionEvent e){
        Stage stage = (Stage) closeAddButton.getScene().getWindow();
        _app_controller.setAdd_Closed();
        stage.close();

    }
}
