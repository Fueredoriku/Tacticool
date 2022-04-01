package please.tacticool.models.Actors;

import please.tacticool.models.Action;
import please.tacticool.models.Coordinate;

public class Movement implements Action {

    private ActionType actionType = ActionType.MOVEMENT;
    private final int cost;
    private final int radius = 1;
    private final Coordinate from, to;

    public Movement(Coordinate from, Coordinate to){
        this.from = from;
        this.to = to;
        this.cost = getVector().getLength();
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
        return radius;
    }

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    public int getCost() {
        return cost;
    } // Throw in Terrain in some way so we can get cost per tile?

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    public int getPriority() {
        return 0;
    }

    /**
     * Return the vector of the movement from from to to (huh)
     * @return
     */
    public Coordinate getVector(){
        return to.plus(from.invert());
    }

    public Coordinate getFrom(){
        return from;
    }

    public Coordinate getTo(){
        return to;
    }
}
