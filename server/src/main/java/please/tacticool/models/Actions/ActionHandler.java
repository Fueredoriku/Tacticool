package please.tacticool.models.Actions;

import java.util.*;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import please.tacticool.enums.ActionType;
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
    }

    public void addActions(Player player, Actions actions) {
        // Check if dead, check if legal,
        if (!player.isDead()) {
            playerActions.put(player, actions);
        }
    }

    public void addActions(Player player, String actions) throws IllegalArgumentException {
        JsonArray actionArray = new Gson().fromJson(actions, JsonObject.class).getAsJsonArray("actions");
        Actions ac = new Actions();
        for (int a = 0; a < actionArray.size(); a++) {
            Action action = JsonConvert.convertToAction(actionArray.get(a).getAsJsonObject());
            ac.addAction(action);
            //ac.addAction(JsonConvert.convertToAction(actionArray.get(a).getAsJsonObject()));
        }
        playerActions.put(player, ac);
        new DBController().addMovesToPlayerInGame(this, player);
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
        if (new DBController().playersReady(this) == getPlayers().size()) {
            performActions();
        }
    }
    
    public void performActions() {
        Map<Integer, String> result = new HashMap<>();
        for (Player player : playerActions.keySet()) {
            result.put(player.getPlayerID(), new Gson().toJson(playerActions.get(player).perform(player, grid)));
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
            players.add(gson.toJson(player));
        }
        results.add("players", players);
        results.addProperty("actions", new DBController().getPerformedActions(this));

        return results.getAsString();
    }
}
