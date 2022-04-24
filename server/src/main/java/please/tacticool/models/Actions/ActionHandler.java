package please.tacticool.models.Actions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import please.tacticool.GameBalance;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.persistance.DBController;
import please.tacticool.util.JsonConvert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ActionHandler {
    
    private final Map<Player, Actions> playerActions;
    private TerrainGrid grid;
    private final int gameID;

    /**
     * Constructor for the actionhandler
     * @param gameID    the id of the game.
     */
    public ActionHandler(int gameID) {
        this.gameID = gameID;
        playerActions = new HashMap<>();
    }

    public void setGrid(TerrainGrid grid) {
        this.grid = grid;
    }

    /**
     * Adds a player to the game.
     * @param player    the player to add.
     */
    public void addPlayer(Player player) {
        playerActions.put(player, new Actions());
        if (!player.isDead()) { 
            grid.setActor(player.getPosition(), player);
        }
    }

    /**
     * Adds a new player to the game.
     * @param playerID  id of the player to add.
     */
    public void addNewPlayer(int playerID) {
        DBController controller = new DBController();
        Player player = controller.getPlayerById(playerID);
        findFreePosition(player);
        controller.addPlayerToGame(this, player);
        addPlayer(player);
    }

    /**
     * Finds a free position in the grid to place the player on.
     * @param player    the player to find a position for.
     */
    private void findFreePosition(Player player) {
        List<Coordinate> possibleTiles = grid.getFreeTiles();
        if (grid.getActor(player.getPosition()) != null) {
            player.setPosition(possibleTiles.get(new Random().nextInt(possibleTiles.size())));
        }
    }

    /**
     * Adds actions for a player to the game.
     * @param player    the player adding the actions.
     * @param actions   the actions to add.
     * @param saveToDb  whether to save to actions to DB or not.
     */
    public void addActions(Player player, Actions actions, boolean saveToDb) {
        // Check if dead, check if legal,
        if (!player.isDead()) {
            playerActions.put(player, actions);
            if (saveToDb) {
                new DBController().addMovesToPlayerInGame(this, player);
            }
        }
    }

    /**
     * Adds actions for a player to the game.
     * @param player    the player adding the action.
     * @param actions   json form of the actions to add.
     * @param saveToDb  boolean whether to add to DB or not.
     * @throws IllegalArgumentException if there exists illegal actions in the string.
     */
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

    /**
     * Finds a player in the game by their ID.
     * @param playerID  id of the player to find.
     * @return          the player if found, null otherwise.
     */
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


    /**
     * A method the get the current win state of the game
     * @return  json on the form {"isGameWon": true/false, "player": playerID / -1 / null}. 
     *          For the "player" field, you get the id on single player win, -1 on a tie and null if the game is not won.
     */
    public JsonObject getWinState() {
        JsonObject obj = new JsonObject();
        List<Player> alive = playerActions.keySet().stream().filter(a -> !a.isDead()).toList();
        boolean isGameWon = alive.size() <= 1;
        obj.addProperty("isGameWon", isGameWon);
        if (isGameWon) {
            obj.addProperty("player", alive.size() == 1 ? alive.get(0).getPlayerID() : -1);
        } else {
            obj.addProperty("player", -2);
        }

        return obj;
    }

    /**
     * Performes a "round" of the game if all alive players have submitted their actions.
     */
    public void simulate() {
        if (new DBController().playersReady(this) == getPlayers().stream().filter(a -> !a.isDead()).count()) {
            performActions();
        }
    }
    
    /**
     * Performs all the actions submitted by alive players and updates the DB with the result.
     */
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



    /**
     * Gets the current game state as a json string. 
     * @return  a json string containing: player information (hp, position), performed actions and the grid.
     */
    public String getGameState() {
        Gson gson = new Gson();
        JsonObject results = new JsonObject();
        JsonArray players = new JsonArray();
        for (Player player : playerActions.keySet()){
            players.add(gson.fromJson(gson.toJson(player), JsonObject.class));
        }
        results.add("players", players);
        String actions = new DBController().getPerformedActions(this);
        if (actions == null || actions.equals("null")) {
            results.add("actions", new JsonObject());
        } else {
            results.add("actions", gson.fromJson(actions, JsonObject.class));
        }
        //results.add("actions", gson.fromJson((new DBController().getPerformedActions(this)).equals("null")  ? "null" : (new DBController().getPerformedActions(this)), JsonObject.class));
        JsonObject board = new JsonObject();
        board.addProperty("board", grid.toStringMap());
        board.addProperty("width", grid.getDimensions().getX());
        board.addProperty("height", grid.getDimensions().getY());
        board.addProperty("turnSwitch", new DBController().getTurnSwitch(gameID));
        results.add("grid", board);
        results.add("winState", getWinState());
        return results.toString();
    }

    /**
     * Creates a new game and adds it to the DB.
     * @return  the new ActionHandled with an ID and a board
     */
    public static ActionHandler createGame() {
        TerrainGrid grid = new TerrainGrid(GameBalance.DefaultWidth, GameBalance.DefaultHeigth);
        int id = new DBController().createGame(grid.toStringMap(), false, grid.getDimensions().getX(), grid.getDimensions().getY());
        ActionHandler handler = new ActionHandler(id);
        handler.setGrid(grid);
        return handler;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Player player : getPlayers()) {
            result.append(String.format("Player %s: %s - %s\n", player.getPlayerID(), player.getHealthPoints(), player.getActionPoints()));
        }
        result.append(grid.toString());

        return result.toString();
    }
}
