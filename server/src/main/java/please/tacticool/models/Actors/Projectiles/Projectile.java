package please.tacticool.models.Actors.Projectiles;

import please.tacticool.models.Coordinate;
import please.tacticool.models.Updatable;
import please.tacticool.models.Actors.Actor;

public abstract class Projectile extends Actor implements Updatable{

    private Coordinate velocity;

    /**
     * Constructs a projectile with an initial velocity and position.
     * @param position : initial position
     * @param velocity : initial velocity
     */
    public Projectile(Coordinate position, Coordinate velocity){
        super(position);
        this.velocity = velocity;
    }

    public Coordinate getVelocity(){
        return velocity;
    }

    @Override
    public void update(float delta) {
        // TODO : update position based on velocity
        // can be overriden in the subclasses if needed
    }
    
}
