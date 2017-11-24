import okhttp3.OkHttpClient;

import java.sql.*;

public class DB {

        private static String url = "jdbc:postgresql://localhost:5432/";
        private static String user = "mihai";
        private static String password = "password123";
        private static OkHttpClient client = new OkHttpClient();
        private static Connection psqlConn = null;
/*
            // URL -> USER -> Password
            psqlConn = utills.DBUtils.getConnectionPSQL(url, user, password);
            utills.DBUtils.dropDB(psqlConn,"testname");
*/

/*
String s = "";
            String t = "";
            s = utills.JSONUtils.getJSON("http://api.icndb.com/jokes/random/600", client);
            t = utills.JSONUtils.getJSON("https://qrng.anu.edu.au/API/jsonI.php?length=500&type=uint8", client);
            JSONObject jokeJSON = new JSONObject(s);
            JSONObject costJSON = new JSONObject(t);
            JSONArray jokes = jokeJSON.getJSONArray("value");
            JSONArray costs = costJSON.getJSONArray("data");
            for(int i =0 ; i<400; i++){
                System.out.println(costs.get(i) + " " + jokes.getJSONObject(i).get("id")+ ". "+ jokes.getJSONObject(i).get("joke"));
            }
 */
        public static void main (String[] args){


        }

}
