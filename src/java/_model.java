import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import utills.DBUtils;
import utills.TableInformation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Observable;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class _model {

    String url = "jdbc:postgresql://localhost:5432/mihai";
    String user = "mihai";
    String password = "password123";
    Connection connection;
    public _model(){
        try{
            this.connection= DBUtils.getConnectionPSQL(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return this.connection !=null;
    }

    public ObservableList<TableColumn> getColumns(String table){
        ObservableList <TableColumn> columns = FXCollections.observableArrayList();
        String query = "SELECT * FROM " + table;
        try{
            ResultSet rs = connection.createStatement().executeQuery(query);
            for(int i=0; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                columns.addAll(col);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return columns;

    }

    public ObservableList<ObservableList> getData(String table){
        ObservableList <ObservableList> data = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM " + table;
            ResultSet rs = connection.createStatement().executeQuery(query);
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }


}
