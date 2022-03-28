package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import please.tacticool.models.Actors.Player;

/**
 * Game controller takes care of making everything happen in the grid. Create the actors
 * and place them in the playfield. Also takes care of updating (and checking validity of)
 * the movement of the players and projectiles, damage dealt by projectiles and so on
 */
public class GameController {
    private final Playfield playfield;
    private final List<Player> characters;
    
    private int nbPlayers;
    // Map playerId to a list of actions
    private Map<Integer, List<Action>> playerActions;
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
        playerActions = new HashMap<Integer, List<Action>>();
    }
    
    public GameController newGame(){
        return new GameController(defaultWidth, defaultHeight);
    }

    public void addPlayer(int playerId){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't add a player if the game has already started");
        }
        Player newPlayer = new Player(playerId, defaultSpawnPoints.get(0), defaultHealthPoint);
        characters.add(newPlayer);
        nbPlayers += 1;
    }

    public void startGame(){
        if(state != GameState.Lobby){
            throw new IllegalStateException("Can't start a game the already started");
        } 
        state = GameState.Live;
    }

    public void registerPlayerMoves(int playerId, List<Action> actions){
        if(state != GameState.Live){
            throw new IllegalStateException("Can't register player move if the game has not started or is finished");
        }
        playerActions.putIfAbsent(playerId, actions);
    }

    public void simulateRound(){
        if(state != GameState.Live){
            throw new IllegalStateException("Can't simulate a round if the game has not started or is finishde");
        }
        /**
         * TODO : simulate the round based on the list of actions of each players and 
         * return the state of list of calls to transmit to the clients
         */ 
    }

    public GameState getState(){
        return state;
    }

    public int getNbPlayers(){
        return nbPlayers;
    }



    enum GameState {
        Lobby, Live, Finished;
    }
}
