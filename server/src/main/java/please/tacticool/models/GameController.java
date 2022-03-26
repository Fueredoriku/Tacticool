package please.tacticool.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Game controller takes care of making everything happen in the grid. Create the actors
 * and place them in the playfield. Also takes care of updating (and checking validity of)
 * the movement of the players and projectiles, damage dealt by projectiles and so on
 */
public class GameController {
    private final Playfield playfield;
    private final List<Character> characters;

    public static int defaultWidth = 40; // value is completely made up
    public static int defaultHeight = 40; // value is completely made up

    private GameController(int width, int height){
        this.playfield = new Playfield(width, height);
        characters = new ArrayList<>();
    }
    
    public GameController newGame(){
        return new GameController(defaultWidth, defaultHeight);
    }

    public void addPlayer(){
        
    }
}
