import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import okhttp3.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class _model {
    OkHttpClient client;
    String jokesAPI = "http://api.icndb.com/jokes/random/600";
    String numbersAPI = "https://qrng.anu.edu.au/API/jsonI.php?length=500&type=uint8" ;
    String randomAPI = "https://randomapi.com/api/0z3zu0yn?key=LWDP-98HG-JI1N-WQY6&results=20";
    String url = "jdbc:postgresql://localhost:5432/christmas";
    String user = "mihai";
    String password = "password123";
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

    public void populateJokes(){
        //Jokes
        PreparedStatement psm;
        String query = "INSERT INTO Jokes VALUES(?,?,?)";

        String jokes_String = utils.JSONUtils.getJSON(jokesAPI, client);
        String prices_String = utils.JSONUtils.getJSON(numbersAPI, client);
        JSONObject jokeJSON = new JSONObject(jokes_String);
        JSONObject costJSON = new JSONObject(prices_String);

        JSONArray jokes = jokeJSON.getJSONArray("value");
        JSONArray costs = costJSON.getJSONArray("data");

        for(int i =0 ; i<500; i++){
              try{
                psm = connection.prepareStatement(query);
                psm.setInt(1,jokes.getJSONObject(i).getInt("id"));
                psm.setString(2, jokes.getJSONObject(i).getString("joke"));
                psm.setInt(3, costs.getInt(i));
                psm.execute();

            }catch (Exception e){continue;}
        }


    }

    public void populateGifts(){
        PreparedStatement psm;
        String query = "INSERT INTO Gifts VALUES(?,?,?)";
        String api = utils.JSONUtils.getJSON(randomAPI, client);
        JSONArray gifts;
        for(int i=0; i<20; i++){
            gifts = new JSONArray(new JSONObject(utils.JSONUtils.getJSON(randomAPI, client)).getJSONArray("result"));
            for(int j=0; j<gifts.length(); j++){
                System.out.println(gifts.getJSONObject(i).get("hid"));
            }
        }


    }

    public void populateHats(){

    }



}
