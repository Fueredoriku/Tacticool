package please.tacticool.models.Actions;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Coordinate;


public class Action2 {
    private Coordinate coordinate;
    private ActionType actionType;


    public Action2(Coordinate coordinate, ActionType actionType) {
        this.coordinate = coordinate;
        this.actionType = actionType;
    }
}

