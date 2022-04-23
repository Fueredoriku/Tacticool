package httpRequests;

import java.util.ArrayList;
import java.util.List;

import com.anything.tacticool.model.InputAction;
import com.anything.tacticool.model.ActionType;
import com.anything.tacticool.model.Player;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Deserializer {
    
    public List<Player> deserializeTurn(String json) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        List<Player> players = deserializePlayers(obj.get("players").getAsJsonArray());
        deserializeActions(obj.get("actions").getAsJsonObject(), players);
        return players;
    }

    private List<Player> deserializePlayers(JsonArray playersJson) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playersJson.size(); i++) {
            JsonObject playerJson = playersJson.get(i).getAsJsonObject();
            Player player = new Player(Integer.parseInt(playerJson.get("playerID").getAsString()), Integer.parseInt(playerJson.get("healthPoint").getAsString()));
            players.add(player);
        }
        return players;
    }

    private void deserializeActions(JsonObject actionsJson, List<Player> players) {
        for (Player player : players) {
            JsonArray actions = actionsJson.get(String.format("%d", player.getPlayerID())).getAsJsonObject().get("actions").getAsJsonArray();
            for (int i = 0; i < actions.size(); i++) {
                JsonObject actionJson = actions.get(i).getAsJsonObject();
                JsonObject coordinate = actionJson.get("coordinate").getAsJsonObject();
                InputAction action = new InputAction(ActionType.valueOf(actionJson.get("actionType").getAsString()), Integer.parseInt(coordinate.get("x").getAsString()), Integer.parseInt(coordinate.get("y").getAsString()));
                player.addAction(action);
            }
        }
    }


    public static void main(String[] args) {
        Deserializer deserializer = new Deserializer();
        List<Player> players = deserializer.deserializeTurn("{\"players\":[{\"healthPoint\":95,\"playerID\":7,\"actionPoints\":9,\"position\":{\"x\":3,\"y\":1}},{\"healthPoint\":95,\"playerID\":8,\"actionPoints\":9,\"position\":{\"x\":1,\"y\":1}}],\"actions\":{\"7\":{\"actions\":[{\"coordinate\":{\"x\":3,\"y\":1},\"actionType\":\"MOVE\"}]},\"8\":{\"actions\":[{\"coordinate\":{\"x\":1,\"y\":1},\"actionType\":\"MOVE\"}]}}}");
        System.out.println(players);
    }
}
