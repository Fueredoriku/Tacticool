package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import please.tacticool.models.Actors.Actor;

public class Playfield {
    private final int width;
    private final int height;
    private final List<List<Tile>> grid;
    private final Map<Actor, Coordinate> actorsPos;

    public Playfield(int width, int height){
        if(width < 0 || height < 0){
            throw new IllegalArgumentException("Can't have negative width and height");
        }
        this.width = width;
        this.height = height;
        // Initialise the grid
        this.grid = new ArrayList<>(height);
        for(int i = 0; i < height; i++){
            grid.add(new ArrayList<>(width));
        }
        actorsPos = new HashMap<>();
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Move an actor from its tile to the given tile. If the actor is not already
     * in the grid it is added at the specified position. Also makes sure to change
     * the position in the provided actor's class (i.e. calls setPosition(..))
     * @param actor : actor to move
     * @param pos : position of the actor after the move
     */
    public void move(Actor actor, Coordinate pos){
        // Put the actor in the grid if it is not already in
        if(!actorsPos.containsKey(actor)){
           put(actor, pos);
        }
        Tile from =  grid.get(actor.getPosition().getX()).get(actor.getPosition().getY());
        if(!from.getActor().equals(actor)){
            throw new IllegalStateException("There's a bug... an Actor is not in the tile it is supposed to ...");
        }
        from.removeActor();
        actorsPos.remove(actor);
        put(actor, pos);  
    }

    /**
     * Add an actor to the grid. If the actor is already in the grid, it is moved
     * @param actor : actor to add
     * @param pos : position of the actor in the grid
     */
    public void put(Actor actor, Coordinate pos){
        // If the actor is already in the grid, move it instead
        if(actorsPos.containsKey(actor)){
            move(actor, pos);
        }
        Tile tile = grid.get(pos.getX()).get(pos.getY());
        tile.setActor(actor);
        actorsPos.put(actor, pos);
        actor.setPosition(pos);
    }

    // SHOULD BE PROTECTED 
    public List<List<Tile>> getGrid(){
        return  grid;
    }
}
