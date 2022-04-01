package please.tacticool.models.Weapons;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;

public class Attack implements Action {

    private final AttackType attackType;
    private final Coordinate direction;

    public Attack(AttackType attackType, Coordinate direction){
        this.attackType = attackType;
        this.direction = direction;
    }

    @Override
    public int getCost() {
        return attackType.cost;
    }

    @Override
    public ActionType getType() {
        return ActionType.ATTACK;
    }

    @Override
    public int getTargetRadius() {
        return attackType.targetRadius;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public Coordinate getDirection(){
        return direction;
    }

    enum AttackType {
        Bazooka(10, 5, 5), // Values are made up for now 
        Rifle(5, 2, 1);    // Values are made up for now

        private final int damage;
        private final int cost;
        private final int targetRadius;

        private AttackType(int damage, int cost, int targetRadius){
            this.damage = damage;
            this.cost = cost;
            this.targetRadius = targetRadius;
        }

        public int getDamage(){
            return damage;
        }

        public int getCost(){
            return cost;
        }

        public int getTargetRadius(){
            return targetRadius;
        }
    }
}
