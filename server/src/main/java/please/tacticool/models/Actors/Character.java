package please.tacticool.models.Actors;

import please.tacticool.models.Coordinate;

public class Character extends Actor {
    // Number of life points of the character
    private int lifePoints; // probably want to find a better name ?
    // Unique ID of the player to whom the character belongs
    private final int playerID;

    /**
     *  Constructs a character with a given position and life points binded to the given player's ID
     * @param playerID : ID of the owner of the character
     * @param position : initial position of the character
     * @param lifePoints : initial life points of the character
     */
    public Character(int playerID, Coordinate position, int lifePoints){
        super(position);
        this.playerID = playerID;
        if(lifePoints < 0){
            throw new IllegalArgumentException("Life points can't be less than 0");
        }
        this.lifePoints = lifePoints;
    }

    public int getLife(){
        return lifePoints;
    }

    public int getPlayerID(){
        return playerID;
    }
}
