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
    public default String getType() {
        return actionType;
    }

    /**
     * Gets the radius of the tiles this action affects
     * @return : radius of action target
     */
    public default int getTargetRadius() {
        return targetRadius;
    }

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    public default int getCost() {
        return defaultCost;
    }

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    public default int getPriority() {
        return 0;
    }

    /**
     * Moves the affected Actor object
     */
    public void move(Coordinate destination); //TODO: implement actual movement
}
