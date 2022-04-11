package please.tacticool.models.Actions.Weapons;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Actor;

public class Bazooka extends Weapon{

    private int range = 3;
    private int radius = 3;

    public Bazooka(Coordinate playerPosition, List<Coordinate> path, int damage) {
        super(playerPosition, path, damage);
    }

    // Currently possible to shot one self.
    @Override
    public List<Coordinate> fire(Coordinate position, Coordinate target, TerrainGrid grid) {
        List<Coordinate> result = new ArrayList<Coordinate>();

        if (position.distance(target) > range) {
            return result;
        }
        for (int i = 0; i < radius * radius; i++) {
            Coordinate newTarget = target.add(new Coordinate(-1 + i % radius, -1 + i / radius));
            if (grid.isValidCoordinate(newTarget)) {
                result.add(newTarget);
                Actor actor = grid.getTile(newTarget).getActor();
                if (actor != null) {
                    actor.getHit(damage);
                }
            }
        }

        return result;
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
