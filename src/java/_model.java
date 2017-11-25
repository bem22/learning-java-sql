import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import okhttp3.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import utills.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;

public class _model {
    OkHttpClient client;
    String url = "jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk:5432/";
    String user = "meb648";
    String password = "Asd123asd";
    Connection connection;
    public _model(){
        try{
            client = new OkHttpClient();
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

    public void loadJokes(){
        String s = utills.JSONUtils.getJSON("http://api.icndb.com/jokes/random/600", client);
        String t = utills.JSONUtils.getJSON("https://qrng.anu.edu.au/API/jsonI.php?length=500&type=uint8", client);
        JSONObject jokeJSON = new JSONObject(s);
        JSONObject costJSON = new JSONObject(t);

        JSONArray jokes = jokeJSON.getJSONArray("value");
        JSONArray costs = costJSON.getJSONArray("data");
        for(int i =0 ; i<400; i++){
            System.out.println(costs.get(i) + " " + jokes.getJSONObject(i).get("id")+ ". "+ jokes.getJSONObject(i).get("joke"));
        }

    }


}
