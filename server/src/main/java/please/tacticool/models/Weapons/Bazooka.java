package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Player;

public class Bazooka extends Weapon{

    private int range = 3;
    private int radius = 3;

    public Bazooka(int damage) {
        super(damage);
    }

    // Currently possible to shot one self.
    @Override
    public void fire(Coordinate position, Coordinate target, TerrainGrid grid) {
        if (position.distance(target) > range) {
            return;
        }
        for (int i = 0; i < radius * radius; i++) {
            Coordinate newTarget = target.add(new Coordinate(-1 + i % radius, -1 + i / radius));
            if (grid.isValidCoordinate(newTarget)) {
                Actor actor = grid.getTile(newTarget).getActor();
                if (actor != null) {
                    actor.getHit(damage);
                }
            }
        }
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getTargetRadius() {
        return radius;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
