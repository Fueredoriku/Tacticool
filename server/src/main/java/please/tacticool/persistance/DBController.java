package please.tacticool.persistance;

import please.tacticool.GameBalance;
import please.tacticool.models.Actions.ActionHandler;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import com.google.gson.Gson;

public class DBController extends DatabaseManager{
    
    public int registerPlayer(String name, String pass){
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO miburgos_tacticool.Player(name,password) VALUES ('%s', '%s');", name.toLowerCase(), pass.toLowerCase());
            stmt.execute(sql);
            sql = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int createGame(String map, boolean ready, int width, int height) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("INSERT INTO GameTable(map,ready,mapH,mapW,actions) VALUES ('%s', '%s', %d, %d, '%s');", map, ready, width, height, null);
            stmt.execute(sql);
            sql = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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

    public Player getPlayerById(int id) {
        try (Statement stmt = getConn().createStatement()){
            String sql = String.format("SELECT * FROM Player WHERE IDplayer = %d", id);
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                return new Player(result.getInt("IDplayer"), new Coordinate(0, 0), GameBalance.DefaultHealthPoints);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        ActionHandler ac = db.getGame(3);
        ac.addNewPlayer(1);
        System.out.println(ac.getGameID());
    }
}
