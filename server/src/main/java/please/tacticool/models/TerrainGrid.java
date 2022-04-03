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
     * @return             true if the actor can be found and the new position is free (null), false otherwise.
     */
    public boolean moveActor(int playerId, Coordinate newPosition) {
        validateCoordinate(newPosition);
        if (getTile(newPosition).getActor() != null) {
            return false;
        }
        for (int i = 0; i < grid.length; i++) {
            Actor actor = grid[i].getActor();
            if (actor instanceof Player && ((Player) actor).getPlayerID() == playerId) {
                grid[i].setActor(null);
                setActor(newPosition, actor);
                return true;
            }
        }
        return false;
    }

    /**
     * Moves an actor from one tile to another.
     * 
     * @param position      current position of an actor
     * @param newPosition   target position of selected actor
     * @return              true if there is an actor on position and newPosition is free, false otherwise.
     */
    public boolean moveActor(Coordinate position, Coordinate newPosition) {
        validateCoordinate(position);
        validateCoordinate(newPosition);
        Actor actor = getTile(position).getActor();
        if (getTile(newPosition).getActor() != null || actor == null) {
            return false;
        }
        setActor(position, null);
        setActor(newPosition, actor);
        return true;
    }

    /**
     * Moves a player to a new position
     * 
     * @param player        the player to move.
     * @param newPosition   target position of player.
     * @return              true if the new position is free (null), false otherwise.  
     */
    public boolean moveActor(Player player, Coordinate newPosition) {
        return moveActor(player.getPosition(), newPosition);
    }

}
