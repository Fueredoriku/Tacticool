package please.tacticool.models;

import java.util.ArrayList;
import java.util.List;

public class TerrainGrid {

    private List<List<Tile>> grid;


    // Instantiates empty board of correct size
    public TerrainGrid(int width, int depth) {
        for (int x = 0; x < width; x++) {
            grid.add(new ArrayList<Tile>());
            for (int y = 0; y < depth; y++) {
                grid.get(x).add(null); // In the future, this should instead create a custom tile
            }
        }
    }

    public void changeTile(int x, int y, Tile newTile) {
        grid.get(x).set(y, newTile);
    }

    public void populateStart(Object[] args){
        // do something
    }

    public Tile getTile(int x, int y) {
        return grid.get(x).get(y);
    }


}
