package please.tacticool.models.Actors;

import please.tacticool.models.Coordinate;

public class Obstacle extends Actor {

    private boolean destroyable;

    public Obstacle(Coordinate position, boolean destroyable) {
        super(position);
        this.destroyable = destroyable;
    }

    public boolean getDestroyable() {
        return destroyable;
    }
}
