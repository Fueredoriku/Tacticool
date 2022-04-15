package please.tacticool.models.Actions.Weapons;

import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actions.Action;

public abstract class Weapon extends Action {

    protected final int damage;

    /**
     * Constructs a weapon dealing the specified amount of damage with the given ammo
     * capacity and load.
     * @param damage : damage dealt by the weapon
     */
    public Weapon(Coordinate playerPosition, List<Coordinate> path, int damage){
        super(playerPosition, path);
        if (getPath().size() != 1) {
            throw new IllegalArgumentException("Target is not correctly defined: " + getPath());
        }
        this.damage = damage;
    }

    public abstract List<Coordinate> fire(Coordinate position, Coordinate target, TerrainGrid grid);


    /**
     * Getter for the amount of damage the weapons inflicts
     * @return : the amount of damage inflicted
     */
    public int getDamage(){
        return damage;
    }

    @Override
    public List<Coordinate> execute(TerrainGrid grid) {
        return fire(getPlayerPosition(), getPath().get(0), grid);
    }
}
