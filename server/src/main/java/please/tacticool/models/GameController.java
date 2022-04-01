package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import please.tacticool.models.Actors.Character;
import please.tacticool.models.Actors.Movement;
import please.tacticool.models.Weapons.Attack;
import please.tacticool.models.Action;
import please.tacticool.models.Action.ActionType;

/**
 * Game controller takes care of making everything happen in the grid. Create the actors
 * and place them in the playfield. Also takes care of updating (and checking validity of)
 * the movement of the players and projectiles, damage dealt by projectiles and so on
 */
public class GameController {
    private final Playfield playfield;
    private final List<Character> characters;
    private final UUID gameUID; // Unique ID to represent the game
    
    private int nbPlayers;
    // Map playerId to a list of actions
    private Map<Integer, Queue<Action>> playersActions;
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
        this.playfield = new Playfield(width, height);
        characters = new ArrayList<>();
        state = GameState.Lobby;
        nbPlayers = 0;
        playersActions = new HashMap<Integer, Queue<Action>>();
        playersOrder = new PriorityQueue<>();
        gameUID = UUID.randomUUID();
    }
    
    public GameController newGame(){
        return new GameController(defaultWidth, defaultHeight);
    }

    public void addPlayer(int playerId){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't add a player if the game has already started");
        }
        Character newPlayer = new Character(playerId, defaultSpawnPoints.get(0), defaultHealthPoint);
        characters.add(newPlayer);
        nbPlayers += 1;
    }

    public void startGame(){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't start a game the already started");
        } 
        state = GameState.Live;
    }

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

    public void simulateRound(){
        if(state != GameState.Live){
            throw new IllegalStateException("Can't simulate a round if the game has not started or is finishde");
        }

        for(int playerID : playersOrder){
            Character player = getPlayerByID(playerID);
            Queue<Action> playerActions = playersActions.get(playerID);

            // /!\ ASSUMES THAT ALL MOVEMENT (passive actions) ARE FIRST AND THEN 
            // ALL WEAPONS (aggressive actions) ACTIONS
            while(!playerActions.isEmpty() &&
                     playerActions.peek().getType() == ActionType.MOVEMENT){
                Movement mvmt = (Movement)playerActions.poll();
                if(player.getPosition() != mvmt.getFrom()){
                    throw new IllegalArgumentException("The given move starts from a different position than the player's position");
                }
                Coordinate endTile = moveFromTo(player.getPosition(), mvmt.getTo());
                playfield.move(player, endTile);
            }
        }

        for(int playerID : playersOrder){
            // TODO : simulate each player's aggressive actions
            Character player = getPlayerByID(playerID); 
            Queue<Action> playerActions = playersActions.get(playerID);
            while(!playerActions.isEmpty()){
                Attack attack = (Attack)playerActions.poll();
                Coordinate conflictTile = moveUntilConflict(player.getPosition(), attack.getDirection());
                // TODO : inflict damages to target in the conflicting tile
            }
        }


    }

    private Coordinate moveUntilConflict(Coordinate from, Coordinate direction){
        // TODO : keep moving by adding the direction vector to the position until
        // conflict and return the tile of conflict
        return null;
    }
    
    private Coordinate moveFromTo(Coordinate from, Coordinate to){
        // TODO : execute the given movement and stop when a tile in the way has a 
        // conflict end return the end tile
        return null;
    }

    private Character getPlayerByID(int playerID){
        for(Character player : characters){
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
