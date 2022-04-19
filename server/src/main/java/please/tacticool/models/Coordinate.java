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

    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public Coordinate(String coorString) {
        String[] coordinates = coorString.split(",");
        if (coordinates.length != 2) {
            throw new IllegalArgumentException("Only 2d coords accepted.");
        }
        this.x = Integer.parseInt(coordinates[0]);
        this.y = Integer.parseInt(coordinates[1]);
    }

    public Coordinate add(Coordinate that){
        return new Coordinate(this.x + that.x, this.y + that.y);
    }

    public int distance(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - this.getX()) + Math.abs(coordinate.getY() - this.getY());
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", this.x, this.y);
    }
}
