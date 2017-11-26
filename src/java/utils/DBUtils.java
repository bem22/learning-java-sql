package utils;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils {
    public static void createDB(Connection psqlConn, String name){
        try {
            PreparedStatement s = psqlConn.prepareStatement("CREATE DATABASE " + name);
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropDB(Connection psqlConn, String name){
        try {
            PreparedStatement s = psqlConn.prepareStatement("DROP DATABASE " + name);
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnectionPSQL(String url, String user, String password){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            System.out.println("You are connected to: " + url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }


    public static ArrayList<String> getTableNames(Connection connection){
        ArrayList<String> tableNames = new ArrayList<String>();
        try{
            DatabaseMetaData md = connection.getMetaData();

            ResultSet rs = md.getTables(null,"public", "%" ,new String[] {"TABLE"});

            while(rs.next()){
                tableNames.add(rs.getString(3));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return tableNames;
    }
}
