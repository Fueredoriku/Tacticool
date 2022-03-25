package please.tacticool.models.Actors;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;

public interface Movement extends Action {

    String actionType = "movement";
    int targetRadius = 1;
    int defaultCost = 1;

    /**
     * Gets the type of action this is
     * @return : type of action
     */
    default String getType() {
        return actionType;
    }

    /**
     * Gets the radius of the tiles this action affects
     * @return : radius of action target
     */
    default int getTargetRadius() {
        return targetRadius;
    }

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    default int getCost() {
        return defaultCost;
    } // Throw in Terrain in some way so we can get cost per tile?

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    default int getPriority() {
        return 0;
    }

    /**
     * Moves the affected Actor object
     */
    void move(Coordinate destination); //TODO: implement actual movement
}
