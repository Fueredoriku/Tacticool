package please.tacticool.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import please.tacticool.enums.Terrain;
import please.tacticool.models.Actors.Actor;

public class TerrainGrid {

    private final List<List<Tile>> grid;
    private final int width, height;


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
     * Move an actor from its tile to the given tile. A movement can't be greater than
     * one tile, if it is greater then the movement is not executed. 
     * If anything blocks the target tile the move is also canceled.
     * @param actor : actor to move
     * @param pos : position of the actor after the move
     * @return : the tile the actor ended up in
     */
    public Coordinate move(Actor actor, Coordinate target){
        if(actor.getPosition().add(target).length() > 1){
            // Can't move the actor is not moved
            return actor.getPosition();
        }

        Tile from =  getTile(actor.getPosition());
        Tile to = getTile(target);

        if(!from.getActor().equals(actor)){
            throw new IllegalStateException("There's a bug... an Actor is not in the tile it is supposed to ...");
        }
        if(to.hasActor()){
            // Tile is occupied so the actor is not moved
            return actor.getPosition();
        }

        // remove the actor from its current tile and place it to the new one and change the actor's position
        from.actorExits();
        to.setActor(actor);
        actor.setPosition(target);
        return target;
    }

    /**
     * Add an actor to the grid at the specified position if possible
     * @param actor : actor to add
     * @param pos : position of the actor in the grid
     * @return : true if the actor could be placed or false if the tile was occupied and 
     *           nothing is added in the grid
     */
    public boolean put(Actor actor, Coordinate pos){
        if(getTile(pos).hasActor()){
            return false;
        }
        getTile(pos).setActor(actor);
        actor.setPosition(pos);
        return true;
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
