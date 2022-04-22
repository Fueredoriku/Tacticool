package please.tacticool.models.Actions;

import please.tacticool.models.Coordinate;

public class Move extends Action2{
    public Move(Coordinate coordinate){
        super(coordinate);
    }

    @Override
    public void perform() {
        System.out.println("Moviing mother trucker");
    }
}
