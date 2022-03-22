package please.tacticool.models.Actors.Projectiles;

import please.tacticool.models.Coordinate;

public class Bullet extends Projectile {
    // Number of damage dealt when hitting a target
    private final int damage;

    /**
     * Constructs a bullet dealing damage upon hitting a target
     * @param position : initial position of the bullet
     * @param velocity : initial velocity of the bullet
     * @param damage : damage dealt by the bullet when hitting a target
     */
    public Bullet(Coordinate position, Coordinate velocity, int damage) {
        super(position, velocity);
        this.damage = damage;
    }

    /**
     * Getter for the amount of damage dealt by the bullet 
     * @return : amount of damage dealt
     */
    public int getDamage(){
        return damage;
    }
    
}
