package please.tacticool.models.Actors;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;

public class Movement implements Action {

    private final ActionType actionType = ActionType.MOVEMENT;
    private final int targetRadius = 1;
    private final int defaultCost = 1;
    private final Coordinate target;

    public Movement(Coordinate to){
        this.target = to;
    }

    /**
     * Gets the type of action this is
     * @return : type of action
     */
    public ActionType getType() {
        return actionType;
    }

    /**
     * Gets the radius of the tiles this action affects
     * @return : radius of action target
     */
    public int getTargetRadius() {
        return targetRadius;
    }

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    public int getCost() {
        return defaultCost;
    } // Throw in Terrain in some way so we can get cost per tile?

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    public int getPriority() {
        return 0;
    }

    public Coordinate getTarget(){
        return target;
    }
}
