package please.tacticool.models.Actors;

import please.tacticool.models.Coordinate;

public class Character extends Actor implements Movement{
    // Characters health
    private int healthPoint;
    // Unique ID of the player to whom the character belongs
    private final int playerID;

    /**
     *  Constructs a character with a given position and life points binded to the given player's ID.
     * @param playerID : ID of the owner of the character.
     * @param position : initial position of the character.
     * @param healthPoint : initial life points of the character.
     */
    public Character(int playerID, Coordinate position, int healthPoint){
        super(position);
        this.playerID = playerID;
        if(healthPoint < 0){
            throw new IllegalArgumentException("Life points can't be less than 0");
        }
        this.healthPoint = healthPoint;
    }

    public int getHealthPoints(){
        return healthPoint;
    }

    public int getPlayerID(){
        return playerID;
    }

    @Override
    public void move(Coordinate destination) { //TODO: Implement movement for character
        this.setPosition(destination);
    }

    @Override
    public void getHit(int dmg) {
        this.healthPoint -= dmg;
    }
}
