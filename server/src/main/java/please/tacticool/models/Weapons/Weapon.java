package please.tacticool.models.Weapons;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;
import please.tacticool.models.Actors.Projectiles.Projectile;

public abstract class Weapon implements Action {

    protected int ammunitions;
    protected final int maxAmmo;
    protected final int damage;

    protected static final ActionType actionType = ActionType.ATTACK;

    /**
     * Constructs a weapon dealing the specified amount of damage with the given ammo
     * capacity and load
     * @param damage : damage dealt by the weapon
     * @param maxAmmo : maximum amount of ammo in the weapon
     * @param ammunitions : current count of ammo in the weapon
     */
    public Weapon(int damage, int maxAmmo, int ammunitions){
        this.maxAmmo = maxAmmo;
        if(ammunitions > maxAmmo){
            throw new IllegalArgumentException("Can't have more ammo than the max");
        }
        this.ammunitions = ammunitions;
        this.damage = damage;
    }

    public abstract Projectile fire(Coordinate position, Coordinate direction);

    /**
     * Getter for the max amount of ammo in the weapon
     * @return : the max ammo possible in the weapon
     */
    public int getMaxAmmunitions(){
        return maxAmmo;
    }

    /**
     * Getter for the amount of ammunitions remaining in the weapon
     * @return : ammunitions left in the weapon
     */
    public int getAmmunitions(){
        return ammunitions;
    }

    /**
     * Getter for the amount of damage the weapons inflicts
     * @return : the amount of damage inflicted
     */
    public int getDamage(){
        return damage;
    }


    /**
     * Getter for the action type
     * @return : type of action
     */
    public ActionType getType() {
        return actionType;
    }
}
