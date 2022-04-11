package please.tacticool.models.Actions;

import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;

public abstract class Action {

    protected final Player player;
    protected final List<Coordinate> path;
    private final int actionCost;

    public Action(Player player, List<Coordinate> path, int actionCost){
        this.player = player;
        this.path = path;
        this.actionCost = actionCost;
    }

    /**
     * Gets the cost of performing this action
     * @return : cost of action
     */
    public abstract int getCost();

    /**
     * Gets the radius of the tiles this action affects
     * @return : radius of action target
     */
    public abstract int getTargetRadius();

    /**
     * Gets the priority this action will take
     * @return : priority of action
     */
    public abstract int getPriority();

    public int getActionCost() {
        return actionCost;
    }

    public abstract List<Coordinate> execute(TerrainGrid grid);

    //public abstract List<Coordinate> execute(Coordinate position, List<Coordinate> path, TerrainGrid grid);
}
