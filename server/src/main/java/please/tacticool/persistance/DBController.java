package please.tacticool.persistance;

import please.tacticool.models.Actions.ActionHandler;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class DBController extends DatabaseManager{
    
    public void registerPlayer(int IDp, String name, String pass){
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO Player VALUES (%d, '%s', '%s');", IDp, name, pass);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGame(int id, String map, boolean ready, int width, int height) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO GameTable VALUES (%d, '%s', '%s', %d, %d, '%s');", id, map, ready, width, height, null);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayerToGame(ActionHandler actionHandler, Player player) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO miburgos_tacticool.GameToPlayer VALUES (%d,%d,'%s',%d,%b,'%s');", actionHandler.getGameID(), player.getPlayerID(), player.getPosition().toString(), player.getHealthPoints(), false, actionHandler.getPlayerActions(player));
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ActionHandler getGame(int gameID) {
        ActionHandler controller = null;
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT * FROM GameTable WHERE IDgame = '%s'", gameID);
            ResultSet result = stmt.executeQuery(sql);
            controller = new ActionHandler(gameID);
            while (result.next()) {
                controller.setGrid(new TerrainGrid(result.getString("map"), result.getInt("mapW"), result.getInt("mapH")));
            }
            sql = String.format("SELECT * FROM GameToPlayer WHERE IDgame = '%s'", gameID);
            result = stmt.executeQuery(sql);
            while (result.next()) {
                Player currentPlayer = new Player(result.getInt("IDplayer"), new Coordinate(result.getString("coord")), result.getInt("hp"));
                controller.addPlayer(currentPlayer);
                controller.addActions(currentPlayer, result.getString("moves"), false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public int playersReady(ActionHandler handler) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT ready FROM GameToPlayer WHERE IDgame = %d", handler.getGameID());
            ResultSet result = stmt.executeQuery(sql);
            int sum = 0;
            while (result.next()) {
                sum += Integer.parseInt(result.getString("ready"));
            }
            return sum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPerformedActions(ActionHandler handler) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT actions FROM GameTable WHERE IDgame = %d", handler.getGameID());
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                return result.getString("actions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

     public void addMovesToPlayerInGame(ActionHandler handler, Player player) {
         try (Statement stmt = getConn().createStatement()){
             String sql = String.format("UPDATE miburgos_tacticool.GameToPlayer SET ready = %b, moves = '%s' WHERE IDgame = %d AND IDplayer = %d", true, new Gson().toJson(handler.getPlayerActions(player)), handler.getGameID(), player.getPlayerID());
             stmt.execute(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    public void updateGameState(ActionHandler handler, String actions) {
        try (Statement stmt = getConn().createStatement()){
            for (Player player : handler.getPlayers()) {
                String updatePlayer = String.format("UPDATE miburgos_tacticool.GameToPlayer SET hp = %d, coord = '%s', ready = %b WHERE IDgame = %d AND IDplayer = %d", player.getHealthPoints(), player.getPosition().toString(), false, handler.getGameID(), player.getPlayerID());
                stmt.execute(updatePlayer);
            }
            String updateGame = String.format("UPDATE miburgos_tacticool.GameTable SET actions = '%s' WHERE IDgame = %d", actions, handler.getGameID());
            stmt.execute(updateGame);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        DBController db = new DBController();
        ActionHandler actionHandler = new ActionHandler(1);
        Player player = new Player(7, new Coordinate(1,1), 100);
        db.addPlayerToGame(actionHandler, player);
    }
}
