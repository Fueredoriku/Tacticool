package please.tacticool.models;

/**
 * Represents a coordinate position. This class is immutable
 */
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate plus(Coordinate that){
        return new Coordinate(this.x + that.x, this.y + that.y);
    }

    public Coordinate invert(){
        return new Coordinate(-x, -y);
    }

    /**
     * Get the length. Since you can only move from tile to tile, the length is equal to x + y
     * @return : the length of the vector
     */
    public int getLength(){
        return x + y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
