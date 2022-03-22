package please.tacticool.models;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.enums.Terrain;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Character;

public class TerrainGrid {

    private List<List<Tile>> grid;


    // Instantiates empty board of correct size
    public TerrainGrid(int width, int depth) {
        for (int x = 0; x < width; x++) {
            grid.add(new ArrayList<Tile>());
            for (int y = 0; y < depth; y++) {
                grid.get(x).add(null);
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

    public void movePlayer(int playerID, Coordinate targetLocation) {
        for (List<Tile> l : grid) {
            for (Tile tile : l) {
                Actor actor = tile.getActor();

                if (actor instanceof Character) {
                    if (((Character) actor).getPlayerID() == playerID) {
                        tile.setActor(null);
                        getTile(targetLocation).setActor(actor);
                    }
                }
            }
        }
    }

    public Tile getTile(Coordinate coordinate) {
        return grid.get(coordinate.getX()).get(coordinate.getY());
    }

}
