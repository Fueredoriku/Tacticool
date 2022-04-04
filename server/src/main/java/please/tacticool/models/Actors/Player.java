package please.tacticool.models.Actors;

import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public class Player extends Actor implements Movement{
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
    public Player(int playerID, Coordinate position, int healthPoint){
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

    public boolean isDead() {
        return this.healthPoint <= 0;
    }

    @Override
    public List<Coordinate> move(List<Coordinate> movement, TerrainGrid grid) { //TODO: Implement movement for character
        boolean collision = false;
        List<Coordinate> path = null;

        if (movement.isEmpty()) {
            throw new IllegalArgumentException("Could not process empty coordinate list");
        }

        while (movement != null && !movement.isEmpty() && !collision) {
            for (Coordinate coord : movement) {
                try{
                    if (!grid.isEmptyTile(coord)) {
                        collision = true;
                        break;
                    }
                    path.add(coord);
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Tried to move to invalid coordinate");
                }
            }
        }

        if (path != null && !path.isEmpty()) {
            this.setPosition(path.get(path.size() - 1));
        }

        return path;
    }

    @Override
    public void getHit(int dmg) {
        this.healthPoint -= dmg;
    }
}
