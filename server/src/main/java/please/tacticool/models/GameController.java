package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import please.tacticool.models.Action.ActionType;
import please.tacticool.models.Actors.Movement;
import please.tacticool.models.Actors.Player;

/**
 * Game controller takes care of making everything happen in the grid. Create the actors
 * and place them in the playfield. Also takes care of updating (and checking validity of)
 * the movement of the players and projectiles, damage dealt by projectiles and so on
 */
public class GameController {
    private final TerrainGrid terrainGrid;
    private final List<Player> players;
    private final UUID gameUID; // Unique ID to represent the game
    
    private int nbPlayers;
    // Map playerId to a list of actions
    private Map<Integer, Queue<Action>> playersActions;
    // Order in which the movement of the players will be performed.
    // Based on the order of receival of the actions
    private Queue<Integer> playersOrder;
    private GameState state;

    public static final int defaultWidth = 40; // value is completely made up
    public static final int defaultHeight = 40; // value is completely made up
    public static final int defaultHealthPoint = 100;
    // List of default spawn points - values are completely made up for now
    public static final List<Coordinate> defaultSpawnPoints = new ArrayList<>() {
        {
            add (new Coordinate(0,0));
            add (new Coordinate(1,0));
            add (new Coordinate(1,1));
            add (new Coordinate(0,1));
        }
    };


    private GameController(int width, int height){
        state = GameState.Lobby;
        terrainGrid = new TerrainGrid(width, height);
        players = new ArrayList<>();
        nbPlayers = 0;
        playersActions = new HashMap<Integer, Queue<Action>>();
        playersOrder = new PriorityQueue<>();
        gameUID = UUID.randomUUID();
    }
    
    public static GameController newGame(){
        return new GameController(defaultWidth, defaultHeight);
    }

    public void addPlayer(int playerId){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't add a player if the game has already started");
        }
        Player newPlayer = new Player(playerId, defaultSpawnPoints.get(0), defaultHealthPoint);
        players.add(newPlayer);
        nbPlayers += 1;
    }

    /**
     * 
     */
    public void startGame(){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't start a game that already started");
        } 
        state = GameState.Live;
    }

    /**
     * Register the list of actions of the specified player
     * @param playerId of the player the moves belongs to
     * @param actions of the player
     */
    public void registerPlayerMoves(int playerId, Queue<Action> actions){
        if(state != GameState.Live){
            throw new IllegalStateException("Can't register player move if the game has not started or is finished");
        }
        if(playersActions.containsKey(playerId)){
            throw new IllegalStateException("Can't change a player moves when it's already been registered");
        }
        playersActions.putIfAbsent(playerId, actions);
        playersOrder.add(playerId);
    }

    /**
     * Cary out the list of actions of all players (Even if all player didn't register their actions)
     */
    public void simulateRound(){
        if(state != GameState.Live){
            throw new IllegalStateException("Can't simulate a round if the game has not started or is finishde");
        }

        Map<Integer, Queue<Action>> actualMoves = new HashMap<>();

        // Condition to check if there's still at least one player who has movement(s) to do 
        boolean cond = true;
        while(cond){
            cond = false;
            for(int playerID : playersOrder){
                Queue<Action> playerActions = playersActions.get(playerID);
                Queue<Action> playerActualMoves = actualMoves.get(playerID);
                if(!playerActions.isEmpty() && playerActions.peek().getType() == ActionType.MOVEMENT){
                    Movement mvmt = (Movement)playerActions.poll();
                    // Send the move request to the terrain grid and register the move actually performed
                    Coordinate actual = terrainGrid.moveActor(getPlayerByID(playerID), mvmt.getTarget());
                    playerActualMoves.add(new Movement(actual));
                    cond = true;
                } else {
                    cond |= false;
                }
            }
        }
        
        for(int playerId : playersOrder){
            // TODO : simulate each player's aggressive actions : still need to define how 
        }
    }

    private Player getPlayerByID(int playerID){
        for(Player player : players){
            if(player.getPlayerID() == playerID){
                return player;
            }
        }
        return null;
    }

    public GameState getState(){
        return state;
    }

    public int getNbPlayers(){
        return nbPlayers;
    }

    public String getUID(){
        return gameUID.toString();
    }

    enum GameState {
        Lobby, Live, Finished;
    }
}
