package please.tacticool.models.Actions;

import java.util.ArrayList;
import java.util.List;

import please.tacticool.enums.ActionType;
import please.tacticool.models.Coordinate;

public class Actions {

    //Placeholder
    private List<Action2> actions;

    public Actions() {
        actions = new ArrayList<>();
    }

    public void addAction(Action2 action) {
        actions.add(action);
    }

    public void perform() {
        
    }
    
    public static void main(String[] args) {

    }
}
