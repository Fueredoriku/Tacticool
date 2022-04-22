package please.tacticool.util;

import com.google.gson.*;
import please.tacticool.enums.ActionType;
import please.tacticool.models.Actions.*;
import please.tacticool.models.Coordinate;

public class JsonConvert{

    public static Action convertToAction(JsonObject jo) {
        Gson gson = new Gson();
        Coordinate co = gson.fromJson(jo.get("coordinate"), Coordinate.class);
        return switch (ActionType.valueOf(jo.get("actionType").getAsString())) {
            case MOVE -> new Move(co);
            case RIFLE -> new Rifle(co);
            case BAZOOKA -> new Bazooka(co);
        };
    }
}
