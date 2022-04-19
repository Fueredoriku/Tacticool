package please.tacticool.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import please.tacticool.models.Coordinate;
import please.tacticool.models.GameController;
import please.tacticool.models.Actors.Player;

public class DBController extends DatabaseManager{
    
    public void insertPlayer(int IDp, String name, String pass){
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO Player VALUES (%d, '%s', '%s');", IDp, name, pass);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGameTable(int id, String map, boolean ready, int width, int height) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO GameTable VALUES (%d, '%s', '%s', '%s', '%s');", id, map, ready, width, height);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GameController getGame(int gameID) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT * GameTable WHERE IDgame = '%s'", gameID);
            ResultSet result = stmt.executeQuery(sql);
            GameController controller = new GameController(gameID);
            while (result.next()) {
                // Convert string to TileGrid
            }
            sql = String.format("SELECT * GameToPlayer WHERE IDgame = '%s'", gameID);
            result = stmt.executeQuery(sql);
            while (result.next()) {
                Player currentPlayer = new Player(result.getInt("IDPlayer"), new Coordinate(result.getString("coord")), result.getInt("hp"));
                controller.addPlayer(currentPlayer);
            }

            return controller;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DBController controller = new DBController();
        GameController test = controller.getGame(1);

        System.out.println(test.getPlayers());
    }
}
