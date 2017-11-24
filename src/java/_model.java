import utills.DBUtils;

import java.sql.Connection;

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
}
