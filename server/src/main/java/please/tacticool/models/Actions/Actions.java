package please.tacticool.models.Actions;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.models.Actors.Player;
import please.tacticool.models.TerrainGrid;

/**
 * Contains List<Action2>
 */
public class Actions {

    //Placeholder
    private List<Action> actions;

    public Actions() {
        actions = new ArrayList<>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public Actions perform(Player player, TerrainGrid grid) {
        Actions actual = new Actions();
        for(Action a : actions) {
            Action b = a.perform(player, grid);
            if(b != null){
                actual.addAction(b);
            }
        }
        return actual;
    }
}
