package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Actor;

public class Rifle extends Weapon {

    public Rifle(int damage) {
        super(damage);
    }

    /**
     * Fires the rifle in a straight line, dealing damage to anything it hits.
     * 
     * @param position  where the shot is fired from
     * @param direction the direction of the shot. [0, 1], [0, -1], [1, 0] or [-1, 0] are the valid directions
     * @param grid      the grid where the shot takes place
     */
    @Override
    public void fire(Coordinate position, Coordinate direction, TerrainGrid grid) {
        if (Math.abs(direction.getX()) + Math.abs(direction.getY()) != 1) {
            throw new IllegalArgumentException("Direction can only be [0, 1], [0, -1], [1, 0] or [-1, 0], not: " + direction);
        }
        
        Coordinate target = position.add(direction);
        while (grid.isValidCoordinate(position.add(target))) {
            Actor actor = grid.getTile(position.add(target)).getActor();
            if (actor != null) {
                actor.getHit(this.damage);
                break;
            }
            target = target.add(direction);
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
