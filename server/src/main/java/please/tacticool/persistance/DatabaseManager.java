package please.tacticool.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {
    protected Connection conn;
    public DatabaseManager () {}

    public void connect() {
        try {
            // Class.forName("com.mysql.jdbc.Driver").newInstance(); when you are using MySQL 5.7
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "miburgos_tacticool");
            p.put("password", "admin");
            //            conn = DriverManager.getConnection("jdbc:mysql://mysql.ansatt.ntnu.no/sveinbra_ektdb?autoReconnect=true&useSSL=false",p);
            conn = DriverManager.getConnection("jdbc:mysql:mysql.stud.ntnu.no:3306/miburgos_tacticool",p); //ekt?*/
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }

    public static void main(String[] args) {
        DatabaseManager dbm = new DatabaseManager();
        dbm.connect();
    }

}
