package please.tacticool.models.Actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;
import please.tacticool.util.JsonConvert;

public class ActionHandler {
    
    private Map<Player, Actions> playerActions;
    private TerrainGrid grid;
    
    public ActionHandler(TerrainGrid grid) {
        playerActions = new HashMap<>();
        this.grid = grid;
    }

    public void addActions(Player player, Actions actions) {
        // Check if dead, check if legal, 
        if (!player.isDead()) {
            playerActions.put(player, actions);
        }

    }

    public void addActions(Player player, String actions) {
        JsonArray actionArray = new Gson().fromJson(actions, JsonObject.class).getAsJsonArray("actions");
        Actions ac = new Actions();
        for (int a = 0; a < actionArray.size(); a++) {
            ac.addAction(JsonConvert.convertToAction(actionArray.get(a).getAsJsonObject()));
        }
        playerActions.put(player, ac);
    }

    public Actions getPlayerActions(Player player) {
        return playerActions.get(player);
    }

    
    public void performActions() {
        for (Player player : playerActions.keySet()) {
            playerActions.get(player).perform(player, grid);
        }
    }
}
