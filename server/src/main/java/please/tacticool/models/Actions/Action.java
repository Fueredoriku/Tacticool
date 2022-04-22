package please.tacticool.models.Actions;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;


public abstract class Action {
    private Coordinate coordinate;
    private ActionType actionType;


    public Action(Coordinate coordinate, ActionType actionType) throws IllegalArgumentException {
        // if (coordinate.distance(new Coordinate(0,0)) != 1) {
        //     throw new IllegalArgumentException("Invalid coordinate direction!");
        // }
        this.coordinate = coordinate;
        this.actionType = actionType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public abstract Action perform(Player player, TerrainGrid grid);
}

