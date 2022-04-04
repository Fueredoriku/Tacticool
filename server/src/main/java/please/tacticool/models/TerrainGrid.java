package please.tacticool.models;

import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Player;

public class TerrainGrid {

    private Tile[] grid;
    private Coordinate dimensions;

    
    public TerrainGrid(int width, int depth) {
        dimensions = new Coordinate(width, depth);
        grid = new Tile[width*depth];
        
        populateGrid();
    }
    
    private void validateCoordinate(Coordinate coordinate) {
        if (!isValidCoordinate(coordinate)) {
            throw new IndexOutOfBoundsException("Tried to change tile out of bounds");
        }
    }

    private void populateGrid() {
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Tile();
        }
    }

    /**
     * Checks if coordinate is in bounds of grid.
     * 
     * @param coordinate to check
     * @return result
     */
    public boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < dimensions.getX() && coordinate.getY() >= 0 && coordinate.getY() < dimensions.getY();
    }
    
    public Tile getTile(Coordinate coordinate) {
        validateCoordinate(coordinate);
        return grid[coordinate.getX() + coordinate.getY() * dimensions.getY()];
    }
    
    public void setTile(Coordinate coordinate, Tile tile) {
        validateCoordinate(coordinate);
        grid[coordinate.getX() + coordinate.getY() * dimensions.getY()] = tile;
    }

    /**
     * Sets the actor of a tile.
     * 
     * @param coordinate    of tile to set actor.
     * @param actor         to set on tile
     */
    public void setActor(Coordinate coordinate, Actor actor) {
        validateCoordinate(coordinate);
        getTile(coordinate).setActor(actor);
    }

    /**
     * Moves actor to position by platerId.
     * 
     * @param playerId     of player to move.
     * @param newPosition  to move the actor to.
     * @return             the posotion where the actor ended in after trying to move it 
     */
    public Coordinate moveActor(int playerId, Coordinate newPosition) {
        for(int i = 0; i < grid.length; i++){
            Actor actor = grid[i].getActor();
            if(actor instanceof Player && ((Player) actor).getPlayerID() == playerId){
                return moveActor(actor.getPosition(), newPosition);
            }
        }
        throw new IllegalArgumentException("The specified playerID was not found in the grid");
    }

    /**
     * Moves an actor from one tile to another.
     * 
     * @param position      current position of an actor
     * @param newPosition   target position of selected actor
     * @return              the posotion where the actor ended in after trying to move it 
     */
    public Coordinate moveActor(Coordinate position, Coordinate newPosition) {
        if(newPosition.distance(position) > 1){
            return position; // Can't move further than one tile
        }
        validateCoordinate(position);
        validateCoordinate(newPosition);
        Actor actor = getTile(position).getActor();
        if (getTile(newPosition).getActor() != null || actor == null) {
            return position; // Could not be moved so the position remains the same
        }
        setActor(position, null);
        setActor(newPosition, actor);
        return newPosition;
    }

    /**
     * Moves a player to a new position
     * 
     * @param player        the player to move.
     * @param newPosition   target position of player.
     * @return              the posotion where the actor ended in after trying to move it   
     */
    public Coordinate moveActor(Player player, Coordinate newPosition) {
        return moveActor(player.getPosition(), newPosition);
    }

    public int getWidth(){
        return dimensions.getX();
    }
    public int getHeight(){
        return dimensions.getY();
    }
}
