package please.tacticool.models.Actors;

import please.tacticool.models.Coordinate;

public abstract class Actor {
    // Position of the actor in the playfield
    private Coordinate position;

    /**
     * Constructs an actor at the given position
     * @param position : initial position of the actor in the playfield
     */
    public Actor(Coordinate position){
        this.position = position;
        // Should it be placed in the playfield now ? Or in a separate function ?
    }

    /**
     * Getter for the position. The position is immutable.
     * @return : the given (immutable) position
     */
    public Coordinate getPosition(){
        return position;
    }

    public void setPosition(Coordinate newPosition) {
        position = newPosition;
    }

    public void getHit(int dmg) {
        return;
    }
}
