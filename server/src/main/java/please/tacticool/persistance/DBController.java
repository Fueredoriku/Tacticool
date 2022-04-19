package please.tacticool.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import please.tacticool.models.Coordinate;
import please.tacticool.models.GameController;
import please.tacticool.models.TerrainGrid;
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
        GameController controller = null;
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT * FROM GameTable WHERE IDgame = '%s'", gameID);
            ResultSet result = stmt.executeQuery(sql);
            controller = new GameController(gameID);
            while (result.next()) {
                controller.setGrid(new TerrainGrid(result.getString("map"), result.getInt("mapW"), result.getInt("mapH")));
            }
            sql = String.format("SELECT * FROM GameToPlayer WHERE IDgame = '%s'", gameID);
            result = stmt.executeQuery(sql);
            while (result.next()) {
                Player currentPlayer = new Player(result.getInt("IDplayer"), new Coordinate(result.getString("coord")), result.getInt("hp"));
                controller.addPlayer(currentPlayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public static void main(String[] args) {
        DBController controller = new DBController();
        GameController test = controller.getGame(1);

        System.out.println(test);
    }
}
