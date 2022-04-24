package httpRequests;

import java.util.ArrayList;
import java.util.List;

import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.Grid;
import com.anything.tacticool.model.Player;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Deserializer {
    
    public Grid deserializeTurn(String json) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        List<Player> players = deserializePlayers(obj.get("players").getAsJsonArray());
        JsonObject actions = obj.get("actions").getAsJsonObject();
        if (actions != null && !actions.equals("null")) {
            deserializeActions(obj.get("actions").getAsJsonObject(), players);
        }

        Grid grid = deserializeGrid(obj.get("grid").getAsJsonObject(), players);

        JsonObject winState = obj.get("winState").getAsJsonObject();
        grid.setWinState(winState.get("isGameWon").getAsBoolean(), winState.get("player").getAsInt());;

        return grid;
    }

    private List<Player> deserializePlayers(JsonArray playersJson) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playersJson.size(); i++) {
            JsonObject playerJson = playersJson.get(i).getAsJsonObject();
            JsonObject position = playerJson.get("position").getAsJsonObject();
            Player player = new Player(
                    Integer.parseInt(playerJson.get("playerID").getAsString()),
                    Integer.parseInt(playerJson.get("healthPoint").getAsString()),
                    Integer.parseInt(position.get("x").getAsString()),
                    Integer.parseInt(position.get("y").getAsString()));
            players.add(player);
        }
        return players;
    }

    private void deserializeActions(JsonObject actionsJson, List<Player> players) {
        for (Player player : players) {
            JsonElement actions1 = actionsJson.get(String.format("%d", player.getPlayerID()));
            if (actions1 == null || actions1.equals("null")) {
                continue;
            }
            JsonArray actions = actions1.getAsJsonObject().get("actions").getAsJsonArray();
            for (int i = 0; i < actions.size(); i++) {
                JsonObject actionJson = actions.get(i).getAsJsonObject();
                JsonObject coordinate = actionJson.get("coordinate").getAsJsonObject();
                InputAction action = new InputAction(ActionType.valueOf(actionJson.get("actionType").getAsString()), Integer.parseInt(coordinate.get("x").getAsString()), Integer.parseInt(coordinate.get("y").getAsString()));
                player.addAction(action);
            }
        }
    }

    private Grid deserializeGrid(JsonObject gridJson, List<Player> players) {
        System.out.println(gridJson);
        Grid grid = new Grid(
            gridJson.get("board").getAsString(), 
            gridJson.get("width").getAsInt(), 
            gridJson.get("height").getAsInt(),
            gridJson.get("turnSwitch").getAsBoolean());
        grid.setPlayers(players);
        return grid;
    }

}
