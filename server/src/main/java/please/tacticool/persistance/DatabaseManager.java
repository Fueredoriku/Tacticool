package please.tacticool.persistance;

import java.sql.*;
import java.util.ArrayList;

public abstract class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/miburgos_tacticool";
    private static final String USER = "miburgos_tacticool";
    private static final String PASS = "admin";
    protected static Connection conn;

    public DatabaseManager() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException sqlException) {
            System.out.println("Could not connect");
        }
    }

    public ArrayList<String> getPlayers(){
        ArrayList<String> tmA = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM miburgos_tacticool.Player");
            while (rs.next()){
                tmA.add(String.format("PlayerID: %d, Name: %s, Pass: %s", rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmA;
    }

    protected static Connection getConn(){
        return conn;
    }
}
