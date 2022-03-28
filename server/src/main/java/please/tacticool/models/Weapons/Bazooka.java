package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public class Bazooka extends Weapon{

    public Bazooka(int damage) {
        super(damage);
    }

    @Override
    public void fire(Coordinate position, Coordinate target, TerrainGrid grid) {

    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getTargetRadius() {
        return 2;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
