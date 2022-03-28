package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.Actors.Projectiles.Projectile;
import please.tacticool.models.Actors.Projectiles.Rocket;

public class Bazooka extends Weapon{

    public Bazooka(int damage, int maxAmmo, int ammunitions) {
        super(damage, maxAmmo, ammunitions);
    }

    @Override
    public Projectile fire(Coordinate position, Coordinate direction) {
        return new Rocket(position, direction, damage);
    }

    @Override
    public int getCost() {

        return 0;
    }

    @Override
    public int getTargetRadius() {

        return 2;
    }

    @Override
    public int getPriority() {

        return 0;
    }
}
