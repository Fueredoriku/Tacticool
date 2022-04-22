package please.tacticool.models.Actions;

import please.tacticool.models.Coordinate;

public class Tesst extends Action2{
    public Tesst(Coordinate coordinate){
        super(coordinate);
    }

    @Override
    public void perform() {
        System.out.println("Slippery snek");
    }
}
