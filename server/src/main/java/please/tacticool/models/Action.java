package please.tacticool.models;

public interface Action {

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    public int getCost();

    /**
     * Gets the type of action this is
     * @return : type of action
     */
    public ActionType getType();

    /**
     * Gets the radius of the tiles this action affects
     * @return : radius of action target
     */
    public int getTargetRadius();

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    public int getPriority();

    enum ActionType {
        MOVEMENT, ATTACK;
    }
}
