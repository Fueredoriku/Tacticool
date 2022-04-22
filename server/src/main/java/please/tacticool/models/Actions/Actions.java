package please.tacticool.models.Actions;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Actors.Player;
import please.tacticool.models.Coordinate;
import please.tacticool.models.TerrainGrid;

/**
 * Contains List<Action2>
 */
public class Actions {

    //Placeholder
    private List<Action2> actions;

    public Actions() {
        actions = new ArrayList<>();
    }

    public void addAction(Action2 action) {
        actions.add(action);
    }

    public void perform(Player player, TerrainGrid grid) {
        List<Action2> actual = new ArrayList<>();
        for(Action2 a : actions) {
            Action2 b = a.perform(player, grid);
            if(b != null){
                actual.add(b);
            }
        }
    }
}
