package please.tacticool.models.Actions;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;
import please.tacticool.models.Actors.Player;

public class Movement extends Action {

    String actionType = "movement";
    int targetRadius = 1;
    int defaultCost = 1;

    public Movement(Coordinate playerPosition, List<Coordinate> path) {
        super(playerPosition, path);
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

    /**
     * Moves the affected Actor object
     */
    private List<Coordinate> move(TerrainGrid grid) {
        Player player = (Player) grid.getTile(getPlayerPosition()).getActor();

        List<Coordinate> npath = new ArrayList<Coordinate>();
        
        if (getPath().isEmpty()) {
            return npath;
        }

        for (Coordinate coordinate : getPath()) {
            if (!grid.isEmptyTile(coordinate)) {
                break;
            }
            npath.add(coordinate);
        }

        if (!npath.isEmpty()) {
            grid.moveActor(getPlayerPosition(), npath.get(npath.size() - 1));
            player.setPosition(npath.get(npath.size() - 1));
        }

        return npath;
    }


    @Override
    public List<Coordinate> execute(TerrainGrid grid) {
        return move(grid);
    }

}
