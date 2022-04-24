package httpRequests;

import java.util.List;

import com.anything.tacticool.model.InputAction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Serializer {
    public String serializeActions(int playerID, List<InputAction> actions) {
        JsonObject obj = new JsonObject();
        JsonArray jsonActions = new JsonArray();
        for (InputAction action : actions) {
            JsonObject jsonAction = new JsonObject();
            JsonObject coordinate = new JsonObject();
            coordinate.addProperty("x", action.getTargetX());
            coordinate.addProperty("y", action.getTargetY());
            jsonAction.add("coordinate", coordinate);
            jsonAction.addProperty("actionType", action.getActionType().toString());
            jsonActions.add(jsonAction);
        }
        obj.add("actions", jsonActions);
        return obj.toString();
    }
}
