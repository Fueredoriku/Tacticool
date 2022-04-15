package please.tacticool.persistance;

import java.sql.SQLException;
import java.sql.Statement;

public class DBController extends DatabaseManager{
    public void inserting(int IDp, String name, String pass){
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO Player VALUES (%d, '%s', '%s');", IDp, name, pass);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBController dbm = new DBController();
        dbm.inserting(6, "Heis", "means");
    }
}
