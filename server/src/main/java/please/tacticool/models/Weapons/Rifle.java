package please.tacticool.models.Weapons;

import please.tacticool.models.Coordinate;
import please.tacticool.models.Actors.Projectiles.Bullet;
import please.tacticool.models.Actors.Projectiles.Projectile;

public class Rifle extends Weapon {

    public Rifle(int damage, int maxAmmo, int ammunitions) {
        super(damage, maxAmmo, ammunitions);
    }

    @Override
    public Projectile fire(Coordinate position, Coordinate direction){
        return new Bullet(position, direction, damage);
    }

    @Override
    public int getCost() {

        return 0;
    }

    @Override
    public int getTargetRadius() {

        return 1;
    }

    @Override
    public int getPriority() {

        return 0;
    }
}
