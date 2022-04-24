package please.tacticool.models;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.enums.Terrain;
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

    public TerrainGrid(String board, int width, int height) {
        dimensions = new Coordinate(width, height);
        grid = new Tile[width * height];

        Terrain[] values = Terrain.values();
        String[] brd = board.split(",");
        for (int i = 0; i < brd.length; i++) {
            grid[i] = new Tile(values[Integer.parseInt(brd[i])]);
        }
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

    public boolean isEmptyTile(Coordinate coordinate) {
        if (getTile(coordinate).isEmpty()) {
            return true;
        }
        return false;
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

    public Actor getActor(Coordinate coordinate) {
        return getTile(coordinate).getActor();
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

    public Coordinate getDimensions() {
        return dimensions;
    }

    public List<Coordinate> getFreeTiles() {
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < dimensions.getY(); i++) {
            for (int j = 0; j < dimensions.getX(); j++) {
                if (isEmptyTile(new Coordinate(j, i))) {result.add(new Coordinate(j, i));}
            }
        }
        return result;
    }

    public String toStringMap() {
        String s = "";
        for (Tile tile : grid) {
            s += String.format("%d,", tile.getTerrain().getId());
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < dimensions.getY(); i++) {
            for (int j = 0; j < dimensions.getX(); j++) {
                result += String.format(" %s", getTile(new Coordinate(j, i)));
            }
            result += "\n";
        }

        return result;
    }

}
