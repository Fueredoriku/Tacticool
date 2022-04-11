package please.tacticool.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import please.tacticool.models.Actions.Action;
import please.tacticool.models.Actions.Movement;
import please.tacticool.models.Actions.Weapons.Bazooka;
import please.tacticool.models.Actions.Weapons.Rifle;
import please.tacticool.models.Actors.Player;

public class Game {

    private UUID gameId;

    private static final int defaultHealthPoint = 10;

    private List<Player> players;
    private Map<Integer, List<Action>> actions;
    private List<Integer> playerOrder; 
    private int width = 10, depth = 10;
    private TerrainGrid grid;
    private int turnCounter = 1;

    public Game(Player... players) {
        this.players = new ArrayList<Player>(Arrays.asList(players));
        instantiateGame();
    }

    public Game() {
        players = new ArrayList<Player>();
        List<Coordinate> startPositions = new ArrayList<Coordinate>(List.of(
            new Coordinate(0, 0),
            new Coordinate(0, depth - 1),
            new Coordinate(width - 1, 0),
            new Coordinate(width - 1, depth - 1)
        ));
        for (int i = 0; i < startPositions.size(); i++) {
            players.add(new Player(i, startPositions.get(i), defaultHealthPoint));
        }
        instantiateGame();
    }

    private void instantiateGame() {
        actions = new HashMap<Integer, List<Action>>();
        playerOrder = new ArrayList<Integer>();
        gameId = UUID.randomUUID();
        grid = new TerrainGrid(width, depth);
        for (Player player : this.players) {
            grid.setActor(player.getPosition(), player);
        }
    }

    public void addPlayerActions(int playerId, List<Action> actions) {
        if (playerOrder.contains(playerId)) {
            playerOrder.remove(playerId);
        }
        playerOrder.add(playerId);
        this.actions.put(playerId, actions);
    }

    public void performTurn() {
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
        players.forEach(a -> a.resetActionPoints());
    }
 
    public UUID getGameId() {
        return gameId;
    }

    public int getTurnCount() {
        return turnCounter;
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    @Override
    public String toString() {
        String result = String.format("Turn: %s\n", turnCounter);
        for (Player player : players) {
            result += String.format("Player %s: %s - %s\n", player.getPlayerID(), player.getHealthPoints(), player.getActionPoints());
        }
        result += grid.toString();

        return result;           
    }

    public static void main(String[] args) {
        Game game = new Game();
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
    }
}
