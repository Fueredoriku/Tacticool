package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Actor;
import please.tacticool.models.Actors.Obstacle;
import please.tacticool.models.Actors.Player;

public class Rifle extends Weapon {

    public Rifle(int damage) {
        super(damage);
    }

    /**
     * Fires the rifle in a straight line, dealing damage to anything it hits.
     * 
     * @param position  where the shot is fired from
     * @param target    the target of the shot. Must be in a straight line from the position
     * @param grid      the grid where the shot takes place
     */
    @Override
    public void fire(Coordinate position, Coordinate target, TerrainGrid grid) {
        Coordinate direction = new Coordinate(target.getX() - position.getX(), target.getY() - position.getY());
        if ((direction.getX() == 0 && direction.getY() == 0) || (direction.getX() != 0 && direction.getY() != 0)) {
            throw new IllegalArgumentException("Must be in a straight line from the position. Position: " + position + "vs target" + target);
        }
        direction = direction.getX() != 0 ? new Coordinate(direction.getX() / Math.abs(direction.getX()), 0) : new Coordinate(0, direction.getY() / Math.abs(direction.getY()));

        Coordinate newTarget = position.add(direction);
        while (grid.isValidCoordinate(newTarget)) {
            Actor actor = grid.getTile(newTarget).getActor();
            if (actor != null) {
                actor.getHit(this.damage);
                break;
            }
            newTarget = newTarget.add(direction);
        }
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public int getTargetRadius() {
        return 1;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
