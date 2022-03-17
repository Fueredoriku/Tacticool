package please.tacticool.models;

public class Tile { //Might extend / implement texture handler in the future
    
    private Actor actor;
    private int moveCost;

    /**
     * Empty constructor, creates Tile with no Actor occupying it and a default movecost of 1.
     */
    public Tile() {
        actor = null;
        moveCost = 1;
    }

    /**
     * Constructor for an occupied Tile with custom move cost.
     * 
     * @param actor The Actor that will occupy this Tile. Can be null if Tile should be empty.
     * @param moveCost The cost of moving into this Tile from an adjacent one.
     */
    public Tile(Actor actor, int moveCost) {
        this.actor = actor;
        this.moveCost = moveCost;
    }

    public Actor getActor() {
        return actor;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
