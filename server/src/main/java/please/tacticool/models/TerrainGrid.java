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
     * Updates the actor of a tile on the specified coordinate.
     * 
     * @param coordinate    of the tile to update
     * @param actor         to put on the selected tile
     */
    public void updateActor(Coordinate coordinate, Actor actor) {
        validateCoordinate(coordinate);
        getTile(coordinate).setActor(actor);
    }

    /**
     * Moves a player to a new tile on the grid by player id.
     * 
     * @param playerId  the id of the player to move.
     * @param position  the new position the player should be in.
     */
    public void movePlayer(int playerId, Coordinate position) {
        for (int i = 0; i < grid.length; i++) {
            Actor actor = grid[i].getActor();
            if (actor instanceof Player && ((Player) actor).getPlayerID() == playerId) {
                grid[i].setActor(null);
                updateActor(position, actor);
                break;
            }
        }
    }

    /**
     * Moves player on give position to a new tile on the grid.
     * 
     * @param position  where the player currently is
     * @param newPosition  the new position the player should be in
     */
    public void movePlayerBy(Coordinate position, Coordinate newPosition) {
        Player player = (Player) getTile(position).getActor();
        updateActor(position, null);
        updateActor(newPosition, player);
    }
}
