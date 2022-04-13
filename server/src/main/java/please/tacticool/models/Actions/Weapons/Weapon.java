package please.tacticool.models.Actions.Weapons;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actions.Action;
import please.tacticool.models.Actors.Player;

public abstract class Weapon extends Action {

    protected final int damage;

    /**
     * Constructs a weapon dealing the specified amount of damage with the given ammo
     * capacity and load.
     * @param damage : damage dealt by the weapon
     */
    public Weapon(Player player, List<Coordinate> path, int damage, int actionCost){
        super(player, path, actionCost);
        if (path.size() != 1) {
            throw new IllegalArgumentException("Target is not correctly defined: " + path);
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
    public void execute(TerrainGrid grid) {
        if (player.getActionPoints() >= getActionCost()) {
            player.useActionPoints(getActionCost());
            setAffectedCoordinates(fire(player.getPosition(), path.get(0), grid));
        } else {
            setAffectedCoordinates(new ArrayList<Coordinate>());
        }
    }
}
