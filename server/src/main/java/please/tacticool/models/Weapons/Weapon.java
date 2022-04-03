package please.tacticool.models.Weapons;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public abstract class Weapon implements Action {

    protected final int damage;

    protected static final ActionType actionType = ActionType.ATTACK;

    /**
     * Constructs a weapon dealing the specified amount of damage with the given ammo
     * capacity and load.
     * @param damage : damage dealt by the weapon
     */
    public Weapon(int damage){
        this.damage = damage;
    }

    public abstract void fire(Coordinate position, Coordinate target, TerrainGrid grid);


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
