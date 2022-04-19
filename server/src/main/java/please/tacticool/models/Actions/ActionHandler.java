package please.tacticool.models.Actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;

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
        Gson gson = new Gson();
        addActions(player, gson.fromJson(actions, Actions.class));
    }

    public Actions getPlayerActions(Player player) {
        return playerActions.get(player);
    }

    
    public void performActions() {
        for (Player player : playerActions.keySet()) {
            playerActions.get(player).perform();
        }
    }

    public static void main(String[] args) {
        ActionHandler handler = new ActionHandler(new TerrainGrid(3, 3));
        Actions ac = new Actions();
        ac.addAction(new Action2(new Coordinate(1, 1), ActionType.MOVE));
        ac.addAction(new Action2(new Coordinate(3, 3), ActionType.SHOOT));
        Player p = new Player(1, new Coordinate(1, 1), 100);
        Gson gson = new Gson();
        ActionHandler handler2 = new ActionHandler(new TerrainGrid(3, 3));
        System.out.println(gson.toJson(ac));
        handler2.addActions(p, gson.toJson(ac));
        System.out.println(handler2.getPlayerActions(p));
    }
}
