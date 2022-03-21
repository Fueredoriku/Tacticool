package please.tacticool.models.Actors.Projectiles;

import please.tacticool.models.Coordinate;

public class Rocket extends Projectile {
    private final int damage;

    /**
     * Constructs a rocket with the given velocity, position and damage
     * @param position : initial position
     * @param velocity : initial velocity
     * @param damage : damage dealt when hitting a target
     */
    public Rocket(Coordinate position, Coordinate velocity, int damage) {
        super(position, velocity);
        this.damage = damage;
        // Does it need to be different from a bullet ?
    }

    /**
     * Getter for the amount of damage dealt by the rocket
     * @return : damage dealt
     */
    public int getDamage(){
        return damage;
    }
    
}
