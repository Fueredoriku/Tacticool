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

    public Coordinate add(Coordinate that){
        return new Coordinate(this.x + that.x, this.y + that.y);
    }

    public Coordinate invert(){
        return new Coordinate(-x, -y);
    }

    // Since you can't move from in diagonale, length of a vector is x + y
    public int length(){
        return x + y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
