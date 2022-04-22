package please.tacticool.models.Actions;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;


public abstract class Action2 {
    private Coordinate coordinate;
    private ActionType actionType;


    public Action2(Coordinate coordinate, ActionType actionType) {
        this.coordinate = coordinate;
        this.actionType = actionType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public abstract Action2 perform(Player player, TerrainGrid grid);
}

