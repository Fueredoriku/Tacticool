package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import please.tacticool.enums.Terrain;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Movement;

public class TerrainGrid {

    private final List<List<Tile>> grid;
    private final int width, height;
    private final Map<Actor, Coordinate> actorsPos;


    // Instantiates empty board of correct size
    public TerrainGrid(int width, int height) {
        this.height = height;
        this.width = width;
        grid = new ArrayList<>();
        for (int x = 0; x < height; x++) {
            grid.add(new ArrayList<Tile>());
            for (int y = 0; y < height; y++) {
                grid.get(x).add(new Tile());
            }
        }
        actorsPos = new HashMap<>();
    }

    public void changeTile(Coordinate coordinate, Tile newTile) {
        grid.get(coordinate.getX()).set(coordinate.getY(), newTile);
    }

    // Populates an empty board with tiles based on a set of terrains
    public void populateStartingTerrain(Terrain[] terrains) {
        try {
            int i = 0;
            for (int y = 0; y < grid.get(0).size(); y++) {
                for (int x = 0; x < grid.size(); x++) {
                    grid.get(x).set(y, new Tile(null, terrains[i]));
                    i++;
                }
            }
        } catch (Exception e) { }
    }

    /**
     * Move an actor from its tile to the given tile. If the actor is not already
     * in the grid it is added at the specified position. Also makes sure to change
     * the position in the provided actor's class (i.e. calls setPosition(...))
     * @param actor : actor to move
     * @param pos : position of the actor after the move
     * @return : if the move could be done or not
     */
    public boolean move(Actor actor, Coordinate pos){
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

    public Tile getTile(Coordinate coordinate) {
        return grid.get(coordinate.getX()).get(coordinate.getY());
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}
