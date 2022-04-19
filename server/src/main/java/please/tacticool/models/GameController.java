package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import please.tacticool.models.Actions.Action;
import please.tacticool.models.Actions.Movement;
import please.tacticool.models.Actions.Weapons.Bazooka;
import please.tacticool.models.Actions.Weapons.Rifle;
import please.tacticool.models.Actors.Player;

public class GameController {

    private int gameId;

    private static final int defaultHealthPoint = 10;

    private Map<Integer, Player> players;
    private Map<Integer, List<Action>> actions;
    private List<Integer> playerOrder; 
    private int width = 10, depth = 10;
    private TerrainGrid grid;
    private int turnCounter = 1;


    public GameController(int id) {
        players = new HashMap<>();
        instantiateGame(id);
    }


    /**
     * Creates a game with a custom list of players.
     * @param players the players to instantiate the game with.
     */
    public GameController(int id, Player... players) {
        this.players = new HashMap<Integer, Player>();
        for (Player player : players) {
            this.players.put(player.getPlayerID(), player);
        }
        instantiateGame(id);
    }

    /**
     * Creates a new game from scratch, using default start positions.
     */
    public GameController() {
        players = new HashMap<Integer, Player>();
        List<Coordinate> startPositions = new ArrayList<Coordinate>(List.of( // Current default start positions.
            new Coordinate(0, 0),
            new Coordinate(0, depth - 1),
            new Coordinate(width - 1, 0),
            new Coordinate(width - 1, depth - 1)
        ));
        for (int i = 0; i < startPositions.size(); i++) {
            players.put(i, new Player(i, startPositions.get(i), defaultHealthPoint));
        }
        instantiateGame(1);
    }

    /**
     * Instantiates all necessary variables.
     */
    private void instantiateGame(int id) {
        actions = new HashMap<Integer, List<Action>>();
        playerOrder = new ArrayList<Integer>();
        gameId = id;
        grid = new TerrainGrid(width, depth);
        for (Player player : this.players.values()) {
            grid.setActor(player.getPosition(), player);
        }
    }

    /**
     * Adds a players actions for this turn. The order of submission in kept, and on a resubmission the
     * prevous one is overwritten.
     * @param playerId  the id of the player submitting actions.
     * @param actions   the actions the player is submitting.
     */
    public void addPlayerActions(int playerId, List<Action> actions) {
        if (players.containsKey(playerId) && players.get(playerId).getHealthPoints() > 0) {
            if (playerOrder.contains(playerId)) {
                playerOrder.remove(playerId);
            }
            playerOrder.add(playerId);
            this.actions.put(playerId, actions);
        }
    }

    public void addPlayer(Player player) {
        if (!players.containsKey(player.getPlayerID())) {
            players.put(player.getPlayerID(), player);
        }
    }

    /**
     * Performs a turn of the game by executing all actions submitted by players. Actions 
     * are sorted by priority and executed in submission order (e.g. if p1 and p2 submits an action
     * with priority 1, the one who submitted first goes first). 
     * @return a list of the actions executed this turn.
     */
    public List<Action> performTurn() {
        List<Action> allActions = new ArrayList<Action>();
        for (int playerId : playerOrder) {
            allActions.addAll(actions.get(playerId));
        }
        allActions.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

        for (Action action : allActions) {
            action.execute(grid);
        }

        turnCounter++;
        playerOrder.clear();
        players.values().forEach(a -> a.resetActionPoints());
        players.values().stream().filter(a -> a.getHealthPoints() <= 0).forEach(a -> {if (grid.getActor(a.getPosition()).equals(a)) {grid.setActor(a.getPosition(), null);}});

        // Do wo want to remove dead players? I think probably not.
        // List<Player> dead = players.values().stream().filter(a -> a.getHealthPoints() <= 0).toList();
        // dead.forEach(a -> {if (grid.getActor(a.getPosition()).equals(a)) {grid.setActor(a.getPosition(), null);} players.remove(a.getPlayerID());});


        return allActions;
    }

    public int getGameId() {
        return gameId;
    }

    public int getTurnCount() {
        return turnCounter;
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(players.values());
    }

    public Player getPlayer(int playerId) {
        if (players.containsKey(playerId)) {
            return players.get(playerId);
        }
        return null;
    }

    public boolean isGameOver() {
        return players.values().stream().filter(a -> a.getHealthPoints() > 0).count() <= 1;
    }

    @Override
    public String toString() {
        String result = String.format("Turn: %s\n", turnCounter);
        for (Player player : players.values()) {
            result += String.format("Player %s: %s - %s\n", player.getPlayerID(), player.getHealthPoints(), player.getActionPoints());
        }
        result += grid.toString();

        return result;           
    }

    public static void main(String[] args) {
        GameController game = new GameController();
        List<Player> players = game.getPlayers();

        System.out.println(game);

        game.addPlayerActions(
            players.get(0).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(0), 
                    List.of(
                        new Coordinate(1, 0),
                        new Coordinate(2, 0)
                    )
                ),
                new Rifle(players.get(0), List.of(new Coordinate(4, 0)), 3)
            )
        );
        game.addPlayerActions(
            players.get(1).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(1), 
                    List.of(
                        new Coordinate(0, 8),
                        new Coordinate(1, 8),
                        new Coordinate(1, 7),
                        new Coordinate(1, 6),
                        new Coordinate(1, 5)
                    )
                )
            )
        );

        game.performTurn();
        System.out.println(game);

        game.addPlayerActions(
            players.get(0).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(0), 
                    List.of(
                        new Coordinate(1, 0)
                    )
                ),
                new Rifle(players.get(0), List.of(new Coordinate(1, 1)), 3)
            )
        );
        game.addPlayerActions(
            players.get(1).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(1), 
                    List.of(
                        new Coordinate(1, 5),
                        new Coordinate(1, 4),
                        new Coordinate(1, 3),
                        new Coordinate(1, 2)
                    )
                )
            )
        );

        game.addPlayerActions(
            players.get(3).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(3), 
                    List.of(
                        new Coordinate(9, 8),
                        new Coordinate(9, 7),
                        new Coordinate(9, 6),
                        new Coordinate(9, 5)
                    )
                )
            )
        );

        game.performTurn();
        System.out.println(game);

        game.addPlayerActions(
            players.get(0).getPlayerID(), 
            List.of(
                new Rifle(players.get(0), List.of(new Coordinate(1, 1)), 3)
            )
        );
        game.addPlayerActions(
            players.get(1).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(1), 
                    List.of(
                        new Coordinate(0, 2)
                    )
                ),
                new Bazooka(
                    players.get(0),
                    List.of(
                        new Coordinate(0, 0)
                    ), 
                    5)
            )
        );

        game.addPlayerActions(
            players.get(3).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(3), 
                    List.of(
                        new Coordinate(9, 4),
                        new Coordinate(9, 3),
                        new Coordinate(9, 2),
                        new Coordinate(9, 1)
                    )
                )
            )
        );

        game.performTurn();
        System.out.println(game);

        game.addPlayerActions(
            players.get(0).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(0), 
                    List.of(
                        new Coordinate(0, 0)
                    )
                ),
                new Rifle(players.get(0), List.of(new Coordinate(0, 1)), 3)
            )
        );
        game.addPlayerActions(
            players.get(1).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(1), 
                    List.of(
                        new Coordinate(0, 1),
                        new Coordinate(0, 0)
                    )
                ),
                new Rifle(players.get(1), List.of(new Coordinate(0,0)), 6)
            )
        );

        game.addPlayerActions(
            players.get(3).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(3), 
                    List.of(
                        new Coordinate(9, 0)
                    )
                )
            )
        );

        List<Action> test = game.performTurn();
        System.out.println(game);
        System.out.println(test.stream().filter(a -> a.getPlayer().getPlayerID() == 0).toList());
        System.out.println(test.get(0).getClass().getSimpleName());
        System.out.println(test.get(0).getClass().getSimpleName() + ": " + test.get(0).getAffectedCoordinates());

        game.addPlayerActions(
            players.get(0).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(0), 
                    List.of(
                        new Coordinate(1, 0),
                        new Coordinate(2, 0)
                    )
                )
            )
        );
        game.addPlayerActions(
            players.get(1).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(1), 
                    List.of(
                        new Coordinate(0, 0)
                    )
                )
            )
        );

        game.addPlayerActions(
            players.get(3).getPlayerID(), 
            List.of(
                new Movement(
                    players.get(3), 
                    List.of(
                        new Coordinate(9, 0)
                    )
                )
            )
        );

        game.performTurn();
        System.out.println(game);
    }
}
