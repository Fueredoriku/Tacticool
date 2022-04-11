package please.tacticool.models.Actions;

import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

public abstract class Action {

    private final Coordinate playerPosition;
    private final List<Coordinate> path;

    public Action(Coordinate playerPosition, List<Coordinate> path){
        this.playerPosition = playerPosition;
        this.path = path;
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

    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    public abstract List<Coordinate> execute(TerrainGrid grid);

    //public abstract List<Coordinate> execute(Coordinate position, List<Coordinate> path, TerrainGrid grid);
}
