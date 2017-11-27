import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import okhttp3.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;

public class _model {
    OkHttpClient client;
    String giftAPI = "https://randomapi.com/api/0z3zu0yn?key=LWDP-98HG-JI1N-WQY6&results=20";
    String hatAPI = "https://randomapi.com/api/b5xkyv97?key=8F47-34PG-ZO78-E8XG&results=20";
    String crackerAPI = "https://randomapi.com/api/cmyz100t?key=R7MX-PY30-DETX-1IKW&results=20";
    String jokeAPI = "http://api.icndb.com/jokes/random/600";
    String numberAPI = "https://randomapi.com/api/0z3zu0yn?key=LWDP-98HG-JI1N-WQY6" ;
    String url = "jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/meb648";
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

    public int count(String table){
        PreparedStatement psm;
        String query = "SELECT COUNT(1) AS COUNT FROM " + table;
        try {

            psm = connection.prepareStatement(query);

            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }


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
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM " + table;
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return data;

    }

    public ObservableList<ObservableList> getData(String table, String object){
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        String columnName;
        try {
            if(isDigits(object)) {
                columnName = table.substring(0, 1) + "id";
            }
            else
                columnName = table.substring(0, 1) + "name";

            String query = "SELECT * FROM " + table + " WHERE " + columnName + "=" + object;
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

        } catch (Exception e) {
            System.out.println("item/s not found");

        }

        return data;

    }

    public void populateJokes(){
        //Jokes
        PreparedStatement psm;
        String query = "INSERT INTO Jokes VALUES(?,?,?)";

        String jokes_String = utils.JSONUtils.getJSON(jokeAPI, client);
        JSONObject jokeJSON = new JSONObject(jokes_String);

        JSONArray jokes = jokeJSON.getJSONArray("value");
        Random rnd = new Random();

        for(int i =0 ; i<1550; i++){
              try{
                psm = connection.prepareStatement(query);
                psm.setInt(1,jokes.getJSONObject(i).getInt("id"));
                psm.setString(2, jokes.getJSONObject(i).getString("joke"));
                psm.setInt(3, rnd.nextInt(600));
                psm.execute();

            }catch (Exception e){e.printStackTrace();continue;}
        }


    }

    public void populateCrackers(){
        PreparedStatement psm;
        String queryJoke = "(SELECT jid FROM Jokes WHERE jid=?)";
        String queryHat = "(SELECT hid FROM Hats WHERE hid=?)";
        String queryGift = "(SELECT gid FROM Gifts WHERE gid=?)";

        String query = "INSERT INTO Crackers(cid, name, jid, hid, gid, quantity) VALUES(" +
                "?, " +
                "?, " +
                queryJoke + ", " +
                queryHat + ", " +
                queryGift + ", " +
                "?)";


        String cracker_String = utils.JSONUtils.getJSON(crackerAPI, client);
        JSONObject crackerJSON = new JSONObject(cracker_String);
        JSONArray crackers = crackerJSON.getJSONArray("results");
        for(int j=0; j<crackers.length(); j++){
            try{
                psm = connection.prepareStatement(query);

                psm.setInt(1,crackers.getJSONObject(j).getInt("cid"));
                psm.setString(2, crackers.getJSONObject(j).getString("cname")+crackers.getJSONObject(j).getInt("cquantity"));
                psm.setInt(3, crackers.getJSONObject(j).getInt("rjoke"));
                psm.setInt(4, crackers.getJSONObject(j).getInt("rhat"));
                psm.setInt(5, crackers.getJSONObject(j).getInt("rgift"));
                psm.setInt(6, crackers.getJSONObject(j).getInt("cquantity"));

                psm.execute();



            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void populateHats(){
        PreparedStatement psm;
        String query = "INSERT INTO Hats VALUES(?,?,?)";

        String gifts_String= utils.JSONUtils.getJSON(hatAPI, client);
        JSONObject giftsJSON = new JSONObject(gifts_String);
        JSONArray gifts = giftsJSON.getJSONArray("results");
        for(int j=0; j<gifts.length(); j++){

            try{
                psm = connection.prepareStatement(query);
                psm.setInt(1,gifts.getJSONObject(j).getInt("hid"));

                psm.setString(2, (gifts.getJSONObject(j).getString("hdescription")));

                psm.setInt(3,gifts.getJSONObject(j).getInt("hprice"));
                psm.executeUpdate();
            }catch (Exception e){e.printStackTrace();continue;}
        }
    }

    public void populateGifts(){

        PreparedStatement psm;
        String query = "INSERT INTO Gifts VALUES(?,?,?)";

        String gifts_String= utils.JSONUtils.getJSON(giftAPI, client);
        JSONObject giftsJSON = new JSONObject(gifts_String);
        JSONArray gifts = giftsJSON.getJSONArray("results");
        for(int j=0; j<gifts.length(); j++){
            try{
                psm = connection.prepareStatement(query);
                psm.setInt(1,gifts.getJSONObject(j).getInt("gid"));

                psm.setString(2, (gifts.getJSONObject(j).getString("gdescription")));

                psm.setInt(3,gifts.getJSONObject(j).getInt("gprice"));
                psm.execute();
            }catch (Exception e){e.printStackTrace();continue;}
        }
    }

    public void createTables(){
        String createJokesQuery ="CREATE TABLE Jokes(" +
                "  jid INTEGER," +
                "  joke VARCHAR NOT NULL," +
                "  royalty INTEGER NOT NULL," +
                "  CONSTRAINT Joke_Primary PRIMARY KEY (jid)," +
                "  CONSTRAINT Joke_Joke_Unique UNIQUE (joke)" +
                ")";
        String createHatsQuery="CREATE TABLE Hats(" +
                "  hid INTEGER," +
                "  description VARCHAR NOT NULL," +
                "  price INTEGER NOT NULL," +
                "  CONSTRAINT Hat_Primary PRIMARY KEY (hid)" +
                ")";
        String createGiftsQuery="CREATE TABLE Gifts(" +
                "  gid INTEGER," +
                "  description VARCHAR NOT NULL," +
                "  price INTEGER NOT NULL," +
                "  CONSTRAINT Gi_Primary PRIMARY KEY (gid)" +
                ")";


        String createCrackersQuery = "CREATE TABLE Crackers(" +
                "  cid INTEGER," +
                "  name VARCHAR NOT NULL," +
                "  jid INTEGER NOT NULL," +
                "  hid INTEGER NOT NULL," +
                "  gid INTEGER NOT NULL," +
                "  quantity INTEGER NOT NULL," +
                "  CONSTRAINT Cracker_Primary PRIMARY KEY (cid)," +
                "  CONSTRAINT Cracker_Name_Unique UNIQUE(name)," +
                "  CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid)," +
                "  CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid), " +
                "  CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid)" +
                ")";

        try {
            connection.prepareStatement(createJokesQuery).execute();
            connection.prepareStatement(createHatsQuery).execute();
            connection.prepareStatement(createGiftsQuery).execute();
            connection.prepareStatement(createCrackersQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTables() {

        String query1 = "DROP TABLE Crackers",
                query2 = "DROP TABLE Hats",
                query3 = "DROP TABLE Gifts",
                query4 = "DROP TABLE Jokes";
        try {
            connection.prepareStatement(query1).execute();
            connection.prepareStatement(query2).execute();
            connection.prepareStatement(query4).execute();
            connection.prepareStatement(query3).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeId(String table, int id){
        String query = "DELETE FROM " + table + " WHERE " + table.substring(0,1) + "id " + "=" + id;
        try{
            PreparedStatement psm = connection.prepareStatement(query);
            psm.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addCracker(int cid, String name, int jid, int hid, int gid, int quantity){
        String query = "INSERT INTO Crackers(cid, name, jid, hid, gid, quantity) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, cid);
            psm.setString(2, name);
            psm.setInt(3, jid);
            psm.setInt(4, hid);
            psm.setInt(5, gid);
            psm.setInt(6, quantity);
            psm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addHat(int hid, String description, int price){
        String query = "INSERT INTO Hats(hid, description, price) VALUES(?,?,?)";
        try{
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1,hid);
            psm.setString(2, description);
            psm.setInt(3, price);
            psm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addJoke(int jid, String joke, int royalty){
        String query = "INSERT INTO Jokes(jid, joke, royalty) VALUES(?,?,?)";
        try{
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1,jid);
            psm.setString(2, joke);
            psm.setInt(3, royalty);
            psm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addGift(int gid, String description, int price){
        String query = "INSERT INTO Gifts(gid, description, price) VALUES(?,?,?)";
        try{
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1,gid);
            psm.setString(2, description);
            psm.setInt(3, price);
            psm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getJokeString(int id){
        String query = "SELECT joke FROM Jokes where jid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getString("joke");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("1");
        }
        return "";
    }

    public int getJokeRoyalty(int id){
        String query = "SELECT royalty FROM Jokes where jid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("royalty");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("2");
        }
        return 0;
    }

    public String getGiftDescription(int id){
        String query = "SELECT description FROM Gifts where gid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getString("description");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("3");
        }
        return "";
    }

    public String getHatDescription(int id){
        String query = "SELECT description FROM Hats where hid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getString("description");
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("4");
        }
        return "";
    }

    public int getGiftPrice(int id){
        String query = "SELECT price FROM Gifts where gid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("price");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("5");
        }
        return 0;

    }

    public int getHatPrice(int id){
        String query = "SELECT price FROM Hats where hid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("price");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("6");
        }
        return 0;
    }

    public int getCrackerHID(int cid){
        String query = "SELECT hid FROM Crackers where cid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, cid);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("hid");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("7");
        }
        return 0;
    }

    public int getCrackerJID(int cid){

        String query = "SELECT jid FROM Crackers where cid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, cid);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("jid");
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("8");
        }
        return 0;

    }

    public int getCrackerGID(int cid){

        String query = "SELECT gid FROM Crackers where cid = ?";
        try {
            PreparedStatement psm = connection.prepareStatement(query);
            psm.setInt(1, cid);
            ResultSet rs = psm.executeQuery();
            rs.next();
            return rs.getInt("gid");
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("9");
        }
        return 0;
    }












}
