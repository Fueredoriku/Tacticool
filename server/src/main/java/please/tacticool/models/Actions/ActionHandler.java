package please.tacticool.models.Actions;

import java.util.*;

import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;
import please.tacticool.persistance.DBController;
import please.tacticool.util.JsonConvert;

public class ActionHandler {
    
    private Map<Player, Actions> playerActions;
    private TerrainGrid grid;
    private int gameID;
    
    public ActionHandler(int gameID) {
        this.gameID = gameID;
        playerActions = new HashMap<>();
    }

    public void setGrid(TerrainGrid grid) {
        this.grid = grid;
    }


    public void addPlayer(Player player) {
        playerActions.put(player, new Actions());
        if (!player.isDead()) { //TODO: OK way of handling dead players?
            grid.setActor(player.getPosition(), player);
        }
    }

    public void addActions(Player player, Actions actions, boolean saveToDb) {
        // Check if dead, check if legal,
        if (!player.isDead()) {
            playerActions.put(player, actions);
            if (saveToDb) {
                new DBController().addMovesToPlayerInGame(this, player);
            }
        }
    }

    public void addActions(Player player, String actions, boolean saveToDb) throws IllegalArgumentException {
        if (actions == null || actions.equals("null")) { return; }
        JsonArray actionArray = new Gson().fromJson(actions, JsonObject.class).getAsJsonArray("actions");
        Actions ac = new Actions();
        for (int a = 0; a < actionArray.size(); a++) {
            Action action = JsonConvert.convertToAction(actionArray.get(a).getAsJsonObject());
            ac.addAction(action);
        }
        addActions(player, ac, saveToDb);
    }

    public Player getPlayerById(int playerID) {
        for (Player player : playerActions.keySet()) {
            if (player.getPlayerID() == playerID) {
                return player;
            }
        }
        return null;
    }

    public Actions getPlayerActions(Player player) {
        return playerActions.get(player);
    }

    public Set<Player> getPlayers() {
        return playerActions.keySet();
    }

    public void simulate() {
        if (new DBController().playersReady(this) == getPlayers().stream().filter(a -> !a.isDead()).count()) {
            performActions();
        }
    }
    
    public void performActions() {
        Map<Integer, JsonObject> result = new HashMap<>();
        for (Player player : playerActions.keySet()) {
            result.put(player.getPlayerID(), new Gson().fromJson(new Gson().toJson(playerActions.get(player).perform(player, grid)), JsonObject.class));
        }
        new DBController().updateGameState(this, new Gson().toJson(result));
    }

    public int getGameID() {
        return this.gameID;
    }

    public String getGameState() {
        Gson gson = new Gson();
        JsonObject results = new JsonObject();
        JsonArray players = new JsonArray();
        for (Player player : playerActions.keySet()){
            players.add(gson.fromJson(gson.toJson(player), JsonObject.class));
        }
        results.add("players", players);
        results.add("actions", gson.fromJson(new DBController().getPerformedActions(this), JsonObject.class));
        return results.toString();
    }

    @Override
    public String toString() {
        String result = "";
        for (Player player : getPlayers()) {
            result += String.format("Player %s: %s - %s\n", player.getPlayerID(), player.getHealthPoints(), player.getActionPoints());
        }
        result += grid.toString();

        return result;           
    }


    public static void main(String[] args) {
        // TODO: Deal with dead players
        DBController dbController = new DBController();
        ActionHandler handler = dbController.getGame(2);
        Player me = handler.getPlayerById(7);
        Actions a = new Actions();
        a.addAction(new Move(new Coordinate(1, 0)));
        handler.addActions(me, a, true);

        handler.simulate();
        System.out.println(handler.toString());

        System.out.println(handler.getGameState());
    }
}
