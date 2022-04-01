package please.tacticool.models;
import please.tacticool.enums.Terrain;
import please.tacticool.models.Actors.Actor;

public class Tile { //Might extend / implement texture handler in the future
    
    private Actor actor;
    private Terrain terrain;

    /**
     * Empty constructor, creates Tile with no Actor occupying it and a default Terrain of GRASS.
     */
    public Tile() {
        actor = null;
        terrain = Terrain.GRASS;
    }

    /**
     * Constructor for an occupied Tile with custom move cost.
     * 
     * @param actor The Actor that will occupy this Tile. Can be null if Tile should be empty.
     * @param terrain The type of terrain this tile is. This specifies movement cost and texture.
     */
    public Tile(Actor actor, Terrain terrain) {
        this.actor = actor;
        this.terrain = terrain;
    }

    public Actor getActor() {
        return actor;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    // Can be removed because of method above.
    public int getMoveCost() {
        return terrain.getMoveCost();
    }

    public boolean hasActor(){
        return actor != null;
    }

    public void removeActor(){
        actor = null;
    }

    public void setActor(Actor actor) {
        if(hasActor()){
            throw new IllegalStateException("Can't set the actor if the tile is already taken by another");
        }
        this.actor = actor;
    }
}
