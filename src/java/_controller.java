import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import utills.DBUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class _controller implements Initializable{

    _model m = new _model();
    Connection c = m.connection;


    @FXML
    private Button close_button;

    @FXML
    private ComboBox<String> table_combobox;

    @FXML
    private ScrollPane main_display_scroll_pane;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> s = DBUtils.getTableNames(c);
        for(int i = 0; i<s.size();i++){
            System.out.print(s.get(i) + " ");
        }
        this.table_combobox.setItems(FXCollections.observableArrayList(s));

    }
}
