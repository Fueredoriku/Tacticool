package please.tacticool.models;

import java.util.ArrayList;
import java.util.List;

public class Playfield {
    private final int width;
    private final int height;
    private final List<List<Tile>> grid;

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
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public List<List<Tile>> getGrid(){
        return grid;
    }
}
